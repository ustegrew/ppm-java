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

package ppm_java.backend.module.converter_db;

import ppm_java.backend.TController;
import ppm_java.typelib.IStatEnabled;
import ppm_java.typelib.IStats;
import ppm_java.typelib.VAudioProcessor;

/**
 * @author Peter Hoppe
 *
 */
public class TNodeConverterDb extends VAudioProcessor implements IStatEnabled
{
    public static final double              gkMinDB         = -130;
    public static final double              gkMinThreshold  = 3.16E-08f;
    public static void CreateInstance (String id)
    {
        new TNodeConverterDb (id);
    }
    
    private TNodeConverterDb_Stats          fStats;
    
    /**
     * @param id
     */
    private TNodeConverterDb (String id)
    {
        super (id, 1, 1);
        fStats = new TNodeConverterDb_Stats (this);
        TController.StatAddProvider (this);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioProcessor#CreatePort_In(java.lang.String)
     */
    @Override
    public void CreatePort_In (String id)
    {
        TNodeConverterDb_Endpoint_In        p;
        
        p = new TNodeConverterDb_Endpoint_In (id, this);
        AddPortIn (p);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioProcessor#CreatePort_Out(java.lang.String)
     */
    @Override
    public void CreatePort_Out (String id)
    {
        TNodeConverterDb_Endpoint_Out       p;
        
        p = new TNodeConverterDb_Endpoint_Out (id, this);
        AddPortOut (p);

    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IStatEnabled#StatsGet()
     */
    @Override
    public IStats StatsGet ()
    {
        return fStats;
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VBrowseable#_Register()
     */
    @Override
    protected void _Register ()
    {
        TController.Register (this);
    }

    void Receive (float sample)
    {
        double                          x;
        float                           dB;
        TNodeConverterDb_Endpoint_Out   out;
        
        if (sample >= gkMinThreshold)
        {
            x = 20 * Math.log10 (sample);
        }
        else
        {
            x = gkMinDB;
        }
        
        dB  = (float) x;                                                /* [120] */
        
        fStats.SetValue (sample);
        fStats.SetDB    (dB);
        
        out = (TNodeConverterDb_Endpoint_Out) GetPortOut (0);
        out.PushSample (dB);
    }
}

/*
[100]   Generic formula: ratio (vdB): Signal Voltage (v) against reference voltage (vr):
        
            vdB = 20 * log (v / vr)
            
            Let vr = 1.0, then
                vdB = 20 * log (v)
        
        Our mapping fpU -> dB:
            fpU = [0.0, 10^-7.5]:  -130
                  ]10^-7.5,   1]:     0

[110]   Note re gkMinThreshold 
            -130dB: Hearing threshold
        
            which computes to a voltage of:
            10 ^ (-130/20) = 10^-7.5 = 3.16 * 10^-8



[120]   There are situations where downcasting from double to float 
        is dangerous. Here it's fairly safe:
        * We deal with dB values, i.e. the really interesting part 
          is to the left of the decimal point. To the human ear, 
          -60dB isn't noticably different from -60.2dB. 
        * Round down errors (double -> float) happen in a dimension 
          somewhere below 1E-30 which makes no difference when 
          displayed on a meter display.
        * We produce a use-once value, i.e. we won't use this value 
          as a base for the next peak calculations. Therefore we won't
          have accumulating errors.

*/