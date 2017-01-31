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

package ppm_java._dev.concept.example.multithread.waitfree_testsafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Peter Hoppe
 *
 */
public class TDev_Example_multithread_waitfree_safe
{
    private static final int            gkLocked        = 1;
    private static final int            gkUnlocked      = 0;
    
    public static void main (String[] args)
    {
        TDev_Example_multithread_waitfree_safe             unsafeClient;
        
        unsafeClient = new TDev_Example_multithread_waitfree_safe ();
        unsafeClient.start ();
    }
    
    private AtomicInteger               fState;
    private TThreadConsumer             fConsumer;
    private TThreadProducer             fProducer;
    private int                         fValue;
    
    /**
     * 
     */
    public TDev_Example_multithread_waitfree_safe ()
    {
        fConsumer   = new TThreadConsumer (this);
        fProducer   = new TThreadProducer (this);
        fValue      = 0;
        fState      = new AtomicInteger (gkUnlocked);
    }

    public void start ()
    {
        fConsumer.start ();
        fProducer.start ();
    }
    
    public boolean Pop ()
    {
        boolean success;
        
        success = fState.compareAndSet (gkUnlocked, gkLocked);
        if (success)
        {
            System.out.println ("Entering     Pop  ().  Number: " + fValue);
            if (fValue > 0)
            {
                fValue--;
                try {Thread.sleep (500);} catch (InterruptedException e) {}
            }
            System.out.println ("    Exiting  Pop  ().  Number: " + fValue);
            fState.getAndSet (gkUnlocked);
        }
        
        return success;
    }
    
    public boolean Push ()
    {
        boolean success;
        
        success = fState.compareAndSet (gkUnlocked, gkLocked);
        if (success)
        {
            System.out.println ("Entering     Push ().  Number: " + fValue);
            if (fValue < 1)
            {
                fValue++;
                try {Thread.sleep (500);} catch (InterruptedException e) {}
            }
            System.out.println ("    Exiting  Push ().  Number: " + fValue);
            fState.getAndSet (gkUnlocked);
        }
        
        return success;
    }
}
