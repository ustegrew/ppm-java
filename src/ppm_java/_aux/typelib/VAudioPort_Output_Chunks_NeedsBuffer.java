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

package ppm_java._aux.typelib;

import java.nio.FloatBuffer;

/**
 * An output that connects to a buffered input. 
 * Should be used as output for audio processors 
 * which run in a different thread space than the 
 * processors receiving data.
 * 
 * @author peter
 *
 */
public abstract class VAudioPort_Output_Chunks_NeedsBuffer extends VAudioPort_Output
{
    protected VAudioPort_Output_Chunks_NeedsBuffer (String id, VAudioProcessor host)
    {
        super (id, host);
    }
    
    public void PushPacket (FloatBuffer chunk)
    {
        VAudioPort_Input_Chunks_Buffered target;
        
        target = (VAudioPort_Input_Chunks_Buffered) _GetTarget ();
        target.ReceivePacket (chunk);
    }
    
    public void SetTarget (VAudioPort_Input_Chunks_Buffered target)
    {
        super.SetTarget (target);
    }
}
