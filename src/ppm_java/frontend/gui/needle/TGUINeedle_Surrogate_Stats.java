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

package ppm_java.frontend.gui.needle;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import ppm_java.typelib.IStats;

/**
 * Combined runtime statistics for all channels.
 * 
 * @author Peter Hoppe
 */
public final class TGUINeedle_Surrogate_Stats implements IStats
{
    /**
     * The hosting frontend.
     */
    private TGUINeedle_Surrogate                                fHost;
    
    /**
     * The list of statistics records, one per channel.
     */
    private ArrayList<TGUINeedle_Surrogate_Stats_Record>        fStatChannels;
    
    /**
     * Absolute time [ms] at startup. In ms since 1970-01-01_00:00:000.
     */
    private long                                                fT0;
    
    /**
     * GUI timer cycle: Time it took between the current and the last cycle.
     */
    private AtomicLong                                          fTimeCycle;
    
    /**
     * cTor.
     * 
     * @param host      The hosting frontend.
     */
    public TGUINeedle_Surrogate_Stats (TGUINeedle_Surrogate host)
    {
        fHost               = host;
        fTimeCycle          = new AtomicLong (0);
        fT0                 = System.currentTimeMillis ();
        fStatChannels       = new ArrayList<> ();
    }
    
    /* (non-Javadoc)
     * @see ppm_java.typelib.IStats#GetDumpStr()
     */
    @Override
    public String GetDumpStr ()
    {
        int                                 i;
        int                                 n;
        TGUINeedle_Surrogate_Stats_Record          r;
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

    /**
     * Adds another channel, i.e. prepares a {@link TGUINeedle_Surrogate_Stats_Record}
     * and adds it to the {@link #fStatChannels list}.
     */
    void AddChannel ()
    {
        TGUINeedle_Surrogate_Stats_Record  r;
        
        r = new TGUINeedle_Surrogate_Stats_Record ();
        fStatChannels.add (r);
    }
    
    /**
     * Called from the UI controller, with each cycle.
     */
    void OnCycleTick ()
    {
        long    dT;
        long    tNow;
        
        tNow            = System.currentTimeMillis ();
        dT              = tNow - fT0;
        fT0             = tNow;
        fTimeCycle.getAndSet (dT);
    }
    
    /**
     * For the given channel, sets the meter section 
     * corresponding to the input value. 
     * 
     * @param iChannel      Zero based index of the channel.
     * @param s             The meter section.
     */
    void SetCalcSection (int iChannel, int s)
    {
        TGUINeedle_Surrogate_Stats_Record  r;
        
        r = fStatChannels.get (iChannel);
        r.SetCalcSection (s);
    }
    
    /**
     * For the given channel, sets the input value.
     * 
     * @param iChannel      Zero based index of the channel.
     * @param dBv           The current input value, in dB.
     */
    void SetDBValue (int iChannel, double dBv)
    {
        TGUINeedle_Surrogate_Stats_Record  r;
        
        r = fStatChannels.get (iChannel);
        r.SetDBValue (dBv);
    }
    
    /**
     * For the given channel, sets the display value.
     * 
     * @param iChannel      Zero based index of the channel.
     * @param dv            The display value, in PPM units.
     */
    void SetDisplayValue (int iChannel, double dv)
    {
        TGUINeedle_Surrogate_Stats_Record  r;
        
        r = fStatChannels.get (iChannel);
        r.SetDisplayValue (dv);
    }
}