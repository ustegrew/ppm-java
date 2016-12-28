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

import eu.hansolo.steelseries.tools.LedColor;
import ppm_java._aux.debug.TWndDebug;
import ppm_java.frontend.gui.TGUISurrogate.EClipType;

/**
 * The PPM UI. 
 * 
 * Unfortunately, repaints are mostly slow, probably due to the way that 
 * the swing framework does repainting. It's much faster when, alongside this window,
 * I have another window with a JTextArea open which I update in high speed {@link TWndDebug}
 * filling it with a lot of text (>1024 chars? has to be new text each time?).
 * At the moment I don't otherwise know how to force a repaint with each level update.
 * I've tried <code>getContentPanel.repaint()</code> and other stuff. But without that
 * extra debug window the UI remains sluggish.
 * 
 * Won't put much effort into resolving this, because this is just a proof of concept application.
 * Maybe I find some indicator component that does (forces?) fast repaint and can replace the 
 * gauge component with minimal amount of coding. For now I will keep the debug window 
 * alongside this one.
 * 
 * @author peter
 * @see    https://github.com/HanSolo/SteelSeries-Swing
 */
public class TWndPPM extends javax.swing.JFrame
{
    private static final int                gkIChanL            = 0;
    private static final int                gkIChanR            = 1;
    private static final LedColor           gkColorClip         = LedColor.RED_LED;
    private static final LedColor           gkColorNormal       = LedColor.RED_LED;
    private static final LedColor           gkColorWarn         = LedColor.YELLOW_LED;
    private static final long               serialVersionUID    = -9218057974191248946L;

    private TGUISurrogate                                               fConnector;
    private eu.hansolo.steelseries.gauges.Radial1Vertical               fMeterL;
    private eu.hansolo.steelseries.gauges.Radial1Vertical               fMeterR;
    private TGUITimerClipping                                           fTimerL;
    private TGUITimerClipping                                           fTimerR;

    /**
     * Creates new form TWndPPM
     */
    public TWndPPM (TGUISurrogate connector)
    {
        initComponents ();
        fConnector  = connector;
        fTimerL     = new TGUITimerClipping (this, gkIChanL);
        fTimerR     = new TGUITimerClipping (this, gkIChanR);
        fTimerL.start ();
        fTimerR.start ();
    }

    /**
     * Sets the clipping LED of the indicated channel to the desired state.
     * 
     * @param cType         The state to set.
     * @param iChannel      The channel (L or R) associated with the LED we'd like to set.
     */
    public void SetClipping (EClipType cType, int iChannel)
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
     * Sets the gauge value (i.e. pointer position) of the indicated channel to the desired level.
     * 
     * @param lvl           The level to set.
     * @param iChannel      The channel (L or R) associated with the gauge we'd like to set.
     */
    public void SetLevel (double lvl, int iChannel)
    {
        eu.hansolo.steelseries.gauges.Radial1Vertical   gauge;
        
        if (iChannel == gkIChanL)
        {
            gauge   = fMeterL;
        }
        else if (iChannel == gkIChanR)
        {
            gauge   = fMeterR;
        }
        else
        {
            gauge   = null;
        }
        
        if (gauge != null)
        {
            gauge.setValue (lvl);
        }
    }
    
    /**
     * Requests termination of this GUI window. Will shut down both
     * monostables for the clipping indicators.
     */
    public void Terminate ()
    {
        setVisible (false);
        fTimerL.Terminate ();
        fTimerR.Terminate ();
    }
    
