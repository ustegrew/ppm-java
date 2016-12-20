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

package ppm_java._dev.concept.trial.FloatBuffer.readWrite;

import java.nio.FloatBuffer;

/**
 * @author peter
 *
 */
public class TDev_Trial_ReadWrite_FloatBuffer_01
{
    public static void main (String[] args)
    {
        _Test_01 ();
    }
    
    /**
     * 
     */
    private static void _Test_01 ()
    {
        final int               kNRuns = 10;
        int                     i;
        FloatBuffer             fb;
        
        fb = FloatBuffer.allocate (12);
        
        _DumpDetails (fb);
        for (i = 0; i < kNRuns; i++)
        {
            fb.put (i);
            _DumpDetails (fb);
        }
        
        System.out.println ("============================================================================================");
        System.out.println ("fb->flip()");
        fb.flip ();
        try
        {
            _DumpDetails (fb);
        }
        catch (Exception e)
        {
            System.out.println (e.getMessage ());
            e.printStackTrace ();
        }
        System.out.println ("============================================================================================");
        System.out.println ("fb->flip()");
        fb.flip ();
        try
        {
            _DumpDetails (fb);
        }
        catch (Exception e)
        {
            System.out.println (e.getMessage ());
            e.printStackTrace ();
        }
    }
    
    private static void _DumpDetails (FloatBuffer fb)
    {
        int     i;
        int     n;
        float   x;
        
        System.out.println ("--------------------------------------------------------------------------------------------");
        System.out.println ("position: " + fb.position () + ", limit: " + fb.limit () + ", capacity: " + fb.capacity ());
        n = fb.capacity ();
        for (i = 0; i < n; i++)
        {
            x = fb.get (i);
            System.out.println (i + " -> " + x);
        }
    }
}
