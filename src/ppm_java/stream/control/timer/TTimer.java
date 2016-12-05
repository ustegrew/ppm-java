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

package ppm_java.stream.control.timer;

import ppm_java._framework.typelib.VBrowseable;
import ppm_java.backend.server.TController;

/**
 * @author peter
 *
 */
public class TTimer extends VBrowseable
{
    public static TTimer CreateInstance (String id, int intervalMs)
    {
        TTimer ret;
        
        ret = new TTimer (id, intervalMs);
        
        return ret;
    }
    
    private TTimerWorker                fWorker;
    
    /**
     * @param id
     */
    TTimer (String id, int delayMs)
    {
        super (id);
        fWorker = new TTimerWorker (this, delayMs);
    }
    
    public void Start ()
    {
        fWorker.start ();
    }

    public void Stop ()
    {
        fWorker.Stop ();
    }
    
    void SendTimerEvent ()
    {
        String                          id;
        TEventTimerTick                 evT;
        
        id  = GetID ();
        evT = new TEventTimerTick (id);
        TController.PostEvent (evT);
    }
}
