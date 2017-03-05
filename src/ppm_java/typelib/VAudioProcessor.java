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

package ppm_java.typelib;

import ppm_java.util.storage.TArrayMap;

/**
 * Base class for all modules processing audio samples.
 * 
 * An audio processor can have any number of {@link VAudioPort_Input inputs}
 * and any number of {@link VAudioPort_Output outputs}.
 * 
 * Mostly, an audio processor receives sample data from its input ports,
 * does some work with the sample data and pushes the result to its
 * output ports. Some audio processors only receive data 
 * (e.g. {@link VFrontend}), some others only send data 
 * (e.g. {@link VAudioDriver}).
 * 
 * Processing is normally driven from the input side - whenever data is 
 * pushed to the inputs, they will push the data on to the processor 
 * which will then do the processing with the data and then push the 
 * result to its outputs.
 * There are exceptions, for example an {@link VAudioDriver audio driver} 
 * receives sample data from an external source and pushes the data on 
 * to its outputs at the appropriate time.   
 * 
 * This class does all the admin related to input and output ports. Port
 * objects can be accessed by index or by key. Index based access is useful 
 * a client wishes to enumerate all ports.
 * 
 * @author Peter Hoppe
 */
public abstract class VAudioProcessor extends VAudioObject
{
    /**
     * Input ports.
     */
    private TArrayMap<VAudioPort_Input>   fPortsIn;
    
    /**
     * Output ports
     */
    private TArrayMap<VAudioPort_Output>  fPortsOut;
    
    /**
     * cTor.
     * 
     * @param id                The unique ID under which we register this object 
     *                          with the registry.
     * @param nMaxChanIn        Maximum number of input channels. Specify <code>-1</code>
     *                          for: No limit. 
     * @param nMaxChanOut       Maximum number of output channels. Specify <code>-1</code>
     *                          for: No limit.
     */
    public VAudioProcessor (String id, int nMaxChanIn, int nMaxChanOut)
    {
        super (id);
        
        fPortsIn    = new TArrayMap<> (nMaxChanIn);
        fPortsOut   = new TArrayMap<> (nMaxChanOut);
    }
    
    /**
     * Creates an input port. It's up to the concrete implementation to provide 
     * the input port class.
     * 
     * @param id    Unique ID under which we register the new input port.
     */
    public abstract void CreatePort_In  (String id);
    
    /**
     * Creates an output port. It's up to the concrete implementation to provide 
     * the output port class.
     * 
     * @param id    Unique ID under which we register the new output port.
     */
    public abstract void CreatePort_Out (String id);
    
    /**
     * @return  The number of input ports. 
     */
    public int GetNumPortsIn ()
    {
        int ret;
        
        ret = fPortsIn.GetNumElements ();
        
        return ret;
    }
    
    /**
     * @return  The number of output ports.
     */
    public int GetNumPortsOut ()
    {
        int ret;
        
        ret = fPortsOut.GetNumElements ();
        
        return ret;
    }
    
    /**
     * Returns the <code>i</code><sup>th<sup> input port.
     * 
     * @param   i   Zero based index of the requested port.
     * @return      Input port [<code>i</code>].
     */
    public VAudioPort GetPortIn (int i)
    {
        VAudioPort ret;
        
        ret = fPortsIn.Get (i);
        
        return ret;
    }
    
    /**
     * Returns the input port associated with <code>key</code>.
     * 
     * @param key   Unique ID of the requested port.
     * @return      Input port [<code>key</code>].
     */
    public VAudioPort_Input GetPortIn (String key)
    {
        VAudioPort_Input ret;
        
        ret = fPortsIn.Get (key);
        
        return ret;
    }
    
    /**
     * Returns the <code>i</code><sup>th<sup> output port.
     * 
     * @param   i   Zero based index of the requested port.
     * @return      Output port [<code>i</code>].
     */
    public VAudioPort_Output GetPortOut (int i)
    {
        VAudioPort_Output ret;
        
        ret = fPortsOut.Get (i);
        
        return ret;
    }
    
    /**
     * Returns the output port associated with <code>key</code>.
     * 
     * @param key   Unique ID of the requested port.
     * @return      Output port [<code>key</code>].
     */
    public VAudioPort_Output GetPortOut (String key)
    {
        VAudioPort_Output ret;
        
        ret = fPortsOut.Get (key);
        
        return ret;
    }
    
    /**
     * Adds a new input port.
     * 
     * @param p     The input port added.
     */
    protected void AddPortIn (VAudioPort_Input p)
    {
        String key;
        
        key = p.GetID ();
        fPortsIn.Set (key, p);
    }
    
    /**
     * Adds a new output port.
     * 
     * @param p     The output port added.
     */
    protected void AddPortOut (VAudioPort_Output p)
    {
        String key;
        
        key = p.GetID ();
        fPortsOut.Set (key, p);
    }
}
