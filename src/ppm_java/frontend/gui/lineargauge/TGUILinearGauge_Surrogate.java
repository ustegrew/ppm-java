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
package ppm_java.frontend.gui.lineargauge;

import ppm_java.backend.TController;
import ppm_java.typelib.IControllable;
import ppm_java.typelib.IStatEnabled;
import ppm_java.typelib.VFrontend;

/**
 * The UI controller. provides client centered access to the UI.
 *
 * @author Peter Hoppe
 */
public class TGUILinearGauge_Surrogate 
    extends     VFrontend 
    implements  IControllable, IStatEnabled
{
    /**
     * The singleton.
     */
    private static TGUILinearGauge_Surrogate        gGUI        = null;
    
    /**
     * Clipping level.
     */
    private static final float                      gkLvlClip   = -1.0f;
    
    /**
     * Warning level.
     */
    private static final float                      gkLvlWarn   = -4.0f;
    
    /**
     * Creates a new instance of the frontend.
     * 
     * @param id        Unique ID as which we register this frontend.
     */
    public static void CreateInstance (String id)
    {
        if (gGUI != null)
        {
            throw new IllegalStateException ("TGUILinearGauge_Surrogate is already instantiated.");
        }
        gGUI = new TGUILinearGauge_Surrogate (id);
    }
    
    /**
     * The GUI instance.
     */
    private TGUILinearGauge_WndPPM             fGUI;
    
    /**
     * The runtime statistics.
     */
    private TGUILinearGauge_Surrogate_Stats fStat;
    
    /**
     * cTor.
     * 
     * @param id            Unique ID as which we register this frontend.
     */
    private TGUILinearGauge_Surrogate (String id)
    {
        super (id, 2);
        
        fGUI = new TGUILinearGauge_WndPPM (this);
        fStat = new TGUILinearGauge_Surrogate_Stats (this);
        TController.StatAddProvider (this);
    }
    
    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioProcessor#CreatePortIn(java.lang.String)
     */
    @Override
    public void CreatePort_In (String id)
    {
        TGUILinearGauge_Endpoint_In       p;
        
        p = new TGUILinearGauge_Endpoint_In (id, this);
        fStat.AddChannel ();
        AddPortIn (p);
    }
    
    /* (non-Javadoc)
     * @see ppm_java.typelib.IControllable#Start()
     */
    @Override
    public void Start ()
    {
        fGUI.setVisible (true);
        fGUI.setLocationRelativeTo (null);
    }


    /* (non-Javadoc)
     * @see ppm_java.typelib.IStatEnabled#StatsGet()
     */
    @Override
    public TGUILinearGauge_Surrogate_Stats StatsGet ()
    {
        return fStat;
    }
    
    /* (non-Javadoc)
     * @see ppm_java.typelib.IControllable#Stop()
     */
    @Override
    public void Stop ()
    {
        fGUI.Terminate ();
    }
    
    /* (non-Javadoc)
     * @see ppm_java.typelib.VBrowseable#_Register()
     */
    @Override
    protected void _Register ()
    {
        TController.Register (this);
    }

    /**
     * Event handler: User clicked on the clipping indicator. 
     * This clears both clipping indicators.
     */
    void OnSigClip_Click ()
    {
        fGUI.ClippingSet (EGUILinearGauge_ClipType.kClear, -1);
    }

    /**
     * Notification - UI terminated.
     */
    void OnTerminate ()
    {
        TController.OnTerminate (GetID ());
    }

    /**
     * For the given channel, sets the signal level.
     * 
     * @param level         The level, in dB.
     * @param iChannel      Zero based index of the channel.
     */
    void SetLevel (float level, int iChannel)
    {
        float   lDisp;

        /* We update stats cycle time when channel #0 is being updated. */
        if (iChannel == 0)
            fStat.OnCycleTick ();
        
        /* Set clipping indicators. */
        if (level >= gkLvlClip)
        {
            fGUI.ClippingSet (EGUILinearGauge_ClipType.kError, iChannel);
        }
        else if (level >= gkLvlWarn)
        {
            fGUI.ClippingSet (EGUILinearGauge_ClipType.kWarn, iChannel);
        }
        
        /* Compute progress bar value */
        if (level > 0)
        {   /* ]0, ...] dB => hard limit to maximum */
            fStat.SetCalcSection (iChannel, 3);
            lDisp = 7.0f;
        }
        else if (level >= -24)
        {   /* [-24, 0] dB => working range 1..7 */                     /* [100] */
            fStat.SetCalcSection (iChannel, 2);
            lDisp = (level + 24) / 4 + 1;
        }
        else if (level >= -130)
        {
            /* [-130, -24[ dB => range 0..1 */                          /* [110] */
            fStat.SetCalcSection (iChannel, 1);
            lDisp = (level + 130) / 130;
        }
        else
        {
            /* below -130dB => hard limit to zero */
            fStat.SetCalcSection (iChannel, 0);
            lDisp = 0;
        }

        fStat.SetDBValue        (iChannel, level);
        fStat.SetDisplayValue   (iChannel, lDisp);

        fGUI.SetLevel           (lDisp, iChannel);
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