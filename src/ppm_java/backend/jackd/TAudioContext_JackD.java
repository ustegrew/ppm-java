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
import ppm_java._aux.typelib.IControllable;
import ppm_java._aux.typelib.IStatEnabled;
import ppm_java._aux.typelib.VAudioDriver;
import ppm_java.backend.server.TController;
import ppm_java.backend.server.TRegistry;

/**
 * Audio driver for the JackD server.
 * 
 * @author peter
 */
public final class TAudioContext_JackD 
    extends     VAudioDriver 
    implements  JJackAudioProcessor, IControllable, IStatEnabled
{
    /**
     * The JackD driver singleton.
     */
    private static TAudioContext_JackD          gContext = null;
    
    /**
     * Creates the JackD audio driver instance. Must only be called once.
     * 
     * @param   idClient                Unique ID as which the JackD driver's instance can be
     *                                  accessed in the global Registry.
     * @throws  IllegalStateException   If this method has been called before.
     * @see     {@link TRegistry#GetAudioDriver()}, {@link TRegistry#GetObject(String)}
     */
    public static void CreateInstance (String idClient)
    {
        if (gContext != null)
        {
            throw new IllegalStateException ("TAudioContext_JackD is already instantiated.");
        }
        gContext = new TAudioContext_JackD (idClient);
    }
    
    /**
     * <code>true</code> if this driver is in working order, i.e. ready to 
     * connect to a client.<br/>
     * Read-only access via {@link #IsWorking()}.
     */
    private boolean fIsWorking;
    
    /**
     * Sample rate (samp/sec) as determined by the running instance of JackD.<br/>
     * Read-only access via {@link #GetSampleRate()}.
     */
    private int fSampleRate;
    
    /**
     * Runtime statistics for diagnostic and debugging purposes.
     */
    private TAudioContext_JackD_Stats     fStats;
    
    /**
     * cTor. Creates a new instance and registers it with the global 
     * {@link TRegistry} under the given id.
     * 
     * @param   idClient                Unique ID as which the JackD driver's instance can be
     *                                  accessed in the global Registry.
     * @see     {@link TRegistry}
     */
    private TAudioContext_JackD (String idClient)
    {
        super (idClient, -1, -1);
        
        TLogger.LogMessage ("Creating JackD bridge (singleton)", this, "cTor ('" + idClient + ")");
        fStats          = new TAudioContext_JackD_Stats (this);
        fSampleRate     = -1;
        fIsWorking      = false;
        TController.StatAddProvider (this);
    }
    
    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VAudioProcessor#CreatePortIn(java.lang.String)
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
     * @see ppm_java._aux.typelib.VAudioProcessor#CreatePortOut(java.lang.String)
     */
    @Override
    public void CreatePort_Out (String id)
    {
        TAudioContext_Endpoint_Output       p;
        
        TLogger.LogMessage ("Creating output port '" + id + "'", this, "CreatePort_Out ('" + id + "')");
        p   = new TAudioContext_Endpoint_Output (id, this);
        AddPortOut (p);
    }
    
    /**
     * @return      The sample rate (in samples / sec) during this session.
     */
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
     * @see de.gulden.framework.jjack.JJackAudioProcessor#process(de.gulden.framework.jjack.JJackAudioEvent)
     */
    @Override
    public void process (JJackAudioEvent e)
    {
        _ProcessOutputs (e);
        _ProcessInputs (e);
    }
    
    public void Start ()
    {
        _LoadDriver ();
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IStatEnabled#StatsGet()
     */
    public TAudioContext_JackD_Stats StatsGet ()
    {
        return fStats;
    }
    
    public void Stop ()
    {
        _StopDriver ();
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VBrowseable#_Register()
     */
    @Override
    protected void _Register ()
    {
        TController.Register (this);
    }

    /**
     * Loads and initializes the JackD driver.
     */
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
    
    /**
     * Processes data ready to be sent to the Jack driver i.e. towards the JackD server). 
     * This sounds paradox, but we define ports as INPUTS which receive data from our  
     * program and send it to the JackD server.
     * 
     * We use a very crude overflow protection - this saves us lots of code which has
     * to deal with exceptions, resp. loop over the buffered samples etc. This is 
     * time critical data handling, therefore we want to keep it as simple as possible.
     * 
     * The client program is responsible to ensure that data frames don't exceed the 
     * prescribed buffer length.
     *  
     * @param e     The handle allowing access to the input buffers.
     */
    private void _ProcessInputs (JJackAudioEvent e)
    {
        TAudioContext_Endpoint_Input    portIn;
        int                             nPorts;
        int                             i;
        int                             nSampIn;
        int                             nSampOut;
        FloatBuffer                     bIn;
        FloatBuffer                     bOut;
        
        nPorts = GetNumPortsIn ();
        if (nPorts >= 1)
        {
            for (i = 0; i < nPorts; i++)
            {
                portIn      = (TAudioContext_Endpoint_Input) GetPortIn (i);
                bIn         = portIn.FetchPacket ();
                bOut        = e.getOutput (i);
                nSampIn     = bIn.capacity ();
                nSampOut    = bOut.capacity ();
                
                /* [100] */
                if (nSampIn <= nSampOut)
                {
                    bOut.put (bIn);
                }
            }
        }
    }
    
    /**
     * Processes data ready to be retrieved from the Jack driver i.e. away from
     * the JackD server and towards the client program. 
     * This sounds paradox, but we define ports as OUTPUTS which receive data 
     * from the JackD server and send it to the client program.
     *  
     * @param e     The handle allowing access to the output buffers.
     */
    private void _ProcessOutputs (JJackAudioEvent e)
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
     * Stops the JackD driver.  
     */
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

/*
[100]   We simply discard longer input buffers. This saves us having to 
        deal with a BufferOverflowException or having to execute code which
        puts data up to a limit etc. This is time critical code, the less
        it has to do the better! 
        The client is responsible to ensure that the number of frames is
        correct. 
*/
