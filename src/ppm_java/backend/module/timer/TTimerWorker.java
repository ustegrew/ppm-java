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

package ppm_java.backend.module.timer;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import ppm_java.typelib.IEvented;

/**
 * Internal worker thread. Runs in an endless loop and triggers 
 * the {@link IEvented#gkEventTimerTick} events. This worker can
 * only run once, i.e. has no suspend state. 
 * 
 * @author Peter Hoppe
 */
class TTimerWorker extends Thread
{
    /**
     * Minimum Interval.
     */
    private static final int        gkLoopInterval          = TTimer.gkLoopIntervalMin;
    
    /**
     * Internal state: Worker thread is running. 
     */
    private static final int        gkStateRun              = 1;
    
    /**
     * Internal state: Worker thread is being stopped / is stopped. 
     */
    private static final int        gkStateStop             = 2;
    
    /**
     * Internal state: Worker thread is in wait state, i.e. not yet started. 
     */
    private static final int        gkStateWait             = 0;
    
    /**
     * The hosting timer module.
     */
    private TTimer          fHost;
    
    /**
     * The loop interval. Can be changed. To make it thread safe we choose an atomic variable.
     */
    private AtomicLong      fIntervalMs;                                /* [100] */
    
    /**
     * The internal state. Changed from outside.  To make it thread safe we choose an atomic variable.
     */
    private AtomicInteger   fState;                                     /* [100] */
    
    /**
     * cTor.
     * 
     * @param host          The hosting timer module.
     * @param delayMs       The initial interval.
     */
    TTimerWorker (TTimer host, long delayMs)
    {
        fHost       = host;
        fState      = new AtomicInteger (gkStateWait);
        fIntervalMs = new AtomicLong    (delayMs);
    }

    /**
     * @return  The current interval.
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
    
    /**
     * Sets ithe interval to a new value.
     * 
     * @param intervalMs        The new interval value.
     */
    void SetInterval (long intervalMs)
    {
        fIntervalMs.getAndSet (intervalMs);
    }

    /**
     * Stops this thread, i.e. terminates it. The worker thread can't be restarted.
     */
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