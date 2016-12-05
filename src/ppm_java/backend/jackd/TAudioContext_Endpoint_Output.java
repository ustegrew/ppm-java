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

package ppm_java.backend.jackd;

import java.nio.FloatBuffer;

import ppm_java._aux.storage.TAtomicBuffer;
import ppm_java._aux.storage.TAtomicBuffer.ECopyPolicy;
import ppm_java._aux.storage.TAtomicBuffer.EIfInvalidPolicy;
import ppm_java._framework.typelib.VAudioPort_Output_Chunks;

/**
 * @author peter
 *
 */
public class TAudioContext_Endpoint_Output extends VAudioPort_Output_Chunks
{
    private TAtomicBuffer               fBuffer;
    
    protected TAudioContext_Endpoint_Output (String id, TAudioContext_JackD host)
    {
        super (id, host);
        fBuffer = new TAtomicBuffer (ECopyPolicy.kCopyOnGet, EIfInvalidPolicy.kReturnEmpty);
    }

    /* (non-Javadoc)
     * @see ppm_java._framework.typelib.VAudioPort_Output_Chunks#PushPacket(java.nio.FloatBuffer)
     */
    @Override
    public void PushPacket (FloatBuffer chunk)
    {
        fBuffer.Set (chunk);
    }
}
