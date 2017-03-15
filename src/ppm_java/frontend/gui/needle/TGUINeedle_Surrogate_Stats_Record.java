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

package ppm_java.frontend.gui.needle;

import java.util.concurrent.atomic.AtomicInteger;

import ppm_java.util.storage.TAtomicDouble;

/**
 * Container for runtime statistics of a {@link TGUINeedle_Surrogate}, for one channel. 
 * 
 * @author Peter Hoppe
 */
final class TGUINeedle_Surrogate_Stats_Record
{
    /**
     * The current meter section corresponding to the input value. 
     */
    private AtomicInteger            fCalcSection;
    
    /**
     * The current input value, in dB.
     */
    private TAtomicDouble            fLastDBValue;
    
    /**
     * The current display value, in PPM units.
     */
    private TAtomicDouble            fLastDisplayValue;
    
    /**
     * cTor.
     */
    public TGUINeedle_Surrogate_Stats_Record ()
    {
        fCalcSection        = new AtomicInteger (0);
        fLastDBValue        = new TAtomicDouble ();
        fLastDisplayValue   = new TAtomicDouble ();
    }
    
    /**
     * @return  The current runtime statistics as text dump.
     */
    public String GetDumpStr ()
    {
        String ret;
        
        ret = "            peak [dB]            = " + fLastDBValue.Get ()       + "\n" +
              "            displayValue         = " + fLastDisplayValue.Get ()  + "\n" +
              "            meter section        = " + fCalcSection.get ()       + "\n";
        
        return ret;
    }
    
    /**
     * Sets the current meter section corresponding to the input value.
     * 
     * @param s         The meter section value.
     */
    void SetCalcSection (int s)
    {
        fCalcSection.getAndSet (s);
    }
    
    /**
     * Sets the current input value.
     * 
     * @param dBv       The current input value, in dB. 
     */
    void SetDBValue (double dBv)
    {
        fLastDBValue.Set (dBv);
    }
    
    /**
     * Sets the current display value.
     * 
     * @param dv        The current display value, in PPM units.
     */
    void SetDisplayValue (double dv)
    {
        fLastDisplayValue.Set (dv);
    }
}