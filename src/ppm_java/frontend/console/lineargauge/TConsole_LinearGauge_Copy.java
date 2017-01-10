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

package ppm_java.frontend.console.lineargauge;

import ppm_java._aux.timer.TTickTimer;
import ppm_java._aux.typelib.EStateClipTimer;
import ppm_java._aux.typelib.IControllable;
import ppm_java._aux.typelib.VFrontend;
import ppm_java.backend.server.TController;

/**
 * @author peter
 *
 */
public class TConsole_LinearGauge_Copy extends VFrontend implements IControllable
{
    private static final String                 gkAnsiRed       = "\u001B[31m";
    private static final String                 gkAnsiReset     = "\u001B[0m";
    private static final String                 gkAnsiYellow    = "\u001B[33m";
    private static final String                 gkAnsiMoveDown  = "\u001B[1B";
    private static final String                 gkAnsiMoveUp    = "\u001B[1A";
    private static final int                    gkBarLenMax     = 280;
    private static final int                    gkBarLenMin     = 14;
    private static final float                  gkLvlClip       = -1.0f;
    private static final float                  gkLvlWarn       = -4.0f;
    private static final String                 gkMeterBarChar  = "#";
    private static final int                    gkNumSections   = 7;
    private static final String                 gkTickmarkChar  = "|";
    
    private String              fEmptyBar;
    private int                 fBarLen;
    private String              fFramebar;
    private String              fNumBar;
    private int                 fNumColsPerSection;
    private EStateClipTimer     fClipState;
    private TTickTimer          fClipTimer;
    
    /**
     * @param barLen        length of gauge on screen (in columns)
     */
    public TConsole_LinearGauge_Copy (String id, int barLen)// TODO: integrate barLen
    {
        super (id, 2, 0);
        
        _PrecomputeFixedElements (barLen);
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VAudioProcessor#CreatePort_In(java.lang.String)
     */
    @Override
    public void CreatePort_In (String id)
    {
        int                                 iP;
        TConsole_LinearGauge_Endpoint       p;
        
        iP  = GetNumPortsIn ();
        p   = new TConsole_LinearGauge_Endpoint (id, this, iP);
        AddPortIn (p);
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VAudioProcessor#CreatePort_Out(java.lang.String)
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
        _PrintGaugeFrame ();
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IControllable#Stop()
     */
    @Override
    public void Stop ()
    {
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VBrowseable#_Register()
     */
    @Override
    protected void _Register ()
    {
        TController.Register (this);
    }
    
    void _Receive (float level, int iChannel)
    {
        double          lDisp;
        int             i;
        int             nSegments;
        String          bar;

        /* Set clipping indicators. */
        _SetClipping (level);
        
        /* Compute progress bar value */
        if (level > 0)
        {   /* ]0, ...] dB => hard limit to 100 */
            lDisp = gkNumSections;
        }
        else if (level >= -24)
        {   /* [-24, 0] dB => working range 1..7 */                     /* [100] */
            lDisp = (level + 24) / 4 + 1;
        }
        else if (level >= -130)
        {
            /* [-130, -24[ dB => range 0..1 */                          /* [110] */
            lDisp = (level + 130) / 130;
        }
        else
        {
            /* below -130dB => hard limit to zero */
            lDisp = 0;
        }
        
        nSegments = (int) (fBarLen * lDisp/gkNumSections + 0.5);
        bar       = fEmptyBar + "\r";
        if (nSegments >= 1)
        {
            for (i = 1; i <= nSegments; i++)
            {
                bar += gkMeterBarChar;
            }
            bar += "\r";
        }
        System.out.print (bar);
    }
    
    private void _SetClipping (float level)
    {
        
        if (level >= gkLvlClip)
        {
            fClipState = EStateClipTimer.kClip;
        }
        else if (level >= gkLvlWarn)
        {
            fClipState = EStateClipTimer.kWarn;
        }
        
    }
    
    private void _PrecomputeFixedElements (int barLen)
    {
        int             i;
        String          spaces;
        
        /* Set bar length to sane value. Bar length must be divisable by gkNumSections */
        if (barLen < gkBarLenMin)
        {
            fBarLen = gkBarLenMin;
        }
        else if (barLen > gkBarLenMax)
        {
            fBarLen = gkBarLenMax;
        }
        else
        {
            fBarLen = gkNumSections * (barLen / gkNumSections);         /* [100] */
        }
        fNumColsPerSection = fBarLen / gkNumSections;

        /* Precompute fixed frame elements */
        spaces = "";
        for (i = 1; i <= fNumColsPerSection-1; i++)
        {
            spaces += " ";
        }
        
        fFramebar = gkTickmarkChar;
        for (i = 1; i <= gkNumSections; i++)
        {
            fFramebar += spaces + gkTickmarkChar;
        }
        
        fNumBar = "0";
        for (i = 1; i <= gkNumSections; i++)
        {
            fNumBar += spaces + i;
        }
        
        fEmptyBar = "";
        for (i = 1; i <= fBarLen; i++)
        {
            fEmptyBar += " ";
        }
    }
    
    private void _PrintGaugeFrame ()
    {
        System.out.println (fFramebar);
        System.out.println (fEmptyBar);
        System.out.println (fFramebar);
        System.out.println (fEmptyBar);
        System.out.println (fFramebar);
        System.out.println (fNumBar);
    }
    
    private void _PrintLevel (float level, int iChannel)
    {
        double          lDisp;
        int             i;
        int             nSegments;
        String          bar;

        /* Compute progress bar value */
        if (level > 0)
        {   /* ]0, ...] dB => hard limit to 100 */
            lDisp = gkNumSections;
        }
        else if (level >= -24)
        {   /* [-24, 0] dB => working range 1..7 */                     /* [100] */
            lDisp = (level + 24) / 4 + 1;
        }
        else if (level >= -130)
        {
            /* [-130, -24[ dB => range 0..1 */                          /* [110] */
            lDisp = (level + 130) / 130;
        }
        else
        {
            /* below -130dB => hard limit to zero */
            lDisp = 0;
        }
        
        /* Print progress bar */
        nSegments = (int) (fBarLen * lDisp/gkNumSections + 0.5);
        bar       = "";
        if (nSegments >= 1)
        {
            for (i = 1; i <= nSegments; i++)
            {
                bar += gkMeterBarChar;
            }
            bar += "\r";
        }
        if (iChannel == 0)
        {   /* [150] */
            bar = "\u001B[5A" + fEmptyBar + "\r" + bar + "\r" + "\u001B[5B";
        }
        else if (iChannel == 1)
        {   /* [151] */
            bar = "\u001B[3A" + fEmptyBar + "\r" + bar + "\r" + "\u001B[3B";
        }
        
        System.out.print (bar);
    }
}

/*
[100]   Guarantees that the bar length is an exact multiple of gkNumSections

[150]   Print level bar for channel 0 (L):
            Cursor up 5 lines, 
            print empty level bar, 
            cursor left to 1st col, 
            print level bar,
            cursor left to 1st col,
            cursor down 5 lines.
        
[151]   Print level bar for channel 1 (R): 
            Cursor up 3 lines, 
            print empty level bar, 
            cursor left to 1st col, 
            print level bar,
            cursor left to 1st col,
            cursor down 3 lines.
*/
