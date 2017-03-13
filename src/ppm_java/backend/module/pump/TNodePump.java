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

package ppm_java.backend.module.pump;

import java.nio.FloatBuffer;

import ppm_java.backend.TController;
import ppm_java.backend.module.jackd.TAudioContext_JackD;
import ppm_java.backend.module.timer.TTimer;
import ppm_java.typelib.IEvented;
import ppm_java.typelib.IStatEnabled;
import ppm_java.typelib.IStats;
import ppm_java.typelib.VAudioProcessor;
import ppm_java.util.storage.TAtomicBuffer.ECopyPolicy;
import ppm_java.util.storage.TAtomicBuffer.EIfInvalidPolicy;
import ppm_java.util.storage.TAtomicBuffer_Stats;
import ppm_java.util.storage.TAtomicBuffer_Stats.TRecord;

/**
 * A data pump. Fetches the audio data from the audio driver, 
 * repackages it in smaller chunks if needed and passes those 
 * on to further stages. Also, initiates cycle time correction 
 * to the GUI timer if we have data loss.<br/>
 * The data pump is designed to work together with an audio driver;
 * it acts as a priority inversion barrier between audio driver
 * and display engine. This enables the audio driver to work 
 * undisturbed, no matter what happens on the display driver side.
 * 
 * @author Peter Hoppe
 */
public class TNodePump 
    extends     VAudioProcessor 
    implements  IEvented, IStatEnabled
{
    /**
     * Creates a new instance of this module.
     * 
     * @param id        Unique ID as which we register this module.
     */
    public static void CreateInstance (String id)
    {
        new TNodePump (id);
    }
    
    /**
     * The global audio driver instance.
     */
    private TAudioContext_JackD         fAudioDriver;
    
    /**
     * Input Endpoint. For this module, it's easier to have it cached.
     */
    private TNodePump_Endpoint_In       fEndptIn;
    
    /**
     * Output endpoint. For this module, it's easier to have it cached.
     */
    private TNodePump_Endpoint_Out      fEndptOut;
    
    /**
     * If <code>false</code>, then we still need to initialize some local properties.
     * Most notably, we need to determine the time of the first incoming 
     * {@link IEvented#gkEventTimerTick}. This time of the first
     * timer event will become the initial time. From the second timer tick 
     * onwards we always compute the time difference to the previous frame which 
     * we need to calculate various runtime statistics.
     */
    private boolean                     fHasBeenInitialized;
    
    /**
     * Number of samples per display engine cycle.
     */
    private int                         fNSamplesPerCycle;
    
    /**
     * The sample chunk we are currently using to extract data.
     */
    private FloatBuffer                 fPacketLast;
    
    /**
     * The audio driver's sample rate.
     */
    private int                         fSampleRate;
    
    /**
     * The statistics record. Updated during runtime.
     */
    private TNodePump_Stats             fStats;
    
    /**
     * For underrun/overrun compensation: Desired cycle time of the display engine.
     */
    private long                        fTCycleDesired;
    
    /**
     * Time difference [ms] between this frame and last frame.
     */
    private long                        fTDelta;
    
    /**
     * Absolute time [ms since 01.01.1970 00:00] when the current display engine cycle started.
     */
    private long                        fTLast;
    
    /**
     * cTor.
     * 
     * @param id    Unique ID as which we register this module.
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
     * @see ppm_java.typelib.VAudioProcessor#CreatePort_In(java.lang.String)
     */
    @Override
    public void CreatePort_In (String id)
    {
        TNodePump_Endpoint_In       p;
        
        p = new TNodePump_Endpoint_In (id, this, ECopyPolicy.kCopyOnGet, EIfInvalidPolicy.kReturnNull);
        AddPortIn (p);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioProcessor#CreatePort_Out(java.lang.String)
     */
    @Override
    public void CreatePort_Out (String id)
    {
        TNodePump_Endpoint_Out      p;
        
        p = new TNodePump_Endpoint_Out (id, this);
        AddPortOut (p);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IEvented#OnEvent(int)
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
     * @see ppm_java.typelib.IEvented#OnEvent(int, int)
     */
    @Override
    public void OnEvent (int e, int arg0)
    {
        // Do nothing
    }
    
    /* (non-Javadoc)
     * @see ppm_java.typelib.IEvented#OnEvent(int, long)
     */
    @Override
    public void OnEvent (int e, long arg0)
    {
        // Do nothing
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IEvented#OnEvent(int, java.lang.String)
     */
    @Override
    public void OnEvent (int e, String arg0)
    {
        // Do nothing
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IStatEnabled#StatsGet()
     */
    @Override
    public IStats StatsGet ()
    {
        return fStats;
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VBrowseable#_Register()
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
     * Initializes a few objects and sets the initial value for {@link #fTLast}.
     */
    private void _Initialize ()
    {
        TAtomicBuffer_Stats     endpointInStats;
        
        fAudioDriver        = TController.GetAudioDriver ();
        fEndptIn            = (TNodePump_Endpoint_In)  GetPortIn (0);
        fEndptOut           = (TNodePump_Endpoint_Out) GetPortOut (0);
        fTLast              = System.currentTimeMillis ();
        fPacketLast         = null;
        
        endpointInStats     = fEndptIn.StatsGet ();
        fStats.SetEndpointInStats (endpointInStats);
    }

    /**
     * Recomputes some runtime statistics for the next 
     * cycle (e.g. how many samples we should pass on). 
     */
    private void _RecomputeRuntimeStats ()
    {
        long                    tNow;
        long                    tDelta;
        double                  xSampleRate;
        double                  nSamplesPerCycle;
        
        tNow        = System.currentTimeMillis ();
        tDelta      = tNow - fTLast;
        if (fTCycleDesired <= -1)
        {
            fTCycleDesired = tDelta;
        }
        xSampleRate         = fAudioDriver.GetSampleRate ();
        nSamplesPerCycle    = tDelta * xSampleRate / 1000;              /* [140] */
        
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
            st.Clear ();                                                /* [130 */
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
        It was this place which inspired the TAtomicBuffer kNoCopy policy, and the
        only place where this policy would make some sense. We leave the policy 
        in place, just in case we find a way to use it without compromising 
        thread safety. For future developments. For now, the extra copy doesn't 
        seem to have a visible impact on the GUI behaviour. 
[130]   Otherwise stRec.fDiffOverUnderruns won't converge to zero without more over/underruns
[140]   Imbalance problem: Number of samples is based on GUI cycle time and sampling frequency.
        We will get a situation where during one GUI cycle we process a majority of the last 
        Jack frame; then in the next GUI cycle we compute process that frame's leftovers
        (as Jack hasn't delivered a new frame yet). The first GUI cycle will have a lot of 
        samples, the next GUI cycle will have a few remaining samples. 
        Mitigation: We need to compute the GUI cycle number of samples in such a way that this
        imbalance is minimized -> the calculation must include the Jack frame size, dividing 
        the Jack frame evenly among the successive GUI cycles as known at the time of calculation. 
        This calculation must be renewed each GUI cycle, i.e. happen inside _RecomputeRuntimeStats().
        This means, we need to modify the calculation at the source line with footnote [140] 
        to include Jack frame size and approx. evenly distribute sample chunk size per GUI cycle. 
        It'll never be totally even, as the number of samples can only be an integer, and 
        "really even" would mean a real number, i.e. in the mathematical set of real numbers.
        You simply can't have a "real number" of samples, only a "Natural{0, 1, 2, ...} number" 
        of samples. 
 */
