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

package ppm_java.backend.server.module._deprecated_ppm;

import java.util.concurrent.atomic.AtomicLong;

import ppm_java._aux.storage.TAtomicDouble;
import ppm_java._aux.typelib.IStats;

public final class TNodePPMProcessor_Stats implements IStats
{
    private TNodePPMProcessor       fHost;
    private TAtomicDouble           fLastDBValue;
    private TAtomicDouble           fLastPeakValue;
    private TAtomicDouble           fLastPeakProjectedValue;
    private AtomicLong              fNumSamplesPerCycle;
    private AtomicLong              fSampleRate;
    private AtomicLong              fTimeCycle;
    
    public TNodePPMProcessor_Stats (TNodePPMProcessor host)
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
    
    void SetCycleTime (long ct)
    {
        fTimeCycle.getAndSet (ct);
    }
    
    void SetDBValue (double dB)
    {
        fLastDBValue.Set (dB);
    }
    
    void SetNumSamplesPerCycle (long nsc)
    {
        fNumSamplesPerCycle.getAndSet (nsc);
    }
    
    void SetPeakValue (double pv)
    {
        fLastPeakValue.Set (pv);
    }
    
    void SetPeakValueProjected (double pvp)
    {
        fLastPeakProjectedValue.Set (pvp);
    }
    
    void SetSampleRate (long sr)
    {
        fSampleRate.getAndSet (sr);
    }
    
}