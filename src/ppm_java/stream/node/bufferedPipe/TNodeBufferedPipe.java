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

package ppm_java.stream.node.bufferedPipe;

import java.nio.FloatBuffer;

import ppm_java._aux.storage.TAtomicBuffer;
import ppm_java._aux.storage.TAtomicBuffer.ECopyPolicy;
import ppm_java._framework.typelib.ITriggerable;
import ppm_java._framework.typelib.VAudioProcessor;

/**
 * A producer-consumer based conduit. To decouple high priority 
 * thread (audio) from low priority activity - preventing priority
 * inversion.
 * 
 * The producing thread can push it's data to this node at any time.
 * When pushed we create a copy of the data, and the consumer thread 
 * can requests the data's copy. Meant for pump scenarios where we need 
 * two independent threads push and receive data at their own time.
 * 
 * Uses a simple atomic (wait-free) locking scheme (no synchronization,
 * but an atomic flag). Thus, the producer and the consumer should 
 * not be in each other's way.
 * 
 * @author peter
 */
public class TNodeBufferedPipe 
    extends     VAudioProcessor
    implements  ITriggerable
{
    public static void CreateInstance (String id, ECopyPolicy copyPolicy)
    {
        new TNodeBufferedPipe (id, copyPolicy);
    }
    
    private TAtomicBuffer                           fBuffer;
    private TNodeBufferedPipe_Endpoint_In           fInput;
    private TNodeBufferedPipe_Endpoint_Out          fOutput;
    
    /**
     * @param id
     */
    TNodeBufferedPipe (String id, ECopyPolicy copyPolicy)
    {
        super (id, 1, 1);
        fBuffer     = new TAtomicBuffer (copyPolicy);
        fInput      = null;
        fOutput     = null;
    }

    /* (non-Javadoc)
     * @see ppm_java._framework.typelib.VAudioProcessor#CreatePort_In(java.lang.String)
     */
    @Override
    public void CreatePort_In (String id)
    {
        fInput = new TNodeBufferedPipe_Endpoint_In (id, this);
        AddPortIn (fInput);
    }

    /* (non-Javadoc)
     * @see ppm_java._framework.typelib.VAudioProcessor#CreatePort_Out(java.lang.String)
     */
    @Override
    public void CreatePort_Out (String id)
    {
        fOutput = new TNodeBufferedPipe_Endpoint_Out (id, this);
        AddPortOut (fOutput);
    }
    
    /**
     * Pushes data to the receiving thread (via the output). Must be 
     * called by some timer event handler.
     */
    public void Trigger ()
    {
        FloatBuffer     b;
        
        b = fBuffer.Get ();
        fOutput.PushPacket (b);
    }
    
    /**
     * Receives data from the sending thread (via the input endpoint)
     * @param chunk 
     */
    void Receive (FloatBuffer chunk)
    {
        fBuffer.Set (chunk);
    }
}
