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
     * The difference between overruns and underruns.
     */
    public int      fDiffOverUnderruns;
    
    /**
     * The number of contentions.
     * 
     * @see TAtomicBuffer_Stats#fNumContentions
     */
    public int      fNumContentions;
    
    /**
     * The number of overruns.
     * 
     * @see TAtomicBuffer_Stats#fNumOverruns
     */
    public int      fNumOverruns;
    
    /**
     * The number of underruns.
     * 
     * @see TAtomicBuffer_Stats#fNumUnderruns
     */
    public int      fNumUnderruns;

    /**
     * The difference between the totals of overruns and underruns.
     */
    public long     fTot_DiffOverUnderruns;
    
    /**
     * The total number of contentions. This number will never be cleared.
     * 
     * @see TAtomicBuffer_Stats#fTot_NumContentions
     */
    public long     fTot_NumContentions;
    
    /**
     * The total number of overruns. This number will never be cleared.
     * 
     * @see TAtomicBuffer_Stats#fTot_NumOverruns
     */
    public long     fTot_NumOverruns;
    
    /**
     * The total number of underruns. This number will never be cleared.
     * 
     * @see TAtomicBuffer_Stats#fTot_NumUnderruns
     */
    public long     fTot_NumUnderruns;
    
}