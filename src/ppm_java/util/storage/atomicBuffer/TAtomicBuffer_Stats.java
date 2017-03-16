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

import java.util.concurrent.atomic.AtomicInteger;

import ppm_java.typelib.IStats;

/**
 * Runtime statistics of a {@link TAtomicBuffer}. 
 * 
 * @author Peter Hoppe
 */
public class TAtomicBuffer_Stats implements IStats
{
    /**
     * Flag value for: Critical sections locked.
     */
    private static final int gkLocked = 1;
    
    /**
     * Flag value for: Critical sections unlocked.
     */
    private static final int gkUnlocked = 0;
    
    /**
     * The synchronization lock
     */
    private AtomicInteger               fLock;
    
    /**
     * The number of times the producer and the consumer clashed 
     * when trying to acquire the critical section. It's likely that there will 
     * be some contention, but a steep count rise means that producer/consumer 
     * are too aggressively trying to acquire the critical section. 
     * To reduce the rise of the contention count consider querying the lock 
     * state by having producer or consumer call {@link #IsLocked()}
     * before trying to get/set sample data. You can also try to reduce the 
     * aggressiveness of both, the producer and the consumer - for example 
     * by having (one of) them cycle fewer times per second.
     * 
     * @see TAtomicBuffer
     */
    private int                         fNumContentions;

    /**
     * The number of overruns. Overruns happen if the producer 
     * sets more chunks than the consumer collects, resulting in dropped 
     * data (= data loss). See the {@link TAtomicBuffer} 
     * for an automated strategy to mitigate.
     * 
     * @see TAtomicBuffer
     */
    private int                         fNumOverruns;
    
    /**
     * The number of underruns. Underruns happen if the consumer 
     * tries to get more chunks than the producer submits, which leads 
     * to consumer starvation (= data loss). See the 
     * {@link TAtomicBuffer} for an automated strategy to 
     * mitigate. 
     * 
     * @see TAtomicBuffer
     */
    private int                         fNumUnderruns;
    
    /**
     * The total number of contentions. This number will never be cleared.
     * 
     * @see #fNumContentions
     */
    private long                        fTot_NumContentions;
    
    /**
     * The total number of overruns. This number will never be cleared.
     * 
     * @see #fNumOverruns
     */
    private long                        fTot_NumOverruns;
    
    /**
     * The total number of underruns. This number will never be cleared.
     * 
     * @see #fNumUnderruns
     */
    private long                        fTot_NumUnderruns;
    
    /**
     * cTor.
     */
    public TAtomicBuffer_Stats ()
    {
        fLock               = new AtomicInteger (gkUnlocked);
        fNumContentions     = 0;
        fNumOverruns        = 0;
        fNumUnderruns       = 0;
        fTot_NumContentions = 0;
        fTot_NumOverruns    = 0;
        fTot_NumUnderruns   = 0;
    }
    
    /**
     * Clears the overrun, underrun and contention counter.
     */
    public void Clear ()
    {
        _Clear ();
    }
    
    /* (non-Javadoc)
     * @see ppm_java.typelib.IStats#GetDumpStr()
     */
    @Override
    public String GetDumpStr ()
    {
        int     diff;
        long    totDiff;
        String  ret;
        
        _Lock ();
        
        diff    = fNumOverruns - fNumUnderruns;
        totDiff = fTot_NumOverruns - fTot_NumUnderruns;
        ret     =   "cnt="    + fNumContentions + " (total:" + fTot_NumContentions + "), " 
                  + "o_runs=" + fNumOverruns    + " (total:" + fTot_NumOverruns    + "), " 
                  + "u_runs=" + fNumUnderruns   + " (total:" + fTot_NumUnderruns   + "), "
                  + "diff="   + diff            + " (total:" + totDiff             + ")";
        
        _Unlock ();
        
        return ret;
    }
    
    /**
     * Returns a dump of all the fields as one object. 
     * This method is thread safe, i.e. the returned data 
     * is guaranteed to be consistent.
     * 
     * @return  Dump of all fields.
     */
    public TAtomicBuffer_DumpRecord GetRecord ()
    {
        TAtomicBuffer_DumpRecord ret;
        
        ret = new TAtomicBuffer_DumpRecord ();
        
        _Lock ();
        
        ret.fNumContentions         = fNumContentions;
        ret.fNumOverruns            = fNumOverruns;
        ret.fNumUnderruns           = fNumUnderruns;
        ret.fDiffOverUnderruns      = fNumOverruns - fNumUnderruns;
        ret.fTot_NumContentions     = fTot_NumContentions;
        ret.fTot_NumOverruns        = fTot_NumOverruns;
        ret.fTot_NumUnderruns       = fTot_NumUnderruns;
        ret.fTot_DiffOverUnderruns  = fTot_NumOverruns - fTot_NumUnderruns;
        
        _Unlock ();
        
        return ret;
    }
    
    /**
     * Increments the contention counter.
     */
    void IncrementContentions ()
    {
        _Increment (EIncField.kContentions);
    }
    
    /**
     * Increments the overrun counter
     */
    void IncrementOverruns ()
    {
        _Increment (EIncField.kOverruns);
    }
    
    /**
     * Increments the underrun counter.
     */
    void IncrementUnderruns ()
    {
        _Increment (EIncField.kUnderruns);
    }
    
    /**
     * Clear all the fields.
     */
    private void _Clear ()
    {
        _Lock ();
        
        fNumContentions     = 0;
        fNumOverruns        = 0;
        fNumUnderruns       = 0;
        
        _Unlock ();
    }
    
    /**
     * Increments the requested field.
     * 
     * @param field     Signifier of the field to be incremented.
     */
    private void _Increment (EIncField field)
    {
        _Lock ();

        if (field == EIncField.kContentions)
        {
            fNumContentions++;
            fTot_NumContentions++;
        }
        else if (field == EIncField.kOverruns)
        {
            fNumOverruns++;
            fTot_NumOverruns++;
        }
        else if (field == EIncField.kUnderruns)
        {
            fNumUnderruns++;
            fTot_NumUnderruns++;
        }
        
        _Unlock ();
    }
    
    /**
     * Locks the critical sections. [100]
     */
    private void _Lock ()
    {
        boolean isLocked;
        
        isLocked = false;
        while (! isLocked)                                              /* [100] */
        {
            isLocked = fLock.compareAndSet (gkUnlocked, gkLocked);
        }
    }

    /**
     * Unlocks the critical sections. [100]
     */
    private void _Unlock ()
    {
        boolean isUnlocked;
        
        isUnlocked = false;
        while (! isUnlocked)                                            /* [100] */
        {
            isUnlocked = fLock.compareAndSet (gkLocked, gkUnlocked);
        }
    }
}

/* 
[100]   For locking/unlocking: We use a simple spin lock as each critical section has
        a linear execution flow and few statements, i.e. short execution time. Not 
        worth implementing anything more sophisticated.
*/