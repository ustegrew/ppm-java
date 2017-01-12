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

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import ppm_java.typelib.IStats;

public final class TGUILinearGauge_Surrogate_Stats implements IStats
{
    private TGUILinearGauge_Surrogate                               fHost;
    private long                                        fT0;
    private AtomicLong                                  fTimeCycle;
    private ArrayList<TGUILinearGauge_Surrogate_Stats_Record>       fStatChannels;
    
    public TGUILinearGauge_Surrogate_Stats (TGUILinearGauge_Surrogate host)
    {
        fHost               = host;
        fTimeCycle          = new AtomicLong (0);
        fT0                 = System.currentTimeMillis ();
        fStatChannels       = new ArrayList<> ();
    }
    
    public void AddChannel ()
    {
        TGUILinearGauge_Surrogate_Stats_Record  r;
        
        r = new TGUILinearGauge_Surrogate_Stats_Record ();
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
        TGUILinearGauge_Surrogate_Stats_Record  r;
        
        r = fStatChannels.get (iChannel);
        r.SetCalcSection (s);
    }
    
    public void SetDBValue (int iChannel, double dBv)
    {
        TGUILinearGauge_Surrogate_Stats_Record  r;
        
        r = fStatChannels.get (iChannel);
        r.SetDBValue (dBv);
    }
    
    public void SetDisplayValue (int iChannel, double dv)
    {
        TGUILinearGauge_Surrogate_Stats_Record  r;
        
        r = fStatChannels.get (iChannel);
        r.SetDisplayValue (dv);
    }
    
    /* (non-Javadoc)
     * @see ppm_java.typelib.IStats#GetDumpStr()
     */
    @Override
    public String GetDumpStr ()
    {
        int                                 i;
        int                                 n;
        TGUILinearGauge_Surrogate_Stats_Record          r;
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