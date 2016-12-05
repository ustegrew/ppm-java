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

import ppm_java.stream.node.bufferedPipe.TNodeBufferedPipe;

/**
 * A Node or other object that provides some service or data upon an external
 * "trigger pulse" (= call to {@link #Trigger()}. For example, the 
 * {@link TNodeBufferedPipe} requires an external {@link #Trigger()}.
 * stimulus for each time buffered data should be passed on to the consumer. 
 * 
 * @author peter
 */
public interface ITriggerable
{
    public void Trigger ();
}
