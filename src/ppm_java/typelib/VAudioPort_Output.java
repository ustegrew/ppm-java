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

import ppm_java.util.logging.TLogger;

/**
 * Base class for an output audio port. 
 * An output port is always associated with a hosting audio
 * {@link VAudioProcessor processor}. It's connected with 
 * another {@link VAudioPort_Input} to where it sends its data.
 * Every time this port sends data it will trigger a processing
 * cycle in the audio processor associated with the connected
 * input.<br/>
 * The connection mechanism utilizes the visitor pattern, so
 * we can connect any type of output port to any type of 
 * input port. We need this pattern so we can retrieve 
 * audio ports by opaque handle and then connect them.  
 * This class is the visitor and provides the overloaded 
 * <code>_Visit(...)</code> methods.
 * 
 * @author Peter Hoppe
 */
public abstract class VAudioPort_Output extends VAudioPort
{
    /**
     * The index as which this output port can be accessed in the hosting
     * audio {@link VAudioProcessor processor}.
     */
    private int                     fIPort;
    
    /**
     * The input connected to this output.
     */
    private VAudioPort_Input        fTarget;
    
    /**
     * cTor.
     * 
     * @param id            Unique ID as which we register this port.
     */
    public VAudioPort_Output (String id, VAudioProcessor host)
    {
        super (id, host);
        
        fIPort = host.GetNumPortsOut ();
        TLogger.LogMessage ("Processor: '" + host.GetID () + "': Creating output port '" + id + "'", this, "cTor");
    }
    
    /**
     * @return  The index under which this port is stored with the
     *          hosting audio processor. The index is zero based.
     */
    public int GetPortNum ()
    {
        return fIPort;
    }
    
    /**
     * Implements the connection to an input port (Public initiating method).
     * This initializes the working part of the visitor pattern. 
     * 
     * @param target    The input port we connect to.
     */
    public void SetTarget (VAudioPort_Input target)
    {
        target._Accept (this);
    }
    
    /**
     * @return  The input port we are connected to. 
     */
    protected VAudioPort_Input _GetTarget ()
    {
        return fTarget;
    }
    
    /**
     * Worker method to establish a connection to an input port, called by
     * the <code>_Visit(...)</code> method of the runtime chose subtype. 
     * 
     * @param target    The input port we connect to.
     */
    protected final void _SetTarget (VAudioPort_Input target)
    {
        fTarget = target;
        _SetTarget_Log (target, false);
    }

    protected final void _SetTarget_Log (VAudioPort_Input target, boolean isErr)
    {
        String      msg;
        
        msg  =     "Connecting endpoints: '" 
                 + GetID() 
                 + "' (" 
                 + _GetType () 
                 + ")  -> '" 
                 + target.GetID () 
                 + "' (" 
                 + target._GetType () 
                 + "): ";
        if (isErr)
        {
            msg += "Failure: Incompatible endpoint types";
            TLogger.LogWarning (msg, this, "SetTarget");
        }
        else
        {
            msg += "OK";
            TLogger.LogMessage (msg, this, "SetTarget");
        }
    }
    
    /**
     * Visitor pattern: Visit method, called if the 
     * visitor is of type {@link VAudioPort_Input_Chunks_Buffered}.
     * 
     * @param target        The visitor.
     */
    protected abstract void _Visit (VAudioPort_Input_Chunks_Buffered target);
    
    /**
     * Visitor pattern: Visit method, called if the 
     * visitor is of type {@link VAudioPort_Input_Chunks_Unbuffered}.
     * 
     * @param target        The visitor.
     */
    protected abstract void _Visit (VAudioPort_Input_Chunks_Unbuffered target);
    
    /**
     * Visitor pattern: Visit method, called if the 
     * visitor is of type {@link VAudioPort_Input_Samples}.
     * 
     * @param target        The visitor.
     */
    protected abstract void _Visit (VAudioPort_Input_Samples target);
}
