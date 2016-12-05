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

import ppm_java._aux.logging.TLogger;
import ppm_java._aux.storage.TAtomicBuffer.ECopyPolicy;
import ppm_java._framework.TConnection;
import ppm_java._framework.TRegistry;
import ppm_java._framework.typelib.IEvented;
import ppm_java._framework.typelib.ITriggerable;
import ppm_java._framework.typelib.VAudioObject;
import ppm_java._framework.typelib.VAudioProcessor;
import ppm_java._framework.typelib.VBrowseable;
import ppm_java._framework.typelib.VEvent;
import ppm_java.backend.jackd.TAudioContext_JackD;
import ppm_java.frontend.gui.TGUISurrogate;
import ppm_java.stream.control.bus.TBroker;
import ppm_java.stream.control.timer.TTimer;
import ppm_java.stream.node.bufferedPipe.TNodeBufferedPipe;
import ppm_java.stream.node.peak.TNodePeakDetector;

/**
 * @author peter
 *
 */
public final class TController
{
    private static TController          gController   = new TController ();
    private static final String         gkID          = TController.class.getCanonicalName ();
    
    /**
     * @param string
     * @param string2
     * @param string3
     * @param string4
     */
    public static void Connect (String idFromPort, String idToPort)
    {
        TConnection.CreateInstance (idFromPort, idToPort);
    }
    
    public static void Create_AudioContext (String idClient, int sampleRate, int bufferSize)
    {
        TAudioContext_JackD.CreateInstance (idClient);
    }
    
    /**
     * @param string
     */
    public static void Create_Frontend_GUI (String idGUI, int nChanInMax)
    {
        TGUISurrogate.CreateInstance (idGUI, nChanInMax);
    }
    
    /**
     * @param string
     * @param kcopyonget
     */
    public static void Create_Node_BufferedPipe (String id, ECopyPolicy copyPolicy)
    {
        TNodeBufferedPipe.CreateInstance (id, copyPolicy);
    }
    
    /**
     * @param string
     */
    public static void Create_Node_PeakDetector (String id)
    {
        TNodePeakDetector.CreateInstance (id);
    }
    
    public static void Create_Node_Timer (String id, int intervalMs)
    {
        TTimer.CreateInstance (id, intervalMs);
    }
    
    /**
     * @param string
     * @param string2
     */
    public static void Create_Port_In (String idProcessor, String idPort)
    {
        VAudioProcessor         proc;
        
        proc = (VAudioProcessor) gController._GetAudioObject (idProcessor);
        proc.CreatePort_In (idPort);
    }
    
    /**
     * @param string
     * @param string2
     */
    public static void Create_Port_Out (String idProcessor, String idPort)
    {
        VAudioProcessor         proc;
        
        proc = (VAudioProcessor) gController._GetAudioObject (idProcessor);
        proc.CreatePort_Out (idPort);
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
    public static void PostEvent (VEvent e)
    {
        gController._PostEvent (e);
    }
    
    /**
     * @param object
     */
    public static void Register (VBrowseable object)
    {
        gController._Register (object);
    }
    
    public static final void Start (String id)
    {
        gController._Start (id);
    }
    
    public static void SubscribeToEvents (String keySubscribed, String keySubscriber)
    {
        gController._SubscribeToEvents (keySubscribed, keySubscriber);
    }
    
    private TBroker                     fEventBus;
    private TRegistry                   fRegistry;
    
    private TController ()
    {
        TLogger.LogMessage ("Creating controller (singleton)", this, "cTor()");
        fRegistry = new TRegistry   ();
        fEventBus = new TBroker     ();
    }
    
    private VAudioObject _GetAudioObject (String id)
    {
        VAudioObject        ret;
        
        ret = (VAudioObject) fRegistry.GetObject (id);
        
        return ret;
    }
    
    private void _PostEvent (VEvent e)
    {
        fEventBus.Broker (e);
    }

    private void _Register (VBrowseable b)
    {
        TLogger.LogMessage ("Registering object: '" + b.GetID () + "'", this, "_Register (VBrowseable b)");
        fRegistry.Register (b);
    }

    private void _Start (String id)
    {
        VBrowseable  module;
        
        TLogger.LogMessage ("Starting module '" + id + "'", this, "_Start ('" + id + "')");
        module = fRegistry.GetObject (id);
        module.Start ();
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
        TEventStop                  eStop;
        TAudioContext_JackD         audioDriver;
        
        eStop       = new TEventStop (gkID);
        audioDriver = (TAudioContext_JackD) fRegistry.GetAudioDriver ();
        audioDriver.OnEvent (eStop);
        try {Thread.sleep (1000);} catch (InterruptedException e) {e.printStackTrace();}
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