    /**
     * Sets the clip indicator of the desired channel. Used by the clipping monostable. 
     * 
     * @param   cType
     * @param   iChannel
     * @see     TGUITimerClipping
     */
    void _SetClipping (EClipType cType, int iChannel)
    {
        eu.hansolo.steelseries.gauges.Radial1Vertical       gauge;
        LedColor                                            color;
        boolean                                             isOn;

        if (iChannel == gkIChanL)
        {
            gauge = fMeterL;
        }
        else if (iChannel == gkIChanR)
        {
            gauge = fMeterR;
        }
        else
        {
            gauge = null;
        }
        
        if (gauge != null)
        {
            if (cType == EClipType.kClear)
            {
                color   = gkColorNormal;
                isOn    = false;
            }
            else if (cType == EClipType.kWarn)
            {
                color   = gkColorWarn;
                isOn    = true;
            }
            else if (cType == EClipType.kError)
            {
                color   = gkColorClip;
                isOn    = true;
            }
            else
            {
                color   = gkColorNormal;
                isOn    = false;
            }
            
            gauge.setUserLedColor   (color);
            gauge.setUserLedOn      (isOn);
        }
    }
    
    private void _OnWindowClosing (java.awt.event.WindowEvent evt)
    {
        fConnector.OnTerminate ();
    }
    
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        fMeterL = new eu.hansolo.steelseries.gauges.Radial1Vertical();
        fMeterR = new eu.hansolo.steelseries.gauges.Radial1Vertical();

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("PPM");
        setPreferredSize(new java.awt.Dimension(640, 350));
        setResizable(true);
        setSize(new java.awt.Dimension(640, 350));
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing (java.awt.event.WindowEvent evt)
            {
                _OnWindowClosing (evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());
        getContentPane ().setBackground (new java.awt.Color (0,0,0));
        
        fMeterL.setFrameDesign(eu.hansolo.steelseries.tools.FrameDesign.ANTHRACITE);
        fMeterL.setFrameEffect(eu.hansolo.steelseries.tools.FrameEffect.EFFECT_INNER_FRAME);
        fMeterL.setFrameType(eu.hansolo.steelseries.tools.FrameType.SQUARE);
        fMeterL.setMaxValue(7.0);
        fMeterL.setMinorTickmarkVisible(false);
        fMeterL.setPointerColor(eu.hansolo.steelseries.tools.ColorDef.WHITE);
        fMeterL.setPointerType(eu.hansolo.steelseries.tools.PointerType.TYPE3);
        fMeterL.setPostsVisible(false);
        fMeterL.setRtzTimeBackToZero(1L);
        fMeterL.setRtzTimeToValue(1L);
        fMeterL.setThreshold(7.1);
        fMeterL.setLedVisible (false);
        fMeterL.setTicklabelOrientation(eu.hansolo.steelseries.tools.TicklabelOrientation.HORIZONTAL);
        fMeterL.setTitle("L");
        fMeterL.setUnitString("");
        fMeterL.setUserLedColor(gkColorNormal);
        fMeterL.setUserLedVisible(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 10);
        getContentPane().add(fMeterL, gridBagConstraints);

        fMeterR.setFrameDesign(eu.hansolo.steelseries.tools.FrameDesign.ANTHRACITE);
        fMeterR.setFrameEffect(eu.hansolo.steelseries.tools.FrameEffect.EFFECT_INNER_FRAME);
        fMeterR.setFrameType(eu.hansolo.steelseries.tools.FrameType.SQUARE);
        fMeterR.setMajorTickSpacing(1.0);
        fMeterR.setMaxValue(7.0);
        fMeterR.setMinorTickmarkVisible(false);
        fMeterR.setPointerColor(eu.hansolo.steelseries.tools.ColorDef.WHITE);
        fMeterR.setPointerType(eu.hansolo.steelseries.tools.PointerType.TYPE3);
        fMeterR.setPostsVisible(false);
        fMeterR.setRtzTimeBackToZero(1L);
        fMeterR.setRtzTimeToValue(1L);
        fMeterR.setThreshold(7.1);
        fMeterR.setLedVisible (false);
        fMeterR.setTicklabelOrientation(eu.hansolo.steelseries.tools.TicklabelOrientation.HORIZONTAL);
        fMeterR.setTitle("R");
        fMeterR.setUnitString("");
        fMeterR.setUserLedColor(eu.hansolo.steelseries.tools.LedColor.GREEN_LED);
        fMeterR.setUserLedVisible(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 5);
        getContentPane().add(fMeterR, gridBagConstraints);

        pack();
    }
}

/* 
[100]   Re: Previous version using JProgressBar:


*/