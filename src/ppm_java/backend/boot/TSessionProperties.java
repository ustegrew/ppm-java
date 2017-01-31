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

/**
 * @author Peter Hoppe
 *
 */
public class TSessionProperties
{
    public static final int                 gkDefaultOptConsoleWidth                = 56;                               /* [100] */
    public static final boolean             gkDefaultOptShowDebugWindow             = false;
    public static final int                 gkDefaultOptTimerIntervalMs             = 20;
    public static final EFrontendType       gkDefaultOptUIType                      = EFrontendType.kConsoleLinearGauge;
    public static final boolean             gkDefaultOptUseDeprecatedPPMProcessor   = false;
    
    private String                          fErrorMsg;
    private String                          fHelpText;
    private boolean                         fIsValid;
    private int                             fOptConsoleWidth;
    private String                          fOptLogFilePath;
    private boolean                         fOptPrintHelp;
    private boolean                         fOptShowDebugWindow;
    private EFrontendType                   fOptUIType;
    private boolean                         fOptUseDeprecatedPPMProcessor;

    /**
     * 
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
     * @return
     */
    public String GetErrorMessage ()
    {
        return fErrorMsg;
    }
    
    public String GetHelpText ()
    {
        return fHelpText;
    }
    
    /**
     * @return the fOptConsoleWidth
     */
    public final int GetOptConsoleWidth ()
    {
        return fOptConsoleWidth;
    }
    
    /**
     * @return the fOptLogFilePath
     */
    public final String GetOptLogFilePath ()
    {
        return fOptLogFilePath;
    }
    
    /**
     * @return the fOptUIType
     */
    public final EFrontendType GetOptUIType ()
    {
        return fOptUIType;
    }
    
    /**
     * @return the fOptPrintHelp
     */
    public final boolean IsOptPrintHelp ()
    {
        return fOptPrintHelp;
    }

    /**
     * @return the fOptShowDebugWindow
     */
    public final boolean IsOptShowDebugWindow ()
    {
        return fOptShowDebugWindow;
    }
    
    /**
     * @return the fOptUseDeprecatedPPMProcessor
     */
    public final boolean IsOptUseDeprecatedPPMProcessor ()
    {
        return fOptUseDeprecatedPPMProcessor;
    }

    /**
     * @return
     */
    public boolean IsValid ()
    {
        return fIsValid;
    }

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
    
    void SetHelpText (String ht)
    {
        fHelpText = ht;
    }
    
    void SetErrorMessage (String m)
    {
        fErrorMsg = m;
    }
    
    private void _Invalidate (String errMsg)
    {
        fIsValid   = false;
        fErrorMsg  = "Missing option: 'uiType'";
    }
}

/*
[100]   For console based linear gauge: 56 = 7 * 8 = seven PPM segments, each having eight sub sections. 
*/