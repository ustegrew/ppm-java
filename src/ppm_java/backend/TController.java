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

package ppm_java.backend;

import java.util.ArrayList;

import ppm_java.backend.event.TBroker;
import ppm_java.backend.module._deprecated_ppm.TNodePPMProcessor;
import ppm_java.backend.module.converter_db.TNodeConverterDb;
import ppm_java.backend.module.integrator.TNodeIntegrator_PPMBallistics;
import ppm_java.backend.module.jackd.TAudioContext_JackD;
import ppm_java.backend.module.peak_estimator.TNodePeakEstimator;
import ppm_java.backend.module.pump.TNodePump;
import ppm_java.backend.module.timer.TTimer;
import ppm_java.frontend.console.lineargauge.TConsole_LinearGauge;
import ppm_java.frontend.console.text.TConsole_TextOut;
import ppm_java.frontend.gui.lineargauge.TGUILinearGauge_Surrogate;
import ppm_java.frontend.gui.needle.TGUINeedle_Surrogate;
import ppm_java.typelib.IControllable;
import ppm_java.typelib.IEvented;
import ppm_java.typelib.IStatEnabled;
import ppm_java.typelib.IStats;
import ppm_java.typelib.VAudioDriver;
import ppm_java.typelib.VAudioObject;
import ppm_java.typelib.VAudioPort_Input;
import ppm_java.typelib.VAudioPort_Output;
import ppm_java.typelib.VAudioProcessor;
import ppm_java.typelib.VBrowseable;
import ppm_java.typelib.VFrontend;
import ppm_java.util.debug.TTimerDebugUpdate;
import ppm_java.util.debug.TWndDebug;
import ppm_java.util.logging.TLogger;

/**
 * Central application point. Contains the API to set up a ppm-java signal 
 * processing network and to run it. Note that every object we create is
 * stored in the global registry and identifiable by a unique ID 
 * (opaque handle). This simplifies the syntax for the setup of the signal
 * network enormously.
 * 
 * @author Peter Hoppe
 * @see    ppm_java.backend.boot.TSetup  The service class that contains the 
 *                                       signal processing network setup.  
 */
@SuppressWarnings ("deprecation")
public final class TController
{
    /**
     * For start/stop sequence: Time between module starts or stops (in ms). 
     * This gives each module time to settle before starting/stopping the next module.  
     */
    private static final int            gkTimeBetweenStartOrStop = 500;
    
    /**
     * Singleton <code>TController</code> instance. 
     */
    private static TController gController = new TController ();
    
    /**
     * Creates the global audio driver.
     * 
     * @param id    ID of the audio driver. 
     */
    public static void Create_AudioContext (String id)
    {
        TAudioContext_JackD.CreateInstance (id);
    }
    
    /**
     * Creates a connection for sample data, i.e. between two compatible audio ports
     * The following connections are possible:
     * 
     * <table style="border:1px;">
     *     <tr>
     *         <th>From port type</th>
     *         <th>To port type</th>
     *     </tr>
     *     <tr>
     *         <td><code>VAudioPort_Output_Samples</code></td>
     *         <td><code>VAudioPort_Input_Samples</code></td>
     *     </tr>
     *     <tr>
     *         <td><code>VAudioPort_Output_Chunks_NeedsBuffer</code></td>
     *         <td><code>VAudioPort_Input_Chunks_Buffered</code></td>
     *     </tr>
     *     <tr>
     *         <td><code>VAudioPort_Output_Chunks_NoBuffer</code></td>
     *         <td><code>VAudioPort_Input_Chunks_Unbuffered</code></td>
     *     </tr>
     * </table>
     * 
     * @param idFromPort        ID of the source port.
     * @param idToPort          ID of the target port.
     */
    public static void Create_Connection_Data (String idFromPort, String idToPort)
    {
        TConnection.CreateInstance (idFromPort, idToPort);
    }
    
    /**
     * Creates a connection for events. This subscribes an {@link IEvented} 
     * object (subscriber) to receive events from another
     * {@link VBrowseable} (subscribed). The subscriber will receive all events emitted by 
     * the subscribed and needs to implement its own event filtering to listen to the 
     * events it's interested in.
     * 
     * @param idSource          ID, subscribed.
     * @param idRecipient       ID, subscriber.
     */
    public static void Create_Connection_Events (String idSource, String idRecipient)
    {
        gController._SubscribeToEvents (idSource, idRecipient);
    }
    
