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

package ppm_java.backend.jackd;

import ppm_java._aux.storage.TAtomicBuffer_Stats;
import ppm_java._aux.typelib.IStats;

public final class TAudioContext_JackD_Stats implements IStats
{
    private TAudioContext_JackD                     fHost;
    
    public TAudioContext_JackD_Stats (TAudioContext_JackD host)
    {
        fHost = host;
    }
    
    public String GetDumpStr ()
    {
        int                             i;
        int                             n;
        String                          id;
        TAudioContext_Endpoint_Input    ip;
        TAudioContext_Endpoint_Output   op;
        TAtomicBuffer_Stats            s;
        String                          ret;
        
        /* First dump line */
        id      = fHost.GetID ();
        ret     = "JackD_driver ["      + id + 
                  "]: sampleRate="      + fHost.GetSampleRate () +
                  ", isWorking="        + fHost.IsWorking () +
                  "\n";

        /* Add one line of statistics per input port. */
        n = fHost.GetNumPortsIn ();
        if (n >= 1)
        {
            ret += "    Inputs:\n";
            for (i = 0; i < n; i++)
            {
                ip          = (TAudioContext_Endpoint_Input) fHost.GetPortIn (i);
                s           = ip.StatsGet ();
                id          = ip.GetID ();
                ret        += "        i/p [" + id              +
                              "]: "           + s.GetDumpStr () + 
                              "\n";
            }
        }
        else
        {
            ret += "    Inputs: None\n";
        }
        
        /* Add one line of statistics per output port. */
        n = fHost.GetNumPortsOut ();
        if (n >= 1)
        {
            ret += "    Outputs:\n";
            for (i = 0; i < n; i++)
            {
                op          = (TAudioContext_Endpoint_Output) fHost.GetPortOut (i);
                s           = op.TargetStatsGet ();
                id          = op.GetID ();
                ret        += "        o/p [" + id              +
                              "]: "           + s.GetDumpStr () + 
                              "\n";
            }
        }
        else
        {
            ret += "    Outputs: None\n";
        }
        
        return ret;
    }
}