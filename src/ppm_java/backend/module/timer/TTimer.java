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

import ppm_java.backend.TController;
import ppm_java.backend.module.pump.TNodePump;
import ppm_java.typelib.IControllable;
import ppm_java.typelib.IEvented;
import ppm_java.typelib.VBrowseable;
import ppm_java.util.logging.TLogger;

/**
 * Emits a constant stream of {@link IEvented#gkEventTimerTick} events,
 * on event per given interval. Modules can subscribe to this timer and
 * will then receive the events. Recipients can use the timer events
 * to perform regular tasks.
 * 
 * Modules, in turn can send {@link IEvented#gkEventTimerAdjustInterval}
 * events to change the interval time in this timer. This is the basis 
 * of the data contention compensation we use in the display engine.
 * 
 * @author  Peter Hoppe
 * @see     TController#Create_Connection_Events(String, String)
 * @see     TNodePump#_Resolve_DataLoss()
 */
public class TTimer 
    extends         VBrowseable
    implements      IControllable, IEvented
{
    /**
     * Minimum interval.
     */
    public static final int gkLoopIntervalMin = 10;

    /**
     * Creates a new TTimer instance. 
     * 
     * @param id            Unique ID as which we register this timer.
     * @param intervalMs    Requested timer interval, in ms. We enforce a lower limit
     *                      of {@link #gkLoopIntervalMin} ms.
     */
    public static void CreateInstance (String id, int intervalMs)
    {
        new TTimer (id, intervalMs);
    }
    
    /**
     * The internal worker thread which does the actual looping and event triggering.
     */
    private TTimerWorker                fWorker;
    
    /**
     * cTor.
     * 
     * @param id            Unique ID as which we register this timer.
     * @param delayMs       Requested timer interval, in ms. We enforce a lower limit
     *                      of {@link #gkLoopIntervalMin} ms.
     */
    private TTimer (String id, int delayMs)
    {
        super (id);
        
        long d;
        
        d       = _GetSaneInterval (delayMs);
        fWorker = new TTimerWorker (this, d);
    }
    
    /**
     * @return  This timer's interval in ms.
     */
    public long GetInterval ()
    {
        long ret;
        
        ret = fWorker.GetInterval ();
        
        return ret;
    }
    
    /* (non-Javadoc)
     * @see ppm_java.typelib.IEvented#OnEvent(int)
     */
    @Override
    public void OnEvent (int e)
    {
        // Do nothing
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IEvented#OnEvent(int, int)
     */
    @Override
    public void OnEvent (int e, int arg0)
    {
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IEvented#OnEvent(int, long)
     */
    @Override
    public void OnEvent (int e, long arg0)
    {
        long         newIntervalMs;
        
        if (e == gkEventTimerAdjustInterval)
        {
            newIntervalMs = _GetSaneInterval (arg0);
            fWorker.SetInterval (newIntervalMs);
        }
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IEvented#OnEvent(int, java.lang.String)
     */
    @Override
    public void OnEvent (int e, String arg0)
    {
        // Do nothing
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IControllable#Start()
     */
    public void Start ()
    {
        fWorker.start ();
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IControllable#Stop()
     */
    public void Stop ()
    {
        fWorker.Stop ();
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VBrowseable#_Register()
     */
    @Override
    protected void _Register ()
    {
        TController.Register (this);
    }

    /**
     * Sends the {@link IEvented#gkEventTimerTick} event to all subscribers.
     */
    void SendTimerEvent ()
    {
        String id;
        
        id = GetID ();
        TController.PostEvent (IEvented.gkEventTimerTick, id);
    }
    
    /**
     * Returns a sanitized timer interval, if the given value was out of bounds.
     * 
     * @param   delayMs     The suggested interval.
     * @return              The suggested interval, if it's larger than (or equal to)
     *                      {@link #gkLoopIntervalMin}. Otherwise, returns 
     *                      {@link #gkLoopIntervalMin} 
     */
    private long _GetSaneInterval (long delayMs)
    {
        long ret;
        
        if (delayMs >= gkLoopIntervalMin)
        {
            ret = delayMs;
        }
        else
        {
            ret = gkLoopIntervalMin;
            TLogger.LogError 
            (
                "For timer '" + GetID () + "': Given interval must be >= " +
                gkLoopIntervalMin + ". Given: " + delayMs + 
                ". Set to " + ret, this, "cTor"
            );
        }
        
        return ret;
    }
}
