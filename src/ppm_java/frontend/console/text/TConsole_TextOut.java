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

package ppm_java.frontend.console.text;

import ppm_java._aux.typelib.IControllable;
import ppm_java._aux.typelib.VFrontend;
import ppm_java.backend.server.TController;

/**
 * A simple text-only frontend. Writes all peak values to stdout in csv format, 
 * one peak value per line.
 * Line format: <code>[chan_num],[peak_value]</code>
 * Where <code>[chan_num]</code> denotes the channel and <code>[peak_value]</code>
 * denotes the value of the peak level. Channel numbers map as 0 (zero) for 
 * the left channel and 1 (one) for the right channel. 
 * 
 * @author peter
 */
public class TConsole_TextOut extends VFrontend implements IControllable
{
    public static final void CreateInstance (String id)
    {
        new TConsole_TextOut (id);
    }
    
    /**
     * @param id
     * @param nMaxChanIn
     * @param nMaxChanOut
     */
    private TConsole_TextOut (String id)
    {
        super (id, 2, 0);
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VAudioProcessor#CreatePort_In(java.lang.String)
     */
    @Override
    public void CreatePort_In (String id)
    {
        int                         iPort;
        TConsole_TextOut_Endpoint   p;
        
        iPort   = GetNumPortsIn ();
        p       = new TConsole_TextOut_Endpoint (id, this, iPort);
        AddPortIn (p);
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VAudioProcessor#CreatePort_Out(java.lang.String)
     */
    @Override
    public void CreatePort_Out (String id)
    {
        throw new IllegalStateException ("This is a front end class - it doesn't use output ports.");
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IControllable#Start()
     */
    @Override
    public void Start ()
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IControllable#Stop()
     */
    @Override
    public void Stop ()
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VBrowseable#_Register()
     */
    @Override
    protected void _Register ()
    {
        TController.Register (this);
    }

    void Receive (float sample, int iPort)
    {
        String lineOut;
        
        lineOut = iPort + "," + sample;
        
        System.out.println (lineOut);
    }
}