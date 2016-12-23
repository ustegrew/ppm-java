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

import java.awt.Color;
import eu.hansolo.steelseries.gauges.LinearBargraph;
import eu.hansolo.steelseries.tools.ThresholdType;
import ppm_java.frontend.gui.TGUISurrogate.EClipType;

/**
 * The PPM UI. 
 * 
 * Previously used the JProgressBar as level indicator. That one had problems [100]. 
 * Replaced it with a steelseries-swing gauge LinearBargraph component 
 * (https://github.com/HanSolo/SteelSeries-Swing) - which is very responsive. 
 * 
 * @author peter
 */
class TWndPPM extends javax.swing.JFrame
{
    private static final Color              kColorClip          = new Color (0xbf, 0x03, 0x03);
    private static final Color              kColorNormal        = new Color (0xe0, 0xe0, 0xe0);
    private static final Color              kColorWarn          = new Color (0xff, 0xd5, 0x00);
    private static final long               serialVersionUID    = -2335417501850617358L;
    
    private TGUISurrogate                   fConnector;
    private javax.swing.JLabel              fLblL;
    private javax.swing.JLabel              fLblR;
    private LinearBargraph                  fMeterL;
    private LinearBargraph                  fMeterR;
    private javax.swing.JPanel              fPnlMeterL;
    private javax.swing.JPanel              fPnlMeterR;
    private javax.swing.JLabel              fSigClipL;
    private javax.swing.JLabel              fSigClipR;

    public TWndPPM (TGUISurrogate surrogate)
    {
        fConnector = surrogate;
        initComponents ();
    }

    public void ClippingSet (EClipType cType, int iChannel)
    {
        _SetClipColor (cType, iChannel);
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
            case 0:
                targetGUI   = fMeterL;
                break;
            case 1:
                targetGUI   = fMeterR;
                break;
            default:
                targetGUI = fMeterL;
        }

        targetGUI.setValue (lvl);
    }
    
    private void _OnSigClipLMouseClicked(java.awt.event.MouseEvent evt)
    {
        fConnector.OnSigClip_Click ();
    }
    
    private void _OnSigClipRMouseClicked(java.awt.event.MouseEvent evt)
    {
        fConnector.OnSigClip_Click ();
    }
    
    private void _OnWindowClose(java.awt.event.WindowEvent evt)
    {
        fConnector.OnTerminate ();
    }
    
    private void _SetClipColor (EClipType cType, int iChannel)
    {
        javax.swing.JLabel  sig;
        Color               c;
        
        switch (cType)
        {
            case kClear:
                c = kColorNormal;
                break;
            case kWarn:
                c = kColorWarn;
                break;
            case kError:
                c = kColorClip;
                break;
            default:
                c = kColorNormal;
        }
        
        if (iChannel == 0)
        {
            sig = fSigClipL;
        }
        else if (iChannel == 1)
        {
            sig = fSigClipR;
        }
        else
        {
            sig = null;
        }
        
        if (sig != null)
        {
            sig.setForeground (c);
        }
        else
        {
            fSigClipL.setForeground (c);
            fSigClipR.setForeground (c);
        }
    }
    
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        fPnlMeterL = new javax.swing.JPanel();
        fLblL = new javax.swing.JLabel();
        fMeterL = new LinearBargraph();
        fSigClipL = new javax.swing.JLabel();
        fPnlMeterR = new javax.swing.JPanel();
        fLblR = new javax.swing.JLabel();
        fMeterR = new LinearBargraph ();
        fSigClipR = new javax.swing.JLabel();

        setDefaultCloseOperation (javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("PPM");
        setBounds(new java.awt.Rectangle(0, 0, 500, 250));
        setMaximumSize(new java.awt.Dimension(600, 250));
        setMinimumSize(new java.awt.Dimension(400, 250));
        setPreferredSize(new java.awt.Dimension(600, 250));
        setResizable(false);
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

        fLblL.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        fLblL.setForeground(new java.awt.Color(102, 0, 255));
        fLblL.setText("L");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        fPnlMeterL.add(fLblL, gridBagConstraints);

        fMeterL.setTitle ("");
        fMeterL.setNiceScale (false);
        fMeterL.setMinValue (0);
        fMeterL.setMaxValue (7);
        fMeterL.setMajorTickSpacing (1);
        fMeterL.setMinorTickSpacing (0.1);
        fMeterL.setTicklabelsVisible (true);
        fMeterL.setMinorTickmarkVisible (false);
        fMeterL.setValue(1);
        fMeterL.setUnitString ("");
        fMeterL.setThreshold (6);
        fMeterL.setThresholdVisible (true);
        fMeterL.setThresholdType (ThresholdType.TRIANGLE);
        fMeterL.setLcdVisible (false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 10);
        fPnlMeterL.add(fMeterL, gridBagConstraints);

        fSigClipL.setForeground(new java.awt.Color(204, 204, 204));
        fSigClipL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fSigClipL.setText("Clip");
        fSigClipL.setToolTipText("");
        fSigClipL.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                _OnSigClipLMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        fPnlMeterL.add(fSigClipL, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        getContentPane().add(fPnlMeterL, gridBagConstraints);

        fPnlMeterR.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fPnlMeterR.setLayout(new java.awt.GridBagLayout());

        fLblR.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        fLblR.setForeground(new java.awt.Color(0, 0, 255));
        fLblR.setText("R");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        fPnlMeterR.add(fLblR, gridBagConstraints);

        fMeterR.setTitle ("");
        fMeterR.setNiceScale (false);
        fMeterR.setMinValue (0);
        fMeterR.setMaxValue (7);
        fMeterR.setMajorTickSpacing (1);
        fMeterR.setMinorTickSpacing (0.1);
        fMeterR.setTicklabelsVisible (true);
        fMeterR.setMinorTickmarkVisible (false);
        fMeterR.setValue(1);
        fMeterR.setUnitString ("");
        fMeterR.setThreshold (6);
        fMeterR.setThresholdVisible (true);
        fMeterR.setThresholdType (ThresholdType.TRIANGLE);
        fMeterR.setLcdVisible (false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 10);
        fPnlMeterR.add(fMeterR, gridBagConstraints);

        fSigClipR.setForeground(new java.awt.Color(204, 204, 204));
        fSigClipR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fSigClipR.setText("Clip");
        fSigClipR.setToolTipText ("");
        fSigClipR.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                _OnSigClipRMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        fPnlMeterR.add(fSigClipR, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
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