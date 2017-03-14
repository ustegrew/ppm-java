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

package ppm_java.util.timer;

/**
 * A resettable monostable timer. Runs in conjunction with an endless loop 
 * which calls {@link #OnTimerTick()}.
 * 
 * @author Peter Hoppe
 */
public class TTickTimer
{
    /**
     * The delay time.
     */
    private long            fExpireTime;
    
    /**
     * The absolute time (since 1970-01-01_00:00 UTC) when the timer has been {@linkplain #Start() started}.
     */
    private long            fStartTime;
    
    /**
     * The state of the timer object.
     */
    private ETimerState     fState;
    
    /**
     * cTor.
     * 
     * @param expireTime    Time delay to expire after calling {@link #Start()}.
     */
    public TTickTimer (long expireTime)
    {
        fExpireTime = expireTime;
        fStartTime  = 0;
        fState      = ETimerState.kNull;
    }
    
    /**
     * @return  <code>true</code> if this timer has expired.
     */
    public boolean HasExpired ()
    {
        boolean ret;
        
        ret = (fState == ETimerState.kExpired);
        
        return ret;
    }
    
    /**
     * The polling method. This timer is driven by calling this method in regular intervals.
     */
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
    
    /**
     * Starts the timer.
     */
    public void Start ()
    {
        fStartTime  = System.currentTimeMillis ();
        fState      = ETimerState.kRunning;
    }
    
    /**
     * Resets the timer back to null state, i.e. it's ready for the next {@link #Start()}.
     */
    public void Reset ()
    {
        fStartTime  = 0;
        fState      = ETimerState.kNull;
    }
}
