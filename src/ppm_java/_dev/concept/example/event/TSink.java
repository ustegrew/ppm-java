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

package ppm_java._dev.concept.example.event;

import ppm_java.backend.TController;
import ppm_java.typelib.IControllable;
import ppm_java.typelib.IEvented;
import ppm_java.typelib.VBrowseable;

/**
 *
 */
class TSink extends VBrowseable implements IEvented, IControllable
{
    public TSink (String id)
    {
        super (id);
    }

    public void OnEvent (int e, String arg0)    
    {
        String msg;
        
        msg = GetID () + ": " + "Received messaging event. Message: " + arg0; 
        System.out.println (msg);
    }

    public void Start ()                        {/* Do nothing */}
    public void Stop ()                         {/* Do nothing */}
    public void OnEvent (int e)                 {/* Do nothing */}
    public void OnEvent (int e, int arg0)       {/* Do nothing */}
    public void OnEvent (int e, long arg0)      {/* Do nothing */}
    protected void _Register ()
    {
        TController.Register (this);
    }
}
