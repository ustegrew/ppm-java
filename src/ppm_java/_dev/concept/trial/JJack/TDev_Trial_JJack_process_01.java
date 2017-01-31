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

package ppm_java._dev.concept.trial.JJack;

import java.nio.FloatBuffer;
import de.gulden.framework.jjack.JJackAudioEvent;
import de.gulden.framework.jjack.JJackAudioProcessor;
import de.gulden.framework.jjack.JJackException;
import de.gulden.framework.jjack.JJackSystem;
import ppm_java.util.logging.TLogger;

/**
 * Loads the JJack driver, connects to a running instance of JackD and prints
 * the peak of every incoming frame to stdout.
 * 
 * @author Peter Hoppe
 */
public class TDev_Trial_JJack_process_01 implements JJackAudioProcessor
{
    public static void main (String[] args)
    {
        TDev_Trial_JJack_process_01 processor;
        
        /* Setting up a standard logger (logs to stdout/stderr) */
        TLogger.CreateInstance ();
        
        /* Setting up Jack client and terminator thread.  */
        processor  = new TDev_Trial_JJack_process_01 ();
        
        /* Connecting with the running instance of Jack */
        JJackSystem.setProcessor (processor);
        
        /* Terminator timer - must run in separate thread */
        new Thread ()
        {
            public void run ()
            {
                try 
                {
                    Thread.sleep (500);
                    JJackSystem.shutdown ();
                }
                catch (JJackException | InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.exit (1);
            }
        }.start ();
    }

    private int fIFrame = 0;
    
    /**
     * 
     */
    public TDev_Trial_JJack_process_01 ()
    {
        fIFrame = 0;
    }
    
    /**
     * The process callback. Called by the Jack server via the JJack bridge. 
     * Time critical! If we get stuck here, we also freeze the Jack server!
     * It's NOT advisable to do major calculations here - shift those to
     * another thread.
     * 
     * @see de.gulden.framework.jjack.JJackAudioProcessor#process(de.gulden.framework.jjack.JJackAudioEvent)
     */
    @Override
    public void process (JJackAudioEvent e)
    {
        FloatBuffer         inBuf;
        int                 nSamples;
        int                 i;
        float               s;
        float               sRect;
        float               peak;
        
        fIFrame++;
        inBuf       = e.getInput (0);
        nSamples    = inBuf.limit ();
        peak        = 0;
        for (i = 0; i < nSamples; i++)
        {
            /* Get next sample. */
            s = inBuf.get (i);
            
            /* Rectify (i.e. mirror a negative sample to it's positive opposite). */
            sRect = (s < 0)  ?  -s : s;
            
            /* Determine peak value. */
            peak = (sRect > peak)  ?  sRect : peak;
        }
        System.out.println ("Frame: " + fIFrame + "; Peak value (abs): " + peak + ".");
    }
}
