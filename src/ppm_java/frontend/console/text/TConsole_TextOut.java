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

import ppm_java.backend.TController;
import ppm_java.typelib.IControllable;
import ppm_java.typelib.VFrontend;

/**
 * A simple text-only frontend. Writes all peak values to stdout in csv format, 
 * one peak value per line.
 * Line format: <code>[chan_num],[peak_value]</code>
 * Where <code>[chan_num]</code> denotes the channel and <code>[peak_value]</code>
 * denotes the value of the peak level. Channel numbers map as 0 (zero) for 
 * the left channel and 1 (one) for the right channel. 
 * 
 * @author Peter Hoppe
 */
public class TConsole_TextOut extends VFrontend implements IControllable
{
    public static final void CreateInstance (String id)
    {
        new TConsole_TextOut (id);
    }

    private static enum EFieldType
    {
        kInfo,
        kSample
    }
    
    private static final String             gkFieldTypeInfo         = "info";
    private static final String             gkFieldTypeSample       = "sample";
    
    
    /**
     * @param id
     * @param nMaxChanIn
     * @param nMaxChanOut
     */
    private TConsole_TextOut (String id)
    {
        super (id, 2);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioProcessor#CreatePort_In(java.lang.String)
     */
    @Override
    public void CreatePort_In (String id)
    {
        TConsole_TextOut_Endpoint   p;
        
        p       = new TConsole_TextOut_Endpoint (id, this);
        AddPortIn (p);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VAudioProcessor#CreatePort_Out(java.lang.String)
     */
    @Override
    public void CreatePort_Out (String id)
    {
        throw new IllegalStateException ("This is a front end class - it doesn't use output ports.");
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IControllable#Start()
     */
    @Override
    public void Start ()
    {
        _PushRecord (EFieldType.kInfo, "event_meter_start", 0, 0);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IControllable#Stop()
     */
    @Override
    public void Stop ()
    {
        _PushRecord (EFieldType.kInfo, "event_meter_stop", 0, 0);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VBrowseable#_Register()
     */
    @Override
    protected void _Register ()
    {
        TController.Register (this);
    }

    void Receive (float sample, int iPort)
    {
        _PushRecord (EFieldType.kSample, null, sample, iPort);
    }
    
    private void _PushRecord (EFieldType fieldType, String info, float sample, int iPort)
    {
        String record;
        
        if (fieldType == EFieldType.kInfo)
        {
            record = 
            "{" +
                "'type':'"  + gkFieldTypeInfo       + "',"  +
                "'value':'" + info                  + "'"   +
            "}";
        }
        else if (fieldType == EFieldType.kSample)
        {
            record = 
            "{" +
                "'type':'"  + gkFieldTypeSample     + "',"  +
                "'value':'" + sample                + "',"  +
                "'port':'"  + iPort                 + "'"   +
            "}";
        }
        else
        {
            record = "{'type':'null'}"; 
        }
        
        System.out.println (record);
    }
}
