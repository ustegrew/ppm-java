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

import ppm_java.backend.TController;
import ppm_java.typelib.IControllable;
import ppm_java.typelib.VFrontend;
import ppm_java.util.timer.TTickTimer;

/**
 * A linear gauge on the console.
 * 
 * @author Peter Hoppe
 */
public class TConsole_LinearGauge extends VFrontend implements IControllable
{
    /**
     * Number of sections (PPM has seven).
     */
    static final int                            gkNumSections               = 7;
    
    /**
     * Ansi control sequence: Position cursor at the bottom of the gauge.
     */
    private static final String                 gkAnsiCursorPosBottom       = "\u001B[7;1H";
    
    /**
     * Ansi control sequence: Position cursor at the top of the gauge.
     */
    private static final String                 gkAnsiCursorPosTop          = "\u001B[1;1H";
    
    /**
     * Ansi control sequence: Position cursor at the left of the left channel bar. 
     */
    private static final String                 gkAnsiCursorPosMeterL       = "\u001B[2;1H";
    
    /**
     * Ansi control sequence: Position cursor at the left of the right channel bar. 
     */
    private static final String                 gkAnsiCursorPosMeterR       = "\u001B[4;1H";
    
    /**
     * Ansi control sequence: Clear the screen.
     */
    private static final String                 gkAnsiScreenClear           = "\u001B[2J";
    
    /**
     * Maximum allowed width of gauge, in characters. 
     */
    private static final int                    gkBarLenMax                 = 280;
    
    /**
     * Minimum allowed width of gauge, in characters.
     */
    private static final int                    gkBarLenMin                 = 14;
    
    /**
     * Interval [ms] to refresh (redraw) the UI [110].
     */
    private static final long                   gkRefreshInterval           = 2000;
    
    /**
     * Character used for the tick marks.
     */
    private static final String                 gkTickmarkChar              = "|";
    
    /**
     * Creates a new instance of this frontend.
     * 
     * @param id            Unique ID as which we register this frontend.
     * @param widthCols     Length of the gauge in characters. Will be 
     *                      sanitized: Bar length limited to given boundaries, 
     *                      {@link #gkBarLenMin} and {@link #gkBarLenMax}, 
     *                      and divisible by {@link #gkNumSections}.
     */
    public static void CreateInstance (String id, int widthCols)
    {
        new TConsole_LinearGauge (id, widthCols);
    }
    
    /**
     * Length of gauge after sanitization. 
     */
    private int                                 fBarLen;
    
    /**
     * Pre cached bar with decorations.
     */
    private String                              fEmptyBar;
    
    /**
     * Pre cached outer bar.
     */
    private String                              fFramebar;
    
    /**
     * UI for meter itself, left channel. 
     */
    private TConsole_LinearGauge_MeterUI        fMeterL;
    
    /**
     * UI for meter itself, right channel. 
     */
    private TConsole_LinearGauge_MeterUI        fMeterR;
    
    /**
     * pre cached PPM divisions, numbers. 
     */
    private String                              fNumBar;
    
    /**
     * Width of each section, in characters.
     */
    private int                                 fNumColsPerSection;
    
    /**
     * Timer to redraw the UI [110].
     */
    private TTickTimer                          fRefreshTimer;

    /**
     * cTor.
     * 
     * @param id            Unique ID as which we register this frontend.
     * @param barLen        Length of gauge on screen (in columns)
     */
    public TConsole_LinearGauge (String id, int barLen)
    {
        super (id, 2);
        
        _Init (barLen);
        fMeterL             = new TConsole_LinearGauge_MeterUI (fBarLen);
        fMeterR             = new TConsole_LinearGauge_MeterUI (fBarLen);
        fRefreshTimer       = new TTickTimer                   (gkRefreshInterval);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioProcessor#CreatePort_In(java.lang.String)
     */
    @Override
    public void CreatePort_In (String id)
    {
        TConsole_LinearGauge_Endpoint_In       p;
        
        p = new TConsole_LinearGauge_Endpoint_In (id, this);
        AddPortIn (p);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IControllable#Start()
     */
    @Override
    public void Start ()
    {
        _RefreshFrame ();
        fRefreshTimer.Start ();
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IControllable#Stop()
     */
    @Override
    public void Stop ()
    {
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
     * Receives a level update on the given channel.
     * 
     * @param level         The new PPM level.
     * @param iChannel      The channel. <code>0</code> for left, <code>1</code> for right.
     */
    void _Receive (float level, int iChannel)
    {
        String                              meterBar;
        
        if (iChannel == 0)
        {
            fMeterL.Receive (level);
            meterBar = fMeterL.GetLevelBar ();
            meterBar = gkAnsiCursorPosMeterL + meterBar + gkAnsiCursorPosBottom;
        }
        else
        {
            fMeterR.Receive (level);
            meterBar = fMeterR.GetLevelBar ();
            meterBar = gkAnsiCursorPosMeterR + meterBar + gkAnsiCursorPosBottom;
        }

        _RefreshUI ();                                                  /* 110 */
        System.out.print (meterBar);
    }
    
    /**
     * initializes the UI - sanitized the bar length and prepares all 
     * pre cached decorations.
     * 
     * @param barLen        The bar length to be used. Will be sanitized: 
     *                      Bar length limited to given boundaries, 
     *                      {@link #gkBarLenMin} and {@link #gkBarLenMax}, 
     *                      and divisible by {@link #gkNumSections}.
     */
    private void _Init (int barLen)
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
    
    /**
     * Redraws the UI - worker method.
     */
    private void _RefreshFrame ()
    {
        System.out.print    (gkAnsiScreenClear);
        System.out.print    (gkAnsiCursorPosTop);
        System.out.println  (fFramebar);
        System.out.println  (fEmptyBar);
        System.out.println  (fFramebar);
        System.out.println  (fEmptyBar);
        System.out.println  (fFramebar);
        System.out.println  (fNumBar);
    }

    /**
     * If the refresh timer has expired: Redraws the 
     * UI and restarts the timer [110]. 
     */
    private void _RefreshUI ()
    {
        boolean     hasExpired;
        
        fRefreshTimer.OnTimerTick ();
        hasExpired = fRefreshTimer.HasExpired ();
        if (hasExpired)
        {
            _RefreshFrame ();
            fRefreshTimer.Start ();
        }
    }
}

/*
[100]   Guarantees that the bar length is an exact multiple of gkNumSections

[110]   The associated JJack library gave problems with the console output
        by writing output to the console and mess up the UI (Text written underneath
        and location of meter bar offset). For this reason we redraw the 
        entire UI in a regular interval and place the meter elements at an
        absolute position.
*/
