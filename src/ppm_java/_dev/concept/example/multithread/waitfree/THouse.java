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

package ppm_java._dev.concept.example.multithread.waitfree;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Peter Hoppe
 *
 */
public class THouse
{
    private static final int        gkNumVisitors   = 5;
    private static final int        gkTimeAudience  = 1000;
    private static final int        gkLocked        = 1;
    private static final int        gkUnlocked      = 0;
    
    public static void main (String[] args)
    {
        THouse              house;
        
        house = new THouse ();
        house.GetMeSomeVisitors ();
    }
    
    private AtomicInteger           fState;
    private TVisitor[]              fVisitors;
    
    /**
     * 
     */
    public THouse ()
    {
        int i;
        
        fState    = new AtomicInteger (gkUnlocked);  
        fVisitors = new TVisitor [gkNumVisitors];
        for (i = 0; i < gkNumVisitors; i++)
        {
            fVisitors[i] = new TVisitor (this, i);
        }
    }
    
    public void GetMeSomeVisitors ()
    {
        int i;
        
        for (i = 0; i < gkNumVisitors; i++)
        {
            fVisitors[i].start ();
        }
    }

    public boolean Visit (int id)
    {
        boolean isSuccess;
        
        isSuccess = fState.compareAndSet (gkUnlocked, gkLocked);
        if (isSuccess)
        {
            try {Thread.sleep (gkTimeAudience);} catch (InterruptedException e) {}
            fState.getAndSet (gkUnlocked);
        }
        
        return isSuccess;
    }
}
