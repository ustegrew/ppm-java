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
 * @author peter
 *
 */
public class TMain
{
    public static final int         gkAudioFrameSize    =  1024;
    public static final int         gkAudioSampleRate   = 44100;
    public static final int         gkGUINChanMax       =     2;
    public static final int         gkTimerIntervalMs   =    20; 
    
    /**
     * @param args
     */
    public static void main (String[] args)
    {
        _Setup ();
    }
    
    private static void _Setup ()
    {
        /* Create modules */
        TController.Create_AudioContext             ("ppm",             gkAudioSampleRate, gkAudioFrameSize     );
        TController.Create_Frontend_GUI             ("gui",             gkGUINChanMax                           );
        TController.Create_Module_PPMProcessor      ("ppp.l"                                                    );
        TController.Create_Module_PPMProcessor      ("ppp.r"                                                    );
        TController.Create_Module_Timer             ("timer",           gkTimerIntervalMs                       );
        
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
        TController.Start ();
    }
}
