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

/**
 * @author Peter Hoppe
 *
 */
public class TNodePump_Stats implements IStats
{
    private TNodePump               fHost;
    private AtomicLong              fNumSamplesPerCycle;
    private AtomicLong              fSampleRate;
    private AtomicLong              fTimeCycle;
    
    public TNodePump_Stats (TNodePump host)
    {
        fHost                   = host;
        fTimeCycle              = new AtomicLong (0);
        fSampleRate             = new AtomicLong (0);
        fNumSamplesPerCycle     = new AtomicLong (0);
    }
    
    /* (non-Javadoc)
     * @see ppm_java.typelib.IStats#GetDumpStr()
     */
    @Override
    public String GetDumpStr ()
    {
        String ret;
        
        ret = "TNodePump_Stats [" + fHost.GetID () + "]:\n"                             +
              "    cycleTime [ms]       = " + fTimeCycle.getAndAdd (0)          + "\n" +
              "    sampleRate [smp/sec] = " + fSampleRate.getAndAdd (0)         + "\n" +
              "    samplesPerCycle      = " + fNumSamplesPerCycle.getAndAdd (0) + "\n";
              
        return ret;
    }
    
    void SetCycleTime (long ct)
    {
        fTimeCycle.getAndSet (ct);
    }
    
    void SetNumSamplesPerCycle (long nsc)
    {
        fNumSamplesPerCycle.getAndSet (nsc);
    }
    
    void SetSampleRate (long sr)
    {
        fSampleRate.getAndSet (sr);
    }
}
