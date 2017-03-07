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

import org.apache.commons.cli.CommandLine;

import ppm_java.typelib.EFrontendType;

/**
 * Properties of a ppm-java session.
 * 
 * @author Peter Hoppe
 */
public class TSessionProperties
{
    /**
     * Default option: Width for console based linear gauge: 
     *     56 = 7 * 8 = seven PPM segments, each having eight sub sections. 
     */
    public static final int                 gkDefaultOptConsoleWidth                = 56;
    
    /**
     * Default option: Show debug UI?
     */
    public static final boolean             gkDefaultOptShowDebugWindow             = false;
    
    /**
     * Default option: Display engine, timer cycle interval.
     */
    public static final int                 gkDefaultOptTimerIntervalMs             = 20;
    
    /**
     * Default option: Which UI to show.
     */
    public static final EFrontendType       gkDefaultOptUIType                      = EFrontendType.kConsoleLinearGauge;
    
    /**
     * Default option: Use the earlier monolithic PPM processor?
     */
    public static final boolean             gkDefaultOptUseDeprecatedPPMProcessor   = false;
    
    /**
     * Error message when there's a problem with the commandline options. 
     */
    private String                          fErrorMsg;
    
    /**
     * Help text, for the user.
     */
    private String                          fHelpText;
    
    /**
     * Commandline options valid, i.e. syntax correct, and values acceptable?
     */
    private boolean                         fIsValid;
    
    /**
     * Runtime option: Width for console based linear gauge.
     */
    private int                             fOptConsoleWidth;
    
    /**
     * Runtime option: Where to write the log file.
     */
    private String                          fOptLogFilePath;
    
    /**
     * Runtime option: Print the help text?
     */
    private boolean                         fOptPrintHelp;
    
    /**
     * Runtime option: Show the debug UI?
     */
    private boolean                         fOptShowDebugWindow;
    
    /**
     * Runtime option: Which Frontend?
     */
    private EFrontendType                   fOptUIType;
    
    /**
     * Runtime option: Use the earlier monolithic PPM processor?
     */
    private boolean                         fOptUseDeprecatedPPMProcessor;

    /**
     * cTor. Sets runtime properties to sensible defaults.
     */
    public TSessionProperties ()
    {
        fErrorMsg                       = "Missing commandline options";
        fIsValid                        = false;
        fOptConsoleWidth                = gkDefaultOptConsoleWidth;
        fOptLogFilePath                 = "";
        fOptPrintHelp                   = false;
        fOptShowDebugWindow             = gkDefaultOptShowDebugWindow;
        fOptUIType                      = gkDefaultOptUIType;
        fOptUseDeprecatedPPMProcessor   = gkDefaultOptUseDeprecatedPPMProcessor;
    }
    
    /**
     * @return  If commandline faulty: Explanatory text.
     */
    public String GetErrorMessage ()
    {
        return fErrorMsg;
    }
    
    /**
     * @return  Help text.
     */
    public String GetHelpText ()
    {
        return fHelpText;
    }
    
    /**
     * @return  Width for console based linear gauge.
     */
    public final int GetOptConsoleWidth ()
    {
        return fOptConsoleWidth;
    }
    
    /**
     * @return  Path where to write the log file.
     */
    public final String GetOptLogFilePath ()
    {
        return fOptLogFilePath;
    }
    
    /**
     * @return  Option which UI type to use.
     */
    public final EFrontendType GetOptUIType ()
    {
        return fOptUIType;
    }
    
    /**
     * @return  Whether to print the help file (<code>true</code>) or not (<code>false</code>).
     */
    public final boolean IsOptPrintHelp ()
    {
        return fOptPrintHelp;
    }

    /**
     * @return  Whether show the debug UI (<code>true</code>) or not (<code>false</code>).
     */
    public final boolean IsOptShowDebugWindow ()
    {
        return fOptShowDebugWindow;
    }
    
    /**
     * @return  Whether to use the earlier monolithic PPM processor (<code>true</code>) or not (<code>false</code>).
     */
    public final boolean IsOptUseDeprecatedPPMProcessor ()
    {
        return fOptUseDeprecatedPPMProcessor;
    }

