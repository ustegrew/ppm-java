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
import ppm_java.typelib.IEvented;
import ppm_java.typelib.VFrontend;

/**
 * A simple text-only frontend. Writes a stream of records in JSON format
 * to STDOUT. Caters for a special use case where we want to stream the metering 
 * information over a network connection to another host. As the JSON records 
 * are written to STDOUT it's possible to pipe them to another program, 
 * e.g. a web socket provider. This frontend produces two types of records:
 * <dl>
 *     <dt>Sample record</dt>
 *     <dd>
 *         A record containing a value (sample). Contains the sample value
 *         and which channel it's for. Record scheme:
 * <pre>
 * {
 *     "type":     "sample",       // Record type ID
 *     "value":    float-value,    // The sample value
 *     "port":     int-value       // For which port (0: Left channel, 1: Right channel)
 * }
 * </pre>
 *     </dd>
 *     <dt>Info record</dt>
 *     <dd>
 *         A record containing in-band information (e.g. "Metering stops"). Record scheme:
 * <pre>
 * {
 *     "type":     "info",         // Record type ID
 *     "value":    string          // The info message
 * }
 * </pre>
 *         To create info records we use ppm-java's event system. If you use this frontend, make 
 *         sure that you connect the frontend instance to an event emitter that sends the 
 *         control messages.<br/>
 *         <b>Note that the info record API isn't fully developed yet.</b>
 *     </dd>
 * </dl>
 * 
 * @author Peter Hoppe
 * 
 * TODO This frontend needs further development, as we haven't yet fully developed the info
 *      record API. So far, only "Start" and "Stop" are implemented. More items are needed,
 *      e.g. "is the audio in mono or stereo?"

 */
public class TConsole_TextOut 
    extends     VFrontend 
    implements  IControllable,
                IEvented
{
    /**
     * Signifier, info record. 
     */
    private static final String             gkRecordTypeInfo        = "info";

    /**
     * Signifier, sample record.
     */
    private static final String             gkRecordTypeSample      = "sample";
    
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
        TConsole_TextOut_Endpoint_In   p;
        
        p       = new TConsole_TextOut_Endpoint_In (id, this);
        AddPortIn (p);
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IEvented#OnEvent(int)
     */
    @Override
    public void OnEvent (int e)
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IEvented#OnEvent(int, int)
     */
    @Override
    public void OnEvent (int e, int arg0)
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IEvented#OnEvent(int, long)
     */
    @Override
    public void OnEvent (int e, long arg0)
    {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see ppm_java.typelib.IEvented#OnEvent(int, java.lang.String)
     */
    @Override
    public void OnEvent (int e, String arg0)
    {
        // TODO Auto-generated method stub
        
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
