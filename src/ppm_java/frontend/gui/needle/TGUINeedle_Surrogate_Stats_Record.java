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

final class TGUINeedle_Surrogate_Stats_Record
{
    private AtomicInteger            fCalcSection;
    private TAtomicDouble            fLastDBValue;
    private TAtomicDouble            fLastDisplayValue;
    
    public TGUINeedle_Surrogate_Stats_Record ()
    {
        fCalcSection        = new AtomicInteger (0);
        fLastDBValue        = new TAtomicDouble ();
        fLastDisplayValue   = new TAtomicDouble ();
    }
    
    public void SetCalcSection (int s)
    {
        fCalcSection.getAndSet (s);
    }
    
    public void SetDBValue (double dBv)
    {
        fLastDBValue.Set (dBv);
    }
    
    public void SetDisplayValue (double dv)
    {
        fLastDisplayValue.Set (dv);
    }
    
    public String GetDumpStr ()
    {
        String ret;
        
        ret = "            peak [dB]            = " + fLastDBValue.Get ()       + "\n" +
              "            displayValue         = " + fLastDisplayValue.Get ()  + "\n" +
              "            meter section        = " + fCalcSection.get ()       + "\n";
        
        return ret;
    }
}