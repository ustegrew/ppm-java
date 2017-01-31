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

import eu.hansolo.steelseries.gauges.LinearBargraph;
import eu.hansolo.steelseries.tools.LedColor;
import ppm_java.frontend.gui.lineargauge.TGUILinearGauge_Surrogate.EClipType;

/**
 * The PPM UI. 
 * 
 * Previously used the JProgressBar as level indicator. That one had problems [100]. 
 * Replaced it with a steelseries-swing gauge LinearBargraph component 
 * (https://github.com/HanSolo/SteelSeries-Swing) - which is very responsive. 
 * 
 * @author Peter Hoppe
 */
class TGUILinearGauge_WndPPM extends javax.swing.JFrame
{
    private static final int                gkIChanL            = 0;
    private static final int                gkIChanR            = 1;
    private static final LedColor           kColorClip          = LedColor.RED_LED;
    private static final LedColor           kColorNormal        = LedColor.RED_LED;
    private static final LedColor           kColorWarn          = LedColor.YELLOW_LED;
    private static final long               serialVersionUID    = -2335417501850617358L;
    
    private TGUILinearGauge_Surrogate       fConnector;
    private LinearBargraph                  fMeterL;
    private LinearBargraph                  fMeterR;
    private javax.swing.JPanel              fPnlMeterL;
    private javax.swing.JPanel              fPnlMeterR;
    private TGUILinearGauge_TimerClipping   fTimerL;
    private TGUILinearGauge_TimerClipping   fTimerR;

    public TGUILinearGauge_WndPPM (TGUILinearGauge_Surrogate surrogate)
    {
        fConnector = surrogate;
        initComponents ();
        fTimerL     = new TGUILinearGauge_TimerClipping (this, gkIChanL);
        fTimerR     = new TGUILinearGauge_TimerClipping (this, gkIChanR);
        fTimerL.start ();
        fTimerR.start ();
    }

    public void ClippingSet (EClipType cType, int iChannel)
    {
        if (iChannel == gkIChanL)
        {
            fTimerL.SetClip (cType);
        }
        else if (iChannel == gkIChanR)
        {
            fTimerR.SetClip (cType);
        }
    }
    
    /**
     * Sets the level information on the UI.
     * 
     * @param lvl
     * @param iChannel
     */
    public void SetLevel (double lvl, int iChannel)
    {
        LinearBargraph        targetGUI;
        
        switch (iChannel)
        {
            case gkIChanL:
                targetGUI   = fMeterL;
                break;
            case gkIChanR:
                targetGUI   = fMeterR;
                break;
            default:
                targetGUI = fMeterL;
        }

        targetGUI.setValue (lvl);
    }
    
    public void Terminate ()
    {
        fTimerL.Terminate ();
        fTimerR.Terminate ();
    }
    
    void _SetClipping (EClipType cType, int iChannel)
    {
        LinearBargraph      meter;
        LedColor            color;
        boolean             isOn;

        switch (cType)
        {
            case kClear:
                color   = kColorNormal;
                isOn    = false;
                break;
            case kWarn:
                color   = kColorWarn;
                isOn    = true;
                break;
            case kError:
                color   = kColorClip;
                isOn    = true;
                break;
            default:
                color   = kColorNormal;
                isOn    = false;
        }
        
        if (iChannel == gkIChanL)
        {
            meter = fMeterL;
        }
        else if (iChannel == gkIChanR)
        {
            meter = fMeterR;
        }
        else
        {
            meter = null;
        }
        
        if (meter != null)
        {
            meter.setUserLedColor   (color);
            meter.setUserLedOn      (isOn);
        }
    }
    
    private void _OnWindowClose(java.awt.event.WindowEvent evt)
    {
        fConnector.OnTerminate ();
    }
    
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        fPnlMeterL = new javax.swing.JPanel();
        fMeterL = new LinearBargraph();
        fPnlMeterR = new javax.swing.JPanel();
        fMeterR = new LinearBargraph ();

        setDefaultCloseOperation (javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("PPM");
        setBounds(new java.awt.Rectangle(0, 0, 500, 250));
        setMaximumSize(new java.awt.Dimension(600, 250));
        setMinimumSize(new java.awt.Dimension(400, 249));
        setPreferredSize(new java.awt.Dimension(600, 250));
        setResizable(true);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing (java.awt.event.WindowEvent evt)
            {
                _OnWindowClose (evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        fPnlMeterL.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fPnlMeterL.setLayout(new java.awt.GridBagLayout());

        fMeterL.setTitle ("L");
        fMeterL.setNiceScale (false);
        fMeterL.setMinValue (0);
        fMeterL.setMaxValue (7);
        fMeterL.setMajorTickSpacing (1);
        fMeterL.setMinorTickSpacing (0.1);
        fMeterL.setTicklabelsVisible (true);
        fMeterL.setMinorTickmarkVisible (false);
        fMeterL.setValue(1);
        fMeterL.setUnitString ("");
        fMeterL.setThreshold (7.1);
        fMeterL.setThresholdVisible (false);
        fMeterL.setLcdVisible (false);
        fMeterL.setLedVisible (false);
        fMeterL.setUserLedVisible (true);
        fMeterL.setUserLedColor (LedColor.RED_LED);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        fPnlMeterL.add(fMeterL, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        getContentPane().add(fPnlMeterL, gridBagConstraints);

        fPnlMeterR.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fPnlMeterR.setLayout(new java.awt.GridBagLayout());

        fMeterR.setTitle ("R");
        fMeterR.setNiceScale (false);
        fMeterR.setMinValue (0);
        fMeterR.setMaxValue (7);
        fMeterR.setMajorTickSpacing (1);
        fMeterR.setMinorTickSpacing (0.1);
        fMeterR.setTicklabelsVisible (true);
        fMeterR.setMinorTickmarkVisible (false);
        fMeterR.setValue(1);
        fMeterR.setUnitString ("");
        fMeterR.setThreshold (7.1);
        fMeterR.setThresholdVisible (false);
        fMeterR.setLcdVisible (false);
        fMeterR.setLedVisible (false);
        fMeterR.setUserLedVisible (true);
        fMeterR.setUserLedColor (LedColor.RED_LED);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        fPnlMeterR.add(fMeterR, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        getContentPane().add(fPnlMeterR, gridBagConstraints);

        pack();
    }
}

/* 
[100]   Re: Previous version using JProgressBar:
        Unfortunately, repaints are mostly slow, probably due to the way that 
        the swing framework does repainting. It's much faster when, alongside this window,
        I have another window with a JTextArea open which I update in high speed {@link TWndDebug}
        filling it with a lot of text (>1024 chars? has to be new text each time?).
        At the moment I don't otherwise know how to force a repaint with each level update.
        I've tried <code>getContentPanel.repaint()</code>, setting UIManager properties
        (<code>UIManager.put ("ProgressBar.repaintInterval", new Integer (10))</code and 
        <code>UIManager.put ("ProgressBar.cycleTime", new Integer (10))</code>, 
        updating the text on the clip indicators, but to no avail. Without that
        extra debug window the UI remains sluggish.
        
        Won't put much effort into resolving this, because this is just a proof of concept application.
        Maybe I find some indicator component that does (forces?) fast repaint and can replace the 
        JProgressBar component with minimal amount of coding. For now I will keep the debug window 
        alongside this one.
        
        Note - I did address it, found a gauge component that is responsive.
*/