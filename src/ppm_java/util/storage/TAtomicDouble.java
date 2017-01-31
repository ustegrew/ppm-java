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
 * @author Peter Hoppe
 *
 */
public class TAtomicDouble
{
    private static final int        gkLocked        = 1;
    private static final int        gkUnlocked      = 0;
    
    private AtomicInteger   fLock;
    private double          fValue;
    
    public TAtomicDouble ()
    {
        fLock  = new AtomicInteger (gkUnlocked);
        fValue = 0;
    }
    
    public double Get ()
    {
        double ret;
        
        _Lock ();
        ret = fValue;
        _Unlock ();
        
        return ret;
    }
    
    public void Set (double value)
    {
        _Lock ();
        fValue = value;
        _Unlock ();
    }
    
    private void _Lock ()
    {
        boolean isLocked;
        
        isLocked = false;
        while (! isLocked)
        {
            isLocked = fLock.compareAndSet (gkUnlocked, gkLocked);
        }
    }
    
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
