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

package ppm_java.backend.module._deprecated_ppm;

import ppm_java.backend.TController;
import ppm_java.typelib.VAudioPort_Input_Chunks_Buffered;
import ppm_java.util.storage.atomicBuffer.EAtomicBuffer_CopyPolicy;
import ppm_java.util.storage.atomicBuffer.EAtomicBuffer_IfInvalidPolicy;

/**
 * Audio input port for a {@link TNodePPMProcessor}.
 * Since we expect this endpoint to be connected to an audio driver (which runs 
 * in a  high priority thread), we use a buffered design, so that we prevent
 * priority inversion. 
 * 
 * @author Peter Hoppe
 */
@SuppressWarnings ("deprecation")
public class TNodePPMProcessor_Endpoint_In extends VAudioPort_Input_Chunks_Buffered
{
    /**
     * cTor.
     * 
     * @param id            ID of this input port.
     * @param host          The module this port is part of.
     * @param copyPolicy    If the other peer is running in a high priority thread, set this to
     *                      {@link EAtomicBuffer_CopyPolicy#kCopyOnGet}. This means that the incoming data
     *                      is being copied when the hosting PPM processor collects it. 
     */
    protected TNodePPMProcessor_Endpoint_In (String id, TNodePPMProcessor host, EAtomicBuffer_CopyPolicy copyPolicy)
    {
        super (id, host, copyPolicy, EAtomicBuffer_IfInvalidPolicy.kReturnNull);
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
