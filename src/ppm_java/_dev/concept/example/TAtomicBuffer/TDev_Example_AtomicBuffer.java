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

package ppm_java._dev.concept.example.TAtomicBuffer;

import java.nio.FloatBuffer;

import ppm_java.util.storage.TAtomicBuffer;
import ppm_java.util.storage.TAtomicBuffer.ECopyPolicy;

/**
 * A simplistic example on how to use the TAtomicBuffer class.
 */
public class TDev_Example_AtomicBuffer
{
    private static final float []   gkFloatArray = {1, 2, 3, 4, 5, 6, 7, 8};
    
    /**
     * The producer. Provides a constant stream of packets.
     */
    private static class THighPriority_Producer implements Runnable
    {
        private float[]         fFArray;
        private TAtomicBuffer   fBuffer;

        public THighPriority_Producer (TAtomicBuffer ab)
        {
            fBuffer = ab;
            fFArray = gkFloatArray;
        }
        
        @Override
        public void run ()
        {
            int                 i;
            FloatBuffer         b;
            
            for (i = 1; i <= 1000; i++)
            {
                fFArray [0] = i;                        /* Just to have some change in the data */
                
                b = FloatBuffer.allocate (8);
                b.put (fFArray);
                fBuffer.Set (b);
                
                try {Thread.sleep (1);} catch (InterruptedException e) {e.printStackTrace();}
            }
        }
    }
    
    /**
     * The consumer. Reads the packets from the producer.
     */
    private static class TLowPriority_Consumer implements Runnable
    {
        private TAtomicBuffer           fBuffer;

        public TLowPriority_Consumer (TAtomicBuffer ab)
        {
            fBuffer = ab;
        }
        
        @Override
        public void run ()
        {
            int             i;
            FloatBuffer     b;
            
            for (i = 1; i <= 100; i++)
            {
                b = fBuffer.Get ();
                Print (b);
                try {Thread.sleep (50);} catch (InterruptedException e) {e.printStackTrace();}
            }
        }
        
        private void Print (FloatBuffer b)
        {
            int     i;
            int     n;
            float   f;
            
            n = b.capacity ();
            if (n >= 1)
            {
                for (i = 0; i < n; i++)
                {
                    f = b.get (i);
                    System.out.print (f + " ");
                }
                System.out.println ();
            }
            else
            {
                System.out.println ("{Buffer underflow}");
            }
        }
    }
    
    public static void main (String[] args)
    {
        TAtomicBuffer       ab;
        Thread              producer;
        Thread              consumer;
        
        /* Consumer is the low priority thread, so we copy data when it gets it.
         * Producer will never block. */
        ab          = new TAtomicBuffer (ECopyPolicy.kCopyOnGet);
        producer    = new Thread (new THighPriority_Producer (ab));
        consumer    = new Thread (new TLowPriority_Consumer  (ab));
        
        /* Just for testing, let's start the consumer first, so we have it get some empty frames */
        consumer.start ();
        producer.start ();
    }
}
