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
 * @author peter
 *
 */
public class TConsole_LinearGauge_MeterUI
{
    private static final String                 gkAnsiRed           = "\u001B[31m";
    private static final String                 gkAnsiReset         = "\u001B[0m";
    private static final String                 gkAnsiYellow        = "\u001B[33m";
    private static final float                  gkLvlClip           = -1.0f;
    private static final float                  gkLvlWarn           = -4.0f;
    private static final String                 gkMeterBarChar      = "#";
    private static final int                    gkNumSections       = TConsole_LinearGauge.gkNumSections;
    private static final long                   gkTimeClipExpire    = 1000;
    
    private int                 fBarLen;
    private TTickTimer          fClipTimer;
    private String              fColorCode;
    private String              fColorCodeReset;
    private String              fEmptyBar;
    private String              fLevelBar;
    
    
    /**
     * @param barLen 
     * 
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
     * @return
     */
    public String GetLevelBar ()
    {
        return fLevelBar;
    }

    /**
     * @param level
     */
    public void Receive (float level)
    {
        _SetClip (level);
        _SetBar  (level);
    }
    
    private void _PrecomputeFixedElements ()
    {
        int         i;
        
        fEmptyBar = "";
        for (i = 1; i <= fBarLen; i++)
        {
            fEmptyBar += " ";
        }
    }
    
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
