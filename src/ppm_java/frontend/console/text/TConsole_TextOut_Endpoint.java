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

package ppm_java.frontend.console.text;

import ppm_java.backend.TController;
import ppm_java.typelib.VAudioPort_Input_Samples;
import ppm_java.typelib.VAudioProcessor;

/**
 * @author Peter Hoppe
 */
public class TConsole_TextOut_Endpoint extends VAudioPort_Input_Samples
{
    /**
     * @param id
     * @param host
     * @param iPort
     */
    public TConsole_TextOut_Endpoint (String id, VAudioProcessor host, int iPort)
    {
        super (id, host, iPort);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioPort_Input_Samples#ReceiveSample(float)
     */
    @Override
    public void ReceiveSample (float sample)
    {
        int                     iPort;
        TConsole_TextOut        host;
        
        host    = (TConsole_TextOut) _GetHost ();
        iPort   = GetPortNum ();
        host.Receive (sample, iPort);
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
