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

import ppm_java.util.timer.TTickTimer;

/**
 * The meter UI for a single gauge. This class doesn't do any rendering, but it creates the
 * meter bar string. The containing UI can retrieve the string ({@link #GetLevelBar()}) and 
 * render it.
 * 
 * @author Peter Hoppe
 */
public class TConsole_LinearGauge_MeterUI
{
    /**
     * Ansi control sequence for: Red font.
     */
    private static final String                 gkAnsiRed           = "\u001B[31m";
    
    /**
     * Ansi control sequence for: Font back to normal.
     */
    private static final String                 gkAnsiReset         = "\u001B[0m";
    
    /**
     * Ansi control sequence for: Yellow font.
     */
    private static final String                 gkAnsiYellow        = "\u001B[33m";
    
    /**
     * Signal clipping level.
     */
    private static final float                  gkLvlClip           = -1.0f;
    
    /**
     * Signal warning level.
     */
    private static final float                  gkLvlWarn           = -4.0f;
    
    /**
     * Base character for the meter bar.
     */
    private static final String                 gkMeterBarChar      = "#";
    
    /**
     * Number of sections on the gauge. For a PPM this is always seven.
     */
    private static final int                    gkNumSections       = TConsole_LinearGauge.gkNumSections;
    
    /**
     * Time the clip/warn indication stays.
     */
    private static final long                   gkTimeClipExpire    = 1000;
    
    /**
     * Length of the meter bar, in characters.
     */
    private int                 fBarLen;
    
    /**
     * Monostable, resets the clip indication after a time period.
     */
    private TTickTimer          fClipTimer;
    
    /**
     * Cached color code for level bar (Bar color changes on warn or clip level).
     */
    private String              fColorCode;
    
    /**
     * Color code for resetting font to normal. If there's no clipping or warning,
     * this will be empty. We always draw a meter and then reset the font, so 
     * another meter bar can be drawn with normal color (e.g. one bar drawn at 
     * clip level, and the other bar at normal level.  
     */
    private String              fColorCodeReset;
    
    /**
     * Pre cached empty meter bar.
     */
    private String              fEmptyBar;
    
    /**
     * The current level bar.
     */
    private String              fLevelBar;
    
    
    /**
     * cTor.
     * 
     * @param barLen        Length of gauge on screen (in columns)
     */
    public TConsole_LinearGauge_MeterUI (int barLen)
    {
        fBarLen         = barLen;
        fClipTimer      = new TTickTimer (gkTimeClipExpire);
        fColorCode      = "";
        fColorCodeReset = "";
        fLevelBar       = "";
        _PrecomputeFixedElements ();
    }


    /**
     * @return  The created level bar string. The containing UI must query
     *          this and render it.
     */
    public String GetLevelBar ()
    {
        return fLevelBar;
    }

    /**
     * Receives a new level value and creates the meter bar.
     * 
     * @param level     The new level value.
     */
    public void Receive (float level)
    {
        _SetClip (level);
        _SetBar  (level);
    }
    
    /**
     * Creates a pre cached empty meter bar.
     */
    private void _PrecomputeFixedElements ()
    {
        int         i;
        
        fEmptyBar = "";
        for (i = 1; i <= fBarLen; i++)
        {
            fEmptyBar += " ";
        }
    }
    
    /**
     * Computes the level bar string for the given level value.
     * 
     * @param level     The level value.
     */
    private void _SetBar (float level)
    {
        double          lDisp;
        int             i;
        int             nSegments;

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
        fLevelBar = fEmptyBar + "\r" + fColorCode;
        if (nSegments >= 1)
        {
            for (i = 1; i <= nSegments; i++)
            {
                fLevelBar += gkMeterBarChar;
            }
            fLevelBar += "\r" + fColorCodeReset;
        }
    }
    
    /**
     * Sets the level warn and clip indication for the given level value.
     * Clip will be cleared if the {@link #fClipTimer} has expired and the 
     * given level value is below clip/warn level.
     * 
     * @param level     The level value.
     */
    private void _SetClip (float level)
    {
        boolean hasExpired;

        fClipTimer.OnTimerTick ();
        hasExpired = fClipTimer.HasExpired ();
        if (level >= gkLvlClip)
        {
            fClipTimer.Start ();
            fColorCode          = gkAnsiRed;
            fColorCodeReset     = gkAnsiReset;
        }
        else if (level >= gkLvlWarn)
        {
            fClipTimer.Start ();
            fColorCode          = gkAnsiYellow;
            fColorCodeReset     = gkAnsiReset;
        }
        else if (hasExpired)
        {
            fClipTimer.Reset ();
            fColorCode          = "";
            fColorCodeReset     = "";
        }
    }
}
