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

package ppm_java.stream.node.bufferedPipe;

import java.nio.FloatBuffer;

import ppm_java._framework.typelib.VAudioPort_Input_Chunks;

/**
 * @author peter
 *
 */
public class TNodeBufferedPipe_Endpoint_In extends VAudioPort_Input_Chunks
{
    /**
     * @param id
     * @param host
     */
    public TNodeBufferedPipe_Endpoint_In (String id, TNodeBufferedPipe host)
    {
        super (id, host, 0);
    }

    /* (non-Javadoc)
     * @see ppm_java._framework.typelib.VAudioPort_Input_Chunks#ReceivePacket(java.nio.FloatBuffer)
     */
    @Override
    public void ReceivePacket (FloatBuffer chunk)
    {
        TNodeBufferedPipe           host;
        
        host = (TNodeBufferedPipe) _GetHost ();
        host.Receive (chunk);
    }
}
