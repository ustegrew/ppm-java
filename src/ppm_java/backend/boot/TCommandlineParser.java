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

package ppm_java.backend.boot;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author Peter Hoppe
 *
 */
public class TCommandlineParser
{
    private static final String     gkCommandlineSyntax =
              "java -jar /path/to/ppm.jar [-h | --help] [-d | --debug] [-x | --dPPMProc] "
            + "(-l <path> | --logFile=<path>) (-u <ui_type> | --uiType=<ui_type>) "
            + "(-w <n> | --consoleWidth=<n>)"; 
    
    private Options                 fCommandlineOptions;
    private String                  fHelpText;
    
    /**
     * 
     */
    public TCommandlineParser ()
    {
        _Init ();
    }

    public TSessionProperties Parse (String[] args)
    {
        boolean                 isOK;
        CommandLineParser       clp;
        CommandLine             cl;
        TSessionProperties      ret;

        /* Parse commandline */
        isOK    = true;
        cl      = null;
        ret     = new TSessionProperties ();
        ret.SetHelpText (fHelpText);
        try
        {
            clp = new DefaultParser ();
            cl  = clp.parse (fCommandlineOptions, args);
        }
        catch (ParseException e)
        {
            isOK = false;
            ret.SetErrorMessage (e.getMessage ());
        }

        /* Parsing complete. Set program properties. */
        if (isOK)
        {
            ret.Set (cl);
            isOK = ret.IsValid ();                                      /* [100] */
        }

        return ret;
    }
    
    private void _Init ()
    {
        Option                  printHelp;
        Option                  showDebugWindow;
        Option                  logFilePath;
        Option                  uiType;
        Option                  consoleWidth;
        Option                  useDeprecatedPPMProcessor;
        
        consoleWidth = new Option ("w", "consoleWidth", true, "If guitype is 'consoleLinear': Width of meter in terminal columns.");
        consoleWidth.setRequired (false);

        logFilePath = new Option ("l", "logFile", true, "Path to log file");
        logFilePath.setRequired (false);
        logFilePath.setArgName  ("path");
        logFilePath.setType     (String.class);

        printHelp = new Option ("h", "help", false, "Print this message");
        printHelp.setRequired (false);

        showDebugWindow = new Option ("d",  "debug", false, "Show debug window");
        showDebugWindow.setRequired (false);
        
        uiType = new Option ("u", "uiType", true, "Which UI to use. Possible values: 'guiRadial', 'guiLinear', 'consoleLinear', 'consoleText'");
        uiType.setRequired (false);
        uiType.setArgName  ("typeID");
        uiType.setType     (String.class);

        useDeprecatedPPMProcessor = new Option ("x", "dPPMProc", false, "Use the deprecated PPM processor. Experimental development stage feature.");
        useDeprecatedPPMProcessor.setRequired (false);
        
        fCommandlineOptions = new Options ();
        fCommandlineOptions.addOption (printHelp);
        fCommandlineOptions.addOption (showDebugWindow);
        fCommandlineOptions.addOption (logFilePath);
        fCommandlineOptions.addOption (uiType);
        fCommandlineOptions.addOption (consoleWidth);
        fCommandlineOptions.addOption (useDeprecatedPPMProcessor);
        
        fHelpText = _GetHelpText ();
    }

    private String _GetHelpText ()
    {
        StringWriter        sw;
        PrintWriter         pw;
        StringBuffer        sb;
        HelpFormatter       hf;
        String              ret;
        
        sw = new StringWriter ();
        pw = new PrintWriter (sw);
        hf = new HelpFormatter ();
        hf.printHelp 
        (
            pw,                                 /* writer to which the help will be written */
            80,                                 /* number of characters to be displayed on each line */
            gkCommandlineSyntax,                /* syntax for this application */
            "",                                 /* banner to display at the beginning of the help */
            fCommandlineOptions,                /* Options instance */
            4,                                  /* number of characters of padding to be prefixed to each line */
            4,                                  /* the number of characters of padding to be prefixed to each description line */
            "",                                 /* the banner to display at the end of the help */
            true                                /* whether to print an automatically generated usage statement */
        );
        
        sb  = sw.getBuffer ();
        ret = sb.toString ();
        
        return ret;
    }
}

/*
[100]   ret.IsValid() - commandline options might parse OK from the 
                        CommanlineParser (first part of method), but may still
                        have syntactically correct, nonsensical values 
 */
