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

import java.util.concurrent.atomic.AtomicLong;
import ppm_java.typelib.IStats;
import ppm_java.util.storage.TAtomicBuffer_Stats;

/**
 * Runtime statistics for a {@link TNodePump}.
 * 
 * @author Peter Hoppe
 */
public class TNodePump_Stats implements IStats
{
    /**
     * Input endpoint statistics.
     */
    private TAtomicBuffer_Stats     fEndpointIn_Stats;
    
    /**
     * The hosting module.
     */
    private TNodePump               fHost;
    
    /**
     * Current number of samples per cycle.
     */
    private AtomicLong              fNumSamplesPerCycle;
    
    /**
     * Current sample rate.
     */
    private AtomicLong              fSampleRate;
    /**
     * Time difference [ms] between this frame and last frame.
     */
    private AtomicLong              fTimeCycle;
    
    /**
     * cTor.
     * 
     * @param host      The hosting module.
     */
    public TNodePump_Stats (TNodePump host)
    {
        fHost                   = host;
        fTimeCycle              = new AtomicLong    (0);
        fSampleRate             = new AtomicLong    (0);
        fNumSamplesPerCycle     = new AtomicLong    (0);
    }
    
    /* (non-Javadoc)
     * @see ppm_java.typelib.IStats#GetDumpStr()
     */
    @Override
    public String GetDumpStr ()
    {
        String epInStats;
        String ret;
        
        epInStats   = fEndpointIn_Stats.GetDumpStr ();
        ret         = "TNodePump_Stats [" + fHost.GetID () + "]:\n"                             +
                      "    cycleTime [ms]       = " + fTimeCycle.getAndAdd (0)          + "\n" +
                      "    sampleRate [smp/sec] = " + fSampleRate.getAndAdd (0)         + "\n" +
                      "    samplesPerCycle      = " + fNumSamplesPerCycle.getAndAdd (0) + "\n" +
                      "Input endpoint statistics:\n" +
                      epInStats +
                      "\n";

        return ret;
    }
    
    /**
     * Sets the time difference [ms] between this frame and last frame.
     * 
     * @param ct        Cycle time value.
     */
    void SetCycleTime (long ct)
    {
        fTimeCycle.getAndSet (ct);
    }
    
    /**
     * Sets the input endpoint statistics record.
     * 
     * @param endpointInStats
     */
    void SetEndpointInStats (TAtomicBuffer_Stats endpointInStats)
    {
        fEndpointIn_Stats = endpointInStats;
    }
    
    /**
     * Sets the current number of samples per cycle.
     * 
     * @param nsc       Number of samples.
     */
    void SetNumSamplesPerCycle (long nsc)
    {
        fNumSamplesPerCycle.getAndSet (nsc);
    }
    
    /**
     * Sets the current sample rate.
     * 
     * @param sr        Sample rate (e.g. 44100).
     */
    void SetSampleRate (long sr)
    {
        fSampleRate.getAndSet (sr);
    }
}
