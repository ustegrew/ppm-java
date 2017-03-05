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

/**
 * Base class for an output that sends single sample values.
 * 
 * @author Peter Hoppe
 *
 */
public abstract class VAudioPort_Output_Samples extends VAudioPort_Output
{
    /**
     * cTor.
     * 
     * @param id            Unique ID as which we register this port.
     * @param host          The audio processor hosting this port.
     */
    public VAudioPort_Output_Samples (String id, VAudioProcessor host)
    {
        super (id, host);
    }

    /**
     * Pushes a sample value to the connected {@link VAudioPort_Input input}.
     * 
     * @param sample        The sample value to send.
     */
    public void PushSample (float sample)
    {
        VAudioPort_Input_Samples target;
        
        target = (VAudioPort_Input_Samples) _GetTarget ();
        target.ReceiveSample (sample);
    }
    
    public void Visit (VAudioPort_Input_Chunks_Buffered target)
    {
        _SetTarget_Log (target, true);
    }
    
    public void Visit (VAudioPort_Input_Chunks_Unbuffered target)
    {
        _SetTarget_Log (target, true);
    }
    
    public void Visit (VAudioPort_Input_Samples target)
    {
        _SetTarget (target);
    }
}
