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

import de.gulden.framework.jjack.JJackAudioEvent;
import de.gulden.framework.jjack.JJackAudioProcessor;
import de.gulden.framework.jjack.JJackSystem;

/**
 * Simple JJack driver load trial. Motivated by problems to load JJack from my 
 * application. This trial successfully loads the JJack driver and connects to 
 * a running instance of JackD; therefore the problem was with my application, 
 * not with JJack. 
 * 
 * @author peter
 */
public class TDev_Trial_JJack_load_01 implements JJackAudioProcessor
{
    public static void main (String[] args)
    {
        String                      idClient;
        int                         nPortsIn;
        int                         nPortsOut;
        int                         nPorts;
        
        @SuppressWarnings ("unused")
        TDev_Trial_JJack_load_01    client;
        
        idClient    = "TDev_Trial_JJack_load_01";
        nPortsIn    = 2;
        nPortsOut   = 2;
        nPorts      = nPortsIn + nPortsOut;
        
        /* Setting properties for the underlying JJack framework.      */
        System.setProperty ("jjack.client.name",                    idClient);
        System.setProperty ("jjack.verbose",                        "true");
        System.setProperty ("jjack.ports",                          Integer.toString (nPorts));
        System.setProperty ("jjack.ports.input",                    Integer.toString (nPortsIn));        
        System.setProperty ("jjack.ports.output",                   Integer.toString (nPortsOut));        
        System.setProperty ("jjack.ports.autoconnect",              Boolean.toString (false));        
        System.setProperty ("jjack.ports.input.autoconnect",        Boolean.toString (false));        
        System.setProperty ("jjack.ports.output.autoconnect",       Boolean.toString (false));
        
        /* Dummy call to load JJack driver. JJackSystem has a static 
         * block at the beginning which loads the JNI system library */
        JJackSystem.class.getName ();
        
        client = new TDev_Trial_JJack_load_01 ();
        System.out.println (JJackSystem.getInfo ());
        try {Thread.sleep (5000);} catch (InterruptedException e) {}
    }
    
    private int fCounter = 0;
    
    private TDev_Trial_JJack_load_01 ()
    {
        System.out.println ("Creating client....");
        JJackSystem.setProcessor (this);
    }

    /* (non-Javadoc)
     * @see de.gulden.framework.jjack.JJackAudioProcessor#process(de.gulden.framework.jjack.JJackAudioEvent)
     */
    @Override
    public void process (JJackAudioEvent e)
    {
        fCounter++;
        if ((fCounter % 100) == 0)
        {
            System.out.println (fCounter);
        }
    }
}
