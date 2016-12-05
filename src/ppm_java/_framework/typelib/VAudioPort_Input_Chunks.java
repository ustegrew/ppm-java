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

package ppm_java._framework.typelib;

import java.nio.FloatBuffer;

/**
 * @author peter
 *
 */
public abstract class VAudioPort_Input_Chunks extends VAudioPort_Input
{
    protected VAudioPort_Input_Chunks (String id, VAudioProcessor host, int iPort)
    {
        super (id, host, iPort);
    }
    
    public abstract void ReceivePacket (FloatBuffer chunk);
}
