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

package ppm_java.backend.server.module.pump;

import java.nio.FloatBuffer;

import ppm_java._aux.logging.TLogger;
import ppm_java._aux.storage.TAtomicBuffer.ECopyPolicy;
import ppm_java._aux.storage.TAtomicBuffer.EIfInvalidPolicy;
import ppm_java._aux.storage.TAtomicBuffer_Stats;
import ppm_java._aux.storage.TAtomicBuffer_Stats.TRecord;
import ppm_java._aux.typelib.IEvented;
import ppm_java._aux.typelib.IStatEnabled;
import ppm_java._aux.typelib.IStats;
import ppm_java._aux.typelib.VAudioProcessor;
import ppm_java.backend.jackd.TAudioContext_JackD;
import ppm_java.backend.server.TController;
import ppm_java.backend.server.module.timer.TTimer;

/**
 * A data pump. Fetches the audio data from the audio driver, repackages it in smaller chunks if needed
 * and passes those on to further stages. Also, initiates cycle time correction to the GUI timer
 * if we have data loss.
 * 
 * @author peter
 */
public class TNodePump 
    extends     VAudioProcessor 
    implements  IEvented, IStatEnabled
{
    public static void CreateInstance (String id)
    {
        new TNodePump (id);
    }
    
    private TAudioContext_JackD         fAudioDriver;
    private TNodePump_Endpoint_In       fEndptIn;
    private TNodePump_Endpoint_Out      fEndptOut;
    private boolean                     fHasBeenInitialized;
    private int                         fNSamplesPerCycle;
    private FloatBuffer                 fPacketLast;
    private int                         fSampleRate;
    private TNodePump_Stats             fStats;
    private long                        fTCycleDesired;
    private long                        fTDelta;
    private long                        fTLast;
    
    /**
     * @param id
     */
    private TNodePump (String id)
    {
        super (id, 1, 1);
        
        fStats                          = new TNodePump_Stats (this);
        fHasBeenInitialized             = false;
        fAudioDriver                    = null;
        fEndptIn                        = null;
        fEndptOut                       = null;
        fTLast                          = -1;
        fTCycleDesired                  = -1;
        fTDelta                         = -1;
        TController.StatAddProvider (this);
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VAudioProcessor#CreatePort_In(java.lang.String)
     */
    @Override
    public void CreatePort_In (String id)
    {
        TNodePump_Endpoint_In       p;
        
        TLogger.LogMessage ("Creating input port '" + id + "'", this, "CreatePort_In ('" + id + "')");
        p = new TNodePump_Endpoint_In (id, this, 0, ECopyPolicy.kCopyOnGet, EIfInvalidPolicy.kReturnNull);
        AddPortIn (p);
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VAudioProcessor#CreatePort_Out(java.lang.String)
     */
    @Override
    public void CreatePort_Out (String id)
    {
        TNodePump_Endpoint_Out      p;
        
        TLogger.LogMessage ("Creating output port '" + id + "'", this, "CreatePort_Out ('" + id + "')");
        p = new TNodePump_Endpoint_Out (id, this);
        AddPortOut (p);
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IEvented#OnEvent(int)
     */
    @Override
    public void OnEvent (int e)
    {
        if (e == gkEventTimerTick)
        {
            if (fHasBeenInitialized)
            {
                _FetchAndSend ();
            }
            else
            {
                _Initialize ();                                         /* [100] */
                fHasBeenInitialized = true;
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
     * Fetches and sends the next sample chunk to the connected module.
     * Also, adjusts the cycle frequency so the data stream processed
     * comprises the same number of samples per second as the data stream 
     * coming in from the audio driver.  
     */
    private void _FetchAndSend ()
    {
        /* Recompute timings and some other statistics. */
        _RecomputeRuntimeStats ();
        
        /* Retrieve next sample packet [110]. */
        _SendNextPacket ();
        
        /* Compensate input buffer overruns/underruns. */
        _Resolve_DataLoss ();
    }

    /**
     * Initializes a few objects.
     */
    private void _Initialize ()
    {
        fAudioDriver = TController.GetAudioDriver ();
        fEndptIn     = (TNodePump_Endpoint_In)  GetPortIn (0);
        fEndptOut    = (TNodePump_Endpoint_Out) GetPortOut (0);
        fTLast       = System.currentTimeMillis ();
        fPacketLast  = null;
    }

    /**
     * Recomputes some runtime statistics for the next 
     * cycle (e.g. how many samples we should pass on). 
     */
    private void _RecomputeRuntimeStats ()
    {
        long        tNow;
        long        tDelta;
        double      xSampleRate;
        double      nSamplesPerCycle;
        
        tNow        = System.currentTimeMillis ();
        tDelta      = tNow - fTLast;
        if (fTCycleDesired <= -1)
        {
            fTCycleDesired = tDelta;
        }
        xSampleRate         = fAudioDriver.GetSampleRate ();
        nSamplesPerCycle    = tDelta * xSampleRate / 1000;
        
        fTDelta             = tDelta;
        fTLast              = tNow;
        fSampleRate         = (int) xSampleRate;
        fNSamplesPerCycle   = (int) (nSamplesPerCycle);
        
        fStats.SetCycleTime             (fTDelta);
        fStats.SetNumSamplesPerCycle    (fNSamplesPerCycle);
        fStats.SetSampleRate            (fSampleRate);
    }

    /**
     * Compensates for data loss (i.e. under/overruns) on the 
     * attached input buffer by issuing a request to the GUI timer 
     * to set the timer interval to a new value. 
     * If we have underruns this means that we aren't getting 
     * enough data which means the cycle interval is too short 
     * and needs to be longer. If we have overruns it means 
     * that the audio driver is delivering more data than we 
     * can process which means the cycle interval is too long.<br/>
     * Correction follows a simple linear strategy - for each
     * cycle, if we detected data loss we increase or decrease
     * the desired cycle time by one millisecond and notify
     * the GUI timer that we request a change of cycle time.
     */
    private void _Resolve_DataLoss ()
    {
        String                          id;
        TAtomicBuffer_Stats             st;
        TRecord                         stRec;
        
        id      = GetID ();
        st      = fEndptIn.StatsGet ();
        stRec   = st.GetRecord ();
        
        if (stRec.fDiffOverUnderruns > 0)
        {   /* Overruns - meaning the JackD audio driver pushes more 
            data than we handle at the moment => We must decrement 
            GUI timer cycle interval. We use a simple linear decrement. */
            if (fTCycleDesired > TTimer.gkLoopIntervalMin)
            {
                fTCycleDesired--;
                TController.PostEvent (gkEventTimerAdjustInterval, fTCycleDesired, id);
            }
            st.Clear ();                                            /* [130 */
        }
        else if (stRec.fDiffOverUnderruns < 0)
        {   /* Underruns - meaning the GUI update cycle tries to draw too
             * much data => We must increment GUI timer cycle interval.
             * We use a simple linear increment. */
            fTCycleDesired++;
            TController.PostEvent (gkEventTimerAdjustInterval, fTCycleDesired, id);
            st.Clear ();                                                /* [130] */
        }
    }

    /**
     * Retrieves next chunk of data. Since our cycle frequency is different 
     * than the frequency in which the JackD driver delivers data we 
     * need to chop the incoming packets up to process the same amount of 
     * samples per time as what's delivered by the audio driver. 
     */
    private void _SendNextPacket ()
    {
        FloatBuffer         newPacket;
        int                 i;
        int                 nSamples;
        float               s;
        FloatBuffer         outPacket;
        
        newPacket = fEndptIn.FetchPacket ();
        if (newPacket != null)
        {
            /* If we have new data we discard any old data */
            fPacketLast = newPacket;            /* Discard previous packet.                                         */
            fPacketLast.rewind ();              /* Ensure read pointer and markers are at zero position.            */
            nSamples = fPacketLast.limit ();    /* How many samples are we talking about?                           */
            if (nSamples > fNSamplesPerCycle)   /* Too many samples?                                                */
            {                                   /* For now, only read what we need. Postpone rest for next cycle(s) */
                nSamples = (int) fNSamplesPerCycle;
            }
        }
        else if (fPacketLast != null)
        {
            /* No fresh data available, but the previous packet may still have unresolved data */
            nSamples = fPacketLast.remaining ();
            if (nSamples > fNSamplesPerCycle)
            {   /* We have too much data to crunch. Only use as much as needed. */
                nSamples = (int) fNSamplesPerCycle;
            }
            else if (nSamples <= 0)
            {   /* No data left to read. Delete packet. */
                fPacketLast = null;
            }
            /* else-case: All the possible values in [1, fNSamplesPerCycle] */
            
            /* At this point, nSamples will be between 0 and fNSamplesPerCycle. */
        }
        else
        {   /* No data available */
            nSamples = 0;
        }
        
        if (nSamples >= 1)
        {
            /* We've got samples to send. Assemble an output packet (= new object). [120] */
            outPacket = FloatBuffer.allocate (nSamples);
            for (i = 1; i <= nSamples; i++)
            {
                s = fPacketLast.get ();
                outPacket.put (s);
            }
        }
        else
        {
            /* We've got no samples to send. Send a null packet. */
            outPacket = null;
        }
        
        /* Push sample data to the next module. */
        fEndptOut.PushPacket (outPacket);
    }
}

/*
[100]   For time measurement. We will use the first cycle after starting 
        to just set an initial time point and otherwise do no data processing.
        Otherwise, the first cycle could provide non-sensical values, maybe 
        even leading to an exception.
[110]   For the next packet we read as many samples as we set in the 
        previous step (fNSamplesPerCycle).
[120]   Unfortunately, this means we have to copy data again which I wanted
        to avoid. One idea was to postpone the copying to by instructing the
        atomic buffer to omit copying and to do the copying when we assemble
        the outgoing packets here. However, this doesn't work. By the time
        we copy the data here, the atomic buffer has released it's critical 
        sections and the producer might rewrite the buffer which will give us 
        faulty data here. Unfortunately there isn't an easy way around this.
[130]   Otherwise stRec.fDiffOverUnderruns won't converge to zero without more over/underruns
 */
