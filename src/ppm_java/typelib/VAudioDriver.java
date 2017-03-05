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

/**
 * Base class for all audio drivers.
 * 
 * An audio driver receives data from an external entity (e.g. jackd audio server)
 * and passes it on to other modules.
 * 
 * @author Peter Hoppe
 */
public abstract class VAudioDriver extends VAudioProcessor
{
    /**
     * cTor.
     * 
     * @param id                The unique ID under which we register this object 
     *                          with the registry.
     * @param nMaxChanOut       Maximum number of output channels. Specify <code>-1</code>
     *                          for: No limit.
     */
    public VAudioDriver (String id, int nMaxChanOut)
    {
        super (id, 0, nMaxChanOut);
    }
}
