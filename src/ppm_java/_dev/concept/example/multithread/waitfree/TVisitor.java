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

/**
 * @author peter
 *
 */
class TVisitor extends Thread
{
    private THouse                  fHouse;
    private int                     fID;
    private long                    fTTot;
    
    /**
     * 
     */
    public TVisitor (THouse house, int id)
    {
        fHouse          = house;
        fID             = id;
        fTTot           = 0;
    }
    
    @Override
    public void run ()
    {
        long            t0;
        long            t1;
        long            dT;
        boolean         isSuccess;

        do
        {
            t0        = System.currentTimeMillis ();
            isSuccess = fHouse.Visit (fID);
            t1        = System.currentTimeMillis ();
            dT        = t1 - t0;
            fTTot    += dT;
            if (isSuccess)
            {
                System.out.println ("Visitor #" + fID + ": Had an audience!     Time spent        : " + dT + "ms.");
            }
            else
            {
                System.out.println ("Visitor #" + fID + ": Unsuccessfull visit, Time spent trying : " + dT + "ms.");
                try {Thread.sleep (500);} catch (InterruptedException e) {}
            }
        }
        while (! isSuccess);
        
        System.out.println ("Visitor #" + fID + ": FINISHED.            Time spent (total): " + fTTot + "ms.");
    }
}
