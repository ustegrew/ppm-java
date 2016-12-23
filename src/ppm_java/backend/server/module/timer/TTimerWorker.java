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

package ppm_java.backend.server.module.timer;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author peter
 *
 */
class TTimerWorker extends Thread
{
    private static final int        gkLoopInterval          = TTimer.gkLoopIntervalMin;
    private static final int        gkStateRun              = 1;
    private static final int        gkStateStop             = 2;
    private static final int        gkStateWait             = 0;
    
    private AtomicLong      fIntervalMs;                                /* [100] */
    private TTimer          fHost;
    private AtomicInteger   fState;                                     /* [100] */
    
    /**
     * @param id
     */
    TTimerWorker (TTimer host, int delayMs)
    {
        fHost       = host;
        fState      = new AtomicInteger (gkStateWait);
        fIntervalMs = new AtomicLong    (delayMs);
    }

    /**
     * @return
     */
    public long GetInterval ()
    {
        long ret;
        
        ret = fIntervalMs.getAndAdd (0);                                   /* [110] */
        
        return ret;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run ()
    {
        long            t0;
        long            t1;
        long            dly;
        long            dt;
        int             state;
        
        fState.getAndSet (gkStateRun); 
        t0 = System.currentTimeMillis ();
        do
        {
            try {Thread.sleep (gkLoopInterval);} catch (InterruptedException e) {}

            t1  = System.currentTimeMillis ();
            dly = fIntervalMs.getAndAdd (0);                               /* [110] */
            dt  = t1 - t0;
            if (dt >= dly)
            {
                fHost.SendTimerEvent ();
                t0 = System.currentTimeMillis ();
            }
            state = fState.getAndAdd (0);                               /* [110] */
        } while (state == gkStateRun);
    }
    
    void SetInterval (int intervalMs)
    {
        fIntervalMs.getAndSet (intervalMs);
    }

    void Stop ()
    {
        fState.compareAndSet (gkStateRun, gkStateStop);                 /* [120] */
    }
}

/*
[100]   We use atomic variables because the values can be accessed by multiple threads.
[110]   A weird way to retrieve the value of fState - return value and add zero. 
        But the AtomicInteger::get() method seems to be non-atomic (according to the docs),
        whilst AtomicInteger::getAndAdd() is atomic.
[120]   compareAndSet: Because we don't set the state flag to gkStateStop unless the worker 
                       is in gkStateRun, i.e. running. For now it seems unnecessary to return 
                       whether setting was successful or not (compareAndSet returns true upon 
                       success and false upon failure).
*/