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

/**
 * @author peter
 */
class TThreadConsumer extends Thread
{
    private TDev_Example_multithread_waitfree_safe             fHost;
    
    /**
     * 
     */
    public TThreadConsumer (TDev_Example_multithread_waitfree_safe host)
    {
        fHost = host;
    }
    
    @Override
    public void run ()
    {
        int         i;
        int         delay;
        boolean     success;
        
        delay = 200;
        for (i = 1; i <= 10; i++)
        {
            delay -= 10;
            do
            {
                success = fHost.Pop ();
                try {Thread.sleep (delay);} catch (InterruptedException e) {}
            } while (! success);
        }
        
        System.out.println ("Consumer finished!");
    }
}
