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

package ppm_java._aux.debug;

import java.util.concurrent.atomic.AtomicInteger;

import ppm_java.backend.server.TController;
import ppm_java.frontend.gui.TWndDebug;

/**
 * @author peter
 *
 */
public class TTimerDebugUpdate extends Thread
{
    private AtomicInteger           fDoRun;
    
    /**
     * 
     */
    public TTimerDebugUpdate ()
    {
        fDoRun = new AtomicInteger (0);
    }
    
    public void Stop ()
    {
        fDoRun.getAndSet (0);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Thread#start()
     */
    @Override
    public void run ()
    {
        String  stats;
        int     doRun;
        
        TWndDebug.Show ();
        
        fDoRun.getAndSet (1);
        doRun = 1;
        while (doRun == 1)
        {
            stats = TController.StatGetDumpStr ();
            TWndDebug.SetText (stats);
            try {Thread.sleep (500);} catch (InterruptedException e) {}
            doRun = fDoRun.addAndGet (0);
        }
    }
}
