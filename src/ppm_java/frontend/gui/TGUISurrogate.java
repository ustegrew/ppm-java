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
package ppm_java.frontend.gui;

import java.util.concurrent.atomic.AtomicInteger;

import ppm_java._aux.typelib.IControllable;
import ppm_java._aux.typelib.VFrontend;
import ppm_java.backend.server.TController;

/**
 *
 * @author peter
 */
public class TGUISurrogate 
    extends     VFrontend 
    implements  IControllable
{
    private static class TTimerDebugUpdate extends Thread
    {
        private AtomicInteger           fDoRun;
        
        /**
         * 
         */
        public TTimerDebugUpdate ()
        {
            fDoRun = new AtomicInteger (0);
        }
        
        public void Stop ()
        {
            fDoRun.getAndSet (0);
        }
        
        /* (non-Javadoc)
         * @see java.lang.Thread#start()
         */
        @Override
        public void run ()
        {
            String  stats;
            int     doRun;
            
            TWndDebug.Show ();
            
            fDoRun.getAndSet (1);
            doRun = 1;
            while (doRun == 1)
            {
                stats = TController.StatGetDumpStr ();
                TWndDebug.SetText (stats);
                try {Thread.sleep (1000);} catch (InterruptedException e) {}
                doRun = fDoRun.addAndGet (0);
            }
        }
    }

    public static enum EClipType
    {
        kClear,
        kError,
        kWarn
    }
    
    private static TGUISurrogate        gGUI        = null;
    private static final float          kLvlClip    = 0.95f;
    private static final float          kLvlWarn    = 0.7f;
    
    public static void CreateInstance (String id, int nMaxChanIn)
    {
        if (gGUI != null)
        {
            throw new IllegalStateException ("TGUISurrogate is already instantiated.");
        }
        gGUI = new TGUISurrogate (id, nMaxChanIn);
    }
    
    private TWndPPM             fGUI;
    private TTimerDebugUpdate   fDebugUpdateWorker;
    
    private TGUISurrogate (String id, int nMaxChanIn)
    {
        super (id, nMaxChanIn, 0);
        fGUI                = new TWndPPM (this);
        fDebugUpdateWorker  = new TTimerDebugUpdate ();
    }
    
    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VAudioProcessor#CreatePortIn(java.lang.String)
     */
    @Override
    public void CreatePort_In (String id)
    {
        int                 iPort;
        TGUI_Endpoint       port;
        
        iPort   = GetNumPortsIn ();
        port    = new TGUI_Endpoint (id, this, iPort);
        AddPortIn (port);
    }
    
    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VAudioProcessor#CreatePortOut(java.lang.String)
     */
    @Override
    public void CreatePort_Out (String id)
    {
        throw new IllegalStateException ("This is a front end class - it doesn't use output ports.");
    }
    
    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IControllable#Start()
     */
    @Override
    public void Start ()
    {
        fGUI.setVisible (true);
        fGUI.setLocationRelativeTo (null);
        fDebugUpdateWorker.start ();
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IControllable#Stop()
     */
    @Override
    public void Stop ()
    {
        fGUI.setVisible (false);
        fDebugUpdateWorker.Stop ();
    }
    
    void OnSigClip_Click ()
    {
        fGUI.ClippingSet (EClipType.kClear, -1);
    }

    void OnTerminate ()
    {
        TController.OnTerminate (GetID ());
    }

    /**
     * @param data
     */
    void SetLevel (float level, int iChannel)
    {
        float   div;
        int     lDisp;
        
        /* Set clipping indicators. */
        if (level >= kLvlClip)
        {
            fGUI.ClippingSet (EClipType.kError, iChannel);
        }
        else if (level >= kLvlWarn)
        {
            fGUI.ClippingSet (EClipType.kWarn, iChannel);
        }
        
        /* PPM II has seven divisions on the scale. We map to a progress bar with 100 divisions. */
        div = 100 / 7;  
        
        /* Compute progress bar value */
        if (level > 0)
        {   /* ]0, ...] dB => hard limit to 100 */
            lDisp = 100;
        }
        else if (level >= -24)
        {   /* [-24, 0] dB => working range 1..7 */                     /* [100] */
            lDisp = (int) (100 + div * level / 4 + 0.5);
        }
        else if (level >= -130)
        {
            /* [-130, -24[ dB => range 0..1 */                          /* [110] */
            lDisp = (int) (level/106 + 1.226 + 0.5);
        }
        else
        {
            /* below -130dB => hard limit to zero */
            lDisp = 0;
        }

        fGUI.SetLevel (lDisp, iChannel);
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VBrowseable#_Register()
     */
    @Override
    protected void _Register ()
    {
        TController.Register (this);
    }
}


/*
[100]   Each marker on a PPM scale means 4dB rise and maps to a step size of 100/7 on the GUI meter,
        i.e. 100/7  stands for 4dB. Also, scale value 1 maps to -24 dB. Hence
        x_gui = 100 + (lv_dB/4)  *  (100/7)
        
        e.g. for -24dB, x_gui = 100 + (-24/4) * (100/7) = 100 - 6*100/7 = 14.28
        
        Before the cast to integer we also add 0.5 to get a round figure. 
        
[110]   Similar calculations, except dB values between -130dB and -24dB 
        map to PPM range 0..1, i.e. 0*100/7 ... 1*100/7
        
        We translate level by 130dB and cover a dynamic range from 
        130dB - 24dB = 106dB and compute our PPM value so that 
        -130dB => PPM(0), -24dB => PPM (1). 
        lDisp = (level + 130) / 106 => We optimize to
        lDisp = level/106 + 130/106
              = level/106 + 1.226
        
        Before the cast to integer we also add 0.5 to get a round figure. 
*/