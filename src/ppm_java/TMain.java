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

import ppm_java._aux.storage.TAtomicBuffer.ECopyPolicy;
import ppm_java.backend.server.TController;

/**
 * @author peter
 *
 */
public class TMain
{
    public static final int         kNChanGUIMax = 2; 
    
    /**
     * @param args
     */
    public static void main (String[] args)
    {
        _Setup ();
    }
    
    private static void _Setup ()
    {
        TController.Create_AudioContext         ("ppm", 44100, 1024);
        
        TController.Create_Node_BufferedPipe    ("pDecoupler_l", ECopyPolicy.kCopyOnGet);
        TController.Create_Node_BufferedPipe    ("pDecoupler_r", ECopyPolicy.kCopyOnGet);
        
        TController.Create_Frontend_GUI         ("gui", kNChanGUIMax);
        TController.Create_Node_PeakDetector    ("pk_l");
        TController.Create_Node_PeakDetector    ("pk_r");
        TController.Create_Node_Timer           ("tmrFontendClock", 20);
        
        TController.Create_Port_Out             ("ppm",         "ppm.in_l");
        TController.Create_Port_Out             ("ppm",         "ppm.in_r");
        TController.Create_Port_In              ("gui",         "gui.in_l");
        TController.Create_Port_In              ("gui",         "gui.in_r");
        TController.Create_Port_In              ("pk_l",        "pk_l.in");
        TController.Create_Port_Out             ("pk_l",        "pk_l.out");
        TController.Create_Port_In              ("pk_r",        "pk_r.in");
        TController.Create_Port_Out             ("pk_r",        "pk_r.out");
        
        TController.Connect                     ("ppm.in_l",    "pk_l.in");
        TController.Connect                     ("ppm.in_r",    "pk_r.in");
        TController.Connect                     ("pk_l.out",    "gui.in_l");
        TController.Connect                     ("pk_r.out",    "gui.in_r");
        
        TController.SubscribeToEvents           ("tmrFrontendClock", "pDecoupler_l");
        TController.SubscribeToEvents           ("tmrFrontendClock", "pDecoupler_r");
        
        TController.Start                       ("ppm");
        TController.Start                       ("gui");
        TController.Start                       ("tmrFontendClock");
    }
}
