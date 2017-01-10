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

package ppm_java.backend.server;

import java.io.OutputStream;
import java.io.PrintStream;

import ppm_java._aux.logging.TLogger;

/**
 * @author peter
 *
 */
public class TStdStreamInterceptor extends PrintStream
{
    private boolean                     fIsErrStream;
    private PrintStream                 fWrappedStream;
    
    /**
     * @param out
     */
    public TStdStreamInterceptor (OutputStream out, boolean isErrStream)
    {
        super (out, true);
        fWrappedStream  = (PrintStream) out;
        fIsErrStream    = isErrStream;
    }
    
    public void println (String s)
    {
        if (fIsErrStream)
        {
            TLogger.LogWarning (s);
        }
        else
        {
            TLogger.LogMessage (s);
        }
    }
    
    public void print (String s)
    {
        if (fIsErrStream)
        {
            TLogger.LogWarning (s);
        }
        else
        {
            TLogger.LogMessage (s);
        }
    }
}
