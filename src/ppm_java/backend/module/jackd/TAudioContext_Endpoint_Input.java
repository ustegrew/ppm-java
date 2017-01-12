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

package ppm_java.backend.module.jackd;

import ppm_java.backend.TController;
import ppm_java.typelib.VAudioPort_Input_Chunks_Buffered;
import ppm_java.util.storage.TAtomicBuffer.ECopyPolicy;
import ppm_java.util.storage.TAtomicBuffer.EIfInvalidPolicy;

/**
 * @author peter
 *
 */
public class TAudioContext_Endpoint_Input extends VAudioPort_Input_Chunks_Buffered
{
    protected TAudioContext_Endpoint_Input (String id, TAudioContext_JackD host, int iPort)
    {
        super (id, host, iPort, ECopyPolicy.kCopyOnSet, EIfInvalidPolicy.kReturnEmpty);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VBrowseable#_Register()
     */
    @Override
    protected void _Register ()
    {
        TController.Register (this);
    }
}
