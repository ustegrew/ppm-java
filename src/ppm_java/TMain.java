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

package ppm_java;

import ppm_java.backend.server.TController;

/**
 * Main class. Sets up the streaming apparatus. We use a very modular design
 * connecting various modules.
 * Since this is a concept test we have the freedom to try various designs and 
 * study the behaviour.
 * 
 * <dl>
 *     <dt>Lightweight modules</dt> 
 *     <dd>
 *         A group of modules, each havin g a very limited functionality, connected
 *         to form a complex apparatus. This is the current design.
 *     </dd>
 *     <dt>Monolithic module</dt> 
 *     <dd>
 *         The entire PPM processor is one monolithic block performing the whole
 *         data processing from raw audio samples to GUI ready peak value stream.
 *         Now deprecated, but kept for education purposes. 
 *     </dd>
 * </dl>
 * 
 * The lighweight module design has the advantage of high flexibility as it's fairly 
 * inexpensive to create new kinds of modules (e.g. a history view showing the last minute
 * of metering data). It comes at a price of multiple data copying between some modules and 
 * some boilerplate code and modules with trivial functionality (e.g. the peak estimator). 
 * However, the flexibility trumps, as it makes the design much more scalable. 
 * 
 * @author peter
 */
public class TMain
{
    public static enum EFrontendType
    {
        kConsoleLinearGauge,
        kConsoleTextOut,
        kGUINeedle,
        kGUILinearGauge
    }
    
    public static final EFrontendType       gkFrontendType                  = EFrontendType.kConsoleLinearGauge;
    public static final boolean             gkDoShowDebugUI                 = false;
    public static final boolean             gkUseDeprecatedPPMProcessor     = false;
    public static final int                 gkAudioFrameSize                =  1024;
    public static final int                 gkAudioSampleRate               = 44100;
    public static final int                 gkTimerIntervalMs               =    20; 
    
    /**
     * @param args
     */
    public static void main (String[] args)
    {
        if (gkUseDeprecatedPPMProcessor)
        {
            _Setup_Deprecated (gkFrontendType);
        }
        else
        {
            _Setup (gkFrontendType);
        }
    }
    
    /**
     * TODO: In debugging state - not working yet. 
     */
    private static void _Setup (EFrontendType feType)
    {
        /* Create modules */
        TController.Create_Module_Timer                     ("timer",           gkTimerIntervalMs                       );
        TController.Create_AudioContext                     ("ppm",             gkAudioSampleRate, gkAudioFrameSize     );
        TController.Create_Module_Pump                      ("datapump.l"                                               );
        TController.Create_Module_Pump                      ("datapump.r"                                               );
        TController.Create_Module_PeakEstimator             ("peakestimator.l"                                          );
        TController.Create_Module_PeakEstimator             ("peakestimator.r"                                          );
        TController.Create_Module_ConverterDB               ("converterdb.l"                                            );
        TController.Create_Module_ConverterDB               ("converterdb.r"                                            );
        TController.Create_Module_IntegratorPPMBallistics   ("intgrppm.l"                                               );
        TController.Create_Module_IntegratorPPMBallistics   ("intgrppm.r"                                               );
        if (feType == EFrontendType.kConsoleLinearGauge)
        {
            TController.Create_Frontend_Console_LinearGauge ("gui", 80);
        }
        else if (feType == EFrontendType.kConsoleTextOut)
        {
            TController.Create_Frontend_Console_TextOut ("gui");
        }
        else if (feType == EFrontendType.kGUILinearGauge)
        {
            TController.Create_Frontend_GUI_LinearGauge ("gui");
        }
        else if (feType == EFrontendType.kGUINeedle)
        {
            TController.Create_Frontend_GUI_Needle ("gui");
        }
        
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
        if (gkDoShowDebugUI)
        {
            TController.SetDebugUI_On ();
        }
        TController.Start ();
    }
    
    private static void _Setup_Deprecated (EFrontendType feType)
    {
        /* Create modules */
        TController.Create_AudioContext             ("ppm",             gkAudioSampleRate, gkAudioFrameSize     );
        TController.Create_Module_PPMProcessor      ("ppp.l"                                                    );
        TController.Create_Module_PPMProcessor      ("ppp.r"                                                    );
        TController.Create_Module_Timer             ("timer",           gkTimerIntervalMs                       );
        if (feType == EFrontendType.kConsoleLinearGauge)
        {
            TController.Create_Frontend_Console_TextOut ("gui");
        }
        else if (feType == EFrontendType.kConsoleTextOut)
        {
            TController.Create_Frontend_Console_TextOut ("gui");
        }
        else if (feType == EFrontendType.kGUILinearGauge)
        {
            TController.Create_Frontend_GUI_LinearGauge ("gui");
        }
        else if (feType == EFrontendType.kGUINeedle)
        {
            TController.Create_Frontend_GUI_Needle ("gui");
        }
        
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
        if (gkDoShowDebugUI)
        {
            TController.SetDebugUI_On ();
        }
        TController.Start ();
    }
}
