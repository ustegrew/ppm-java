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
 * Note - this program never finishes by itself unless terminated 
 *        from a thread separate from the main thread.
 * 
 * @author peter
 */
public class TDev_Trial_JJack_process_02 implements JJackAudioProcessor
{
    /**
     * Termination timer. Has to run in it's own thread (can't invoke Thread.sleep() 
     * in the main thread, as execution flow never reaches it).
     */
    private static class TTimeout extends Thread
    {
        private int fTimeout;
        
        public TTimeout (int timeout)
        {
            fTimeout = timeout;
        }
        
        public void run ()
        {
            try
            {
                Thread.sleep (fTimeout);
            }
            catch (InterruptedException e)
            {
            }
            
            Terminate ();
        }
    }
    
    private static TDev_Trial_JJack_process_02          gProcessor;
    private static TTimeout                             gTerminator;
    
    public static void main (String[] args)
    {
        /* Setting up a standard logger (logs to stdout/stderr) */
        TLogger.CreateInstance ();
        
        /* Setting up Jack client and terminator thread.  */
        gTerminator = new TTimeout (2000);
        gProcessor  = new TDev_Trial_JJack_process_02 ();
        
        /* Start termination timer. */
        gTerminator.start ();
        
    }
    
    private static void Terminate ()
    {
        System.out.println ("-------------------------------------------------------------------------------------------------");
        System.out.println ("Terminating...");
        System.out.println ("-------------------------------------------------------------------------------------------------");
        gProcessor._Terminate ();
        System.exit (0);
    }
    
    private int fIFrame;
    
    /**
     * 
     */
    public TDev_Trial_JJack_process_02 ()
    {
        _Init ();
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
        int                 nInPorts;
        FloatBuffer         inBuf;
        int                 nSamples;
        int                 i;
        float               s;
        float               sRect;
        float               peak;
        
        fIFrame++;
        System.out.print ("Frame: " + fIFrame + "; ");

        nInPorts = e.countInputPorts ();
        if (nInPorts >= 1)
        {
            inBuf       = e.getInput (0);
            nSamples    = inBuf.limit ();
            peak        = 0;
            if (nSamples >= 1)
            {
                for (i = 0; i < nSamples; i++)
                {
                    /* Get next sample. */
                    s = inBuf.get (i);
                    
                    /* Rectify (i.e. mirror a negative sample to it's positive opposite). */
                    sRect = (s < 0)  ?  -s : s;
                    
                    /* Determine peak value. */
                    peak = (sRect > peak)  ?  sRect : peak;
                }
                
                System.out.println ("Peak value (abs): " + peak + ".");
            }
            else
            {
                System.out.println ("Empty frame (no samples). Peak value unavailable.");
            }
        }
        else
        {
            System.out.println ("No ports registered. Peak value unavailable.");
        }
    }
    
    private void _Init ()
    {
        String details;

        /* Setting some properties for the Jack bridge. */
        System.setProperty ("jjack.client.name",                    "TDev_Trial_JJack_process_01");
        System.setProperty ("jjack.ports",                          Integer.toString (1));
        System.setProperty ("jjack.ports.in",                       Integer.toString (1));        
        System.setProperty ("jjack.ports.out",                      Integer.toString (0));        
        System.setProperty ("jjack.ports.autoconnect",              Boolean.toString (false));        
        System.setProperty ("jjack.ports.input.autoconnect",        Boolean.toString (false));        
        System.setProperty ("jjack.ports.output.autoconnect",       Boolean.toString (false));
        
        /* Dummy call; will load JJack bridge */
        JJackSystem.class.getName ();
        
        /* Get some basic information about this connection */
        details     = JJackSystem.getInfo ();
        fIFrame     = 0;
        
        /* Make an announcement */
        System.out.println ("For the next few seconds we'll print the peak of each incoming frame on input channel #0.");
        System.out.println ("-------------------------------------------------------------------------------------------------");
        System.out.println (details);
        System.out.println ("-------------------------------------------------------------------------------------------------");
        
        /* Activate connection to JackD. This will begin to call the ::process() method */
        JJackSystem.setProcessor (this);
    }

    private void _Terminate ()
    {
        try
        {
            JJackSystem.shutdown ();
        }
        catch (JJackException e)
        {
            e.printStackTrace();
        }
    }
}
