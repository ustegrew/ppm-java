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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * A generic map that stores objects by key and by index.
 * Element order is guaranteed during the lifetime of the
 * map, elements are ordered in the sequence they are added.
 * The index is zero based.<br>
 * This store is not thread safe. Clients that need thread 
 * safety need to add their own thread safety mechanism.
 * 
 * @author Peter Hoppe
 */
public class TArrayMap<T>
{
    /**
     * List containing the objects, for index based access.
     */
    private ArrayList<TNode<T>>         fList;
    
    /**
     * Map containing the objects, for key based access.
     */
    private HashMap<String, TNode<T>>   fMap;
    
    /**
     * Number of elements stored. 
     */
    private int                         fNElements;
    
    /**
     * The maximum number of elements that are allowed.
     * @see #TArrayMap(int)
     */
    private int                         fNElementsMax;
    
    /**
     * cTor.
     * 
     * Store has no limit as to how many elements can be added.
     */
    public TArrayMap ()
    {
        _Init (-1);
    }
    
    /**
     * cTor.
     * 
     * Store has a limit as to how many elements can be added.
     * 
     * @param nElementsMax      The maximum number of elements allowed. 
     *                          <code>-1</code> means: No limit, the same as 
     *                          using {@link #TArrayMap()}.
     */
    public TArrayMap (int nElementsMax)
    {
        _Init (nElementsMax);
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
        TNode<T>    nd;
        T           ret;
        
        _AssertInRange (i);
        nd  = fList.get (i);
        ret = nd.fValue;
        
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
        TNode<T>    nd;
        T           ret;
        
        _AssertHasElement (key, false);
        nd  = fMap.get (key);
        ret = nd.fValue;
        
        return ret;
    }
    
    /**
     * Returns the key associated with the <code>i</code><sup>th</sup> element.
     * 
     * @param i     Zero based index of the requested element.
     * @return      Requested element.
     * @throws      IndexOutOfBoundsException       If <code>i</code> is out of bounds.
     */
    public String GetKey (int i)
    {
        TNode<T>    nd;
        String      ret;
        
        _AssertInRange (i);
        nd  = fList.get (i);
        ret = nd.fKey;
        
        return ret;
    }
    
    /**
     * @return      Number of elements stored.
     */
    public int GetNumElements ()
    {
        return fNElements;
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
        
        ret = fMap.containsKey (key);
        
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
        TNode<T>    nd;
        
        _AssertInRange (i);
        nd = fList.get (i);
        _Remove (nd);
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
        TNode<T>    nd;
        
        _AssertHasElement (key, false);
        nd = fMap.get (key);
        _Remove (nd);
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
        TNode<T> nd;
        
        _AssertSpace ();
        _AssertHasElement (key, true);
        nd = new TNode<> ();
        fList.add (nd);
        fMap.put (key, nd);
        nd.fValue   = value;
        nd.fKey     = key;
        nd.fIndex   = fNElements;
        fNElements++;
    }
    
    /**
     * Asserts that the given <code>key</code> does exist, resp. is unique.
     * 
     * Throws an {@link IllegalArgumentException} if <code>isInverse</code> is 
     * <code>false</code> and the given <code>key</code> does <i>not</i> exist.
     * If <code>isInverse</code> is <code>true</code>, the test is reversed, i.e. 
     * the exception is thrown when the key <i>does</i> exist. 
     * 
     * @param key           The key being tested.
     * @param isInverse     If <code>true</code>, reverse the test.
     * @throws              IllegalArgumentException        if the test fails.
     */
    private void _AssertHasElement (String key, boolean isInverse)
    {
        boolean hasElement;
        
        hasElement = fMap.containsKey (key);
        if (isInverse)
        {
            if (hasElement)
            {
                throw new IllegalArgumentException ("Key already assigned: " + key);
            }
        }
        else
        {
            if (! hasElement)
            {
                throw new NoSuchElementException ("Can't find key: " + key);
            }
        }
    }
    
    /**
     * Asserts that the given index is in bounds. Pass condition:
     * <code>i</code> must be in <code>[0, </code>{@link #fNElements}<code>[</code>.
     * 
     * @param i     The tested index.
     * @throws      IndexOutOfBoundsException       if given index is out of bounds.
     */
    private void _AssertInRange (int i)
    {
        if (i < 0)
        {
            throw new IndexOutOfBoundsException ("Index must be in [0, " + fNElements + "[. Given: " + i);
        }
        if (i >= fNElements)
        {
            throw new IndexOutOfBoundsException ("Index must be in [0, " + fNElements + "[. Given: " + i);
        }
    }
    
    /**
     * Asserts that there's still space for a new element.
     * If there's no limit imposed, test will always pass.
     * 
     * @throws IllegalStateException    if the upper storage limit has been reached.
     * @see     #TArrayMap(int) 
     */
    private void _AssertSpace ()
    {
        if (fNElementsMax >= 0)
        {
            if (fNElements >= fNElementsMax)
            {
                throw new IllegalStateException ("Size restricted to " + fNElementsMax + " Elements. Can't add more.");
            }
        }
    }
    
    /**
     * Initializes the store.
     * 
     * @param nElementsMax      Upper limit of elements that can be stored. <code>-1</code> means: No limit.
     */
    private void _Init (int nElementsMax)
    {
        if (nElementsMax <= -2)
        {
            throw new IllegalArgumentException ("Condition for max number of elements: n >= -1, where -1 means: No limit. Given: " + nElementsMax);
        }
        fNElementsMax       = nElementsMax;
        fList               = new ArrayList<> ();
        fMap                = new HashMap<> ();
    }
    
    /**
     * Removes the given node.
     * 
     * @param nd        Node to be removed.
     */
    private void _Remove (TNode<T> nd)
    {
        int         i;
        TNode<T>    nd1;
        
        fList.remove    (nd.fIndex);
        fMap.remove     (nd.fKey);
        fNElements--;
        
        /* Reindex after nd.fIndex */
        if (fNElements >= 1)
        {
            for (i = nd.fIndex; i < fNElements; i++)
            {
                nd1 = fList.get (i);
                nd1.fIndex = i;
            }
        }
    }
}
