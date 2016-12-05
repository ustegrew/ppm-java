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

import ppm_java._framework.typelib.VAudioPort_Output_Chunks;

/**
 * @author peter
 *
 */
public class TNodeBufferedPipe_Endpoint_Out extends VAudioPort_Output_Chunks
{
    /**
     * @param id
     * @param host
     */
    protected TNodeBufferedPipe_Endpoint_Out (String id, TNodeBufferedPipe host)
    {
        super (id, host);
    }
}
