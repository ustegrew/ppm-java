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

package ppm_java.backend.module.converter_db;

import ppm_java.typelib.IStats;
import ppm_java.util.storage.TAtomicDouble;

/**
 * Runtime statistics of a {@link TNodeConverterDb}.
 * 
 * @author Peter Hoppe
 */
public class TNodeConverterDb_Stats implements IStats
{
    /**
     * The current dB value.
     */
    private TAtomicDouble               fDB;
    
    /**
     * The hosting module.
     */
    private TNodeConverterDb            fHost;
    
    /**
     * The current raw value.
     */
    private TAtomicDouble               fValue;
    
    /**
     * cTor.
     * 
     * @param host      The hosting module.
     */
    public TNodeConverterDb_Stats (TNodeConverterDb host)
    {
        fHost   = host;
        fDB     = new TAtomicDouble ();
        fValue  = new TAtomicDouble ();
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IStats#GetDumpStr()
     */
    @Override
    public String GetDumpStr ()
    {
        String ret;
        
        ret = "TNodeConverterDb_Stats [" + fHost.GetID ()           + "]:\n"    +
              "    dB       = " + fDB.Get ()                        + "\n"      +
              "    value    = " + fValue.Get ()                     + "\n";
        
        return ret;
    }

    /**
     * Sets the current dB value.
     * 
     * @param dB        dB value.
     */
    void SetDB (float dB)
    {
        fDB.Set (dB);
    }
    
    /**
     * Sets the current raw value.
     * 
     * @param v     Raw value.
     */
    void SetValue (float v)
    {
        fValue.Set (v);
    }
}
