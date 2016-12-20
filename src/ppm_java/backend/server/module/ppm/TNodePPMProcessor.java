/* -----------------------------------------------------------------------------
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
----------------------------------------------------------------------------- */

package ppm_java.backend.server.module.ppm;

import java.nio.FloatBuffer;

import ppm_java._aux.logging.TLogger;
import ppm_java._aux.storage.TAtomicBuffer.ECopyPolicy;
import ppm_java._aux.typelib.IEvented;
import ppm_java._aux.typelib.VAudioProcessor;
import ppm_java.backend.jackd.TAudioContext_JackD;
import ppm_java.backend.server.TController;

/**
 * PPM meter class. Updates all associated front ends with one 
 * level value per cycle, emulating PPM ballistics.
 * 
 * @author peter
 */
public class TNodePPMProcessor 
    extends         VAudioProcessor
    implements      IEvented
{
    private static final long       gkIntegrTimeRise    = 10;           /* [110] */
    private static final long       gkIntegrTimeFall    = 2800;         /* [110] */
    private static final double     gkMinThreshold      = 3.16E-08f;    /* [120] */
    
    public static void CreateInstance (String id)
    {
        new TNodePPMProcessor (id, 1, 1);
    }

    private long                                fTLast;
    private long                                fNSamplesPerCycle;
    private int                                 fSampleRate;
    private FloatBuffer                         fInPacket;
    private float                               fPeak;
    private boolean                             fHasInitialTime;
    
    /**
     * @param id
     * @param nMaxChanIn
     * @param nMaxChanOut
     */
    private TNodePPMProcessor (String id, int nMaxChanIn, int nMaxChanOut)
    {
        super (id, nMaxChanIn, nMaxChanOut);

        TAudioContext_JackD         drv;

        fInPacket           = null;
        fPeak               = 0;
        fTLast              = 0;                                        /* [140] */
        drv                 = TController.GetAudioDriver ();
        fSampleRate         = drv.GetSampleRate ();
        fHasInitialTime     = false;                                    /* [140] */
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VAudioProcessor#CreatePort_In(java.lang.String)
     */
    @Override
    public void CreatePort_In (String id)
    {
        int                                     iPort;
        TNodePPMProcessor_Endpoint_In           p;
        
        iPort   = GetNumPortsIn ();
        p       = new TNodePPMProcessor_Endpoint_In (id, this, iPort, ECopyPolicy.kCopyOnGet);
        AddPortIn (p);
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VAudioProcessor#CreatePort_Out(java.lang.String)
     */
    @Override
    public void CreatePort_Out (String id)
    {
        TNodePPMProcessor_Endpoint_Out p;

        TLogger.LogMessage ("Creating output port '" + id + "'", this, "CreatePort_Out ('" + id + "')");
        p = new TNodePPMProcessor_Endpoint_Out (id, this);
        AddPortOut (p);
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IEvented#OnEvent(int)
     */
    @Override
    public void OnEvent (int e)
    {
        /* Incoming timer event to update GUI */
        if (e == gkEventTimer)
        {
            if (fHasInitialTime)
            {
                _ProcessChunk ();
            }
            else
            {
                fTLast          = System.currentTimeMillis ();          /* [140] */
                fHasInitialTime = true;
            }
        }
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IEvented#OnEvent(int, int)
     */
    @Override
    public void OnEvent (int e, int arg0)
    {
        // Do nothing
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IEvented#OnEvent(int, java.lang.String)
     */
    @Override
    public void OnEvent (int e, String arg0)
    {
        // Do nothing
    }
    
    /**
     * The central computation piece of the PPM level meter. Sends one level update
     * per cycle to the attached meter frontend. Output is one single floating 
     * point value per cycle. Level value is in dB.<br/>
     * 
     * The method performs the necessary ballistics of a PPM meter:<br/>
     */
    private void _ProcessChunk ()
    {
        TNodePPMProcessor_Endpoint_In       in;
        TNodePPMProcessor_Endpoint_Out      out;
        FloatBuffer                         packetNew;
        long                                nowT;
        long                                dT;
        double                              dY;
        double                              p;
        double                              pProj;
        double                              pdB;
        float                               pSend;
        long                                nSamples;
        int                                 nRem;
        
        nowT                = System.currentTimeMillis ();
        dT                  = nowT - fTLast;
        fNSamplesPerCycle   = dT * fSampleRate / 1000;
        
        /* Analyze next chunk of data. */
        in          = (TNodePPMProcessor_Endpoint_In) GetPortIn (0);
        packetNew   = in.FetchPacket ();
        if (packetNew != null)
        {
            /* We have fresh data. Do analysis with that one. */
            fInPacket  = packetNew;
            nSamples   = fInPacket.limit ();
            if (nSamples > fNSamplesPerCycle)
            {
                nSamples = fNSamplesPerCycle;
            }
            p = _FindPeak (fInPacket, nSamples);
        }
        else if (fInPacket != null)
        {
            /* No fresh data available, but the previous packet still has unresolved data */
            p = _FindPeak (fInPacket, fNSamplesPerCycle);
            nRem = fInPacket.remaining ();
            if (nRem <= 0)
            {
                fInPacket = null;
            }
        }
        else
        {
            /* No data available for analysis. Just rebroadcast previous peak value. */
            p = fPeak;
        }
        
        /* Integration part - compute new peak value with given PPM ballistics. */
        nowT    = System.currentTimeMillis ();
        dT      = nowT - fTLast;
        dY      = p - fPeak;
        fTLast  = dT;
        
        /* Interpolate next meter point. We use a simple linear interpolation. */
        if (dY > 0)
        {
            /* Value is rising. Use value-rise ballistics.  */
            pProj = p + gkIntegrTimeRise * dY / dT;
            /* Limiter */
            if (pProj > p)
            {
                pProj = p;
            }
        }
        else if (dY < 0)
        {
            /* Value is falling. Use value-fall ballistics. */
            pProj = p + gkIntegrTimeFall * dY / dT;
            /* Limiter */
            if (pProj < p)
            {
                pProj = p;
            }
        }
        else
        {
            /* Same value as before */
            pProj = p;
        }
        
        
        /* Convert approximated peak value to dB */                     /* [120] */
        if (pProj <= gkMinThreshold)
        {
            pdB = -130; 
        }
        else
        {
            pdB = 20 * Math.log10 (pProj);
        }
        
        /* Send peak value */
        pSend = (float) pdB;                                            /* [130] */
        out   = (TNodePPMProcessor_Endpoint_Out) GetPortOut (0);
        out.PushSample (pSend);
    }
    
    private double _FindPeak (FloatBuffer b, long forNSamples)
    {
        boolean doContinue;
        long    nDone;
        long    nRem;
        double  x;
        double  ret;
        
        ret         = 0;
        nDone       = 0;
        nRem        = b.remaining ();
        doContinue  = (nRem > 0);
        while (doContinue)
        {
            x = b.get ();
            
            if (x < 0)
            {
                x = -x;
            }
            
            if (x > ret)
            {
                ret = x;
            }
            
            nDone++;
            nRem            = b.remaining ();
            doContinue      = ((nRem > 0)  &&  (nDone < fNSamplesPerCycle));
        }
        
        return ret;
    }
}

/* 
[100]   Set arbitrarily: Highest frame rate: 20 frames / second, lowest frame rate: 5 frames / second.
[110]   PPM type II ballistics:
        Rise: -24dB -> - 2dB:   10  ms  ~  0.455 ms/dB
        Fall:   0dB -> -24dB: 2800  ms  ~116.667 ms/dB
        After harmonizing rise/fall times:
        Rise: -24dB -> - 0dB:   10.91  ms   ~11 ms
        Fall:   0dB -> -24dB: 2800     ms
[120]   For each sample, JJack delivers a floating point value
        in the range [-1.0, 1.0] floating point units.
        For each frame, we map all negative values to the 
        positive range, effectively rectifying the signal.          
        The positive maximum value in each frame becomes the 
        peak value.

        Range (peak value, fpU):                floating point units, from JJack provided values
            [0.0, 1.0]

        Generic formula: ratio (vdB): Signal Voltage (v) against reference voltage (vr):
        
            vdB = 20 * log (v / vr)
            
            Let vr = 1.0, then
                vdB = 20 * log (v)
        
        Our mapping fpU -> dB:
            fpU = [0.0, 10^-7.5]:  -130
                  ]10^-7.5,   1]:     0

        Note that 
            -130dB: Hearing threshold
        
            which computes to a voltage of:
            10 ^ (-130/20) = 10^-7.5 = 3.16 * 10^-8
            
        Calculating peak values in dB:
            for p: [0, 3.16E-08]
                pdB = -130
            for p: ]3.16E-08, 1.0]
                pdB = 20 * log (p)
[130]   There are situations where downcasting from double to float 
        is dangerous. Here it's fairly safe:
        * We deal with dB values, i.e. the really interesting part 
          is to the left of the decimal point. To the human ear, 
          -60dB isn't noticably different from -60.2dB. 
        * Round down errors (double -> float) happen in a dimension 
          somewhere below 1E-30 which makes no difference when 
          displayed on a meter display.
        * We produce a use-once value, i.e. we won't use this value 
          as a base for the next peak calculations. Therefore we won't
          have accumulating errors.
[140]   For time measurement. We will use the first cycle after starting 
        to just set an initial time point and otherwise do no data processing.
        Otherwise, the first cycle could provide non-sensical values, maybe 
        even leading to an exception.
*/