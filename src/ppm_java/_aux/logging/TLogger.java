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

package ppm_java._aux.logging;

import java.io.PrintStream;
import java.util.Date;

/**
 * @author peter
 *
 */
public class TLogger
{
    private enum ELevel
    {
        kMessage,
        kWarn,
        kError
    };
    
    public static void LogError (String msg, Object source, String method)
    {
        _Log (msg, ELevel.kError, source, method);
    }
    
    public static void LogMessage (String msg)
    {
        _Log (msg, ELevel.kMessage, null, null);
    }
    
    public static void LogMessage (String msg, Object source)
    {
        _Log (msg, ELevel.kMessage, source, null);
    }
    
    public static void LogMessage (String msg, Object source, String method)
    {
        _Log (msg, ELevel.kMessage, source, method);
    }
    
    public static void LogWarning (String msg)
    {
        _Log (msg, ELevel.kWarn, null, null);
    }
    
    public static void LogWarning (String msg, Object source)
    {
        _Log (msg, ELevel.kWarn, source, null);
    }
    
    public static void LogWarning (String msg, Object source, String method)
    {
        _Log (msg, ELevel.kWarn, source, method);
    }
    
    /**
     * Writes a log entry to stderr.
     * 
     * @param msg       The message to log
     * @param lv        The log level
     * @param source    Name of the class from which the log entry originates.
     * @param method    Name of the method from which the log entry originates.
     */
    private static void _Log (String msg, ELevel lv, Object source, String method)
    {
        String      src;
        String      m;
        String      dtime;
        PrintStream out;
        
        src = "";
        if (source != null)
        {
            src = source.getClass ().getCanonicalName ();
        }
        
        if (method != null)
        {
            src += "::" + method;
        }
        
        dtime = new Date().toString ();
        m     = dtime + ": " + src + ": ";
        switch (lv)                                                     /* [100] */
        {
            case kError:
                m   += "FATAL: " + msg;
                out  = System.err;
                break;
            case kMessage:
                m   += "INFO: " + msg;
                out  = System.err;
                break;
            case kWarn:
                m   += "WARNING: " + msg;
                out  = System.err;
                break;
            default:
                m   += "INFO: " + msg;
                out  = System.err;
        }
        
        out.println (m);
    }
}

/*
[100]   We log everything to stderr, so the console output front end can write sample values to stdout.  
*/
