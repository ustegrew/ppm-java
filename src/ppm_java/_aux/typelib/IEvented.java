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

package ppm_java._aux.typelib;

import ppm_java.backend.server.module.timer.TTimer;

/**
 * Base interface for the event system. Normally, we'd provide a dedicated 
 * class each type of event. This would scale well; 
 * each event sending class could simply define its own event classes, 
 * and we could put whatever features we desire into each event type.
 * At runtime, upon firing an event we create an object of the desired 
 * event type and <code>post()</code> it with the global {@link TController}. 
 * Once the event has been handled we throw the event object away.  
 * This means at runtime we create a lot of throw-away objects which seems 
 * to be a stupid design to me (especially as some of the modules fire 
 * events at high frequency, such as {@link TTimer}).
 * Therefore we simplify dramatically and assign an integer constant to each
 * event type. With each event fired we post the (integer) event ID and the unique ID 
 * of the construct firing the event. This saves a lot of object creation and 
 * is a lot faster, too!
 * Disadvantage is that we can't provide in-band data with a simple call to 
 * {@link #OnEvent(int)}. That's why we provide additional methods that allow
 * for arguments with each event (e.g. {@link #OnEvent(int, int)}.
 * 
 * @author peter
 */
public interface IEvented
{
    public static final int     gkEventStart                = 1000;
    public static final int     gkEventStop                 = 1010;
    public static final int     gkEventTimerTick            = 1020;
    public static final int     gkEventTimerAdjustInterval  = 1030;
    
    /**
     * Receive an event with no parameter.
     * 
     * @param e     ID of the event we send. Must be one of the constants
     *              <code>gkEvent...</code> provided here.
     */
    public void OnEvent (int e);
    
    /**
     * Receive an event with one in-band integer as parameter.
     * 
     * @param e     ID of the event we send. Must be one of the constants
     *              <code>gkEvent...</code> provided here.
     * @param arg0  The in-band parameter.
     */
    public void OnEvent (int e, int arg0);
    
    /**
     * Receive an event with one in-band String as parameter.
     * 
     * @param e     ID of the event we send. Must be one of the constants
     *              <code>gkEvent...</code> provided here.
     * @param arg0  The in-band parameter.
     */
    public void OnEvent (int e, String arg0);
}
