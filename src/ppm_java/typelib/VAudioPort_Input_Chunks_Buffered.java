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
 * @author peter
 *
 */
public abstract class VAudioPort_Input_Chunks_Buffered extends VAudioPort_Input
{
    private TAtomicBuffer           fBuffer;
    
    protected VAudioPort_Input_Chunks_Buffered 
    (
        String              id, 
        VAudioProcessor     host, 
        int                 iPort, 
        ECopyPolicy         copyPolicy,
        EIfInvalidPolicy    ifInvalidPolicy
    )
    {
        super (id, host, iPort);
        fBuffer = new TAtomicBuffer (copyPolicy, ifInvalidPolicy);
    }
    
    public FloatBuffer FetchPacket ()
    {
        FloatBuffer     ret;
        
        ret = fBuffer.Get ();
        
        return ret;
    }
    
    public void ReceivePacket (FloatBuffer chunk)
    {
        fBuffer.Set (chunk);
    }

    public void StatsClear ()
    {
        fBuffer.StatsClear ();
    }
    
    public TAtomicBuffer_Stats StatsGet ()
    {
        TAtomicBuffer_Stats ret;
        
        ret = fBuffer.StatsGet ();
        
        return ret;
    }
}