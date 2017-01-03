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

package ppm_java.backend.server.module.converter_db;

import ppm_java._aux.typelib.VAudioPort_Input_Samples;
import ppm_java._aux.typelib.VAudioProcessor;
import ppm_java.backend.server.TController;

/**
 * @author peter
 *
 */
public class TNodeConverterDb_Endpoint_In extends VAudioPort_Input_Samples
{
    /**
     * @param id
     * @param host
     * @param iPort
     */
    public TNodeConverterDb_Endpoint_In (String id, VAudioProcessor host, int iPort)
    {
        super (id, host, iPort);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VAudioPort_Input_Samples#ReceiveSample(float)
     */
    @Override
    public void ReceiveSample (float sample)
    {
        // TODO Auto-generated method stub

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