    /**
     * Creates a frontend module (console): Linear gauge.  
     * 
     * @param id            ID of the frontend.
     * @param widthCols     Length of the gauge, in characters.
     * @see                 TConsole_LinearGauge
     */
    public static final void Create_Frontend_Console_LinearGauge (String id, int widthCols)
    {
        TConsole_LinearGauge.CreateInstance (id, widthCols);
    }
    
    /**
     * Creates a frontend module (console): Audio level values as stream 
     * of records in text format, to stdout.
     * 
     * @param id            ID of the frontend.
     * @see                 TConsole_TextOut
     */
    public static final void Create_Frontend_Console_TextOut (String id)
    {
        TConsole_TextOut.CreateInstance (id);
    }
    
    /**
     * Creates a frontend module (GUI): Linear gauge.
     * 
     * @param id            ID of the frontend.
     * @see                 TGUILinearGauge_Surrogate
     */
    public static final void Create_Frontend_GUI_LinearGauge (String id)
    {
        TGUILinearGauge_Surrogate.CreateInstance (id);
    }
    
    /**
     * Creates a frontend module (GUI): PPM lookalike.
     * 
     * @param id            ID of the frontend.
     * @see                 TGUINeedle_Surrogate
     */
    public static void Create_Frontend_GUI_Needle (String id)
    {
        TGUINeedle_Surrogate.CreateInstance (id);
    }
    
    /**
     * Creates a backend module: Converter, raw values [0..1] to dB.
     * 
     * @param id            ID of the module.
     * @see                 TNodeConverterDb
     */
    public static void Create_Module_ConverterDB (String id)
    {
        TNodeConverterDb.CreateInstance (id);
    }
    
    /**
     * Creates a backend module: Stepwise integrator to emulate PPM ballistics.
     * 
     * @param id            ID of the module.
     * @see                 TNodeIntegrator_PPMBallistics
     */
    public static void Create_Module_IntegratorPPMBallistics (String id)
    {
        TNodeIntegrator_PPMBallistics.CreateInstance (id);
    }
    
    /**
     * Creates a backend module: Peak estimator.
     * 
     * @param id            ID of the module.
     * @see                 TNodePeakEstimator
     */
    public static void Create_Module_PeakEstimator (String id)
    {
        TNodePeakEstimator.CreateInstance (id);
    }
    
    /**
     * Creates a backend module: PPM processor. This is an all-in-one
     * (monolithic) unit from an earlier development. Kept for 
     * educational purposes.
     * 
     * @param id            ID of the module.
     * @see                 TNodePPMProcessor
     * @deprecated          PPMProcessor has been superseded by a more modular framework
     */
    public static void Create_Module_PPMProcessor (String id)
    {
        TNodePPMProcessor.CreateInstance (id);
    }
    
    /**
     * Creates a backend module: Data pump.
     * 
     * @param id            ID of the module.
     * @see                 TNodePump
     */
    public static void Create_Module_Pump (String id)
    {
        TNodePump.CreateInstance (id);
    }
    
    /**
     * Creates a clock timer which posts a timer 
     * {@link IEvented#gkEventTimerTick event} every 
     * <code>intervalMs</code> ms.
     * 
     * @param id            ID of the module.
     * @param intervalMs    Initial timer interval in ms.
     * @see                 TTimer
     */
    public static void Create_Module_Timer (String id, int intervalMs)
    {
        TTimer.CreateInstance (id, intervalMs);
    }
    
    /**
     * Creates an input port on a module.
     * 
     * @param idModule      ID of the module.
     * @param idPort        ID of the port.
     * @see                 VAudioPort_Input
     */
    public static void Create_Port_In (String idModule, String idPort)
    {
        VAudioProcessor         proc;
        
        proc = (VAudioProcessor) gController._GetAudioObject (idModule);
        proc.CreatePort_In (idPort);
    }
    
