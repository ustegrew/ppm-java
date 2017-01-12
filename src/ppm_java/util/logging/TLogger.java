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

package ppm_java.util.logging;

import java.io.IOException;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author peter
 *
 */
public class TLogger
{
    public static void CreateInstance (String filePath)
    {
        if (gLogger == null)
        {
            gLogger = new TLogger (filePath);
        }
    }

    private static enum ELevel
    {
        kMessage,
        kWarn,
        kError
    };

    private static TLogger          gLogger = null;
    
    public static void LogError (String msg, Object source, String method)
    {
        gLogger._Log (msg, ELevel.kError, source, method);
    }
    
    public static void LogMessage (String msg)
    {
        gLogger._Log (msg, ELevel.kMessage, null, null);
    }
    
    public static void LogMessage (String msg, Object source)
    {
        gLogger._Log (msg, ELevel.kMessage, source, null);
    }
    
    public static void LogMessage (String msg, Object source, String method)
    {
        gLogger._Log (msg, ELevel.kMessage, source, method);
    }
    
    public static void LogWarning (String msg)
    {
        gLogger._Log (msg, ELevel.kWarn, null, null);
    }
    
    public static void LogWarning (String msg, Object source)
    {
        gLogger._Log (msg, ELevel.kWarn, source, null);
    }
    
    public static void LogWarning (String msg, Object source, String method)
    {
        gLogger._Log (msg, ELevel.kWarn, source, method);
    }

    private Logger          fLogger;
    
    private TLogger (String filePath)
    {
        Logger                          rootLogger;
        Handler[]                       handlers;
        int                             i;
        int                             nHandlers;
        Handler                         cH;
        FileHandler                     fH;
        SimpleFormatter                 sF;

        /* Remove console handlers; we write log entries to a log file */
        rootLogger = Logger.getLogger ("");
        handlers   = rootLogger.getHandlers ();
        nHandlers  = handlers.length;
        if (nHandlers >= 1)
        {
            for (i = 0; i < nHandlers; i++)
            {
                cH = handlers [i];
                if (cH instanceof ConsoleHandler)
                {
                    rootLogger.removeHandler (cH);
                }
            }
        }
        
        try
        {
            sF = new SimpleFormatter ();
            fH = new FileHandler (filePath);
            fH.setFormatter (sF);
            fLogger = Logger.getLogger (Logger.GLOBAL_LOGGER_NAME);
        }
        catch (SecurityException | IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Writes a log entry.
     * 
     * @param msg       The message to log
     * @param lv        The log level
     * @param source    Name of the class from which the log entry originates.
     * @param method    Name of the method from which the log entry originates.
     */
    private void _Log (String msg, ELevel lv, Object source, String method)
    {
        String      src;
        String      m;
        String      dtime;
        
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
        m     = dtime + ": " + src + ": " + msg;
        switch (lv)                                                     /* [100] */
        {
            case kError:
                fLogger.severe (m);
                break;
            case kMessage:
                fLogger.info (m);
                break;
            case kWarn:
                fLogger.warning (m);
                break;
            default:
                fLogger.info (m);
        }
    }
}

/*
[100]   We log everything to stderr, so the console output front end can write sample values to stdout.  
*/
