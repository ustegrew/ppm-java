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

package ppm_java.backend.server.module.integrator;

import ppm_java._aux.storage.TAtomicDouble;
import ppm_java._aux.typelib.IStats;

/**
 * @author peter
 *
 */
public class TNodeIntegrator_PPMBallistics_Stats implements IStats
{
    private TNodeIntegrator_PPMBallistics           fHost;
    private TAtomicDouble                           fTimeCycle;
    private TAtomicDouble                           fValueDBActual;
    private TAtomicDouble                           fValueDBRef;
    private TAtomicDouble                           fValueErr;
    
    /**
     * 
     */
    public TNodeIntegrator_PPMBallistics_Stats (TNodeIntegrator_PPMBallistics host)
    {
        fHost                   = host;
        fValueDBActual          = new TAtomicDouble ();
        fValueDBRef             = new TAtomicDouble ();
        fValueErr               = new TAtomicDouble ();
        fTimeCycle              = new TAtomicDouble ();
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IStats#GetDumpStr()
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

    void SetTimeCycle (double t)
    {
        fTimeCycle.Set (t);
    }
    
    void SetValueDBActual (double value)
    {
        fValueDBActual.Set (value);
        _RecomputeDiff ();
    }
    
    void SetValueDBRef (double value)
    {
        fValueDBRef.Set (value);
        _RecomputeDiff ();
    }
    
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
