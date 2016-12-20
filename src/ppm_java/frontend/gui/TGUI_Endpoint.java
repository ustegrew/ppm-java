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

package ppm_java.frontend.gui;

import ppm_java._aux.typelib.VAudioPort_Input_Samples;
import ppm_java.backend.server.TController;

/**
 * @author peter
 *
 */
public class TGUI_Endpoint extends VAudioPort_Input_Samples
{
    public TGUI_Endpoint (String id, TGUISurrogate host, int iChannel)
    {
        super (id, host, iChannel);
    }

    /* (non-Javadoc)
     * @see ppm_java.stream.stream.VConnectible#SampleReceive(float)
     */
    @Override
    public void ReceiveSample (float sample)
    {
        TGUISurrogate   host;
        int             iPort;

        host    = (TGUISurrogate) _GetHost ();
        iPort   = GetPortNum ();
        host.SetLevel (sample, iPort);
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