    /**
     * Creates an output port on a module.
     * 
     * @param idModule      ID of the module.
     * @param idPort        ID of the port.
     * @see                 VAudioPort_Output
     */
    public static void Create_Port_Out (String idModule, String idPort)
    {
        VAudioProcessor         proc;
        
        proc = (VAudioProcessor) gController._GetAudioObject (idModule);
        proc.CreatePort_Out (idPort);
    }

    /**
     * Creates an entry at the end of the global start list. 
     * When we start the network, this entry will start the 
     * corresponding module. Modules will be started in 
     * list order. 
     * 
     * @param idModuleToStart   ID of the module to start.
     */
    public static void Create_StartListEntry (String idModuleToStart)
    {
        gController._Create_StartListEntry (idModuleToStart);
    }
    
    /**
     * Creates an entry at the end of the global stop list. 
     * When we terminate the network, this entry will stop the 
     * corresponding module. Modules will be stopped in 
     * list order. 
     * 
     * @param idModuleToStart   ID of the module to stop.
     */
    public static void Create_StopListEntry (String idModuleToStop)
    {
        gController._Create_StopListEntry (idModuleToStop);
    }

    /**
     * Set's the debug window's content.
     * 
     * @param text      The text to set.
     * @see             TWndDebug
     */
    public static void DebugWindowSetText (String text)
    {
        TWndDebug.SetText (text);
    }
    
    /**
     * Shows the debug window.
     * 
     * @see TWndDebug
     */
    public static void DebugWindowShow ()
    {
        TWndDebug.Show ();
    }

    /**
     * @return  The global audio driver instance.
     */
    public static TAudioContext_JackD GetAudioDriver ()
    {
        TAudioContext_JackD ret;
        
        ret = gController._GetAudioDriver ();
        
        return ret;
    }
    
    /**
     * Looks up an audio object by ID.
     * 
     * @param   id      The ID being searched for.
     * @return          The object associated with the given ID. 
     */
    public static VAudioObject GetObject (String id)
    {
        VAudioObject ret;
        
        ret = gController._GetAudioObject (id);
        
        return ret;
    }
    
    /**
     * Handler, called when a module has been terminated.
     * 
     * @param id        The module that's heen terminated.
     */
    public static void OnTerminate (String id)
    {
        gController._Unregister (id);
    }
    
    /**
     * Event API: Whenever the module <code>idSource</code> posts 
     * an event it will be distributed to the module's subscribers. 
     * Carries an extra argument as in-band data. Useful if the 
     * subscribers needs some more qualifying information with the event.
     * 
     * @param e             Event posted.
     * @param idSource      ID of the module posting the event.
     * @param arg0          The in-band data.
     * @see   #Create_Connection_Events(String, String)
     * @see   IEvented
     */
    public static void PostEvent (int e, int arg0, String idSource)
    {
        gController._PostEvent (e, arg0, idSource);
    }
    
    /**
     * Event API: Whenever the module <code>idSource</code> posts 
     * an event it will be distributed to the module's subscribers. 
     * Carries an extra argument as in-band data. Useful if the 
     * subscribers needs some more qualifying information with the event.
     * 
     * @param e             Event posted.
     * @param idSource      ID of the module posting the event.
     * @param arg0          The in-band data.
     * @see   #Create_Connection_Events(String, String)
     * @see   IEvented
     */
    public static void PostEvent (int e, long arg0, String idSource)
    {
        gController._PostEvent (e, arg0, idSource);
    }

    /**
     * Event API: Whenever the module <code>idSource</code> posts 
     * an event it will be distributed to the module's subscribers. 
     * 
     * @param e             Event posted.
     * @param idSource      ID of the module posting the event.
     * @see   #Create_Connection_Events(String, String)
     * @see   IEvented
     */
    public static void PostEvent (int e, String idSource)
    {
        gController._PostEvent (e, idSource);
    }
    
    /**
     * Event API: Whenever the module <code>idSource</code> posts 
     * an event it will be distributed to the module's subscribers.
     * Carries an extra argument as in-band data. Useful if the 
     * subscribers needs some more qualifying information with the event.
     * 
     * @param e             Event posted.
     * @param idSource      ID of the module posting the event.
     * @param arg0          The in-band data.
     * @see   #Create_Connection_Events(String, String)
     * @see   IEvented
     */
    public static void PostEvent (int e, String arg0, String idSource)
    {
        gController._PostEvent (e, arg0, idSource);
    }
    
