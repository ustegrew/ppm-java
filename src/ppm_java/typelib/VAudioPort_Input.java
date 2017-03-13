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
 * Base class for an input audio port.<br/>
 *  
 * An input port is always associated with a hosting audio 
 * {@link VAudioProcessor processor}. It's connected with 
 * another {@link VAudioPort_Output output} port from where it 
 * receives its data.<br/>
 * 
 * To ensure type safety the connection mechanism uses 
 * the visitor pattern for the connection to an output port.   
 * This class provides the prototype for the 
 * {@link #_Accept(VAudioPort_Output)} method.
 * 
 * @author Peter Hoppe
 */
public abstract class VAudioPort_Input extends VAudioPort
{
    /**
     * The index as which this input port can be accessed in the hosting
     * audio {@link VAudioProcessor processor}.
     */
    private int                 fIPort;
    
    /**
     * cTor.
     * 
     * @param id            Unique ID as which we register this port.
     * @param host          The audio processor hosting this port.
     */
    public VAudioPort_Input (String id, VAudioProcessor host)
    {
        super (id, host);
     
        fIPort = host.GetNumPortsIn ();
        TLogger.LogMessage ("Processor: '" + host.GetID () + "': Creating input port '" + id + "'", this, "cTor");
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
     * Accepts a {@link VAudioPort_Output Visitor}. Which type
     * of visitor is determined by the runtime types of output 
     * and input port.
     * 
     * @param source    The visitor.
     */
    protected abstract void _Accept (VAudioPort_Output source);
}
