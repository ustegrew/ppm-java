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
import java.util.concurrent.atomic.AtomicLong;

import ppm_java._aux.logging.TLogger;
import ppm_java._aux.storage.TAtomicBuffer.ECopyPolicy;
import ppm_java._aux.storage.TAtomicDouble;
import ppm_java._aux.storage.TStats_TAtomicBuffer;
import ppm_java._aux.storage.TStats_TAtomicBuffer.TRecord;
import ppm_java._aux.typelib.IEvented;
import ppm_java._aux.typelib.IStatEnabled;
import ppm_java._aux.typelib.IStats;
import ppm_java._aux.typelib.VAudioProcessor;
import ppm_java.backend.jackd.TAudioContext_JackD;
import ppm_java.backend.server.TController;
import ppm_java.backend.server.module.timer.TTimer;

/**
 * PPM meter class. Updates all associated front ends with one 
 * level value per cycle, emulating PPM ballistics.
 * 
 * @author peter
 */
public class TNodePPMProcessor 
    extends         VAudioProcessor
    implements      IEvented, IStatEnabled
{
    public static final class TStats_TNodePPMProcessor implements IStats
    {
        private TNodePPMProcessor       fHost;
        private TAtomicDouble           fLastDBValue;
        private TAtomicDouble           fLastPeakValue;
        private TAtomicDouble           fLastPeakProjectedValue;
        private AtomicLong              fNumSamplesPerCycle;
        private AtomicLong              fSampleRate;
        private AtomicLong              fTimeCycle;
        
        public TStats_TNodePPMProcessor (TNodePPMProcessor host)
        {
            fHost                   = host;
            fTimeCycle              = new AtomicLong (0);
            fSampleRate             = new AtomicLong (0);
            fNumSamplesPerCycle     = new AtomicLong (0);
            fLastDBValue            = new TAtomicDouble ();
            fLastPeakValue          = new TAtomicDouble ();
            fLastPeakProjectedValue = new TAtomicDouble ();
        }
        
        /* (non-Javadoc)
         * @see ppm_java._aux.typelib.IStats#GetDumpStr()
         */
        @Override
        public String GetDumpStr ()
        {
            String ret;
            
            ret = "ppmProc [" + fHost.GetID () + "]:\n"        +
                  "    last_peak            = " + fLastPeakValue.Get ()             + "\n" +
                  "    last_peak_projected  = " + fLastPeakProjectedValue.Get ()    + "\n" +
                  "    peak [dB]            = " + fLastDBValue.Get ()               + "\n" +
                  "    cycleTime [ms]       = " + fTimeCycle.getAndAdd (0)          + "\n" +
                  "    sampleRate [smp/sec] = " + fSampleRate.getAndAdd (0)         + "\n" +
                  "    samplesPerCycle      = " + fNumSamplesPerCycle.getAndAdd (0) + "\n";
                  
            return ret;
        }
        
        public void SetCycleTime (long ct)
        {
            fTimeCycle.getAndSet (ct);
        }
        
        public void SetDBValue (double dB)
        {
            fLastDBValue.Set (dB);
        }
        
        public void SetNumSamplesPerCycle (long nsc)
        {
            fNumSamplesPerCycle.getAndSet (nsc);
        }
        
        public void SetPeakValue (double pv)
        {
            fLastPeakValue.Set (pv);
        }
        
        public void SetPeakValueProjected (double pvp)
        {
            fLastPeakProjectedValue.Set (pvp);
        }
        
        public void SetSampleRate (long sr)
        {
            fSampleRate.getAndSet (sr);
        }
        
    }
    
    private static final double     gkIntegrRiseRangedB =   23;         /* [110] */
    private static final double     gkIntegrFallRangedB =  -24;         /* [110] */
    private static final long       gkIntegrFallTime    = 2800;         /* [110] */
    private static final long       gkIntegrRiseTime    =   10;         /* [110] */
    private static final double     gkMinThreshold      = 3.16E-08f;    /* [120] */
    
    public static void CreateInstance (String id)
    {
        new TNodePPMProcessor (id, 1, 1);
    }

    private boolean                             fHasInitialTime;
    private FloatBuffer                         fInPacket;
    private long                                fNSamplesPerCycle;
    private double                              fPeak_dB;
    private double                              fPeak_Raw;
    private double                              fPeakDeltaRise;
    private double                              fPeakDeltaFall;
    private long                                fSampleRate;
    private TStats_TNodePPMProcessor            fStats;
    private long                                fTLast;
    private long                                fTDeltaRequested;
    
    /**
     * @param id
     * @param nMaxChanIn
     * @param nMaxChanOut
     */
    private TNodePPMProcessor (String id, int nMaxChanIn, int nMaxChanOut)
    {
        super (id, nMaxChanIn, nMaxChanOut);
        
        fStats              = new TStats_TNodePPMProcessor (this);
        fHasInitialTime     = false;                                    /* [140] */
        fPeakDeltaRise      = gkIntegrRiseRangedB / gkIntegrRiseTime;   /* [110] */
        fPeakDeltaFall      = gkIntegrFallRangedB / gkIntegrFallTime;   /* [110] */
        fInPacket           = null;
        fPeak_dB            = 0;
        fPeak_Raw           = 0;
        fTLast              = 0;                                        /* [140] */
        fTDeltaRequested    = -1;
        fSampleRate         = -1;
        TController.StatAddProvider (this);
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
        if (e == gkEventTimerTick)
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
     * @see ppm_java._aux.typelib.IEvented#OnEvent(int, long)
     */
    @Override
    public void OnEvent (int e, long arg0)
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IEvented#OnEvent(int, java.lang.String)
     */
    @Override
    public void OnEvent (int e, String arg0)
    {
        // Do nothing
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IStatEnabled#StatsGet()
     */
    @Override
    public IStats StatsGet ()
    {
        return fStats;
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VBrowseable#_Register()
     */
    @Override
    protected void _Register ()
    {
        TController.Register (this);
    }
    
    /**
     * Auto compensation for Overruns/Underruns on Atomic buffer associated with input.
     */
    private void _CompensateCongestions ()
    {
        TNodePPMProcessor_Endpoint_In   in;
        TStats_TAtomicBuffer            stats;
        TRecord                         stRec;
        
        in      = (TNodePPMProcessor_Endpoint_In) GetPortIn (0); 
        stats   = in.StatsGet ();
        stRec   = stats.GetRecord ();
        
        if (stRec.fDiffOverUnderruns > 0)
        {   /* Overruns - meaning the JackD audio driver pushes more 
               data than we handle at the moment => We must decrement 
               GUI timer cycle interval. We use a simple linear decrement. */
            if (fTDeltaRequested > TTimer.gkLoopIntervalMin)
            {
                fTDeltaRequested--;
                TController.PostEvent (gkEventTimerAdjustInterval, fTDeltaRequested, GetID ());
                stats.Clear ();                                         /* [150] */
            }
        }
        else if (stRec.fDiffOverUnderruns < 0)
        {
            /* Underruns - meaning the GUI update cycle tries to draw too
             * much data => We must increment GUI timer cycle interval.
             * We use a simple linear increment. */
            fTDeltaRequested++;
            TController.PostEvent (gkEventTimerAdjustInterval, fTDeltaRequested, GetID ());
            stats.Clear ();                                             /* [150] */
        }
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
    
    /**
     * The central computation piece of the PPM level meter. Sends one level update
     * per cycle to the attached meter frontend. Output is one single floating 
     * point value per cycle. Level value is in dB.<br/>
     * 
     * The method performs the necessary ballistics of a PPM meter:<br/>
     */
    private void _ProcessChunk ()
    {
        TAudioContext_JackD                 drv;
        TNodePPMProcessor_Endpoint_In       in;
        TNodePPMProcessor_Endpoint_Out      out;
        FloatBuffer                         packetNew;
        long                                nowT;
        long                                dT;
        double                              dY;
        double                              dY_cycle;
        double                              pdB;
        float                               pSend;
        long                                nSamples;
        int                                 nRem;
        
        drv                 = TController.GetAudioDriver ();
        nowT                = System.currentTimeMillis ();
        dT                  = nowT - fTLast;
        fTLast              = nowT;
        if (fTDeltaRequested <= -1)
        {   /* Will be -1 on first cycle only */
            fTDeltaRequested = dT;
        }
        fSampleRate         = drv.GetSampleRate ();
        fNSamplesPerCycle   = dT * fSampleRate / 1000;
        
        _CompensateCongestions ();
        
        fStats.SetCycleTime             (dT);
        fStats.SetSampleRate            (fSampleRate);
        fStats.SetNumSamplesPerCycle    (fNSamplesPerCycle);
        
        /* Analyze next chunk of data. */
        in          = (TNodePPMProcessor_Endpoint_In) GetPortIn (0);
        packetNew   = in.FetchPacket ();
        if (packetNew != null)
        {
            /* We have fresh data. Do analysis with that one. */
            fInPacket  = packetNew;
            fInPacket.rewind ();
            nSamples   = fInPacket.limit ();
            if (nSamples > fNSamplesPerCycle)
            {
                nSamples = fNSamplesPerCycle;
            }
            fPeak_Raw = _FindPeak (fInPacket, nSamples);
        }
        else if (fInPacket != null)
        {
            /* No fresh data available, but the previous packet still has unresolved data */
            fPeak_Raw = _FindPeak (fInPacket, fNSamplesPerCycle);
            nRem = fInPacket.remaining ();
            if (nRem <= 0)
            {
                fInPacket = null;
            }
        }
        else
        {
            /* No data available for analysis. Just rebroadcast previous peak value. */
        }
        
        /* Convert approximated peak value to dB */                     /* [120] */
        if (fPeak_Raw <= gkMinThreshold)
        {
            pdB = -130; 
        }
        else
        {
            pdB = 20 * Math.log10 (fPeak_Raw);
        }
        
        fStats.SetPeakValue (fPeak_Raw);
        fStats.SetDBValue   (pdB);
        
        /* Integration part - compute new peak value with given PPM ballistics. */
        /* Interpolate next meter point. We use a simple linear interpolation. */
        dY = pdB - fPeak_dB;
        if (dY > 0)
        {
            /* Value is rising. Use value-rise ballistics.  */
            dY_cycle    = dT * fPeakDeltaRise;
            fPeak_dB    = fPeak_dB + dY_cycle;
            
            /* Limit peak value in case it's above given peak parameter */
            if (fPeak_dB > pdB)
            {
                fPeak_dB = pdB;
            }
        }
        else if (dY < 0)
        {
            /* Value is falling. Use value-fall ballistics. */
            dY_cycle    = dT * fPeakDeltaFall;
            fPeak_dB    = fPeak_dB + dY_cycle;
            
            /* Limit peak value in case it's below given peak parameter */
            if (fPeak_dB < pdB)
            {
                fPeak_dB = pdB;
            }
        }
        else
        {
            /* Same value as before */
            fPeak_dB = pdB;
        }
        
        fStats.SetPeakValueProjected (fPeak_dB);
        
        /* Send peak value */
        pSend = (float) fPeak_dB;                                       /* [130] */
        out   = (TNodePPMProcessor_Endpoint_Out) GetPortOut (0);
        out.PushSample (pSend);
    }
}

/* 
[110]   PPM type II ballistics (strict):
        Rise: -24dB -> - 1dB:   10  ms  =  23dB /   10mS =  2.3      dB/mS 
        Fall:   0dB -> -24dB: 2800  ms  = -24dB / 2800ms ~ -0.00857  dB/ms
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
[150]   Otherwise stRec.fDiffOverUnderruns won't converge to zero without more over/underruns
*/