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

package ppm_java._dev.concept.example.multithread.unsafe;

/**
 * @author Peter Hoppe
 *
 */
public class TDev_Example_multithread_unsafe
{
    public static void main (String[] args)
    {
        TDev_Example_multithread_unsafe             unsafeClient;
        
        unsafeClient = new TDev_Example_multithread_unsafe ();
        unsafeClient.start ();
    }
    
    private TThreadConsumer             fConsumer;
    private TThreadProducer             fProducer;
    private int                         fValue;
    
    /**
     * 
     */
    public TDev_Example_multithread_unsafe ()
    {
        fConsumer   = new TThreadConsumer (this);
        fProducer   = new TThreadProducer (this);
        fValue      = 0;
    }

    public void start ()
    {
        fConsumer.start ();
        fProducer.start ();
    }
    
    public void Pop ()
    {
        System.out.println ("Entering Pop  ().  Number: " + fValue);
        if (fValue > 0)
        {
            fValue--;
            //try {Thread.sleep (500);} catch (InterruptedException e) {}
        }
        System.out.println ("Exiting  Pop  ().  Number: " + fValue);
    }
    
    public void Push ()
    {
        System.out.println ("Entering Push ().  Number: " + fValue);
        if (fValue < 1)
        {
            fValue++;
            //try {Thread.sleep (500);} catch (InterruptedException e) {}
        }
        System.out.println ("Exiting  Push ().  Number: " + fValue);
    }
}
