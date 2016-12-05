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

import ppm_java._framework.typelib.VAudioPort_Input_Chunks;

/**
 * @author peter
 *
 */
public class TNodePeakDetector_Endpoint_In extends VAudioPort_Input_Chunks
{
    public TNodePeakDetector_Endpoint_In (String id, TNodePeakDetector host)
    {
        super (id, host, 0);
    }

    /* (non-Javadoc)
     * @see ppm_java.stream.stream.VAudioPort_Input_Chunks#_ReceivePacket(java.nio.FloatBuffer)
     */
    @Override
    public void ReceivePacket (FloatBuffer samples)
    {
        TNodePeakDetector   host;
        
        host = (TNodePeakDetector) _GetHost ();
        host.Receive (samples);
    }
}
