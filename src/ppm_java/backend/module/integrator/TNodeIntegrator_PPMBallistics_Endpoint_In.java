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

package ppm_java.backend.module.integrator;

import ppm_java.backend.TController;
import ppm_java.typelib.VAudioPort_Input_Samples;
import ppm_java.typelib.VAudioPort_Output;
import ppm_java.typelib.VAudioProcessor;

/**
 * @author Peter Hoppe
 *
 */
public class TNodeIntegrator_PPMBallistics_Endpoint_In extends VAudioPort_Input_Samples
{
    /**
     * @param id
     * @param host
     * @param iPort
     */
    public TNodeIntegrator_PPMBallistics_Endpoint_In (String id, VAudioProcessor host)
    {
        super (id, host);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioPort_Input_Samples#ReceiveSample(float)
     */
    @Override
    public void ReceiveSample (float sample)
    {
        TNodeIntegrator_PPMBallistics           host;
        
        host = (TNodeIntegrator_PPMBallistics) _GetHost ();
        host.SetRef (sample);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VBrowseable#_Register()
     */
    @Override
    protected void _Register ()
    {
        TController.Register (this);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioPort_Input#Accept(ppm_java.typelib.VAudioPort_Output)
     */
    @Override
    protected void _Accept (VAudioPort_Output source)
    {
        source.Visit (this);
    }
}
