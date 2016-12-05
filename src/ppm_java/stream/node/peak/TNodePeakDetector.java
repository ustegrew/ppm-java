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

package ppm_java.stream.node.peak;

import java.nio.FloatBuffer;

import ppm_java._framework.typelib.VAudioProcessor;

/**
 * Detects the peak in an audio chunk and passes this on as single value. 
 * 
 * @author peter
 */
public class TNodePeakDetector extends VAudioProcessor
{
    /**
     * @param id
     */
    public static void CreateInstance (String id)
    {
        new TNodePeakDetector (id);
    }
    
    private TNodePeakDetector_Endpoint_In   fInput;
    private TNodePeakDetector_Endpoint_Out  fOutput;
    
    public TNodePeakDetector (String id)
    {
        super (id, 1, 1);
        fInput  = null;
        fOutput = null;
    }
    
    /* (non-Javadoc)
     * @see ppm_java._framework.typelib.VAudioProcessor#CreatePortIn(java.lang.String)
     */
    @Override
    public void CreatePort_In (String id)
    {
        fInput = new TNodePeakDetector_Endpoint_In (id, this);
        AddPortIn (fInput);
    }

    /* (non-Javadoc)
     * @see ppm_java._framework.typelib.VAudioProcessor#CreatePortOut(java.lang.String)
     */
    @Override
    public void CreatePort_Out (String id)
    {
        fOutput = new TNodePeakDetector_Endpoint_Out (id, this);
        AddPortOut (fOutput);
    }

    void Receive (FloatBuffer data)
    {
        int             n;
        int             i;
        float           x;
        float           pk;
        
        pk  = 0;
        n   = data.capacity ();
        if (n >= 1)
        {
            for (i = 0; i < n; i++)
            {
                x   = data.get (i);
                
                if (x < 0)
                {
                    x = -x;
                }
                
                if (x > pk)
                {
                    pk = x;
                }
            }
        }
        
        fOutput.PushSample (pk);
    }
}
