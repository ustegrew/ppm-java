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
 * @author Peter Hoppe
 *
 */
public class TConsole_LinearGauge extends VFrontend implements IControllable
{
    static final int                            gkNumSections               = 7;
    private static final String                 gkAnsiCursorPosBottom       = "\u001B[7;1H";
    private static final String                 gkAnsiCursorPosTop          = "\u001B[1;1H";
    private static final String                 gkAnsiCursorPosMeterL       = "\u001B[2;1H";
    private static final String                 gkAnsiCursorPosMeterR       = "\u001B[4;1H";
    private static final String                 gkAnsiScreenClear           = "\u001B[2J";
    private static final int                    gkBarLenMax                 = 280;
    private static final int                    gkBarLenMin                 = 14;
    private static final long                   gkRefreshInterval           = 2000;
    private static final String                 gkTickmarkChar              = "|";
    
    /**
     * @param id
     */
    public static void CreateInstance (String id, int widthCols)
    {
        new TConsole_LinearGauge (id, widthCols);
    }
    
    private int                                 fBarLen;
    private String                              fEmptyBar;
    private String                              fFramebar;
    private TConsole_LinearGauge_MeterUI        fMeterL;
    private TConsole_LinearGauge_MeterUI        fMeterR;
    private String                              fNumBar;
    private int                                 fNumColsPerSection;
    private TTickTimer                          fRefreshTimer;

    /**
     * @param barLen        length of gauge on screen (in columns)
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
        TConsole_LinearGauge_Endpoint       p;
        
        p = new TConsole_LinearGauge_Endpoint (id, this);
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
