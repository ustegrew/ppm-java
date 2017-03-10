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

package ppm_java.backend.module.integrator;

import ppm_java.typelib.IStats;
import ppm_java.util.storage.TAtomicDouble;

/**
 * Runtime statistics of a {@link TNodeIntegrator_PPMBallistics}.
 * 
 * @author peter
 */
public class TNodeIntegrator_PPMBallistics_Stats implements IStats
{
    /**
     * The hosting module.
     */
    private TNodeIntegrator_PPMBallistics           fHost;
    
    /**
     * The time it took between the previous cycle and the current cycle.
     */
    private TAtomicDouble                           fTimeCycle;
    
    /**
     * The computed output value after application of the PPM ballistics. 
     */
    private TAtomicDouble                           fValueDBActual;
    
    /**
     * The input value for the current cycle.
     */
    private TAtomicDouble                           fValueDBRef;
    
    /**
     * Difference between input value and output value.
     */
    private TAtomicDouble                           fValueErr;
    
    public TNodeIntegrator_PPMBallistics_Stats (TNodeIntegrator_PPMBallistics host)
    {
        fHost                   = host;
        fValueDBActual          = new TAtomicDouble ();
        fValueDBRef             = new TAtomicDouble ();
        fValueErr               = new TAtomicDouble ();
        fTimeCycle              = new TAtomicDouble ();
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IStats#GetDumpStr()
     */
    @Override
    public String GetDumpStr ()
    {
        String ret;
        
        ret = "TNodeIntegrator_PPMBallistics [" + fHost.GetID ()    + "]:\n"    +
              "    dBRef    = " + fValueDBRef.Get ()                + "\n"      +
              "    dBCur    = " + fValueDBActual.Get ()             + "\n"      +
              "    dBErr    = " + fValueErr.Get ()                  + "\n"      +
              "    tCycle   = " + fTimeCycle.Get ()                 + "\n";
        
        return ret;
    }

    /**
     * Sets the time it took between the previous cycle and the current cycle.
     * 
     * @param t         Time [ms].
     */
    void SetTimeCycle (double t)
    {
        fTimeCycle.Set (t);
    }
    
    /**
     * Sets the computed output value after application of the PPM ballistics. 
     * 
     * @param value     Output value.
     */
    void SetValueDBActual (double value)
    {
        fValueDBActual.Set (value);
        _RecomputeDiff ();
    }
    
    /**
     * Sets the input value for the current cycle.
     * 
     * @param value     Input value.
     */
    void SetValueDBRef (double value)
    {
        fValueDBRef.Set (value);
        _RecomputeDiff ();
    }
    
    /**
     * Recomputes the difference between input value and output value.
     */
    private void _RecomputeDiff ()
    {
        double yAct;
        double yRef;
        double yDiff;
        
        yAct    = fValueDBActual.Get ();
        yRef    = fValueDBRef.Get ();
        yDiff   = yRef - yAct;
        fValueErr.Set (yDiff);
    }
}
