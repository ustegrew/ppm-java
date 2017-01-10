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

package ppm_java._aux.timer;

/**
 * @author peter
 *
 */
public class TTickTimer
{
    private long            fExpireTime;
    private long            fStartTime;
    private ETimerState     fState;
    
    /**
     * 
     */
    public TTickTimer (long expireTime)
    {
        fExpireTime = expireTime;
        fStartTime  = 0;
        fState      = ETimerState.kNull;
    }
    
    public boolean HasExpired ()
    {
        boolean ret;
        
        ret = (fState == ETimerState.kExpired);
        
        return ret;
    }
    
    public void OnTimerTick ()
    {
        long        dT;
        long        t0;
        
        if (fState == ETimerState.kRunning)
        {
            t0  = System.currentTimeMillis ();
            dT  = t0 - fStartTime;
            if (dT > fExpireTime)
            {
                fState = ETimerState.kExpired;
            }
        }
    }
    
    public void Start ()
    {
        fStartTime  = System.currentTimeMillis ();
        fState      = ETimerState.kRunning;
    }
    
    public void Reset ()
    {
        fStartTime  = 0;
        fState      = ETimerState.kNull;
    }
}
