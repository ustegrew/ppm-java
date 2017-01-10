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

package ppm_java._dev.concept.trial.console;

/**
 * @author peter
 *
 */
public class TDev_Trial_Console_MoveCursorUp_01
{
    public static void main (String[] args)
    {
        int i;
        
        // Print 5 lines
        for (i = 1; i <= 5; i++)
        {
            System.out.println ("--------------------------------------------");
        }
        
        // Go up 3 lines
        for (i = 1; i <= 3; i++)
        {
            System.out.print ("\u001B[1A");
        }
        
        // Insert text - will it appear above last line?
        System.out.println ("inserted line");
    }
}
