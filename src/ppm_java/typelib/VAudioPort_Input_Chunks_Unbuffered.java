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
 * Base class for an input port that receives sample chunks. This is an unbuffered
 * port, i.e. sample chunks are passed straight through to the hosting module.
 * 
 * @author Peter Hoppe
 */
public abstract class VAudioPort_Input_Chunks_Unbuffered extends VAudioPort_Input
{
    /**
     * cTor.
     * 
     * @param id        ID of this output port.
     * @param host      The module this port is part of.
     */
    protected VAudioPort_Input_Chunks_Unbuffered (String id, VAudioProcessor host)
    {
        super (id, host);
    }

    /**
     * Receives a sample chunk from the connected input port.
     * 
     * @param chunk     The sample data received.
     */
    public abstract void ReceivePacket (FloatBuffer chunk);
    
    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioPort_Input#_Accept(VAudioPort_Output)
     */
    @Override
    protected void _Accept (VAudioPort_Output source)
    {
        source._Visit (this);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioPort#GetType()
     */
    @Override
    protected final String _GetType ()
    {
        return "VAudioPort_Input_Chunks_Unbuffered";
    }
}
