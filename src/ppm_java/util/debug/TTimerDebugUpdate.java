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

package ppm_java.util.debug;

import java.util.concurrent.atomic.AtomicInteger;

import ppm_java.backend.TController;

/**
 * Debug update worker thread. Updates the runtime stats window. Polls 
 * the statistics in a regular cycle ({@link #gkInterval}) and writes 
 * them to the associated debug {@link TWndDebug UI}.
 * 
 * @author  peter
 */
public class TTimerDebugUpdate extends Thread
{
    /**
     * Flag value: Thread running.
     */
    private static final int        gkFlagRun   =  1;
    
    /**
     * Flag value: Thread stopped.
     */
    private static final int        gkFlagStop  =  0;
    
    /**
     * Cycle's interval, in ms.
     */
    private static final int        gkInterval  = 10;
    
    /**
     * Flag. Set to {@link #gkFlagStop} when we want to terminate the thread.
     */
    private AtomicInteger           fDoRun;
    
    /**
     * cTor.
     */
    public TTimerDebugUpdate ()
    {
        fDoRun = new AtomicInteger (gkFlagStop);
    }
    
    /**
     * Terminates the worker thread.
     */
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
