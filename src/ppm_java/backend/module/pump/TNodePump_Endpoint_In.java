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

package ppm_java.backend.module.pump;

import ppm_java.backend.TController;
import ppm_java.typelib.VAudioPort_Input_Chunks_Buffered;
import ppm_java.typelib.VAudioPort_Output;
import ppm_java.util.storage.TAtomicBuffer.ECopyPolicy;
import ppm_java.util.storage.TAtomicBuffer.EIfInvalidPolicy;

/**
 * @author Peter Hoppe
 *
 */
public class TNodePump_Endpoint_In extends VAudioPort_Input_Chunks_Buffered
{

    /**
     * @param id
     * @param host
     * @param iPort
     * @param copyPolicy
     * @param ifInvalidPolicy
     */
    protected TNodePump_Endpoint_In (String id, TNodePump host, ECopyPolicy copyPolicy, EIfInvalidPolicy ifInvalidPolicy)
    {
        super (id, host, copyPolicy, ifInvalidPolicy);
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
