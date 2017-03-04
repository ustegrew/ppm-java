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

package ppm_java.backend;

import ppm_java.typelib.VAudioPort_Input;
import ppm_java.typelib.VAudioPort_Output;
import ppm_java.typelib.VBrowseable;

/**
 * A connection between two endpoints. This object just ensures that the 
 * connection is browseable. It won't handle any data flow.  
 * 
 * @author Peter Hoppe
 */
public class TConnection extends VBrowseable
{
    /**
     * Creates a connection from endpoint <code>idFromPort</code> to 
     * endpoint <code>idToPort</code>. 
     * 
     * @param idFromPort    Unique ID, source port.
     * @param idToPort      Unique ID, target port.
     */
    public static void CreateInstance (String idFromPort, String idToPort)
    {
        VAudioPort_Output           fromPort;
        VAudioPort_Input            toPort;
        
        fromPort        = (VAudioPort_Output) TController.GetObject (idFromPort);
        toPort          = (VAudioPort_Input) TController.GetObject  (idToPort);
        fromPort.SetTarget (toPort);
        new TConnection (fromPort, toPort);
    }
    
    /**
     * Counter to achieve unique ID of this connection.
     */
    private static int gCounter = -1;
    
    /**
     * @return  Creates the unique ID as which we register this connection.
     */
    private static String _GetID ()
    {
        String ret;
        
        gCounter++;
        ret = TConnection.class.getCanonicalName () + "_" + gCounter;
        
        return ret;
    }
    
    /**
     * The Source port
     */
    private VAudioPort_Output               fSource;
    
    
    /**
     * The target port
     */
    private VAudioPort_Input                fTarget;
    
    /**
     * cTor. Caches fromPort and toPort, so we won't need to query those 
     * from the global registry.
     * 
     * @param fromPort      Source port
     * @param toPort        Target port
     */
    private TConnection (VAudioPort_Output fromPort, VAudioPort_Input toPort)
    {
        super (TConnection._GetID ());
        
        fSource     = fromPort;
        fTarget     = toPort;
    }
    
    /**
     * @return  The source port.
     */
    public VAudioPort_Output Get_FromPort ()
    {
        return fSource;
    }
    
    /**
     * @return  The target port.
     */
    public VAudioPort_Input Get_ToPort ()
    {
        return fTarget;
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VBrowseable#_Register()
     */
    @Override
    protected void _Register ()
    {
        TController.Register (this);
    }
}
