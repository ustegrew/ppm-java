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

package ppm_java.util.storage.atomicBuffer;

/**
 * A dumping class. Used to dump all statistics into
 * one object in a thread safe way. 
 * 
 * @author Peter Hoppe
 * @see    TAtomicBuffer_Stats#GetRecord()
 */
public class TAtomicBuffer_DumpRecord
{
    /**
     * @see TAtomicBuffer_Stats#fNumContentions
     */
    public int      fNumContentions;
    
    /**
     * @see TAtomicBuffer_Stats#fNumOverruns
     */
    public int      fNumOverruns;
    
    /**
     * @see TAtomicBuffer_Stats#fNumOverruns
     */
    public int      fNumUnderruns;
    
    /**
     * The difference between overruns and underruns.
     */
    public int      fDiffOverUnderruns;
}