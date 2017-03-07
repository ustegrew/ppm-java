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

import ppm_java.backend.TController;
import ppm_java.typelib.EFrontendType;
import ppm_java.util.logging.TLogger;

/**
 * @author Peter Hoppe
 *
 */
public class TSetup
{
    private int                             fOptConsoleWidth;
    private String                          fOptLogFilePath;
    private boolean                         fOptPrintHelp;
    private boolean                         fOptShowDebugWindow;
    private EFrontendType                   fOptUIType;
    private boolean                         fOptUseDeprecatedPPMProcessor;
    
    /**
     * 
     */
    public TSetup ()
    {
        fOptConsoleWidth                    = TSessionProperties.gkDefaultOptConsoleWidth;
        fOptLogFilePath                     = "";
        fOptPrintHelp                       = false;
        fOptShowDebugWindow                 = TSessionProperties.gkDefaultOptShowDebugWindow;
        fOptUIType                          = TSessionProperties.gkDefaultOptUIType;
        fOptUseDeprecatedPPMProcessor       = TSessionProperties.gkDefaultOptUseDeprecatedPPMProcessor;
    }

    /**
     * @param fSessionProps
     */
    public void ExecuteWith (TSessionProperties sessionProps)
    {
        String helpText;
        
        fOptConsoleWidth                    = sessionProps.GetOptConsoleWidth ();
        fOptLogFilePath                     = sessionProps.GetOptLogFilePath ();
        fOptPrintHelp                       = sessionProps.IsOptPrintHelp ();
        fOptShowDebugWindow                 = sessionProps.IsOptShowDebugWindow ();
        fOptUIType                          = sessionProps.GetOptUIType ();
        fOptUseDeprecatedPPMProcessor       = sessionProps.IsOptUseDeprecatedPPMProcessor ();

        if (! fOptPrintHelp)
        {
            TLogger.CreateInstance (fOptLogFilePath);
            if (fOptUseDeprecatedPPMProcessor)
            {
                _Setup_UseDeprecatedPPMProcessor ();
            }
            else
            {
                _Setup ();
            }
        }
        else
        {
            helpText = sessionProps.GetHelpText ();
            System.out.println (helpText);
        }
    }

    /**
     * 
     */
    private void _Setup ()
    {
        /* Create modules */
        if (fOptUIType == EFrontendType.kConsoleLinearGauge)
        {
            TController.Create_Frontend_Console_LinearGauge ("gui", fOptConsoleWidth);
        }
        else if (fOptUIType == EFrontendType.kConsoleTextOut)
        {
            TController.Create_Frontend_Console_TextOut ("gui");
        }
        else if (fOptUIType == EFrontendType.kGUILinearGauge)
        {
            TController.Create_Frontend_GUI_LinearGauge ("gui");
        }
        else
        {
            TController.Create_Frontend_GUI_Needle ("gui");
        }
        
        TController.Create_Module_Timer                     ("timer", TSessionProperties.gkDefaultOptTimerIntervalMs            );
        TController.Create_AudioContext                     ("ppm"                                                              );
        TController.Create_Module_Pump                      ("datapump.l"                                                       );
        TController.Create_Module_Pump                      ("datapump.r"                                                       );
        TController.Create_Module_PeakEstimator             ("peakestimator.l"                                                  );
        TController.Create_Module_PeakEstimator             ("peakestimator.r"                                                  );
        TController.Create_Module_ConverterDB               ("converterdb.l"                                                    );
        TController.Create_Module_ConverterDB               ("converterdb.r"                                                    );
        TController.Create_Module_IntegratorPPMBallistics   ("intgrppm.l"                                                       );
        TController.Create_Module_IntegratorPPMBallistics   ("intgrppm.r"                                                       );
        
        /* For each module, create in/out ports */
        TController.Create_Port_Out                         ("ppm",                     "ppm.out.l"                             );
        TController.Create_Port_Out                         ("ppm",                     "ppm.out.r"                             );
        
        TController.Create_Port_In                          ("datapump.l",              "datapump.l.in"                         );
        TController.Create_Port_Out                         ("datapump.l",              "datapump.l.out"                        );
        
        TController.Create_Port_In                          ("datapump.r",              "datapump.r.in"                         );
        TController.Create_Port_Out                         ("datapump.r",              "datapump.r.out"                        );
        
        TController.Create_Port_In                          ("peakestimator.l",         "peakestimator.l.in"                    );
        TController.Create_Port_Out                         ("peakestimator.l",         "peakestimator.l.out"                   );
        
        TController.Create_Port_In                          ("peakestimator.r",         "peakestimator.r.in"                    );
        TController.Create_Port_Out                         ("peakestimator.r",         "peakestimator.r.out"                   );
        
        TController.Create_Port_In                          ("converterdb.l",           "converterdb.l.in"                      );
        TController.Create_Port_Out                         ("converterdb.l",           "converterdb.l.out"                     );
        
        TController.Create_Port_In                          ("converterdb.r",           "converterdb.r.in"                      );
        TController.Create_Port_Out                         ("converterdb.r",           "converterdb.r.out"                     );
        
        TController.Create_Port_In                          ("intgrppm.l",              "intgrppm.l.in"                         );
        TController.Create_Port_Out                         ("intgrppm.l",              "intgrppm.l.out"                        );
        
        TController.Create_Port_In                          ("intgrppm.r",              "intgrppm.r.in"                         );
        TController.Create_Port_Out                         ("intgrppm.r",              "intgrppm.r.out"                        );
        
        TController.Create_Port_In                          ("gui",                     "gui.in.l"                              );
        TController.Create_Port_In                          ("gui",                     "gui.in.r"                              );
        
        /* Connect modules */
        TController.Create_Connection_Data                  ("ppm.out.l",               "datapump.l.in"                         );
        TController.Create_Connection_Data                  ("ppm.out.r",               "datapump.r.in"                         );
        TController.Create_Connection_Data                  ("datapump.l.out",          "peakestimator.l.in"                    );
        TController.Create_Connection_Data                  ("datapump.r.out",          "peakestimator.r.in"                    );
        TController.Create_Connection_Data                  ("peakestimator.l.out",     "converterdb.l.in"                      );
        TController.Create_Connection_Data                  ("peakestimator.r.out",     "converterdb.r.in"                      );
        TController.Create_Connection_Data                  ("converterdb.l.out",       "intgrppm.l.in"                         );
        TController.Create_Connection_Data                  ("converterdb.r.out",       "intgrppm.r.in"                         );
        TController.Create_Connection_Data                  ("intgrppm.l.out",          "gui.in.l"                              );
        TController.Create_Connection_Data                  ("intgrppm.r.out",          "gui.in.r"                              );

        /* Subscribe PPM processor to timer events */
        TController.Create_Connection_Events                ("timer",                   "datapump.l"                            );
        TController.Create_Connection_Events                ("timer",                   "datapump.r"                            );
        TController.Create_Connection_Events                ("timer",                   "intgrppm.l"                            );
        TController.Create_Connection_Events                ("timer",                   "intgrppm.r"                            );
        TController.Create_Connection_Events                ("datapump.l",              "timer"                                 );
        TController.Create_Connection_Events                ("datapump.r",              "timer"                                 );
        
        /* Create start and stop lists */
        TController.Create_StartListEntry                   ("gui");                 
        TController.Create_StartListEntry                   ("timer");                 
        TController.Create_StartListEntry                   ("ppm");
        TController.Create_StopListEntry                    ("timer");                 
        TController.Create_StopListEntry                    ("ppm");
        
        /* Start all modules */
        if (fOptShowDebugWindow)
        {
            if (fOptUIType == EFrontendType.kGUILinearGauge)
            {
                TController.SetDebugUI_On ();
            }
            else if (fOptUIType == EFrontendType.kGUINeedle)
            {
                TController.SetDebugUI_On ();
            }
        }
        TController.Start ();
    }

