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

/**
 * A simple text dump window for runtime statistics. Updated in 
 * a regular interval. During run tests it turned out that 
 * the main UI (<code>TWndPPM</code> was very sluggish, but when 
 * I had this window open alongside the main UI and updated this 
 * window every 10ms then the main window was very responsive.
 *  
 * I assume it's to do with the way the swing framework updates 
 * its components. 
 * 
 * Since this is a concept application (ultimately ported to C++/QT)
 * and it's a swing internal issue I don't place high priority on
 * fixing the sluggish UI response - it works when I have this one open
 * and update it every 10ms. 
 * 
 * @author  peter
 * @see     TWndPPM
 */
public class TTimerDebugUpdate extends Thread
{
    private static final int        gkFlagRun   = 1;
    private static final int        gkFlagStop  = 0;
    private static final int        gkInterval  = 10;
    
    private AtomicInteger           fDoRun;
    
    /**
     * 
     */
    public TTimerDebugUpdate ()
    {
        fDoRun = new AtomicInteger (gkFlagStop);
    }
    
    public void Stop ()
    {
        fDoRun.getAndSet (gkFlagStop);
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
        
        doRun = gkFlagRun;
        fDoRun.getAndSet (gkFlagRun);
        while (doRun == gkFlagRun)
        {
            stats = TController.StatGetDumpStr ();
            TWndDebug.SetText (stats);
            try {Thread.sleep (gkInterval);} catch (InterruptedException e) {}
            doRun = fDoRun.addAndGet (0);
        }
    }
}
