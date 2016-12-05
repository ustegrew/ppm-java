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

package ppm_java.stream.control.timer;

import java.util.concurrent.atomic.AtomicInteger;

import ppm_java._aux.logging.TLogger;

/**
 * @author peter
 *
 */
class TTimerWorker extends Thread
{
    private static final int        gkLoopInterval          = 1;
    private static final int        gkStateRun              = 1;
    private static final int        gkStateStop             = 2;
    private static final int        gkStateWait             = 0;
    
    private long            fDelayMs;
    private TTimer          fHost;
    private AtomicInteger   fState;
    
    /**
     * @param id
     */
    TTimerWorker (TTimer host, int delayMs)
    {
        fHost  = host;
        fState = new AtomicInteger (gkStateWait);
        if (delayMs >= gkLoopInterval)
        {
            fDelayMs = delayMs;
        }
        else
        {
            TLogger.LogError 
            (
                "For timer '" + host.GetID () + "': Given interval must be >= " + 
                gkLoopInterval + ". Given: " + delayMs + 
                ". Set to " + fDelayMs, this, "cTor"
            );
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run ()
    {
        long            t0;
        long            t1;
        long            dt;
        int             state;
        
        fState.getAndSet (gkStateRun); 
        t0 = System.currentTimeMillis ();
        do
        {
            try {Thread.sleep (gkLoopInterval);} catch (InterruptedException e) {}

            t1 = System.currentTimeMillis ();
            dt = t1 - t0;
            if (dt >= fDelayMs)
            {
                fHost.SendTimerEvent ();
                t0 = System.currentTimeMillis ();
            }
            state = fState.getAndAdd (0);                               /* [100] */
        } while (state == gkStateRun);
    }
    
    void Stop ()
    {
        fState.compareAndSet (gkStateRun, gkStateStop);
    }
}

/*
[100]   A weird way to retrieve the value of fState - return value and add zero. 
        But the AtomicInteger::get() method seems to be non-atomic (according to the docs),
        whilst AtomicInteger::getAndAdd() is.
*/