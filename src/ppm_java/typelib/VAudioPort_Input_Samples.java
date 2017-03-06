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
 * Base class for an input that receives single sample values.
 * 
 * @author Peter Hoppe
 */
public abstract class VAudioPort_Input_Samples extends VAudioPort_Input
{
    /**
     * cTor.
     * 
     * @param id            Unique ID as which we register this port.
     * @param host          The audio processor hosting this port.
     */
    public VAudioPort_Input_Samples (String id, VAudioProcessor host)
    {
        super (id, host);
    }

    /**
     * Receives a sample value from the connected {@link VAudioPort_Output output}.
     * 
     * @param sample        The sample value received.
     */
    public abstract void ReceiveSample (float sample);

    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioPort#GetType()
     */
    @Override
    protected String _GetType ()
    {
        return "VAudioPort_Input_Samples";
    }
}