    @SuppressWarnings ("deprecation")
    private void _Setup_UseDeprecatedPPMProcessor ()
    {
        /* Create modules */
        if (fOptUIType == EFrontendType.kConsoleLinearGauge)
        {
            TController.Create_Frontend_Console_TextOut ("gui");
        }
        else if (fOptUIType == EFrontendType.kConsoleTextOut)
        {
            TController.Create_Frontend_Console_TextOut ("gui");
        }
        else if (fOptUIType == EFrontendType.kGUILinearGauge)
        {
            TController.Create_Frontend_GUI_LinearGauge ("gui");
        }
        else if (fOptUIType == EFrontendType.kGUINeedle)
        {
            TController.Create_Frontend_GUI_Needle ("gui");
        }
        TController.Create_AudioContext             ("ppm"                                                      );
        TController.Create_Module_PPMProcessor      ("ppp.l"                                                    );
        TController.Create_Module_PPMProcessor      ("ppp.r"                                                    );
        TController.Create_Module_Timer             ("timer", TSessionProperties.gkDefaultOptTimerIntervalMs    );
        
        /* For each module, create in/out ports */
        TController.Create_Port_Out                 ("ppm",             "ppm.out.l"                             );
        TController.Create_Port_Out                 ("ppm",             "ppm.out.r"                             );
        
        TController.Create_Port_In                  ("ppp.l",           "ppp.l.in"                              );
        TController.Create_Port_Out                 ("ppp.l",           "ppp.l.out"                             );
        
        TController.Create_Port_In                  ("ppp.r",           "ppp.r.in"                              );
        TController.Create_Port_Out                 ("ppp.r",           "ppp.r.out"                             );
        
        TController.Create_Port_In                  ("gui",             "gui.in.l"                              );
        TController.Create_Port_In                  ("gui",             "gui.in.r"                              );
        
        /* Connect modules */
        TController.Create_Connection_Data          ("ppm.out.l",       "ppp.l.in"                              );
        TController.Create_Connection_Data          ("ppm.out.r",       "ppp.r.in"                              );
        TController.Create_Connection_Data          ("ppp.l.out",       "gui.in.l"                              );
        TController.Create_Connection_Data          ("ppp.r.out",       "gui.in.r"                              );
        
        /* Subscribe PPM processor to timer events */
        TController.Create_Connection_Events        ("timer",           "ppp.l"                                 );
        TController.Create_Connection_Events        ("timer",           "ppp.r"                                 );
        TController.Create_Connection_Events        ("ppp.l",           "timer"                                 );
        TController.Create_Connection_Events        ("ppp.r",           "timer"                                 );
        
        /* Create start and stop lists */
        TController.Create_StartListEntry           ("gui");                 
        TController.Create_StartListEntry           ("timer");                 
        TController.Create_StartListEntry           ("ppm");
        TController.Create_StopListEntry            ("timer");                 
        TController.Create_StopListEntry            ("ppm");
        
        /* Start all modules */
        if (fOptShowDebugWindow)
        {
            if (fOptUIType == EFrontendType.kGUILinearGauge)
            {
                TController.SetDebugUI_On ();
            }
            else if (fOptUIType == EFrontendType.kGUINeedle)
            {
                TController.SetDebugUI_On ();
            }
        }
        TController.Start ();
    }
}
