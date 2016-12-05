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

package ppm_java._framework.typelib;

/**
 * A runtime event during the normal run of the system,
 * i.e. after all modules have been started and in the 
 * time before they are shut down. 
 * 
 * @author peter
 */
public abstract class VEvent
{
    private String          fKeyFrom;
    
    public VEvent (String keyFrom)
    {
        fKeyFrom = keyFrom;
    }

    /**
     * @return
     */
    public String GetSource ()
    {
        return fKeyFrom;
    }
    
    /**
     * Returns more detailed information re this event. In most cases this would 
     * be the canonical name of the event's class. Classes that need to provide more 
     * detailed information should override this class (e.g. an event with 
     * parameters).
     * 
     * @return      Some more detailed information re this event.
     */
    public String GetAsString ()
    {
        String ret;
        
        ret = getClass ().getCanonicalName ();
        
        return ret;
    }
    
    public boolean IsType (String typeID)
    {
        String      thisID;
        boolean     ret;
        
        thisID  = getClass ().getCanonicalName ();
        ret     = thisID.equals (typeID); 
        
        return ret;
    }
}
