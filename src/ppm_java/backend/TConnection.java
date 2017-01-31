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
 * @author Peter Hoppe
 *
 */
public class TConnection extends VBrowseable
{
    public static void CreateInstance (String idFromPort, String idToPort)
    {
        VAudioPort_Output           fromPort;
        VAudioPort_Input            toPort;
        
        fromPort        = (VAudioPort_Output) TController.GetObject (idFromPort);
        toPort          = (VAudioPort_Input) TController.GetObject  (idToPort);
        fromPort.SetTarget (toPort);
        new TConnection (fromPort, toPort);
    }
    
    private static int gCounter = -1;
    private static String _GetID ()
    {
        String ret;
        
        gCounter++;
        ret = TConnection.class.getCanonicalName () + "_" + gCounter;
        
        return ret;
    }
    
    private VAudioPort_Output               fSource;
    private VAudioPort_Input                fTarget;
    
    private TConnection (VAudioPort_Output fromPort, VAudioPort_Input toPort)
    {
        super (TConnection._GetID ());
        
        fSource     = fromPort;
        fTarget     = toPort;
    }
    
    public VAudioPort_Output Get_FromPort ()
    {
        return fSource;
    }
    
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
