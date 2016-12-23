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

import ppm_java._aux.logging.TLogger;
import ppm_java._aux.typelib.IControllable;
import ppm_java._aux.typelib.IEvented;
import ppm_java._aux.typelib.VBrowseable;
import ppm_java.backend.server.TController;

/**
 * @author peter
 *
 */
public class TTimer 
    extends         VBrowseable
    implements      IControllable, IEvented
{
    public static final int gkLoopIntervalMin = 10;

    public static TTimer CreateInstance (String id, int intervalMs)
    {
        TTimer ret;
        
        ret = new TTimer (id, intervalMs);
        
        return ret;
    }
    
    private String                      fID;
    private TTimerWorker                fWorker;
    
    /**
     * @param id
     */
    TTimer (String id, int delayMs)
    {
        super (id);
        
        long d;
        
        d       = _GetSaneInterval (delayMs);
        fWorker = new TTimerWorker (this, d);
        fID     = id;                                                   /* [100] */
    }
    
    public long GetInterval ()
    {
        long ret;
        
        ret = fWorker.GetInterval ();
        
        return ret;
    }
    
    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IEvented#OnEvent(int)
     */
    @Override
    public void OnEvent (int e)
    {
        // Do nothing
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IEvented#OnEvent(int, int)
     */
    @Override
    public void OnEvent (int e, int arg0)
    {
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IEvented#OnEvent(int, long)
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
     * @see ppm_java._aux.typelib.IEvented#OnEvent(int, java.lang.String)
     */
    @Override
    public void OnEvent (int e, String arg0)
    {
        // Do nothing
    }

    public void Start ()
    {
        fWorker.start ();
    }

    public void Stop ()
    {
        fWorker.Stop ();
    }

    void SendTimerEvent ()
    {
        /* [100] */
        TController.PostEvent (IEvented.gkEventTimerTick, fID);         /* [100] */
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VBrowseable#_Register()
     */
    @Override
    protected void _Register ()
    {
        TController.Register (this);
    }
    
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
                "For timer '" + fID + "': Given interval must be >= " + /* [100] */
                gkLoopIntervalMin + ". Given: " + delayMs + 
                ". Set to " + ret, this, "cTor"
            );
        }
        
        return ret;
    }
}

/*
[100]   We simply cache the unique ID rather than calling GetID()
        /every time/ we fire an event.
 */