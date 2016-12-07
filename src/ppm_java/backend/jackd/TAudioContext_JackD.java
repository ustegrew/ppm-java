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
import java.util.concurrent.atomic.AtomicInteger;
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
    
    public static boolean GetStats_IsWorking ()
    {
        boolean ret;
        
        ret = (gContext != null)  ?  gContext.IsWorking ()  :  false;
        
        return ret;
    }
    
    public static int GetStats_NumContentions_In (int iPort)
    {
        int ret;

        ret = (gContext != null)  ?  gContext.GetNumContentions_In (iPort)  :  0;
        
        return ret;
    }
    
    public static int GetStats_NumContentions_Out (int iPort)
    {
        int ret;
        
        ret = (gContext != null)  ?  gContext.GetNumContentions_Out (iPort)  :  0;
        
        return ret;
    }
    
    public static int GetStats_NumFrames ()
    {
        int ret;
        
        ret = (gContext != null)  ?  gContext.GetNumFrames ()  :  0;
            
        return ret;
    }
    
    public static int GetStats_NumOverruns_In (int iPort)
    {
        int ret;
        
        ret = (gContext != null)  ?  gContext.GetNumOverruns_In (iPort)  :  0;
        
        return ret;
    }
    
    public static int GetStats_NumOverruns_Out (int iPort)
    {
        int ret;

        ret = (gContext != null)  ?  gContext.GetNumOverruns_Out (iPort)  :  0;;
        
        return ret;
    }
    
    public static int GetStats_NumPortsIn ()
    {
        int ret;

        ret = (gContext != null)  ?  gContext.GetNumPortsIn ()  :  0;
            
        return ret;
    }
    
    public static int GetStats_NumPortsOut ()
    {
        int ret;

        ret = (gContext != null)  ?  gContext.GetNumPortsOut ()  :  0;
            
        return ret;
    }
    
    public static int GetStats_NumUnderruns_In (int iPort)
    {
        int ret;

        ret = (gContext != null)  ?  gContext.GetNumUnderruns_In (iPort)  :  0;
        
        return ret;
    }
    
    public static int GetStats_NumUnderruns_Out (int iPort)
    {
        int ret;

        ret = (gContext != null)  ?  gContext.GetNumUnderruns_Out (iPort)  :  0;
        
        return ret;
    }
    
    public static int GetStats_SampleRate ()
    {
        int ret;
        
        ret = (gContext != null)  ?  gContext.GetSampleRate ()  :  0;
            
        return ret;
    }
    
    public static void Stats_Clear_In (int iPort)
    {
        if (gContext != null)
        {
            gContext.ClearStats_In (iPort);
        }
    }
    
    public static void Stats_Clear_Out (int iPort)
    {
        if (gContext != null)
        {
            gContext.ClearStats_Out (iPort);
        }
    }
    
    boolean                                     fIsWorking;
    private AtomicInteger                       fNumFrames;
    private int                                 fSampleRate;
    
    private TAudioContext_JackD (String idClient)
    {
        super (idClient, -1, -1);
        
        TLogger.LogMessage ("Creating JackD bridge (singleton)", this, "cTor ('" + idClient + ")");
        fSampleRate      = -1;
        fIsWorking       = false;
        fNumFrames       = new AtomicInteger (0);
    }
    
    public void ClearStats_In (int iPort)
    {
        TAudioContext_Endpoint_Input    p;
        
        p = (TAudioContext_Endpoint_Input) GetPortIn (iPort);
        p.ClearStats ();
    }
    
    public void ClearStats_Out (int iPort)
    {
        TAudioContext_Endpoint_Output    p;
        
        p = (TAudioContext_Endpoint_Output) GetPortOut (iPort);
        p.ClearStats ();
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
    
    /**
     * @param iPort
     * @return
     */
    public int GetNumContentions_In (int iPort)
    {
        TAudioContext_Endpoint_Input    p;
        int                             ret;
        
        p   = (TAudioContext_Endpoint_Input) GetPortIn (iPort);
        ret = p.GetNumContentions ();
        
        return ret;
    }
    
    /**
     * @param iPort
     * @return
     */
    public int GetNumContentions_Out (int iPort)
    {
        TAudioContext_Endpoint_Output   p;
        int                             ret;
        
        p   = (TAudioContext_Endpoint_Output) GetPortOut (iPort);
        ret = p.GetNumContentions ();
        
        return ret;
    }
    
    /**
     * @return
     */
    public int GetNumFrames ()
    {
        int ret;
        
        ret = fNumFrames.getAndAdd (0);
        
        return ret;
    }
    
    /**
     * @param iPort
     * @return
     */
    public int GetNumOverruns_In (int iPort)
    {
        TAudioContext_Endpoint_Input    p;
        int                             ret;
        
        p   = (TAudioContext_Endpoint_Input) GetPortIn (iPort);
        ret = p.GetNumOverruns ();
        
        return ret;
    }
    
    /**
     * @param iPort
     * @return
     */
    public int GetNumOverruns_Out (int iPort)
    {
        TAudioContext_Endpoint_Output   p;
        int                             ret;
        
        p   = (TAudioContext_Endpoint_Output) GetPortOut (iPort);
        ret = p.GetNumOverruns ();
        
        return ret;
    }
    
    /**
     * @param iPort
     * @return
     */
    public int GetNumUnderruns_In (int iPort)
    {
        TAudioContext_Endpoint_Input    p;
        int                             ret;
        
        p   = (TAudioContext_Endpoint_Input) GetPortIn (iPort);
        ret = p.GetNumUnderruns ();
        
        return ret;
    }
    
    /**
     * @param iPort
     * @return
     */
    public int GetNumUnderruns_Out (int iPort)
    {
        TAudioContext_Endpoint_Output   p;
        int                             ret;
        
        p   = (TAudioContext_Endpoint_Output) GetPortOut (iPort);
        ret = p.GetNumUnderruns ();
        
        return ret;
    }

    /**
     * @return
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
        _ProcessStats (e);
        _ProcessOutputs (e);
        _ProcessInputs (e);
    }

    /**
     * 
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
                bIn         = portIn._RetrieveSamples ();
                bOut        = e.getOutput (i);
                nSampIn     = bIn.capacity ();
                nSampOut    = bOut.capacity ();
                
                /* [100] */
                if (nSampIn <= nSampOut)
                {
                    bOut.rewind ();
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
     * Retrieves various runtime statistics which can be queried by the
     * client program.
     * 
     * @param e     The handle allowing access to some of the statistics data.
     */
    private void _ProcessStats (JJackAudioEvent e)
    {
        FloatBuffer     b;
        int             nPortsIn;
        int             nPortsOut;
        int             nSamp;
        
        nPortsIn    = GetNumPortsIn ();
        nPortsOut   = GetNumPortsOut ();
        if (nPortsIn >= 1)
        {
            b       = e.getInput (0);
            nSamp   = b.capacity ();
            fNumFrames.getAndSet (nSamp);
        }
        else if (nPortsOut >= 1)
        {
            b       = e.getOutput (0);
            nSamp   = b.capacity ();
            fNumFrames.getAndSet (nSamp);
        }
    }

    /**
     * 
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
