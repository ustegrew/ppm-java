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

package ppm_java.util.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * @author Peter Hoppe
 *
 */
public class TArrayMap<T>
{
    private ArrayList<TNode<T>>         fList;
    private HashMap<String, TNode<T>>   fMap;
    private int                         fNElements;
    private int                         fNElementsMax;
    
    public TArrayMap ()
    {
        _Init (-1);
    }
    
    public TArrayMap (int nElementsMax)
    {
        _Init (nElementsMax);
    }
    
    public T Get (int i)
    {
        TNode<T>    nd;
        T           ret;
        
        _AssertInRange (i);
        nd  = fList.get (i);
        ret = nd.fValue;
        
        return ret;
    }
    
    public T Get (String key)
    {
        TNode<T>    nd;
        T           ret;
        
        _AssertHasElement (key, false);
        nd  = fMap.get (key);
        ret = nd.fValue;
        
        return ret;
    }
    
    public String GetKey (int i)
    {
        TNode<T>    nd;
        String      ret;
        
        _AssertInRange (i);
        nd  = fList.get (i);
        ret = nd.fKey;
        
        return ret;
    }
    
    public int GetNumElements ()
    {
        return fNElements;
    }
    
    public boolean HasElement (String key)
    {
        boolean ret;
        
        ret = fMap.containsKey (key);
        
        return ret;
    }
    
    public void Remove (int i)
    {
        TNode<T>    nd;
        
        _AssertInRange (i);
        nd = fList.get (i);
        _Remove (nd);
    }
    
    public void Remove (String key)
    {
        TNode<T>    nd;
        
        _AssertHasElement (key, false);
        nd = fMap.get (key);
        _Remove (nd);
    }
    
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
