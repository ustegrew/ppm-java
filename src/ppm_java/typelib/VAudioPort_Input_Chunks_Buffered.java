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

import ppm_java.util.storage.TAtomicBuffer;
import ppm_java.util.storage.TAtomicBuffer_Stats;
import ppm_java.util.storage.TAtomicBuffer.ECopyPolicy;
import ppm_java.util.storage.TAtomicBuffer.EIfInvalidPolicy;

/**
 * Base class for an input port that receives sample chunks. This is a 
 * {@linkplain TAtomicBuffer buffered} port, i.e. sample chunks are 
 * received and stored in a {@linkplain TAtomicBuffer buffer}. 
 * The hosting module is responsible for fetching the buffered data 
 * on time.
 * 
 * @author Peter Hoppe
 */
public abstract class VAudioPort_Input_Chunks_Buffered extends VAudioPort_Input
{
    /**
     * The buffer.
     */
    private TAtomicBuffer           fBuffer;
    
    /**
     * 
     * @param id                        ID of this output port.
     * @param host                      The module this port is part of.
     * @param copyPolicy                The desired copy policy.
     * @param ifInvalidPolicy           The desired invalid data policy.
     * 
     * @see TAtomicBuffer
     */
    protected VAudioPort_Input_Chunks_Buffered 
    (
        String              id, 
        VAudioProcessor     host, 
        ECopyPolicy         copyPolicy,
        EIfInvalidPolicy    ifInvalidPolicy
    )
    {
        super (id, host);
        fBuffer = new TAtomicBuffer (copyPolicy, ifInvalidPolicy);
    }
    
    /**
     * Fetch the buffered data. This is the access method for the hosting module. 
     * 
     * @return      The buffered data.
     */
    public FloatBuffer FetchPacket ()
    {
        FloatBuffer     ret;
        
        ret = fBuffer.Get ();
        
        return ret;
    }

    /**
     * Pushes one sample chunk onto the {@linkplain TAtomicBuffer buffer}. 
     * The hosting module must {@linkplain #FetchPacket() fetch}
     * the data before the next sample chunk is pushed.<br/>
     * This is the access method for the module connected to the input.  
     * 
     * @param chunk     The sample data pushed.
     */
    public void ReceivePacket (FloatBuffer chunk)
    {
        fBuffer.Set (chunk);
    }
    
    /**
     * Clears the statistics of the {@linkplain TAtomicBuffer buffer}.
     */
    public void StatsClear ()
    {
        fBuffer.StatsClear ();
    }

    /**
     * @return  The runtime statistics of the  {@linkplain TAtomicBuffer buffer}.
     */
    public TAtomicBuffer_Stats StatsGet ()
    {
        TAtomicBuffer_Stats ret;
        
        ret = fBuffer.StatsGet ();
        
        return ret;
    }
    
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
        return "VAudioPort_Input_Chunks_Buffered";
    }
}
