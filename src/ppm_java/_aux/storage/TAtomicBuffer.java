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

package ppm_java._aux.storage;

import java.nio.FloatBuffer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * An atomic-access wrapper around a <code>FloatBuffer</code>. Designed 
 * for situations where we need to transport data between a high priority
 * thread and a low priority thread in a producer-consumer scenario. 
 * Example scenario: A thread operating audio hardware sends audio sample data 
 * to another thread updating the GUI. The audio hardware thread cannot 
 * afford to be blocked, hence has high priority. The GUI thread isn't time 
 * critical, and we can afford to have it loose some data. 
 * <p><br/></p>
 * 
 * The <code>TAtomicBuffer</code> guarantees that data can be passed from (to)
 * a high priority thread within a fixed (short) time without 
 * blocking it. This comes at a cost - the associated low priority thread 
 * does not have the guarantee that all data actually reaches it - in highly 
 * contended situations the <code>TAtomicBuffer</code> would simply drop
 * data where the low priority thread cannot keep up. If the low priority 
 * thread can't keep up with the streaming data it can't make full use of 
 * the stream anyway, so this seemed to be the best solution for data in 
 * contention situations.
 * <p><br/></p>
 * 
 * To monitor the quality of the data flow we offer various counters which can 
 * be queried by all parties (all {@link #ClearStats() clearable}}:
 * <p><br/></p>
 * 
 * <ul>
 *     <li>Overrun counter:    Increments each time the producer pushes a new 
 *                             packet whilst an old packet still awaits collection 
 *                             by the consumer. A steady increase of the overruns 
 *                             indicates that the consumer can't keep up with the 
 *                             producer and looses packets.</li>
 *     <li>Underrun counter:   Increments each time the consumer requests a packet
 *                             whilst none has been pushed since the last collection.
 *                             A steady increase of underruns indicates that the 
 *                             producer can't keep up with the consumer.</li>
 *     <li>Contention counter: Increments each time the producer and the consumer are
 *                             both trying to acquire access to the buffer at the 
 *                             same time. A steady increase of the contentions 
 *                             indicates that both, producer and consumer, are too 
 *                             aggressive trying to set and get data.</li>
 * </ul>
 * <p><br/></p>
 * 
 * Clients can use the underrun and overrun counters for automatic dropout compensation. 
 * For example, a low priority consumer thread can, with each cycle: 
 * <ul>
 *     <li>read overrun and underrun counts</li>
 *     <li>compute the difference: <code>d = nOver - nUnder</code>. If
 *         <code>d</code> is zero: All is well. If it's negative: Underruns. 
 *         If it's positive: Overruns.</li>
 *     <li>then compensate by adjusting the time to the next cycle:</li>
 *     <li>
 *         <ul>
 *             <li><code>d == 0</code>: No adjustment.</li>
 *             <li><code>d  < 0</code>: Underruns! Decrease read frequency => 
 *                                      Increase cycle time by a fixed number of milliseconds.</li>
 *             <li><code>d  > 0</code>: Overruns! Increase read frequency => 
 *                                      Decrease cycle time by a fixed number of milliseconds.</li>
 *         </ul>
 *     </li>
 * </ul>
 * <p><br/></p>
 * 
 * If the contention counter has a steep rise then both, the producer and consumer are
 * too aggressively trying to set and read data. As a mitigation strategy the party with the 
 * lower priority must be forced to yield whilst the higher priority party has the critical section.
 * You can use the {@link #IsLocked()} method for that:
 * <p><br/></p>
 * 
 * <pre>
 * while (myBuffer.IsLocked()) 
 * {
 *     try 
 *     {
 *         Thread.sleep (2);
 *     } catch (ThatInterruptedExceptionISeeNoUseFor e){}
 * }
 * </pre>
 * <p><br/></p>
 * 
 * This is not a pretty solution, but it works - best use sparingly, if you have to. 
 * <p><br/></p>
 * 
 * Don't assume the counters are precise. Overall access to the counters isn't thread safe
 * (it seemed overkill to implement that, too). Whilst each counter is using an atomic 
 * integer (and should be thread safe by itself), we still can have scenarios such as one
 * thread incrementing the underrun counter whilst another thread reads the overrun counter.
 * Due to lack of overall counter thread safety the counters can only serve as guideline 
 * to assess the various reasons for dropped data. Them counters aren't brilliant, but good 
 * enough for simple diagnosis.
 * <p><br/></p>
 * 
 * Clients can customize what's returned to the consumer thread when no valid data is 
 * available upon call to {@link #Get()}. Default is to return an empty sample chunk
 * (in effect saying - zero samples available). But it means that for every invalid 
 * {@link #Get()} call we create a new empty {@link FloatBuffer} which doesn't really 
 * feel that good. Therefore there's also the possibility to have {@link #Get()} 
 * return <code>null</code> which allows consumers to operate in a similar fashion 
 * as it's done with {@link AtomicInteger#compareAndSet(int, int)}:
 * <p><br/></p>
 * 
 * <pre>
 * // At some place: 
 * TAtomicBuffer myBuf = new TAtomicBuffer (ECopyPolicy.kCopyOnGet, EIfInvalidPolicy.kReturnNull)
 * 
 * // Inside consumer thread
 * do
 * {
 *     chunk = myBuf.Get ();
 *     try {
 *     Thread.sleep (yieldTime); // Can be a few mSec so the processor doesn't grind to a halt.
 *     } catch (ThatInterruptedExceptionISeeNoUseFor e){}
 * } while (chunk == null);
 * 
 * // Now do nifty stuff with the sample chunk. 
 * </pre>
 * <p><br/></p>
 * 
 * For synchronization between producer and consumer the <code>TAtomicBuffer</code>
 * makes use of an {@link AtomicInteger} flag; more specifically, the 
 * {@link AtomicInteger#compareAndSet(int, int)} method. This method is guaranteed 
 * to be atomic, i.e. allow access to the enclosed integer only by a single thread at
 * a time without blocking the other threads. We don't use the standard 
 * <code>synchronized</code> mutex as that one does block threads which haven't
 * acquired it. This can block the time critical thread, leading to priority inversion
 * (where the low priority thread blocks the high priority one). Additionally, 
 * on some platforms the <code>synchronized</code> primitive is quite costly, i.e.
 * time consuming. The {@link AtomicInteger#compareAndSet(int, int)} is much faster.
 * 
 * @author peter
 */
public class TAtomicBuffer
{
    /**
     * The Copy policy. To be supplied as argument to the constructor.
     * <dl>
     *     <dt>{@link #kCopyOnGet}</dt>
     *     <dd>
     *          Data will be deep copied when the consumer thread retrieves it. 
     *          This means that the data won't be copied upon setting it 
     *          ({@link TAtomicBuffer#Set(FloatBuffer)}) which
     *          means that setting data costs the least time. Use this policy 
     *          when the producer thread has a higher priority than the consumer thread. 
     *     </dd>
     *     <dt>{@link #kCopyOnSet}</dt>
     *     <dd>
     *          (<b>default</b>). Data will be deep copied when the producer thread sets it. 
     *          This means that the data won't be copied upon getting it 
     *          ({@link TAtomicBuffer#Get()) which means that getting the data 
     *          costs the least time. Use this policy when the consumer thread has 
     *          a higher priority than the producer thread.
     *     </dd>
     * </dl> 
     */
    public static enum ECopyPolicy
    {
        kCopyOnGet,
        kCopyOnSet
    }
    
    /**
     * The Invalid data policy. Determines what will be returned when the consumer calls
     * {@link TAtomicBuffer#Get()} and we have no data to offer - either because nothing
     * has been {@link TAtomicBuffer#Set(FloatBuffer) set} since the last call to 
     * {@link TAtomicBuffer#Get()}, or because just in this moment the producer is 
     * {@link TAtomicBuffer#Set(FloatBuffer) setting} a new sample chunk so we have a
     * contention. To be supplied as argument to the constructor.
     * <dl>
     *      <dt>{@link #kReturnEmpty}</dt>
     *      <dd>
     *          (<b>default</b>). Return an empty sample chunk.
     *      </dd>
     *      <dt>{@link #kReturnNull}</dt>
     *      <dd>
     *          Return <code>null</code>. This may be interesting for 
     *          a consumer which wants to call {@link TAtomicBuffer#Get()}
     *          inside an endless loop as long as it returns <code>null</code>.
     *      </dd>
     * </dl>
     */
    public static enum EIfInvalidPolicy
    {
        kReturnNull,
        kReturnEmpty
    }
    
    private static final int                gkFree                  = 0;
    private static final int                gkLocked                = 1;
    
    private FloatBuffer                     fBuffer;
    private ECopyPolicy                     fCopyPolicy;
    private AtomicInteger                   fFlag;
    private boolean                         fHasBeenCollected;
    private EIfInvalidPolicy                fIfInvalidPolicy;
    private AtomicInteger                   fNumContentions;
    private AtomicInteger                   fNumOverruns;
    private AtomicInteger                   fNumUnderruns;
    
    /**
     * Creates a new atomic buffer with default policies:
     * <ul>
     *     <li>{@link ECopyPolicy#kCopyOnSet}</li>
     *     <li>{@link EIfInvalidPolicy#kReturnEmpty}</li>
     * </ul> 
     */
    public TAtomicBuffer ()
    {
        _Init (ECopyPolicy.kCopyOnSet, EIfInvalidPolicy.kReturnEmpty);
    }
    
    /**
     * Creates a new atomic buffer with a custom {@link ECopyPolicy} and 
     * default {@link EIfInvalidPolicy#kReturnEmpty}. 
     *
     * @param copyPolicy        The desired copy policy. Determines which thread
     *                          is treated as high priority thread, and which one as 
     *                          low priority thread. 
     */
    public TAtomicBuffer (ECopyPolicy copyPolicy)
    {
        _Init (copyPolicy, EIfInvalidPolicy.kReturnEmpty);
    }
    
    /**
     * Creates a new atomic buffer with a custom {@link ECopyPolicy} and 
     * default {@link EIfInvalidPolicy#kReturnEmpty}. 
     *
     * @param copyPolicy        The desired copy policy. Determines which thread
     *                          is treated as high priority thread, and which one as 
     *                          low priority thread. 
     * @param ifInvalidPolicy   The desired invalid data policy. Determines what is 
     *                          returned to the consumer thread when there's no valid
     *                          data. 
     */
    public TAtomicBuffer (ECopyPolicy copyPolicy, EIfInvalidPolicy ifInvalidPolicy)
    {
        _Init (copyPolicy, ifInvalidPolicy);
    }
    
    /**
     * Clears the overrun, underrun and contention counter.
     * Not thread safe!
     */
    public void ClearStats ()
    {
        fNumContentions.getAndSet (0);
        fNumOverruns.getAndSet (0);
        fNumUnderruns.getAndSet (0);
    }
    
    /**
     * Returns the newest sample data. Meant for the consumer thread.
     * Note that at some stage there's a deep copying;
     * therefore we will never return the original {@link FloatBuffer} object. 
     * <p><br/></p>
     *
     * If there was no new sample data (i.e. stale data = that particular chunk has 
     * already been collected before) or if there was contention with the producer
     * thread then we'll return either an empty sample chunk or null. See 
     * {@link TAtomicBuffer introduction} for further information.
     * 
     * @return The newest sample data, or empty chunk, resp. <code>null</code>.   
     */
    public FloatBuffer Get ()
    {
        boolean         isLocked;
        FloatBuffer     ret;

        if (fCopyPolicy == ECopyPolicy.kCopyOnGet)
        {   /* Consumer has low priority */
            _Lock ();
            ret = _DataGet (true);
            _Unlock ();
        }
        else
        {   /* Consumer has high priority */
            isLocked = _TryLock ();                                     /* [110] */
            if (isLocked)
            {
                ret = _DataGet (false);
                _Unlock ();
            }
            else
            {   /* Consumer clashed with producer. Increment 
                   contention counter and return empty chunk 
                   or null. */
                fNumContentions.incrementAndGet ();
                if (fIfInvalidPolicy == EIfInvalidPolicy.kReturnEmpty)
                {
                    ret = FloatBuffer.allocate (0);
                }
                else
                {
                    ret = null;
                }
            }
        }
        
        return ret;
    }
    
    /**
     * Returns the number of times the producer and the consumer clashed 
     * when trying to acquire the critical section. It's likely that there will 
     * be some contention, but a steep count rise means that producer/consumer 
     * are too aggressively trying to acquire the critical section. 
     * To reduce the rise of the contention count consider querying the lock 
     * state by having producer or consumer call {@link #IsLocked()}
     * before trying to get/set sample data. You can also try to reduce the 
     * aggressiveness of both, the producer and the consumer - for example 
     * by having them cycle fewer times per second.
     * 
     * @return The current number of contentions between producer and consumer.
     * @see    {@link #ClearStats()}
     */
    public int GetNumContentions ()
    {
        int ret;
        
        ret = fNumContentions.getAndAdd (0);
        
        return ret;
    }
    
    /**
     * Returns the number of overruns. Overruns happen if the producer 
     * sets more chunks than the consumer collects, resulting in dropped 
     * data (= data loss). See the {@link TAtomicBuffer introduction} 
     * for an automated strategy to mitigate. 
     * 
     * @return The current number of overruns.
     * @see    {@link #ClearStats()}
     */
    public int GetNumOverruns ()
    {
        int ret;
        
        ret = fNumOverruns.getAndAdd (0);
        
        return ret;
    }
    
    /**
     * Returns the number of underruns. Underruns happen if the consumer 
     * tries to get more chunks than the producer submits, which leads 
     * to consumer starvation (= data loss). See the 
     * {@link TAtomicBuffer introduction} for an automated strategy to 
     * mitigate. 
     * 
     * @return The current number of underruns.
     * @see    {@link #ClearStats()}
     */
    public int GetNumUnderruns ()
    {
        int ret;
        
        ret = fNumUnderruns.getAndAdd (0);
        
        return ret;
    }
    
    /**
     * Returns <code>true</code> if the critical section is locked. 
     * It's not thread-safe because by the time the result is returned
     * the blocking thread may already have (un)locked the critical 
     * section. However, it's good enough to be used for automatic 
     * resolution of high contention situations. See the 
     * {@link TAtomicBuffer introduction} for an automated strategy to 
     * reduce contentions. 
     * 
     * @return <code>true</code> if the critical section is locked right now.
     */
    public boolean IsLocked ()
    {
        int     flag;
        boolean ret;
        
        flag = fFlag.getAndAdd (0);
        ret  = (flag == gkFree); 
        
        return ret;
    }
    
    /**
     * Sets the new sample chunk. If there is stale data it will be overwritten.
     * Meant for the producer thread. 
     * 
     * @param fb    Sample chunk to set.
     */
    public void Set (FloatBuffer fb)
    {
        boolean isLocked;
        
        if (fCopyPolicy == ECopyPolicy.kCopyOnSet)
        {   /* Producer has low priority */
            _Lock ();                                                   /* [120] */
            _DataSet (fb, true);
            _Unlock ();
        }
        else
        {   /* Producer has high priority */
            isLocked = _TryLock ();                                     /* [110] */
            if (isLocked)
            {
                _DataSet (fb, false); 
                _Unlock ();
            }
            else
            {
                fNumContentions.incrementAndGet ();                     /* [130] */
            }
        }
    }
    
    /**
     * An internal work method: Retrieves (a deep copy of) the newest sample chunk,
     * or empty chunk, resp. <code>null</code> if there's stale data. If there was
     * just stale data we will increment the underrun counter.
     * 
     * @param       doCopy      Return a deep copy, not the original FloatBuffer object.
     * @return                  The (deep copy of the) latest sample chunk.
     */
    private FloatBuffer _DataGet (boolean doCopy)
    {
        FloatBuffer ret;

        /*
         * Note, it's mandatory to NEVER do: fBuffer=FloatBuffer.allocate (0)! 
         * Otherwise, if doCopy == false, we will set ret=fBuffer,
         * then fBuffer=FloatBuffer.allocate(0) which is in effect 
         * ret=FloatBuffer.allocate (0) since ret==fBuffer! Thus the consumer 
         * will receive an empty data set even though there was valid data!
         * However we neither want to pass on stale data to the consumer. 
         * Our compromise is: When data is stale, we create an empty data 
         * set or null and pass that back to the consumer.
         */
        if (! fHasBeenCollected)
        {   /* Ah! Some fresh data is ready to collect! */
            if (doCopy)
            {   /* The consumer thread is low priority. */
                ret = _GetDeepCopy (fBuffer);                           /* [100] */
            }
            else
            {   /* The consumer is high priority - can't afford to copy. */
                ret = fBuffer;
            }
        }
        else
        {   /* Ouch! We are sitting on stale data! Increment underrun 
               counter and return empty chunk or null. */
            fNumUnderruns.incrementAndGet ();
            if (fIfInvalidPolicy == EIfInvalidPolicy.kReturnEmpty)
            {
                ret = FloatBuffer.allocate (0);    
            }
            else
            {
                ret = null;
            }
        }

        /* Mark data as stale. For simplicity we ignore whatever fHasBeenCollected was before */
        fHasBeenCollected = true;
        
        return ret;
    }
    
    /**
     * Store (a copy of the) new sample chunk, whether the consumer collected or not. 
     * If there was stale data (i.e. uncollected since last _DataSet(..)
     * we will increment the overrun counter.  
     * 
     * @param fb        The sample chunk to be stored.
     * @param doCopy    If <code>true</code>, store a deep copy instead of
     *                  the original. 
     */
    private void _DataSet (FloatBuffer fb, boolean doCopy)
    {
        if (doCopy)
        {   /* The producer thread is low priority. */
            fBuffer = _GetDeepCopy (fb);
        }
        else
        {   /* The producer is high priority - can't afford to copy. */
            fBuffer = fb;
        }
        
        if (! fHasBeenCollected)
        {   /* Ouch! We were sitting on stale data which means the consumer 
               wasn't able to collect in time. Increment the overrun counter */
            fNumOverruns.incrementAndGet ();
        }
        
        /* Mark data as fresh. For simplicity we ignore whatever fHasBeenCollected was before */
        fHasBeenCollected = false;
    }
    
    /**
     * Creates a deep copy of the passed float buffer (for copy-on-write semantics).
     * 
     * @param   fb      The {@link FloatBuffer} to be copied.
     * @return          The deep copy. Essentially, this is a new (separate) object.
     */
    private FloatBuffer _GetDeepCopy (FloatBuffer fb)
    {
        int         nNums;
        float[]     values;
        FloatBuffer ret;
        
        nNums   = fb.capacity ();
        values  = fb.array ();
        ret     = FloatBuffer.allocate (nNums);
        ret.put (values);
                
        return ret;
    }
    
    /**
     * Common initialization method - used by the various cTors.
     * 
     * @param copyPolicy        The copy policy.
     * @param ifInvalidPolicy   The "what do I return if no good data" policy. 
     */
    private void _Init (ECopyPolicy copyPolicy, EIfInvalidPolicy ifInvalidPolicy)
    {
        fNumContentions     = new AtomicInteger (0);
        fNumOverruns        = new AtomicInteger (0);
        fNumUnderruns       = new AtomicInteger (0);
        fFlag               = new AtomicInteger (gkFree);
        fBuffer             = FloatBuffer.allocate (0);
        fCopyPolicy         = copyPolicy;
        fIfInvalidPolicy    = ifInvalidPolicy;
        fHasBeenCollected   = true;
    }
    
    /**
     * Definitely acquire the lock on the critical section (spin lock, as it's 
     * merry-go-round until lock has been acquired).  
     */
    private void _Lock ()
    {
        boolean isLocked;
        
        do
        {
            isLocked = fFlag.compareAndSet (gkFree, gkLocked);
        } while (! isLocked);                                           /* [120] */
    }
    
    /**
     * Tentatively try to lock the critical section.
     * 
     * @return  <code>true</code> if lock acquired, <code>false</code> if acquiring failed.
     */
    private boolean _TryLock ()
    {
        boolean ret;
        
        ret = fFlag.compareAndSet (gkFree, gkLocked);
        
        return ret;
    }
    
    /**
     * Definitely unlock the critical section (spin lock, as it's 
     * merry-go-round until lock has been released). 
     */
    private void _Unlock ()
    {
        boolean isUnlocked;
        
        do
        {
            isUnlocked = fFlag.compareAndSet (gkLocked, gkFree);
        }
        while (! isUnlocked);                                           /* [120] */
    }
}

/*
[100]   It's safer to make a copy, as the producer thread 
        might overwrite values whilst the consumer still works 
        with the values retrieved. 
        
[110]   We only lock if the consumer thread isn't retrieving values - 
        The deep copy operation in Get() takes more time than the 
        reference copy in the Set() method. 
        That way at least the producer thread doesn't have to wait 
        on what's effectively a spin lock (whilst we deep copy). 
        It's justifiable on the grounds
        that we can simply discard updates to the buffer whilst the buffer 
        is being read by the consumer. This policy is especially effective when 
        the producer thread is a high priority thread and the consumer
        thread is lower priority (e.g. audio core loop produces 
        sample chunks, GUI thread consumes the data. With this policy 
        we minimize wait time for the audio core thread. We simply say
        that the GUI doesn't need fresh frames whilst it's updating itself.
        
[120]   This is, effectively, a spin lock.

[130]   In a previous version I did a silly mistake by writing
 
            else
            {
                fNumUnderruns.incrementAndGet ();
                fBuffer = FloatBuffer.allocate (0);     // silly mistake
            }

        This must have been because I thoughtlessly duplicated the idea 
        from the Get() method
        
            else
            {
                fNumUnderruns.incrementAndGet ();
                ret = FloatBuffer.allocate (0);
            }

        which has the aim of returning something definite to the caller.
        Similarly, in the Set() method I intended to bring fBuffer onto 
        something definite if the caller fails to acquire the lock. 
        This, of course, is faulty thinking - the last thing we want is to 
        clear the fBuffer whilst another thread is accessing it! With one 
        thoughtless line I would have killed the protection. I really wasn't 
        thinking whilst typing! I'm thankful I discovered it. 
*/