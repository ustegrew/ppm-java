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

package ppm_java.backend.server;

import java.util.ArrayList;

import ppm_java._aux.debug.TTimerDebugUpdate;
import ppm_java._aux.debug.TWndDebug;
import ppm_java._aux.logging.TLogger;
import ppm_java._aux.typelib.IControllable;
import ppm_java._aux.typelib.IEvented;
import ppm_java._aux.typelib.IStatEnabled;
import ppm_java._aux.typelib.IStats;
import ppm_java._aux.typelib.VAudioDriver;
import ppm_java._aux.typelib.VAudioObject;
import ppm_java._aux.typelib.VAudioProcessor;
import ppm_java._aux.typelib.VBrowseable;
import ppm_java._aux.typelib.VFrontend;
import ppm_java.backend.jackd.TAudioContext_JackD;
import ppm_java.backend.server.event.TBroker;
import ppm_java.backend.server.module._deprecated_ppm.TNodePPMProcessor;
import ppm_java.backend.server.module.converter_db.TNodeConverterDb;
import ppm_java.backend.server.module.integrator.TNodeIntegrator_PPMBallistics;
import ppm_java.backend.server.module.peak_estimator.TNodePeakEstimator;
import ppm_java.backend.server.module.pump.TNodePump;
import ppm_java.backend.server.module.timer.TTimer;
import ppm_java.frontend.gui.lineargauge.TGUILinearGauge_Surrogate;
import ppm_java.frontend.gui.needle.TGUINeedle_Surrogate;

/**
 * @author peter
 */
public final class TController
{
    private static TController gController = new TController ();
    
    public static void Create_AudioContext (String id, int sampleRate, int bufferSize)
    {
        TAudioContext_JackD.CreateInstance (id);
    }
    
    /**
     * @param idFromPort
     * @param idToPort
     */
    public static void Create_Connection_Data (String idFromPort, String idToPort)
    {
        TConnection.CreateInstance (idFromPort, idToPort);
    }
    
    public static void Create_Connection_Events (String idSource, String idRecipient)
    {
        gController._SubscribeToEvents (idSource, idRecipient);
    }
    
    public static final void Create_Frontend_GUI_LinearGauge (String id)
    {
        TGUILinearGauge_Surrogate.CreateInstance (id);
    }
    
    /**
     * @param string
     */
    public static void Create_Frontend_GUI_Needle (String id)
    {
        TGUINeedle_Surrogate.CreateInstance (id);
    }
    
    public static void Create_Module_ConverterDB (String id)
    {
        TNodeConverterDb.CreateInstance (id);
    }
    
    public static void Create_Module_IntegratorPPMBallistics (String id)
    {
        TNodeIntegrator_PPMBallistics.CreateInstance (id);
    }
    
    public static void Create_Module_PeakEstimator (String id)
    {
        TNodePeakEstimator.CreateInstance (id);
    }
    
    public static void Create_Module_Pump (String id)
    {
        TNodePump.CreateInstance (id);
    }
    
    /**
     * @param id
     * @deprecated      PPMProcessor has been superseded by a more modular framework
     */
    public static void Create_Module_PPMProcessor (String id)
    {
        TNodePPMProcessor.CreateInstance (id);
    }
    
    public static void Create_Module_Timer (String id, int intervalMs)
    {
        TTimer.CreateInstance (id, intervalMs);
    }
    
    /**
     * @param string
     * @param string2
     */
    public static void Create_Port_In (String idModule, String idPort)
    {
        VAudioProcessor         proc;
        
        proc = (VAudioProcessor) gController._GetAudioObject (idModule);
        proc.CreatePort_In (idPort);
    }
    
    /**
     * @param string
     * @param string2
     */
    public static void Create_Port_Out (String idModule, String idPort)
    {
        VAudioProcessor         proc;
        
        proc = (VAudioProcessor) gController._GetAudioObject (idModule);
        proc.CreatePort_Out (idPort);
    }
    
    /**
     * @param idModuleToStart
     */
    public static void Create_StartListEntry (String idModuleToStart)
    {
        gController._Create_StartListEntry (idModuleToStart);
    }

    /**
     * @param string
     */
    public static void Create_StopListEntry (String idModuleToStop)
    {
        gController._Create_StopListEntry (idModuleToStop);
    }
    
    public static void DebugWindowShow ()
    {
        TWndDebug.Show ();
    }

    public static void DebugWindowSetText (String text)
    {
        TWndDebug.SetText (text);
    }
    
    public static TAudioContext_JackD GetAudioDriver ()
    {
        TAudioContext_JackD ret;
        
        ret = gController._GetAudioDriver ();
        
        return ret;
    }

    public static VAudioObject GetObject (String id)
    {
        VAudioObject ret;
        
        ret = gController._GetAudioObject (id);
        
        return ret;
    }
    
    public static void OnTerminate (String id)
    {
        gController._Unregister (id);
    }
    
    /**
     * @param e
     */
    public static void PostEvent (int e, String idSource)
    {
        gController._PostEvent (e, idSource);
    }
    