    /**
     * Registers an audio driver with the global registry.
     * 
     * @param driver    The audio driver being registered.
     */
    public static void Register (VAudioDriver driver)
    {
        gController._Register (driver);
    }
    
    /**
     * Registers a browseable object with the global registry.
     * 
     * @param object    The object being registered.
     */
    public static void Register (VBrowseable object)
    {
        gController._Register (object);
    }
    
    /**
     * Registers a frontend with the global registry.
     * 
     * @param object    The frontend being registered.
     */
    public static void Register (VFrontend fe)
    {
        gController._Register (fe);
    }
    
    /**
     * Sets a flag so that we'll start/stop the debug UI when
     * we start/stop the network.
     */
    public static void SetDebugUI_On ()
    {
        gController._SetDebugUI_On ();
    }
    
    /**
     * Starts all modules in the start list. Modules will be started 
     * in the order the entries appear in the list.
     * 
     *  @see    #Create_StartListEntry(String)
     */
    public static void Start ()
    {
        gController._StartStop (true);
    }
    
    /**
     * Activates a runtime statistics provider, so its statistics appears 
     * on the debug UI.
     * 
     * @param p     The provider being added.
     * @see   #StatGetDumpStr()
     */
    public static void StatAddProvider (IStatEnabled p)
    {
        gController._StatCreateProviderListEntry (p);
    }
    
    /**
     * @return The accumulated statistics from all activated statistics 
     *         providers as formatted text.
     * 
     * @see    #StatAddProvider(IStatEnabled)
     */
    public static String StatGetDumpStr ()
    {
        String ret;
        
        ret = gController._StatGetDumpStr ();
        
        return ret;
    }
    
    /**
     * Terminates the program.
     */
    static void SystemTerminate ()
    {
        gController._StartStop (false);
    }

    /**
     * The debug info update worker.
     */
    private TTimerDebugUpdate           fDebugUpdateWorker;
    
    /**
     * If <code>true</code>, show debug UI when we start the network.
     */
    private boolean                     fDoShowDebugUI;
    
    /**
     * Event API: The event broker.
     */
    private TBroker                     fEventBus;
    
    /**
     * Module start list.
     */
    private ArrayList<String>           fListIDModulesStart;
    
    /**
     * Module stop list.
     */
    private ArrayList<String>           fListIDModulesStop;
    
    /**
     * Global object registry.
     */
    private TRegistry                   fRegistry;
    
    /**
     * Registry of activated statistics providers.
     */
    private ArrayList<IStatEnabled>     fStatProviders;
    
    /**
     * cTor. Made private, so nobody else can instantiate this class.
     */
    private TController ()
    {
        TShutdownHook           sh;
        
        TLogger.LogMessage ("Creating controller (singleton)", this, "cTor()");
        fRegistry           = new TRegistry             ();
        fEventBus           = new TBroker               ();
        fListIDModulesStart = new ArrayList<>           ();
        fListIDModulesStop  = new ArrayList<>           ();
        fStatProviders      = new ArrayList<>           ();
        fDebugUpdateWorker  = new TTimerDebugUpdate     ();
        fDoShowDebugUI      = false;
        
        /* Set up shutdown hook, so we can CTRL-C the application */
        sh = new TShutdownHook ();                                      /* [150] */
        Runtime.getRuntime ().addShutdownHook (sh);
    }
    
    /**
     * Creates an entry at the end of the global start list. 
     * When we start the network, this entry will start the 
     * corresponding module. Modules will be started in 
     * list order. 
     * 
     * @param idModuleToStart   ID of the module to start.
     */
    private void _Create_StartListEntry (String idModuleToStart)
    {
        fListIDModulesStart.add (idModuleToStart);
    }
    
