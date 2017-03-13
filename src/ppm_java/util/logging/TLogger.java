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
 * The global logger. Logs all messages to a file or to stdout.
 * Access via static methods only.
 * 
 * @author Peter Hoppe
 */
public class TLogger
{
    /**
     * Creates a global logger. That one writes to stdout.
     */
    public static void CreateInstance ()
    {
        if (gLogger == null)
        {
            gLogger = new TLogger ();
        }
    }
    
    /**
     * Creates a global logger. That one writes to a file.
     * 
     * @param filePath      Log file path.
     */
    public static void CreateInstance (String filePath)
    {
        if (gLogger == null)
        {
            gLogger = new TLogger (filePath);
        }
    }

    /**
     * The logger singleton.
     */
    private static TLogger          gLogger = null;
    
    /**
     * Logs a fatal error.
     * 
     * @param msg           The error message.
     * @param source        Object of which class the log was coming from.
     * @param method        In which method did the error occur. 
     */
    public static void LogError (String msg, Object source, String method)
    {
        gLogger._Log (msg, ELogLevel.kError, source, method);
    }
    
    /**
     * Logs an info message.
     * 
     * @param msg           The message.
     */
    public static void LogMessage (String msg)
    {
        gLogger._Log (msg, ELogLevel.kMessage, null, null);
    }
    
    /**
     * Logs an info message.
     * 
     * @param msg           The message.
     * @param source        Object of which class the log was coming from.
     */
    public static void LogMessage (String msg, Object source)
    {
        gLogger._Log (msg, ELogLevel.kMessage, source, null);
    }
    
    /**
     * Logs an info message.
     * 
     * @param msg           The message.
     * @param source        Object of which class the log was coming from.
     * @param method        In which method did the error occur. 
     */
    public static void LogMessage (String msg, Object source, String method)
    {
        gLogger._Log (msg, ELogLevel.kMessage, source, method);
    }
    
    /**
     * Logs a warning message.
     * 
     * @param msg           The message.
     */
    public static void LogWarning (String msg)
    {
        gLogger._Log (msg, ELogLevel.kWarn, null, null);
    }
    
    /**
     * Logs a warning message.
     * 
     * @param msg           The message.
     * @param source        Object of which class the log was coming from.
     */
    public static void LogWarning (String msg, Object source)
    {
        gLogger._Log (msg, ELogLevel.kWarn, source, null);
    }
    
    /**
     * Logs a warning message.
     * 
     * @param msg           The message.
     * @param source        Object of which class the log was coming from.
     * @param method        In which method did the error occur. 
     */
    public static void LogWarning (String msg, Object source, String method)
    {
        gLogger._Log (msg, ELogLevel.kWarn, source, method);
    }

    /**
     * In the background we use the java logging API.
     */
    private Logger          fLogger;

    /**
     * cTor. Log messages will end up on stdout.
     */
    private TLogger ()
    {
        _Init (ELogTarget.kStdOut, null);
    }
    
    /**
     * cTor. Log messages will be written to a log file. 
     * 
     * @param filePath      The log file's path.
     */
    private TLogger (String filePath)
    {
        _Init (ELogTarget.kFile, filePath);
    }
    
    /**
     * Does any necessary setup work.
     * 
     * @param target        The target to send log messages to.
     * @param filePath      The log file to write to, if target is
     *                      {@link ELogTarget#kFile}. Otherwise ignored.
     */
    private void _Init (ELogTarget target, String filePath)
    {
        Logger                          rootLogger;
        Handler[]                       handlers;
        int                             i;
        int                             nHandlers;
        Handler                         cH;
        FileHandler                     fH;
        SimpleFormatter                 sF;

        if (target == ELogTarget.kFile)
        {
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
                fLogger.addHandler (fH);
            }
            catch (SecurityException | IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            fLogger = Logger.getGlobal ();
        }
    }

    /**
     * Writes a log entry. Depending on the log target, the message will either be written
     * to a log file or to stdout/stderr.
     * 
     * @param msg       The message to log
     * @param lv        The log level
     * @param source    Name of the class from which the log entry originates.
     * @param method    Name of the method from which the log entry originates.
     */
    private void _Log (String msg, ELogLevel lv, Object source, String method)
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
        switch (lv)
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
