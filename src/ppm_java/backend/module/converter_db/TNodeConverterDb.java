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
 * Converts a raw value <code>[0, 1]</code> into its equivalent dB value.
 * We use the following conversion scheme:
 * <table border="1">
 *     <tr>
 *         <td>Raw values</td>
 *         <td>Values (dB)</td>
 *     </tr>
 *     <tr>
 *         <td><code>&lt; 10<sup>-7.5</sup><code></td>
 *         <td>-130</td>
 *     </tr>
 *     <tr>
 *         <td><code>10<sup>-7.5</sup> .. 1</code></td>
 *         <td><code>-130 .. 0</code></td>
 *     </tr>
 * </table>   
 * 
 * @author Peter Hoppe
 */
public class TNodeConverterDb extends VAudioProcessor implements IStatEnabled
{
    /**
     * Minimum dB level. Maps to PPM 0 .. PPM 1
     */
    public static final double              gkMinDB         = -130;         /* [100] */
    
    /**
     * Voltage (= raw value) corresponding to {@link #gkMinDB}.
     * See [100] in the java source.
     */
    public static final double              gkMinThreshold  = 3.16E-08f;    /* [100] */
    
    /**
     * Creates a new instance of this module.
     * 
     * @param id    Unique ID as which we register this module.
     */
    public static void CreateInstance (String id)
    {
        new TNodeConverterDb (id);
    }
    
    /**
     * The statistics record. Updated during runtime.
     */
    private TNodeConverterDb_Stats          fStats;
    
    /**
     * cTor.
     * 
     * @param id    Unique ID as which we register this module.
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
        TNodeConverterDb_Endpoint_In p;
        
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

    /**
     * Receives a sample value from the associated endpoint and converts it 
     * to its equivalent value in dB. 
     * 
     * @param sample        The sample value.
     */
    void Receive (float sample)
    {
        double                          x;
        float                           dB;
        TNodeConverterDb_Endpoint_Out   out;
        
        if (sample >= gkMinThreshold)                                   /* Re calculations: [100] */
        {
            x = 20 * Math.log10 (sample);
        }
        else
        {
            x = gkMinDB;
        }
        
        dB  = (float) x;                                                /* [110] */
        
        fStats.SetValue (sample);
        fStats.SetDB    (dB);
        
        out = (TNodeConverterDb_Endpoint_Out) GetPortOut (0);
        out.PushSample (dB);
    }
}

/*
[100]   Peak values, Raw values, Calibration and all that...

        For each sample, JJack delivers a floating point value
        in the range [-1.0, 1.0] floating point units.
        For each frame, we map all negative values to the 
        positive range, effectively rectifying the signal.  
        The positive maximum value in each frame becomes the 
        peak value.

        Peak value vs. Input signal voltage
        -----------------------------------
        Our calculations make the simplifying assumption
        that the raw values delivered by jackd map directly 
        to voltage, e.g. 0.5 means: 0.5V. It's an arbitrary
        assumption; in reality the voltage will differ wildly
        from sound card to sound card. That's why we can't 
        use a unified calibration procedure. Future developments 
        could include an input gain setting that calibrates the 
        PPM to map peak value 1.0 against PPM 7 + 4dB (i.e. 
        an imaginary PPM 8) to add some headroom. 
        This is a very crude measurement. For now, if the sound 
        card input is connected to a mixer output, then use the 
        mixer's VU meter and match +4dB to PPM 7. 

        Calculations
        ------------
        Range (peak value, fpU):                floating point units, from JJack provided values
            [0.0, 1.0]
            
        Generic formula: ratio (vdB): Signal Voltage (v) against reference voltage (vr):
        
            vdB = 20 * log (v / vr)
            
            Let vr = 1.0, then
                vdB = 20 * log (v)
        
        Our mapping fpU -> dB:
            fpU = [0.0, 10^-7.5]:  -130
                  ]10^-7.5,   1]:  -130 .. 0

        Note that 
            -130dB: Hearing threshold
        
            which computes to a voltage of:
            10 ^ (-130/20) = 10^-7.5 = 3.16 * 10^-8
            
        Calculating peak values in dB:
            for p: [0, 3.16E-08]
                pdB = -130
            for p: ]3.16E-08, 1.0]
                pdB = 20 * log (p)

[110]   There are situations where downcasting from double to float 
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