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

package ppm_java.backend.server.module.converter_db;

import ppm_java._aux.storage.TAtomicDouble;
import ppm_java._aux.typelib.IStats;

/**
 * @author peter
 *
 */
public class TNodeConverterDb_Stats implements IStats
{
    private TAtomicDouble               fDB;
    private TNodeConverterDb            fHost;
    private TAtomicDouble               fValue;
    
    public TNodeConverterDb_Stats (TNodeConverterDb host)
    {
        fHost   = host;
        fDB     = new TAtomicDouble ();
        fValue  = new TAtomicDouble ();
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IStats#GetDumpStr()
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

    void SetDB (float dB)
    {
        fDB.Set (dB);
    }
    
    void SetValue (float v)
    {
        fValue.Set (v);
    }
}
