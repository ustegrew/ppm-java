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

package ppm_java.util.storage.arrayMap;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread safe {@link TArrayMap} wrapper.
 * 
 * @author Peter Hoppe
 */
public class TAtomicArrayMap<T>
{
    /**
     * Flag value: A thread acquired the lock.
     */
    private static final int            gkLocked    = 1;
    
    /**
     * Flag value: Store can be accessed.
     */
    private static final int            gkUnlocked  = 0;
    
    /**
     * The underlying (thread-unsafe) store.
     */
    private TArrayMap<T>                fData;
    
    /**
     * Thread-safe lock.
     */
    private AtomicInteger               fLock;
    
    /**
     * cTor.
     */
    public TAtomicArrayMap ()
    {
        fLock       = new AtomicInteger (gkUnlocked);
        fData       = new TArrayMap<>   ();
    }
    
    /**
     * Returns the <code>i</code><sup>th</sup> element.
     * 
     * @param i     Zero based index of the requested element.
     * @return      Requested element.
     * @throws      IndexOutOfBoundsException       If <code>i</code> is out of bounds.
     */
    public T Get (int i)
    {
        T ret;
        
        _Lock ();
        ret = fData.Get (i);
        _Unlock ();
        
        return ret;
    }

    /**
     * Returns the element associated with the given <code>key</code>. 
     * 
     * @param key   The key associated with the requested element.
     * @return      Requested element.
     * @throws      IllegalArgumentException        If there's no element with the given <code>key</code>.
     */
    public T Get (String key)
    {
        T ret;
        
        _Lock ();
        ret = fData.Get (key);
        _Unlock ();
        
        return ret;
    }

    /**
     * @return      Number of elements stored.
     */
    public int GetNumElements ()
    {
        int ret;
        
        _Lock ();
        ret = fData.GetNumElements ();
        _Unlock ();
        
        return ret;
    }

    /**
     * Returns <code>true</code> if there is an element associated with the given <code>key</code>.
     * 
     * @param key       The key queried.
     * @return          <code>true</code> if <code>key</code> is known, <code>false</code> otherwise.
     */
    public boolean HasElement (String key)
    {
        boolean ret;
        
        _Lock ();
        ret = fData.HasElement (key);
        _Unlock ();
        
        return ret;
    }

    /**
     * Removes the <code>i</code><sup>th</sup> element. Note that of all 
     * operations, removing costs the most time. Too many removes might 
     * impact performance.
     * 
     * @param i     Zero based index of the element to be removed.
     * @throws      IndexOutOfBoundsException       If <code>i</code> is out of bounds.
     */
    public void Remove (int i)
    {
        _Lock ();
        fData.Remove (i);
        _Unlock ();
    }

    /**
     * Removes the element associated with the given <code>key</code>.
     * Note that of all operations, removing costs the most time. Too many 
     * removes might impact performance.
     * 
     * @param key   The key associated with the element to be removed.
     * @throws      IllegalArgumentException        If there's no element with the given <code>key</code>.
     */
    public void Remove (String key)
    {
        _Lock ();
        fData.Remove (key);
        _Unlock ();
    }

    /**
     * Adds an object. Object will be associated with the given <code>key</code>.
     * The key must be unique. Index will be one higher than the last object 
     * stored (or zero, if no object exists in storage yet). 
     * 
     * @param key       The key associated with the element to be stored.
     * @param value     The object to be stored.
     * @throws          IllegalArgumentException    If <code>key</code> is already known.
     * @throws          IllegalStateException       If the store is full. See {@link #TArrayMap(int)}.
     */
    public void Set (String key, T value)
    {
        _Lock ();
        fData.Set (key, value);
        _Unlock ();
    }

    /**
     * A spin lock, acquiring a critical section.
     */
    private void _Lock ()
    {
        boolean         isLocked;
        
        do                                                              /* [100] */
        {
            isLocked = fLock.compareAndSet (gkUnlocked, gkLocked);
        }  while (! isLocked);
    }
    
    /**
     * A spin lock, releasing a critical section.
     */
    private void _Unlock ()
    {
        boolean isUnlocked;                                             /* [100] [110] */
        
        do
        {
            isUnlocked = fLock.compareAndSet (gkLocked, gkUnlocked);
        } while (! isUnlocked);
    }
}

/*
[100]   Using a simple spin lock for synchronization: Since it's a loop it smells 
        of program freeze. However, in practice the loop should be finished within 
        one or a few cycles, as the critical sections follow a very straight forward, 
        linear code execution flow - in effect, just a few statements (function call
        with everything that is run whilst executing it) per critical 
        section. Suppose:
        * Thread A calls Get(0)
        * Thread A calls _Lock()
        * Thread A enters the spin lock and calls compareAndSet().
        * Thread A acquires the lock. Critical section now protected.
        * Just at that time, Thread B calls Get(1)
        * Thread B calls _Lock(). However, fLock is already set to locked, so 
              Thread B sits in the spin lock. It shouldn't take long (e.g. a few cycles).
        * Object is being retrieved from the TArrayMap. This is what Thread B is waiting for.
        * Thread A calls _Unlock()
        * Thread A enters the spin lock in _Unlock(). 
        * Thread A calls compareAndSet which sets the state to unlocked.
        * Thread B reaches the compareAndSet statement and locks the thread.
        In effect, Thread B has to wait a very short time - whilst the object is being retrieved.
        Therefore, in practice, the spin lock won't hold up Thread B for very long.
        
        I could use a simple synchronized primitive, but somewhere I read that 
        using atomic variables has less costs than using synchronized. Besides, I'd
        like to port this program into C++ (QT toolkit) which I know to offer
        atomic variables, but I don't know what replacement exists for Java's  
        synchronized primitive. 
        
[110]   I don't know whether I actually need a spin lock here or whether a single
        compareAndSet statement suffices (without loop). For by the time execution 
        reaches the compareAndSet statement all other threads are already excluded.
        It still feels better - the AtomicInteger itself can be accessed by multiple
        threads simultaneously (e.g. the exclusive thread calls _Unlock() whilst 
        another thread calls _Lock() at all times. I don't know whether a single 
        compareAndSet statement without loop is enough - just to be safe I use the same 
        spin lock mechanism as in _Lock().
*/