    /**
     * Creates an entry at the end of the global stop list. 
     * When we terminate the network, this entry will stop the 
     * corresponding module. Modules will be stopped in 
     * list order. 
     * 
     * @param idModuleToStart   ID of the module to stop.
     */
    private void _Create_StopListEntry (String idModuleToStop)
    {
        fListIDModulesStop.add (idModuleToStop);
    }
    
    /**
     * @return  The global audio driver instance.
     */
    private TAudioContext_JackD _GetAudioDriver ()
    {
        TAudioContext_JackD ret;
        
        ret = (TAudioContext_JackD) fRegistry.GetAudioDriver ();
        
        return ret;
    }

    /**
     * Looks up an audio object by ID.
     * 
     * @param   id      The ID being searched for.
     * @return          The object associated with the given ID. 
     */
    private VAudioObject _GetAudioObject (String id)
    {
        VAudioObject        ret;
        
        ret = (VAudioObject) fRegistry.GetObject (id);
        
        return ret;
    }
    
    /**
     * Event API: Whenever the module <code>idSource</code> posts 
     * an event it will be distributed to the module's subscribers. 
     * Carries an extra argument as in-band data. Useful if the 
     * subscribers needs some more qualifying information with the event.
     * 
     * @param e             Event posted.
     * @param idSource      ID of the module posting the event.
     * @param arg0          The in-band data.
     * @see   IEvented
     */
    private void _PostEvent (int e, int arg0, String idSource)
    {
        fEventBus.Broker (e, arg0, idSource);
    }
    
    /**
     * Event API: Whenever the module <code>idSource</code> posts 
     * an event it will be distributed to the module's subscribers. 
     * Carries an extra argument as in-band data. Useful if the 
     * subscribers needs some more qualifying information with the event.
     * 
     * @param e             Event posted.
     * @param idSource      ID of the module posting the event.
     * @param arg0          The in-band data.
     * @see   IEvented
     */
    private void _PostEvent (int e, long arg0, String idSource)
    {
        fEventBus.Broker (e, arg0, idSource);
    }
    
    /**
     * Event API: Whenever the module <code>idSource</code> posts 
     * an event it will be distributed to the module's subscribers. 
     * 
     * @param e             Event posted.
     * @param idSource      ID of the module posting the event.
     * @see   IEvented
     */
    private void _PostEvent (int e, String idSource)
    {
        fEventBus.Broker (e, idSource);
    }
    
    /**
     * Event API: Whenever the module <code>idSource</code> posts 
     * an event it will be distributed to the module's subscribers.
     * Carries an extra argument as in-band data. Useful if the 
     * subscribers needs some more qualifying information with the event.
     * 
     * @param e             Event posted.
     * @param idSource      ID of the module posting the event.
     * @param arg0          The in-band data.
     * @see   IEvented
     */
    private void _PostEvent (int e, String arg0, String idSource)
    {
        fEventBus.Broker (e, arg0, idSource);
    }
    
    /**
     * Registers an audio driver with the global registry.
     * 
     * @param driver    The audio driver being registered.
     */
    private void _Register (VAudioDriver d)
    {
        TLogger.LogMessage ("Registering object: '" + d.GetID () + "'", this, "_Register (VAudioDriver d)");
        fRegistry.Register (d);
    }
    
    /**
     * Registers a browseable object with the global registry.
     * 
     * @param object    The object being registered.
     */
    private void _Register (VBrowseable b)
    {
        TLogger.LogMessage ("Registering object: '" + b.GetID () + "'", this, "_Register (VBrowseable b)");
        fRegistry.Register (b);
    }
    
    /**
     * Registers a frontend with the global registry.
     * 
     * @param object    The frontend being registered.
     */
    private void _Register (VFrontend fe)
    {
        TLogger.LogMessage ("Registering object: '" + fe.GetID () + "'", this, "_Register (VFrontend fe)");
        fRegistry.Register (fe);
    }
    
    /**
     * Sets a flag so that we'll start/stop the debug UI when
     * we start/stop the network.
     */
    private void _SetDebugUI_On ()
    {
        fDoShowDebugUI = true;
    }
    
