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

import ppm_java.backend.boot.TCommandlineParser;
import ppm_java.backend.boot.TSessionProperties;
import ppm_java.backend.boot.TSetup;

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
 * @author Peter Hoppe
 */
public class TMain
{
    /**
     * Application entry point.
     * 
     * @param args          Commandline arguments. See user documentation.
     */
    public static void main (String[] args)
    {
        new TMain (args);
    }
    
    /**
     * Properties for the current session.
     */
    private TSessionProperties                  fSessionProps;
    
    /**
     * The setup executor. 
     */
    private TSetup                              fSetupAgent;
    
    /**
     * cTor. 
     * 
     * @param args          Commandline arguments. See user documentation.
     */
    private TMain (String[] args)
    {
        _Init (args);
    }
    
    /**
     * Initializer. Sets up the session according to the commandline args and starts all the modules.
     * 
     * @param args          Commandline arguments. See user documentation.
     */
    private void _Init (String[] args)
    {
        TCommandlineParser          cp;
        boolean                     isValid;
        String                      errMsg;
        
        cp            = new TCommandlineParser ();
        fSessionProps = cp.Parse (args);
        isValid       = fSessionProps.IsValid ();
        if (isValid)
        {
            fSetupAgent = new TSetup ();
            fSetupAgent.ExecuteWith (fSessionProps);
        }
        else
        {
            errMsg = fSessionProps.GetErrorMessage () + "\n" +
                     "-----------------------------------------------------------------\n" +
                     fSessionProps.GetHelpText ();
            throw new IllegalArgumentException (errMsg);
        }
    }
}
