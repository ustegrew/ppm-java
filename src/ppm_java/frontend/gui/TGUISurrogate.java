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

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import ppm_java._aux.storage.TAtomicDouble;
import ppm_java._aux.typelib.IControllable;
import ppm_java._aux.typelib.IStatEnabled;
import ppm_java._aux.typelib.IStats;
import ppm_java._aux.typelib.VFrontend;
import ppm_java.backend.server.TController;

/**
 *
 * @author peter
 */
public class TGUISurrogate 
    extends     VFrontend 
    implements  IControllable, IStatEnabled
{
    public static enum EClipType
    {
        kClear,
        kError,
        kWarn
    }
    
    private static final class TStat_TGUISurrogate_Record
    {
        private AtomicInteger            fCalcSection;
        private TAtomicDouble            fLastDBValue;
        private TAtomicDouble            fLastDisplayValue;
        
        public TStat_TGUISurrogate_Record ()
        {
            fCalcSection        = new AtomicInteger (0);
            fLastDBValue        = new TAtomicDouble ();
            fLastDisplayValue   = new TAtomicDouble ();
        }
        
        public void SetCalcSection (int s)
        {
            fCalcSection.getAndSet (s);
        }
        
        public void SetDBValue (double dBv)
        {
            fLastDBValue.Set (dBv);
        }
        
        public void SetDisplayValue (double dv)
        {
            fLastDisplayValue.Set (dv);
        }
        
        public String GetDumpStr ()
        {
            String ret;
            
            ret = "            peak [dB]            = " + fLastDBValue.Get ()       + "\n" +
                  "            displayValue         = " + fLastDisplayValue.Get ()  + "\n" +
                  "            meter section        = " + fCalcSection.get ()       + "\n";
            
            return ret;
        }
    }
    
    public static final class TStat_TGUISurrogate implements IStats
    {
        private TGUISurrogate                               fHost;
        private long                                        fT0;
        private AtomicLong                                  fTimeCycle;
        private ArrayList<TStat_TGUISurrogate_Record>       fStatChannels;
        
        public TStat_TGUISurrogate (TGUISurrogate host)
        {
            fHost               = host;
            fTimeCycle          = new AtomicLong (0);
            fT0                 = System.currentTimeMillis ();
            fStatChannels       = new ArrayList<> ();
        }
        
        public void AddChannel ()
        {
            TStat_TGUISurrogate_Record  r;
            
            r = new TStat_TGUISurrogate_Record ();
            fStatChannels.add (r);
        }

        public void OnCycleTick ()
        {
            long    dT;
            long    tNow;
            
            tNow            = System.currentTimeMillis ();
            dT              = tNow - fT0;
            fT0             = tNow;
            fTimeCycle.getAndSet (dT);
        }
        
        public void SetCalcSection (int iChannel, int s)
        {
            TStat_TGUISurrogate_Record  r;
            
            r = fStatChannels.get (iChannel);
            r.SetCalcSection (s);
        }
        
        public void SetDBValue (int iChannel, double dBv)
        {
            TStat_TGUISurrogate_Record  r;
            
            r = fStatChannels.get (iChannel);
            r.SetDBValue (dBv);
        }
        
        public void SetDisplayValue (int iChannel, double dv)
        {
            TStat_TGUISurrogate_Record  r;
            
            r = fStatChannels.get (iChannel);
            r.SetDisplayValue (dv);
        }
        
        /* (non-Javadoc)
         * @see ppm_java._aux.typelib.IStats#GetDumpStr()
         */
        @Override
        public String GetDumpStr ()
        {
            int                                 i;
            int                                 n;
            TStat_TGUISurrogate_Record          r;
            String                              ret;

            ret = "GUI [" + fHost.GetID () + "]:\n" +  
                    "    cycleTime [ms]               = " + fTimeCycle.getAndAdd (0)  + "\n";

            n = fStatChannels.size ();
            if (n >= 1)
            {
                ret += "    Channels:\n";
                for (i = 0; i < n; i++)
                {
                    r       = fStatChannels.get (i);
                    ret    += "        Channel [" + i + "]:\n" + r.GetDumpStr ();
                }
            }
            else
            {
                ret += "    Channels: None\n";
            }
            
            return ret;
        }
    }
    
    private static TGUISurrogate        gGUI        = null;
    private static final float          gkLvlClip   = -1.0f;
    private static final float          gkLvlWarn   = -4.0f;
    
    public static void CreateInstance (String id)
    {
        if (gGUI != null)
        {
            throw new IllegalStateException ("TGUISurrogate is already instantiated.");
        }
        gGUI = new TGUISurrogate (id);
    }
    
    private TWndPPM            fGUI;
    private TStat_TGUISurrogate fStat;
    
    private TGUISurrogate (String id)
    {
        super (id, 2, 0);
        fGUI  = new TWndPPM (this);
        fStat = new TStat_TGUISurrogate (this);
        TController.StatAddProvider (this);
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
        fStat.AddChannel ();
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
    }


    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IStatEnabled#StatsGet()
     */
    @Override
    public TStat_TGUISurrogate StatsGet ()
    {
        return fStat;
    }
    
    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IControllable#Stop()
     */
    @Override
    public void Stop ()
    {
        fGUI.Terminate ();
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
        double lDisp;

        if (iChannel == 0)
            fStat.OnCycleTick ();
        
        /* Set clipping indicators. */
        if (level >= gkLvlClip)
        {
            fGUI.SetClipping (EClipType.kError, iChannel);
        }
        else if (level >= gkLvlWarn)
        {
            fGUI.SetClipping (EClipType.kWarn, iChannel);
        }
        
        /* Compute progress bar value */
        if (level > 0)
        {   /* ]0, ...] dB => hard limit to 100 */
            fStat.SetCalcSection (iChannel, 3);
            lDisp = 7;
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