    /**
     * @return  Whether the commandline options are valid (<code>true</code>) or not (<code>false</code>).
     */
    public boolean IsValid ()
    {
        return fIsValid;
    }

    /**
     * Sets the options from the parsed commandline.
     * 
     * @param cl    Parsed commandline.
     */
    void Set (CommandLine cl)
    {
        boolean     doContinue;
        String      v;
        
        fIsValid    = true;
        fErrorMsg   = "";
        
        doContinue  = true;
        
        /* Setting up: Option: -h | --help */
        if (cl.hasOption ("h"))
        {
            fOptPrintHelp   = true;
            doContinue      = false;
        }
        
        /* Setting up: Option -l | --logFile */
        if (doContinue)
        {
            if (cl.hasOption ("l"))
            {
                fOptLogFilePath = cl.getOptionValue ("l");
            }
            else
            {
                doContinue = false;
                _Invalidate ("Missing option: 'l'");
            }
        }
        
        /* Setting up: Option: -u | --uiType  */
        if (doContinue)
        {
            if (cl.hasOption ("u"))
            {
                v = cl.getOptionValue ("u");
                if (v.equals ("consoleLinear"))
                {
                    fOptUIType = EFrontendType.kConsoleLinearGauge;
                }
                else if (v.equals ("consoleText"))
                {
                    fOptUIType = EFrontendType.kConsoleTextOut;
                }
                else if (v.equals ("guiLinear"))
                {
                    fOptUIType = EFrontendType.kGUILinearGauge;
                }
                else if (v.equals ("guiRadial"))
                {
                    fOptUIType = EFrontendType.kGUINeedle;
                }
                else
                {
                    System.err.println ("Error in option 'u'. Must be one of: 'consoleLinear', 'consoleText', 'guiLinear', 'guiRadial'. Set to 'consoleLinear'.");
                }
            }
            else
            {
                System.err.println ("Missing option 'u'. Set to 'consoleLinear'.");
            }
        }
        
        /* Setting up: Option: -w | --consoleWidth */
        if (doContinue)
        {
            if (cl.hasOption ("w"))
            {
                v = cl.getOptionValue ("u");
                if (v.equals ("consoleLinear"))
                {
                    if (cl.hasOption ("consoleWidth"))
                    {
                        v = cl.getOptionValue ("w");
                        try
                        {
                            fOptConsoleWidth = Integer.parseInt (v);
                        }
                        catch (NumberFormatException e)
                        {
                            System.err.println ("Invalid value for option 'w'. Set to default value (" + gkDefaultOptConsoleWidth + ")");
                            fOptConsoleWidth = gkDefaultOptConsoleWidth;
                        }
                    }
                    else
                    {
                        System.err.println ("Missing option 'w' (Should be specified if uiType = 'consoleLinear'). Set to default value (" + gkDefaultOptConsoleWidth + ")");
                    }
                }
            }
        }
        
        /* Setting up: Option: -d | --debug */
        if (doContinue)
        {
            if (cl.hasOption ("d"))
            {
                fOptShowDebugWindow = true;
            }
        }
        
        /* Setting up: Option: -x | --dPPMProc  */
        if (doContinue)
        {
            if (cl.hasOption ("x"))
            {
                fOptUseDeprecatedPPMProcessor = true;
            }
        }
    }
    
    /**
     * Sets the help text.
     * 
     * @param ht    The help text.
     */
    void SetHelpText (String ht)
    {
        fHelpText = ht;
    }
    
    /**
     * Sets the explanatory message if commandline is faulty.
     * 
     * @param m     The explanatory message.
     */
    void SetErrorMessage (String m)
    {
        fErrorMsg = m;
    }
    
    /**
     * Invalidates this session property container.
     * 
     * @param errMsg    Explanatory message.
     */
    private void _Invalidate (String errMsg)
    {
        fIsValid   = false;
        fErrorMsg  = errMsg;
    }
}
