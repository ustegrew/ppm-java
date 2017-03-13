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

package ppm_java.typelib;

import java.nio.FloatBuffer;

/**
 * An output that connects to a buffered input. 
 * Should be used as output for audio processors 
 * which run in a different thread space than the 
 * processors receiving data.
 * 
 * @author Peter Hoppe
 */
public abstract class VAudioPort_Output_Chunks_NeedsBuffer extends VAudioPort_Output
{
    /**
     * cTor.
     * 
     * @param id        ID of this output port.
     * @param host      The module this port is part of.
     */
    protected VAudioPort_Output_Chunks_NeedsBuffer (String id, VAudioProcessor host)
    {
        super (id, host);
    }

    /**
     * Pushes a sample chunk out to the connected input port..
     * 
     * @param chunk         The sample data pushed.
     */
    public void PushPacket (FloatBuffer chunk)
    {
        VAudioPort_Input_Chunks_Buffered target;
        
        target = (VAudioPort_Input_Chunks_Buffered) _GetTarget ();
        target.ReceivePacket (chunk);
    }
    
    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioPort#GetType()
     */
    @Override
    protected final String _GetType ()
    {
        return "VAudioPort_Output_Chunks_NeedsBuffer";
    }
    
    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioPort_Output#_Visit(ppm_java.typelib.VAudioPort_Input_Chunks_Buffered)
     */
    protected final void _Visit (VAudioPort_Input_Chunks_Buffered target)
    {
        _SetTarget (target);
    }
    
    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioPort_Output#_Visit(ppm_java.typelib.VAudioPort_Input_Chunks_Unbuffered)
     */
    protected final void _Visit (VAudioPort_Input_Chunks_Unbuffered target)
    {
        _SetTarget_Log (target, true);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioPort_Output#_Visit(ppm_java.typelib.VAudioPort_Input_Samples)
     */
    protected final void _Visit (VAudioPort_Input_Samples target)
    {
        _SetTarget_Log (target, true);
    }
}