    /**
     * Starts/stops all modules in the start list. Modules will be processed 
     * in the order the entries appear in the resp. list.
     * 
     * @param  doStart      If <code>true</code>, start modules in the start list.
     *                      Otherwise, stop modules in the stop list.
     */
    private void _StartStop (boolean doStart)
    {
        int                 i;
        int                 n;
        String              verb;
        String              id;
        ArrayList<String>   list;
        IControllable       mod;
        
        verb = doStart  ?  "starting"          :  "stopping";
        list = doStart  ?  fListIDModulesStart : fListIDModulesStop;
        n    = list.size ();
        if (n >= 1)
        {
            for (i = 0; i < n; i++)
            {
                id  = list.get (i);
                TLogger.LogMessage (verb + ": module " + id);
                mod = (IControllable) fRegistry.GetObject (id);
                if (doStart)
                {
                    mod.Start ();
                }
                else
                {
                    mod.Stop ();
                }
                try {Thread.sleep (gkTimeBetweenStartOrStop);} catch (InterruptedException e) {}
            }
        }
        
        if (doStart)
        {
            if (fDoShowDebugUI)
            {
                fDebugUpdateWorker.start ();
            }
        }
        else
        {
            if (fDoShowDebugUI)
            {
                fDebugUpdateWorker.Stop ();
            }
        }
    }

    /**
     * Activates a runtime statistics provider, so its statistics appears 
     * on the debug UI.
     * 
     * @param sp    The provider being added.
     */
    private void _StatCreateProviderListEntry (IStatEnabled sp)
    {
        fStatProviders.add (sp);
    }

    /**
     * @return The accumulated statistics from all activated statistics 
     *         providers as formatted text.
     */
    private String _StatGetDumpStr ()
    {
        int             i;
        int             n;
        IStatEnabled    se;
        IStats          st;
        String          ret;
        
        n = fStatProviders.size ();
        if (n >= 1)
        {
            ret = "";
            for (i = 0; i < n; i++)
            {
                se      = fStatProviders.get (i);
                st      = se.StatsGet ();
                ret    += st.GetDumpStr () +
                          "\n" +
                          "=============================================================================\n" +
                          "\n\n";
            }
        }
        else
        {
            ret = "No statistics available";
        }
        
        return ret;
    }

    /**
     * Subscribes an {@link IEvented} object (subscriber) to receive events from another
     * {@link VBrowseable} (subscribed). The subscriber will receive all events emitted by 
     * the subscribed and needs to implement its own event filtering to listen to the 
     * events it's interested in.
     * 
     * @param keySubscribed     ID, subscribed.
     * @param keySubscriber     ID, subscriber.
     */
    private void _SubscribeToEvents (String keySubscribed, String keySubscriber)
    {
        VBrowseable         subscribed;
        IEvented            subscriber;
        
        subscribed  = fRegistry.GetObject (keySubscribed);
        subscriber  = (IEvented) fRegistry.GetObject (keySubscriber);
        fEventBus.Subscribe (subscribed, subscriber);
    }

    /**
     * Hard terminates this program, i.e. by call to <code>System.exit()</code>. 
     */
    private void _SystemTerminate ()
    {
        TLogger.LogMessage ("Terminating application", this, "_SystemTerminate ()");
        System.exit (0);                                                /* [150] */
    }

    /**
     * Unregisters a module from the registry. If it's the last
     * frontend then we hard terminate the application. 
     * 
     * @param id        ID of the module being terminated.
     */
    private void _Unregister (String id)
    {
        int n;
        
        TLogger.LogMessage ("Unregistering object '" + id + "'", this, "_Unregister ('" + id + "')");
        fRegistry.Unregister (id);
        n = fRegistry.GetNumFrontends ();
        if (n <= 0)
        {
            TLogger.LogMessage ("Last frontend unregistered.", this, "Unregister (" + id + ")");
            _SystemTerminate ();
        }
    }
}

/* 
[150]   This will call the shutdown hook which will call _StartStop (false).

[160]   For preserving output to the console (used by the console frontends).
        Specifically, the JackD driver (Shared library) writes some information 
        to stdout, and when we use one of the console front ends, it injects 
        the output into the frontend output. 
        The wrapper class redirects output to a log file when the output is 
        written via System.out.print... statements. The wrapper does provide
        two methods to still write to the console (That's why the console 
        frontends do work for the console).  
*/