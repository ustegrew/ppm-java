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

package ppm_java.backend.server.module.integrator;

import ppm_java._aux.typelib.IEvented;
import ppm_java._aux.typelib.IStatEnabled;
import ppm_java._aux.typelib.IStats;
import ppm_java._aux.typelib.VAudioProcessor;
import ppm_java.backend.server.TController;

/**
 * @author peter
 *
 */
public class TNodeIntegrator_PPMBallistics 
    extends     VAudioProcessor 
    implements  IStatEnabled, IEvented
{
    public static void CreateInstance (String id)
    {
        new TNodeIntegrator_PPMBallistics (id);
    }
    
    private static final double     gkIntegrFallRangedB =  -24;         /* [110] */
    private static final double     gkIntegrFallTime    = 2800;         /* [110] */
    private static final double     gkIntegrRiseRangedB =   23;         /* [110] */
    private static final double     gkIntegrRiseTime    =   10;         /* [110] */

    private TNodeIntegrator_PPMBallistics_Stats     fStats;
    private boolean                                 fHasNotBeenInitialized;
    private long                                    fTLast;
    private double                                  fVDB;
    private double                                  fValueDeltaRise;
    private double                                  fValueDeltaFall;
    
    /**
     * @param id
     * @param nMaxChanIn
     * @param nMaxChanOut
     */
    private TNodeIntegrator_PPMBallistics (String id)
    {
        super (id, 1, 1);
        fStats                  = new TNodeIntegrator_PPMBallistics_Stats (this);
        fHasNotBeenInitialized  = true;
        fTLast                  = 0;
        fVDB                    = 0;
        fValueDeltaRise         = gkIntegrRiseRangedB / gkIntegrRiseTime;       /* [110] */
        fValueDeltaFall         = gkIntegrFallRangedB / gkIntegrFallTime;       /* [110] */
        
        TController.StatAddProvider (this);
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VAudioProcessor#CreatePort_In(java.lang.String)
     */
    @Override
    public void CreatePort_In (String id)
    {
        TNodeIntegrator_PPMBallistics_Endpoint_In       p;
        
        p = new TNodeIntegrator_PPMBallistics_Endpoint_In (id, this, 0);
        AddPortIn (p);
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VAudioProcessor#CreatePort_Out(java.lang.String)
     */
    @Override
    public void CreatePort_Out (String id)
    {
        TNodeIntegrator_PPMBallistics_Endpoint_Out      p;

        p = new TNodeIntegrator_PPMBallistics_Endpoint_Out (id, this);
        AddPortOut (p);
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IEvented#OnEvent(int)
     */
    @Override
    public void OnEvent (int e)
    {
        if (e == gkEventTimerTick)
        {
            if (fHasNotBeenInitialized)
            {
                fTLast                  = System.currentTimeMillis ();
                fHasNotBeenInitialized  = false;
            }
        }
    }
    
    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IEvented#OnEvent(int, int)
     */
    @Override
    public void OnEvent (int e, int arg0)
    {
        // Do nothing
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IEvented#OnEvent(int, long)
     */
    @Override
    public void OnEvent (int e, long arg0)
    {
        // Do nothing
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IEvented#OnEvent(int, java.lang.String)
     */
    @Override
    public void OnEvent (int e, String arg0)
    {
        // Do nothing
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.IStatEnabled#StatsGet()
     */
    @Override
    public IStats StatsGet ()
    {
        return fStats;
    }

    /* (non-Javadoc)
     * @see ppm_java._aux.typelib.VBrowseable#_Register()
     */
    @Override
    protected void _Register ()
    {
        TController.Register (this);
    }

    void SetRef (float dBValue)
    {
        double                                      t0;
        double                                      dY;
        double                                      dT;
        float                                       y1;
        TNodeIntegrator_PPMBallistics_Endpoint_Out  out;
        
        t0 = System.currentTimeMillis ();
        dY = dBValue - fVDB;
        dT = t0 - fTLast;
        
        if (dY > 0)
        {   /* Value has risen. Apply rise ballistics. */
            fVDB = fVDB + fValueDeltaRise * dT;
            if (fVDB > dBValue)
            {   /* Brickwall limiter */
                fVDB = dBValue;
            }
        }
        else if (dY < 0)
        {   /* Value has fallen. Apply fall ballistics. */
            fVDB = fVDB - fValueDeltaFall * dT;
            if (fVDB < dBValue)
            {   /* Brickwall limiter */
                fVDB = dBValue;
            }
        }
        /* No else clause [130] */

        fStats.SetTimeCycle         (dT);
        fStats.SetValueDBActual     (fVDB);
        fStats.SetValueDBRef        (dBValue);
        
        y1      = (float) fVDB;
        out     = (TNodeIntegrator_PPMBallistics_Endpoint_Out) GetPortOut (0);
        out.PushSample (y1);
    }
}


/*
[110]   PPM type II ballistics (strict):
        Rise: -24dB -> - 1dB:   10  ms  =  23dB /   10mS =  2.3      dB/mS 
        Fall:   0dB -> -24dB: 2800  ms  = -24dB / 2800ms ~ -0.00857  dB/ms
        
[120]   Whilst developing the step integrator I learned that I must avoid 
        mixing variable classes in calculations. Early versions used 
        calculations such as this one:
            fVDB = fVDB + dY * fValueDeltaRise
        Where I mixed up the corrector internal variable (dY) with the output
        variable (fVDB). It was a bad potshot to have something, but resulted in
        erratic behaviour on the attached meter. By keeping dY out of the output 
        value calculation I have a smooth meter movement which follows the 
        PPM ballistics as I observed in the studio. It may be worthwhile to 
        find a better theoretical underpinning of the PPM ballistics by finding 
        the differential equations that drives the meter up rise and fall movement 
        and then to redesign this class as a one-step-per-cycle solver of the rise 
        and fall equations (e.g. Runge-Kutta method). It sound yummily interesting, 
        but for later.
          
[130]   Else clause - do nothing. If value doesn't change, just re-use what's there.
*/
