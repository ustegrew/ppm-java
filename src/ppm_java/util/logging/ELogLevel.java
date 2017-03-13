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

package ppm_java.util.logging;

/**
 * Possible log levels for a message.
 * 
 * @author peter
 */
enum ELogLevel
{
    /**
     * An info message, e.g. "connection established"
     */
    kMessage,
    
    /**
     * A warning message, e.g. "Oh dear, buffer overrun"
     */
    kWarn,
    
    /**
     * A fatal error, e.g. "Oh dear, class cast exception" 
     */
    kError
}