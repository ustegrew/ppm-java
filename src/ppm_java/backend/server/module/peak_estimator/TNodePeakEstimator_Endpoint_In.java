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

package ppm_java.backend.server.module.peak_estimator;

import java.nio.FloatBuffer;
import ppm_java._aux.typelib.VAudioPort_Input_Chunks_Unbuffered;
import ppm_java.backend.server.TController;

/**
 * @author peter
 *
 */
public class TNodePeakEstimator_Endpoint_In extends VAudioPort_Input_Chunks_Unbuffered
{
    /**
     * @param id
     * @param host
     * @param iPort
     */
    protected TNodePeakEstimator_Endpoint_In (String id, TNodePeakEstimator host)
    {
        super (id, host, 0);
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VAudioPort_Input_Chunks_Unbuffered#ReceivePacket(java.nio.FloatBuffer)
     */
    @Override
    public void ReceivePacket (FloatBuffer chunk)
    {
        TNodePeakEstimator              host;
        
        host = (TNodePeakEstimator) _GetHost ();
        host.ReceivePacket (chunk);
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VBrowseable#_Register()
     */
    @Override
    protected void _Register ()
    {
        TController.Register (this);
    }
}
