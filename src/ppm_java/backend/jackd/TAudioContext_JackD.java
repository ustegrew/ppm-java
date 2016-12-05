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

package ppm_java.backend.jackd;

import java.nio.FloatBuffer;

import de.gulden.framework.jjack.JJackAudioEvent;
import de.gulden.framework.jjack.JJackAudioProcessor;
import de.gulden.framework.jjack.JJackException;
import de.gulden.framework.jjack.JJackSystem;
import ppm_java._aux.logging.TLogger;
import ppm_java._framework.typelib.IEvented;
import ppm_java._framework.typelib.VAudioDriver;
import ppm_java._framework.typelib.VEvent;
import ppm_java.backend.server.TEventStart;
import ppm_java.backend.server.TEventStop;

/**
 * @author peter
 *
 */
public final class TAudioContext_JackD 
    extends     VAudioDriver 
    implements  JJackAudioProcessor, IEvented
{
    private static TAudioContext_JackD          gContext = null;
    
    public static void CreateInstance (String idClient)
    {
        if (gContext != null)
        {
            throw new IllegalStateException ("TAudioContext_JackD is already instantiated.");
        }
        gContext = new TAudioContext_JackD (idClient);
    }
    
    boolean                                     fIsWorking;
    private int                                 fSampleRate;
    
    private TAudioContext_JackD (String idClient)
    {
        super (idClient, -1, -1);
        
        TLogger.LogMessage ("Creating JackD bridge (singleton)", this, "cTor ('" + idClient + ")");
        fSampleRate      = -1;
        fIsWorking       = false;
    }
    
    /* (non-Javadoc)
     * @see ppm_java._framework.typelib.VAudioProcessor#CreatePortIn(java.lang.String)
     */
    @Override
    public void CreatePort_In (String id)
    {
        int                                 iPort;
        TAudioContext_Endpoint_Input        p;
        
        TLogger.LogMessage ("Creating input port '" + id + "'", this, "CreatePort_In ('" + id + "')");
        iPort   = GetNumPortsIn ();
        p       = new TAudioContext_Endpoint_Input (id, this, iPort);
        AddPortIn (p);
    }
    
    /* (non-Javadoc)
     * @see ppm_java._framework.typelib.VAudioProcessor#CreatePortOut(java.lang.String)
     */
    @Override
    public void CreatePort_Out (String id)
    {
        TAudioContext_Endpoint_Output       p;
        
        TLogger.LogMessage ("Creating output port '" + id + "'", this, "CreatePort_Out ('" + id + "')");
        p = new TAudioContext_Endpoint_Output (id, this);
        AddPortOut (p);
    }

    public int GetSampleRate ()
    {
        return fSampleRate;
    }
    
    /**
     * @return      <code>true</code>  if the underlying JackD driver has loaded successfully and can thus be used,
     *              <code>false</code> otherwise.
     */
    public boolean IsWorking ()
    {
        return fIsWorking;
    }

    /* (non-Javadoc)
     * @see ppm_java._framework.typelib.IEvented#OnEvent(ppm_java._framework.typelib.VEvent)
     */
    @Override
    public void OnEvent (VEvent e)
    {
        if (e.IsType (TEventStart.kID))
        {
            _LoadDriver ();
        }
        else if (e.IsType (TEventStop.kID))
        {
            _StopDriver ();
        }
    }

    /* (non-Javadoc)
     * @see de.gulden.framework.jjack.JJackAudioProcessor#process(de.gulden.framework.jjack.JJackAudioEvent)
     */
    @Override
    public void process (JJackAudioEvent e)
    {
        TAudioContext_Endpoint_Output   portOut;
        int                             nPorts;
        int                             i;
        FloatBuffer                     b;

        nPorts = GetNumPortsOut ();
        if (nPorts >= 1)
        {
            for (i = 0; i < nPorts; i++)
            {
                portOut = (TAudioContext_Endpoint_Output) GetPortOut (i);
                b       = e.getInput (i);
                portOut.PushPacket (b);
            }
        }
    }
    
    /**
     * @param samples
     */
    public void Receive (FloatBuffer samples, int iPort)
    {
        //x="error to get a marker";
        // TODO Auto-generated method stub
        
    }
    
    private void _LoadDriver ()
    {
        String  idClient;
        int     nPortsIn;
        int     nPortsOut;
        int     nPorts;
        
        idClient    = GetID ();
        nPortsIn    = GetNumPortsIn ();
        nPortsOut   = GetNumPortsOut ();
        nPorts      = nPortsIn + nPortsOut;
        
        /* Setting properties for the underlying JJack framework.      */
        System.setProperty ("jjack.client.name",                    idClient);
        System.setProperty ("jjack.ports",                          Integer.toString (nPorts));
        System.setProperty ("jjack.ports.in",                       Integer.toString (nPortsOut));        
        System.setProperty ("jjack.ports.out",                      Integer.toString (nPortsIn));        
        System.setProperty ("jjack.ports.autoconnect",              Boolean.toString (false));        
        System.setProperty ("jjack.ports.input.autoconnect",        Boolean.toString (false));        
        System.setProperty ("jjack.ports.output.autoconnect",       Boolean.toString (false));
        
        TLogger.LogMessage ("Loading JackD audio bridge.", this, "_LoadDriver ()");
        /* Dummy call to load JJack driver. JJackSystem has a static 
         * block at the beginning which loads the JNI system library */
        JJackSystem.class.getName ();
        fIsWorking  = JJackSystem.isInitialized ();
        
        if (fIsWorking)
        {
            fSampleRate = JJackSystem.getSampleRate ();
            
            /* Plug this instance into JackD. From now on process(JJackAudioEvent) 
             * will be called by JackD, in regular intervals. */
            TLogger.LogMessage ("Activating JackD driver.", this, "_LoadDriver");
            TLogger.LogMessage (JJackSystem.getInfo ());

            JJackSystem.setProcessor (this);
        }
        else
        {
            TLogger.LogError ("JackD driver did not load successfully", this, "_LoadDriver");
        }
    }

    private void _StopDriver ()
    {
        try
        {
            TLogger.LogMessage ("Stopping JackD driver", this, "Stop ()");
            JJackSystem.shutdown ();
        }
        catch (JJackException e)
        {
            TLogger.LogError ("Exception during Jack bridge shutdown: " + e.getMessage (), this, "Stop ()");
            e.printStackTrace();
        }
    }
}

