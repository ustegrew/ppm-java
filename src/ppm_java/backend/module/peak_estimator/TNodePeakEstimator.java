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

package ppm_java.backend.module.peak_estimator;

import java.nio.FloatBuffer;

import ppm_java.backend.TController;
import ppm_java.typelib.IStatEnabled;
import ppm_java.typelib.IStats;
import ppm_java.typelib.VAudioProcessor;

/**
 * @author Peter Hoppe
 *
 */
public class TNodePeakEstimator 
    extends         VAudioProcessor 
    implements      IStatEnabled
{
    public static void CreateInstance (String id)
    {
        new TNodePeakEstimator (id);
    }
    
    private float                       fPeakLast;
    private TNodePeakEstimator_Stats    fStats;
    
    /**
     * 
     */
    private TNodePeakEstimator (String id)
    {
        super (id, 1, 1);
        fPeakLast   = 0;
        fStats      = new TNodePeakEstimator_Stats (this);
        TController.StatAddProvider (this);
    }
    
    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioProcessor#CreatePort_In(java.lang.String)
     */
    @Override
    public void CreatePort_In (String id)
    {
        TNodePeakEstimator_Endpoint_In          p;
        
        p = new TNodePeakEstimator_Endpoint_In (id, this);
        AddPortIn (p);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioProcessor#CreatePort_Out(java.lang.String)
     */
    @Override
    public void CreatePort_Out (String id)
    {
        TNodePeakEstimator_Endpoint_Out         p;
        
        p = new TNodePeakEstimator_Endpoint_Out (id, this);
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
    
    void ReceivePacket (FloatBuffer chunk)
    {
        int                                 i;
        int                                 nSamples;
        float                               s;
        TNodePeakEstimator_Endpoint_Out     out;
        
        if (chunk != null)
        {
            nSamples = chunk.limit ();
            if (nSamples >= 1)
            {
                fPeakLast = 0;
                for (i = 0; i < nSamples; i++)
                {
                    s = chunk.get (i);
                    if (s < 0)
                    {
                        s = -s;
                    }
                    
                    if (s > fPeakLast)
                    {
                        fPeakLast = s;
                    }
                }
            }
        }
        
        fStats.SetPeak (fPeakLast);
        
        out = (TNodePeakEstimator_Endpoint_Out) GetPortOut (0);         /* [100] */
        out.PushSample (fPeakLast);
    }
}


/*
[100]   For each sample, JJack delivers a floating point value
        in the range [-1.0, 1.0] floating point units.
        For each frame, we map all negative values to the 
        positive range, effectively rectifying the signal.          
        The positive maximum value in each frame becomes the 
        peak value.

        Range (peak value, fpU):                floating point units, from JJack provided values
            [0.0, 1.0]
[110]   We will push the last peak if no valid data came in. This is by design. 
*/
