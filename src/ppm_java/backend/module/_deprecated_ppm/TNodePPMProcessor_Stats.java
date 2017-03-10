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

package ppm_java.backend.module._deprecated_ppm;

import java.util.concurrent.atomic.AtomicLong;

import ppm_java.typelib.IStats;
import ppm_java.util.storage.TAtomicDouble;

/**
 * Runtime statistics of a {@link TNodePPMProcessor}.
 * 
 * @author Peter Hoppe
 */
@SuppressWarnings ("deprecation")
public final class TNodePPMProcessor_Stats implements IStats
{
    /**
     * The module hosting this statistics record.
     */
    private TNodePPMProcessor       fHost;
    
    /**
     * Latest DB value as converted from the raw value.
     */
    private TAtomicDouble           fLastDBValue;
    
    /**
     * Latest dB value as sent to the frontend. This is the value after the 
     * PPM ballistics have been considered.  
     */
    private TAtomicDouble           fLastPeakProjectedValue;
    
    /**
     * Latest raw peak value.
     */
    private TAtomicDouble           fLastPeakValue;
    
    /**
     * Current number of samples per clock cycle.
     */
    private AtomicLong              fNumSamplesPerCycle;
    
    /**
     * Sample rate as provided by jackd.
     */
    private AtomicLong              fSampleRate;
    
    /**
     * Time [ms] between previous cycle and this cycle.
     */
    private AtomicLong              fTimeCycle;
    
    /**
     * cTor.
     * 
     * @param host      The hosting module.
     */
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
     * @see ppm_java.typelib.IStats#GetDumpStr()
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
    
    /**
     * Sets the {@link #fTimeCycle}, in ms.
     * 
     * @param ct    Time value.
     */
    void SetCycleTime (long ct)
    {
        fTimeCycle.getAndSet (ct);
    }
    
    /**
     * Sets the {@link #fLastDBValue}.
     * 
     * @param dB    The dB value.
     */
    void SetDBValue (double dB)
    {
        fLastDBValue.Set (dB);
    }
    
    /**
     * Sets the {@link #fNumSamplesPerCycle}.
     * 
     * @param nsc   The number value.
     */
    void SetNumSamplesPerCycle (long nsc)
    {
        fNumSamplesPerCycle.getAndSet (nsc);
    }
    
    /**
     * Sets the {@link #fLastPeakValue}.
     * 
     * @param pv    The peak value.
     */
    void SetPeakValue (double pv)
    {
        fLastPeakValue.Set (pv);
    }
    
    /**
     * Sets the {@link #fLastPeakProjectedValue}.
     * 
     * @param pvp   The projected peak value.
     */
    void SetPeakValueProjected (double pvp)
    {
        fLastPeakProjectedValue.Set (pvp);
    }
    
    /**
     * Sets the {@link #fSampleRate}.
     * 
     * @param sr    The sample rate [sampl / sec]
     */
    void SetSampleRate (long sr)
    {
        fSampleRate.getAndSet (sr);
    }
    
}