    /**
     * @param e
     */
    public static void PostEvent (int e, int arg0, String idSource)
    {
        gController._PostEvent (e, arg0, idSource);
    }
    
    
    /**
     * @param e
     */
    public static void PostEvent (int e, long arg0, String idSource)
    {
        gController._PostEvent (e, arg0, idSource);
    }
    /**
     * @param e
     */
    public static void PostEvent (int e, String arg0, String idSource)
    {
        gController._PostEvent (e, arg0, idSource);
    }
    
    /**
     * @param object
     */
    public static void Register (VAudioDriver driver)
    {
        gController._Register (driver);
    }
    
    /**
     * @param object
     */
    public static void Register (VBrowseable object)
    {
        gController._Register (object);
    }
    
    /**
     * @param object
     */
    public static void Register (VFrontend fe)
    {
        gController._Register (fe);
    }
    
    public static void SetDebugUI_On ()
    {
        gController._SetDebugUI_On ();
    }
    
    /**
     * 
     */
    public static void Start ()
    {
        gController._StartStop (true);
    }
    
    public static void StatAddProvider (IStatEnabled p)
    {
        gController._StatCreateProviderListEntry (p);
    }
    
    public static String StatGetDumpStr ()
    {
        String ret;
        
        ret = gController._StatGetDumpStr ();
        
        return ret;
    }
    
    private static final int            gkTimeBetweenStartOrStop = 500;

    private TBroker                     fEventBus;
    private ArrayList<String>           fListIDModulesStart;
    private ArrayList<String>           fListIDModulesStop;
    private TRegistry                   fRegistry;
    private ArrayList<IStatEnabled>     fStatProviders;
    private TTimerDebugUpdate           fDebugUpdateWorker;
    private boolean                     fDoShowDebugUI;
    
    private TController ()
    {
        TLogger.LogMessage ("Creating controller (singleton)", this, "cTor()");
        fRegistry           = new TRegistry             ();
        fEventBus           = new TBroker               ();
        fListIDModulesStart = new ArrayList<>           ();
        fListIDModulesStop  = new ArrayList<>           ();
        fStatProviders      = new ArrayList<>           ();
        fDebugUpdateWorker  = new TTimerDebugUpdate     ();
        fDoShowDebugUI      = false;
    }
    
    /**
     * @param idModuleToStart
     */
    private void _Create_StartListEntry (String idModuleToStart)
    {
        fListIDModulesStart.add (idModuleToStart);
    }
    
    /**
     * @param idModuleToStop
     */
    private void _Create_StopListEntry (String idModuleToStop)
    {
        fListIDModulesStop.add (idModuleToStop);
    }
    
    private TAudioContext_JackD _GetAudioDriver ()
    {
        TAudioContext_JackD ret;
        
        ret = (TAudioContext_JackD) fRegistry.GetAudioDriver ();
        
        return ret;
    }

    private VAudioObject _GetAudioObject (String id)
    {
        VAudioObject        ret;
        
        ret = (VAudioObject) fRegistry.GetObject (id);
        
        return ret;
    }
    
    private void _PostEvent (int e, String idSource)
    {
        fEventBus.Broker (e, idSource);
    }
    
    private void _PostEvent (int e, int arg0, String idSource)
    {
        fEventBus.Broker (e, arg0, idSource);
    }
    
    private void _PostEvent (int e, long arg0, String idSource)
    {
        fEventBus.Broker (e, arg0, idSource);
    }
    
    private void _PostEvent (int e, String arg0, String idSource)
    {
        fEventBus.Broker (e, arg0, idSource);
    }
    
    private void _Register (VAudioDriver d)
    {
        TLogger.LogMessage ("Registering object: '" + d.GetID () + "'", this, "_Register (VAudioDriver d)");
        fRegistry.Register (d);
    }
    
    private void _Register (VBrowseable b)
    {
        TLogger.LogMessage ("Registering object: '" + b.GetID () + "'", this, "_Register (VBrowseable b)");
        fRegistry.Register (b);
    }
    
    private void _Register (VFrontend fe)
    {
        TLogger.LogMessage ("Registering object: '" + fe.GetID () + "'", this, "_Register (VFrontend fe)");
        fRegistry.Register (fe);
    }
    
    private void _SetDebugUI_On ()
    {
        fDoShowDebugUI = true;
    }
    
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

    private void _StatCreateProviderListEntry (IStatEnabled sp)
    {
        fStatProviders.add (sp);
    }

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

    private void _SubscribeToEvents (String keySubscribed, String keySubscriber)
    {
        VBrowseable         subscribed;
        IEvented            subscriber;
        
        subscribed  = fRegistry.GetObject (keySubscribed);
        subscriber  = (IEvented) fRegistry.GetObject (keySubscriber);
        fEventBus.Subscribe (subscribed, subscriber);
    }

    private void _SystemTerminate ()
    {
        _StartStop (false);
        TLogger.LogMessage ("Terminating application", this, "_SystemTerminate ()");
        System.exit (0);
    }

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
