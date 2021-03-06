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

import ppm_java.util.logging.TLogger;

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
     * @param nMaxChanIn        Maximum number of input channels. Specify <code>-1</code>
     *                          for: No limit.
     * @param nMaxChanOut       Maximum number of output channels. Specify <code>-1</code>
     *                          for: No limit.
     */
    public VAudioDriver (String id, int nMaxChanIn, int nMaxChanOut)
    {
        super (id, nMaxChanIn, nMaxChanOut);
    }
    
    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioProcessor#CreatePortIn(java.lang.String)
     */
    @Override
    public final void CreatePort_In (String id)
    {
        TLogger.LogWarning ("This is an audio driver. It doesn't provide input ports.", this, "CreatePort_In ('" + id + "')");
    }
}
