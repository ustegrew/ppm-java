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

/**
 * @author peter
 *
 */
public class TStatsOfWorker implements IStats
{
    private long        fCounter;
    private double      fLastRandom;
    private double      fAverage;      
    
    public TStatsOfWorker ()
    {
        fCounter            = 0;
        fLastRandom         = 0.0;
        fAverage            = 0.0;
    }
    
    public void SetRandom (double rNum)
    {
        fCounter++;
        fLastRandom += rNum;
        fAverage     = fLastRandom / fCounter;
    }
    
    public double GetAverage ()
    {
        return fAverage;
    }

    public String GetDumpStr ()
    {
        return "Worker stats: nValues: " + fCounter + "; Average: " + fAverage;
    }
}
