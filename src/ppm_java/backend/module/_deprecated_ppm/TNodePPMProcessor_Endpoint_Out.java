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
import ppm_java.typelib.VAudioPort_Output_Samples;

/**
 * @author Peter Hoppe
 *
 */
@SuppressWarnings ("deprecation")
public class TNodePPMProcessor_Endpoint_Out extends VAudioPort_Output_Samples
{
    /**
     * @param id
     * @param host
     */
    public TNodePPMProcessor_Endpoint_Out (String id, TNodePPMProcessor host)
    {
        super (id, host);
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
