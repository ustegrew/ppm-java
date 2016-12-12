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

package ppm_java.stream.node.scaler;

import java.nio.FloatBuffer;

import ppm_java._aux.logging.TLogger;
import ppm_java._framework.TRegistry;
import ppm_java._framework.typelib.IEvented;
import ppm_java._framework.typelib.VAudioProcessor;

/**
 * A sample chunk (re)scaler. Receives packets and repackages 
 * the samples into packets of another size. Enables to decouple 
 * parts of the program that run with different cycle times
 * (e.g. system audio thread supplying a stream of samples
 * to a GUI thread).
 * 
 * @author Peter
 */
public class TNodeScaler 
    extends         VAudioProcessor
    implements      IEvented
{
    private static final int                    gkPacketSizeMin     = 64;       /* [100] */
    private static final int                    gkPacketSizeMax     = 16384;    /* [100] */
    
    private TNodeScaler_Endpoint_In             fInput;
    private TNodeScaler_Endpoint_Out            fOutput;
    private int                                 fNumSamplesPerPacket_In;
    private int                                 fNumSamplesPerPacket_Out;
    
    /**
     * @param id        Unique ID as which this node is registered 
     *                  with the global {@link TRegistry}.
     */
    public TNodeScaler (String id)
    {
        super (id, 1, 1);
        fInput                      = null;
        fOutput                     = null;
        fNumSamplesPerPacket_In     = 0;
        fNumSamplesPerPacket_Out    = 0;
    }

    /* (non-Javadoc)
     * @see ppm_java._framework.typelib.VAudioProcessor#CreatePort_In(java.lang.String)
     */
    @Override
    public void CreatePort_In (String id)
    {
        fInput = new TNodeScaler_Endpoint_In (id, this, 0);
        AddPortIn (fInput);
    }

    /* (non-Javadoc)
     * @see ppm_java._framework.typelib.VAudioProcessor#CreatePort_Out(java.lang.String)
     */
    @Override
    public void CreatePort_Out (String id)
    {
        fOutput = new TNodeScaler_Endpoint_Out (id, this);
        AddPortOut (fOutput);
    }
    
    /* (non-Javadoc)
     * @see ppm_java._framework.typelib.IEvented#OnEvent(int)
     */
    @Override
    public void OnEvent (int e)
    {
        /* Timer at the output side requested next packet. */
        if (e == gkEventTimer)
        {
            
        }
    }
    
    /* (non-Javadoc)
     * @see ppm_java._framework.typelib.IEvented#OnEvent(int, int)
     */
    @Override
    public void OnEvent (int e, int arg0)
    {
        int         nSamplesPerPacket;
        
        /* Adjust ouput packet size to given packet size. */
        if (e == gkEventScalerAdjustSizeOut)
        {
            nSamplesPerPacket = arg0;
            _SetPacketSize (nSamplesPerPacket);
        }
    }

    /* (non-Javadoc)
     * @see ppm_java._framework.typelib.IEvented#OnEvent(int, java.lang.String)
     */
    @Override
    public void OnEvent (int e, String arg0)
    {
        // Do nothing
    }

    /**
     * Receives one packet and repackages it into one or more buffer(s)
     * of a different size.
     * 
     * @param samples       
     */
    void Receive (FloatBuffer samples)
    {
        
    }

    private void _Repackage (FloatBuffer samples)
    {
        fNumSamplesPerPacket_In = samples.capacity ();
        
    }
    
    /**
     * Sets a new size for produced packets.
     * 
     * @param s     Requested number of samples per packets. 
     */
    private void _SetPacketSize (int s)
    {
        if (s < gkPacketSizeMin)
        {
            fNumSamplesPerPacket_Out = gkPacketSizeMin;
            
            TLogger.LogWarning 
            (
                "TNodeScaler[" + GetID () + 
                "]: (gkEventScalerAdjustSize): Requested " + 
                "packet size too small(" + s + "). Setting " + 
                "to: " + gkPacketSizeMin
            );
        }
        else if (s > gkPacketSizeMax)
        {
            fNumSamplesPerPacket_Out = gkPacketSizeMax;

            TLogger.LogWarning 
            (
                "TNodeScaler[" + GetID () + 
                "]: (gkEventScalerAdjustSize): Requested " + 
                "packet size too large(" + s + "). Setting " + 
                "to: " + gkPacketSizeMax
            );
        }
        else
        {
            fNumSamplesPerPacket_Out = s;
        }
    }
}

/*
[100]   Set arbitrarily; it seems silly to reduce packets any further than this. 
*/