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

package ppm_java.backend.module.converter_db;

import ppm_java.backend.TController;
import ppm_java.typelib.VAudioPort_Output_Samples;
import ppm_java.typelib.VAudioProcessor;

/**
 * Audio output port for a {@link TNodeConverterDb}.
 * 
 * @author Peter Hoppe
 */
public class TNodeConverterDb_Endpoint_Out extends VAudioPort_Output_Samples
{
    /**
     * cTor.
     * 
     * @param id        ID of this output port.
     * @param host      The module this port is part of.
     */
    public TNodeConverterDb_Endpoint_Out (String id, VAudioProcessor host)
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
