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

package ppm_java._framework.typelib;

import ppm_java._aux.storage.TArrayMap;

/**
 * @author peter
 *
 */
public abstract class VAudioProcessor extends VAudioObject
{
    private TArrayMap<VAudioPort_Input>   fPortsIn;
    private TArrayMap<VAudioPort_Output>  fPortsOut;
    
    public VAudioProcessor (String id, int nMaxChanIn, int nMaxChanOut)
    {
        super (id);
        fPortsIn    = new TArrayMap<> (nMaxChanIn);
        fPortsOut   = new TArrayMap<> (nMaxChanOut);
    }
    
    public abstract void CreatePort_In  (String id);
    public abstract void CreatePort_Out (String id);
    
    public int GetNumPortsIn ()
    {
        int ret;
        
        ret = fPortsIn.GetNumElements ();
        
        return ret;
    }
    
    public int GetNumPortsOut ()
    {
        int ret;
        
        ret = fPortsOut.GetNumElements ();
        
        return ret;
    }
    
    public VAudioPort GetPortIn (int i)
    {
        VAudioPort ret;
        
        ret = fPortsIn.Get (i);
        
        return ret;
    }
    
    public VAudioPort_Input GetPortIn (String key)
    {
        VAudioPort_Input ret;
        
        ret = fPortsIn.Get (key);
        
        return ret;
    }
    
    public VAudioPort_Output GetPortOut (int i)
    {
        VAudioPort_Output ret;
        
        ret = fPortsOut.Get (i);
        
        return ret;
    }
    
    public VAudioPort_Output GetPortOut (String key)
    {
        VAudioPort_Output ret;
        
        ret = fPortsOut.Get (key);
        
        return ret;
    }
    
    protected void AddPortIn (VAudioPort_Input p)
    {
        String key;
        
        key = p.GetID ();
        fPortsIn.Set (key, p);
    }
    
    protected void AddPortOut (VAudioPort_Output p)
    {
        String key;
        
        key = p.GetID ();
        fPortsOut.Set (key, p);
    }
}
