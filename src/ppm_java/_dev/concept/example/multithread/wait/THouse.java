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

package ppm_java._dev.concept.example.multithread.wait;

/**
 * @author peter
 *
 */
public class THouse
{
    private static final int        gkNumVisitors   = 5;
    private static final long       gkTimeAudience  = 1000;
    
    public static void main (String[] args)
    {
        THouse              house;
        
        house = new THouse ();
        house.GetMeSomeVisitors ();
    }
    
    private TVisitor[]              fVisitors;
    
    /**
     * 
     */
    public THouse ()
    {
        int i;
        
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

    public synchronized void Visit (int id)
    {
        try {Thread.sleep (gkTimeAudience);} catch (InterruptedException e) {}
    }
}
