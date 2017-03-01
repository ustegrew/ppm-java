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
 */
class TSource extends VBrowseable implements IEvented, IControllable
{
    public TSource (String id)
    {
        super (id);
    }

    /**
     * Sends ten events (zeros) to any subscribers. 
     */
    public void Start ()
    {
        String  id;
        String  msg;
        int     i;
        
        id = GetID ();
        for (i = 1; i <= 10; i++)
        {
            msg = "Event #" + i;
            TController.PostEvent (0, msg, id);
            try {Thread.sleep (1000);} catch (InterruptedException e) {}
        }
    }

    public void Stop ()                         {/* Do nothing. */}
    public void OnEvent (int e)                 {/* Do nothing; this is just an event source */}
    public void OnEvent (int e, int arg0)       {/* Do nothing; this is just an event source */}
    public void OnEvent (int e, long arg0)      {/* Do nothing; this is just an event source */}
    public void OnEvent (int e, String arg0)    {/* Do nothing; this is just an event source */}
    protected void _Register ()
    {
        TController.Register (this);
    }
}
