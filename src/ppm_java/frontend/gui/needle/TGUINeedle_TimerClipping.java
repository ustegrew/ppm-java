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

package ppm_java.frontend.gui.needle;

import java.util.concurrent.atomic.AtomicInteger;
import ppm_java.frontend.console.lineargauge.TConsole_LinearGauge;
import ppm_java.util.timer.TTickTimer;

/**
 * A monostable timer for a clipping LED on our PPM GUI. 
 * Sets the clipping LED to the desired state for a certain
 * time interval and resets it back to the clear state when 
 * the interval has passed. 
 * 
 * Internally, this timer is running in an endless loop and 
 * does the LED setting in response to incoming client 
 * requests. A termination request will terminate the loop (i.e.
 * this timer. To coordinate all the LED setting we use a
 * simple state machine which receives the requests and 
 * does the corresponding transistions.
 * 
 * This is an older development, the {@link TConsole_LinearGauge} 
 * uses the next development - a {@link TTickTimer} - for the clip 
 * setting. That one doesn't use its own thread and therefore would 
 * be better to use here. Since this one works, and it's a prototype
 * application we keep this solution. 
 * 
 * @author Peter Hoppe
 */
class TGUINeedle_TimerClipping extends Thread 
{
    /**
     * Interval time [ms] for internal loop.
     */
    private static final long       gkIntervalLoop      = 250;
    
    /**
     * Wait time [ms] until a clip/warn condition auto clears.
     */
    private static final long       gkIntervalWait      = 999;
    
    /**
     * Flag value: Internal state: Clip clear.
     */
    private static final int        gkStateClear        = 0;
    
    /**
     * Flag value: Internal state: Clipping level. 
     */
    private static final int        gkStateErr          = 2;
    
    /**
     * Flag value: Internal state: Thread terminated. 
     */
    private static final int        gkStateTerm         = 10;
    
    /**
     * Flag value: Internal state: Warning level. 
     */
    private static final int        gkStateWarn         = 1;
    
    /**
     * The channel with which this clip timer is associated.
     */
    private int                     fChannel;
    
    /**
     * The hosting module.
     */
    private TGUINeedle_WndPPM       fHost;
    
    /**
     * Request flag.
     */
    private AtomicInteger           fRequest;
    
    /**
     * Internal state.
     */
    private int                     fState;
    
    /**
     * Absolute time [ms, since 1970-01-01 00:00] the current cycle started.
     */
    private long                    fTLast;
    
    /**
     * cTor.
     * 
     * @param host          The PPM GUI we interoperate with.
     * @param iChannel      The specific channel of which we set the clipping LED.  
     */
    public TGUINeedle_TimerClipping (TGUINeedle_WndPPM host, int iChannel)
    {
        fRequest    = new AtomicInteger (gkStateClear);
        fState      = gkStateClear;
        fHost       = host;
        fChannel    = iChannel;
    }
    
    @Override
    public void run ()
    {
        fTLast = System.currentTimeMillis ();
        while (fState != gkStateTerm)
        {
            _DoTransition ();
            try {Thread.sleep (gkIntervalLoop);} catch (InterruptedException e) {}
        }
        
    }
    
    /**
     * Requests setting of the clipping LED to given level.
     */
    public void SetClip (EGUINeedle_Surrogate_ClipType cType)
    {
        if (cType == EGUINeedle_Surrogate_ClipType.kWarn)
        {
            fRequest.getAndSet (gkStateWarn);
        }
        else if (cType == EGUINeedle_Surrogate_ClipType.kError)
        {
            fRequest.getAndSet (gkStateErr);
        }
    }
    
    /**
     * Requests terminating this timer. In the background, this timer runs an endless loop
     * which we terminate when requested. 
     */
    public void Terminate ()
    {
        fRequest.getAndSet (gkStateTerm);
    }
    
    /**
     * Automaton core method. Sets the various states in response to incoming
     * client requests and sets the associated clipping LED accordingly.  
     */
    private void _DoTransition ()
    {
        int         request;
        
        request = fRequest.getAndAdd (0);                               /* [100] */
        if (fState == gkStateClear)
        {
            if (request == gkStateClear)
            {
            }
            else if (request == gkStateWarn)
            {
                fState = gkStateWarn;
                _SetLED ();
            }
            else if (request == gkStateErr)
            {
                fState = gkStateErr;
                _SetLED ();
            }
            else
            {
                fState = gkStateTerm;
            }
        }
        else if (fState == gkStateWarn)
        {
            _TryReset ();
            if (request == gkStateClear)
            {
                fState = gkStateClear;
                _SetLED ();
            }
            else if (request == gkStateWarn)
            {
                
            }
            else if (request == gkStateErr)
            {
                fState = gkStateErr;
                _SetLED ();
            }
            else
            {
                fState = gkStateTerm;
            }
        }
        else if (fState == gkStateErr)
        {
            _TryReset ();
            if (request == gkStateClear)
            {
                fState = gkStateClear;
                _SetLED ();
            }
            else if (request == gkStateWarn)
            {
                fState = gkStateWarn;
                _SetLED ();
            }
            else if (request == gkStateErr)
            {
                
            }
            else
            {
                fState = gkStateTerm;
            }
        }
        else
        {
            if (request == gkStateClear)
            {
            }
            else if (request == gkStateWarn)
            {
            }
            else if (request == gkStateErr)
            {
            }
            else
            {
            }
        }
    }

    /**
     * Tells the hosting GUI to set the associated clipping LED.
     */
    private void _SetLED ()
    {
        if (fState == gkStateClear)
        {
            fHost._SetClipping (EGUINeedle_Surrogate_ClipType.kClear, fChannel);
        }
        else if (fState == gkStateWarn)
        {
            fHost._SetClipping (EGUINeedle_Surrogate_ClipType.kWarn, fChannel);
        }
        else if (fState == gkStateErr)
        {
            fHost._SetClipping (EGUINeedle_Surrogate_ClipType.kError, fChannel);
        }
        else
        {
        }
    }
    
    /**
     * Reset the clip/warn condition if {@link #gkIntervalWait wait interval}
     * has passed.
     */
    private void _TryReset ()
    {
        long            t;
        long            dt;
        
        t  = System.currentTimeMillis ();
        dt = t - fTLast;
        if (dt > gkIntervalWait)
        {
            fTLast = t;
            fRequest.getAndSet (gkStateClear);
        }
    }
}

/*
[100]   A weird way to retrieve the value of fState - return value and add zero. 
        But the AtomicInteger::get() method seems to be non-atomic (according to the docs),
        whilst AtomicInteger::getAndAdd() is atomic.
*/
