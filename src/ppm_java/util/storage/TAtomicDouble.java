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

package ppm_java.util.storage;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * An thread safe double value. 
 * 
 * @author Peter Hoppe
 *
 */
public class TAtomicDouble
{
    /**
     * Flag value: A thread acquired the lock.
     */
    private static final int        gkLocked        = 1;
    
    /**
     * Flag value: Value can be accessed.
     */
    private static final int        gkUnlocked      = 0;
    
    /**
     * The lock.
     */
    private AtomicInteger   fLock;
    
    /**
     * The value. 
     */
    private double          fValue;
    
    /**
     * cTor.
     */
    public TAtomicDouble ()
    {
        fLock  = new AtomicInteger (gkUnlocked);
        fValue = 0;
    }
    
    /**
     * @return The value.
     */
    public double Get ()
    {
        double ret;
        
        _Lock ();
        ret = fValue;
        _Unlock ();
        
        return ret;
    }
    
    /**
     * Sets the value.
     * 
     * @param value     The value.
     */
    public void Set (double value)
    {
        _Lock ();
        fValue = value;
        _Unlock ();
    }
    
    /**
     * Definitely acquire the lock on the critical section (spin lock, as it's 
     * merry-go-round until lock has been acquired).  
     */
    private void _Lock ()
    {
        boolean isLocked;
        
        isLocked = false;
        while (! isLocked)
        {
            isLocked = fLock.compareAndSet (gkUnlocked, gkLocked);
        }
    }
    
    /**
     * Definitely release the lock on the critical section (spin lock, as it's 
     * merry-go-round until lock has been released).  
     */
    private void _Unlock ()
    {
        boolean isUnlocked;
        
        isUnlocked = false;
        while (! isUnlocked)
        {
            isUnlocked = fLock.compareAndSet (gkLocked, gkUnlocked);
        }
    }
}
