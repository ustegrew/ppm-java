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

package ppm_java.backend.module.peak_estimator;

import ppm_java.typelib.IStats;
import ppm_java.util.storage.TAtomicDouble;

/**
 * Runtime statistics for a {@link TNodePeakEstimator}.
 * 
 * @author Peter Hoppe
 */
public class TNodePeakEstimator_Stats implements IStats
{
    /**
     * The hosting module. 
     */
    private TNodePeakEstimator  fHost;
    
    /**
     * Difference between the values of this peak and last peak.
     */
    private TAtomicDouble       fPeakDelta;
    
    /**
     * Value of the last peak.
     */
    private TAtomicDouble       fPeakLast;
    
    /**
     * cTor.
     * 
     * @param host      The hosting module.
     */
    public TNodePeakEstimator_Stats (TNodePeakEstimator host)
    {
        fHost           = host;
        fPeakLast       = new TAtomicDouble ();
        fPeakDelta      = new TAtomicDouble ();
    }
    
    /* (non-Javadoc)
     * @see ppm_java.typelib.IStats#GetDumpStr()
     */
    @Override
    public String GetDumpStr ()
    {
        String ret;
        
        ret = "TNodePeakEstimator_Stats [" + fHost.GetID ()         + "]:\n"    +
              "    peak     = " + fPeakLast.Get ()                  + "\n"      +
              "    delta    = " + fPeakDelta.Get ()                 + "\n";
        
        return ret;
    }

    /**
     * Sets a new peak value.
     * 
     * @param peak      The new peak value.
     */
    void SetPeak (float peak)
    {
        double  p0;
        double  pD;

        p0          = fPeakLast.Get ();
        pD          = p0 - peak;
        fPeakDelta.Set (pD);
        fPeakLast.Set (peak);
    }
}
