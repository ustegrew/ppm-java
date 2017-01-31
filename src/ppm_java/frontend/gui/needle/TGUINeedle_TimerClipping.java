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

import ppm_java.frontend.gui.needle.TGUINeedle_Surrogate.EClipType;

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
 * @author Peter Hoppe
 */
class TGUINeedle_TimerClipping extends Thread 
{
    private static final int        gkStateClear        = 0;
    private static final int        gkStateWarn         = 1;
    private static final int        gkStateErr          = 2;
    private static final int        gkStateTerm         = 10;
    private static final long       gkIntervalLoop      = 250;
    private static final long       gkIntervalWait      = 999;
    
    private AtomicInteger           fRequest;
    private int                     fState;
    private TGUINeedle_WndPPM                 fHost;
    private int                     fChannel;
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
    
    /**
     * Requests setting of the clipping LED to given level.
     */
    public void SetClip (EClipType cType)
    {
        if (cType == EClipType.kWarn)
        {
            fRequest.getAndSet (gkStateWarn);
        }
        else if (cType == EClipType.kError)
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
            fHost._SetClipping (EClipType.kClear, fChannel);
        }
        else if (fState == gkStateWarn)
        {
            fHost._SetClipping (EClipType.kWarn, fChannel);
        }
        else if (fState == gkStateErr)
        {
            fHost._SetClipping (EClipType.kError, fChannel);
        }
        else
        {
        }
    }
    
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
