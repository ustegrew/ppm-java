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

package ppm_java._dev.concept.example.stats;

import ppm_java.typelib.IStats;

public class TDev_Example_Stats
{
    public static void main (String[] args)
    {
        TWorker         worker;
        int             i;
        IStats          stats;
        String          dump;
        
        worker = new TWorker ();
        for (i = 1; i <= 20; i++)
        {
            worker.DoSomething ();
            
            stats = worker.StatsGet ();
            dump  = stats.GetDumpStr ();
            System.out.println (dump);
        }
    }
}
