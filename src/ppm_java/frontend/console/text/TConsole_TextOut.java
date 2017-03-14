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
 * A simple text-only frontend. Writes all peak values to stdout in JSON format, 
 * one peak value per record.
 * 
 * @author Peter Hoppe
 */
public class TConsole_TextOut extends VFrontend implements IControllable
{
    /**
     * Creates a new instance of this frontend.
     * 
     * @param id            Unique ID as which we register this frontend.
     */
    public static final void CreateInstance (String id)
    {
        new TConsole_TextOut (id);
    }

    /**
     * Signifier, info record. 
     */
    private static final String             gkRecordTypeInfo        = "info";
    
    /**
     * Signifier, sample record.
     */
    private static final String             gkRecordTypeSample      = "sample";
    
    
    /**
     * cTor.
     * 
     * @param id            Unique ID as which we register this frontend.
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
     * @see ppm_java.typelib.IControllable#Start()
     */
    @Override
    public void Start ()
    {
        _PushRecord (ERecordType.kInfo, "event_meter_start", 0, 0);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IControllable#Stop()
     */
    @Override
    public void Stop ()
    {
        _PushRecord (ERecordType.kInfo, "event_meter_stop", 0, 0);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.VBrowseable#_Register()
     */
    @Override
    protected void _Register ()
    {
        TController.Register (this);
    }

    /**
     * Receives a sample value and prints it to stdout as JSON record.
     * 
     * @param sample        The sample value.
     * @param iPort         Which port the sample value is for.
     */
    void Receive (float sample, int iPort)
    {
        _PushRecord (ERecordType.kSample, null, sample, iPort);
    }
    
    /**
     * Creates a JSON record from the given parameters.
     * 
     * @param fieldType     Type of record.
     * @param info          If it's an info record, this is the info payload.
     * @param sample        If it's a sample record, this is the sample value.
     * @param iPort         If it's a sample record, the port the sample is for (L or R).
     */
    private void _PushRecord (ERecordType fieldType, String info, float sample, int iPort)
    {
        String record;
        
        if (fieldType == ERecordType.kInfo)
        {
            record = 
            "{" +
                "'type':'"  + gkRecordTypeInfo       + "',"  +
                "'value':'" + info                   + "'"   +
            "}";
        }
        else if (fieldType == ERecordType.kSample)
        {
            record = 
            "{" +
                "'type':'"  + gkRecordTypeSample     + "',"  +
                "'value':'" + sample                 + "',"  +
                "'port':'"  + iPort                  + "'"   +
            "}";
        }
        else
        {
            record = "{'type':'null'}"; 
        }
        
        System.out.println (record);
    }
}
