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
package ppm_java.frontend.gui;

import ppm_java._framework.typelib.IControllable;
import ppm_java._framework.typelib.VFrontend;
import ppm_java.backend.server.TController;

/**
 *
 * @author peter
 */
public class TGUISurrogate 
    extends     VFrontend 
    implements  IControllable
{
    public static enum EClipType
    {
        kClear,
        kError,
        kWarn
    }
    
    private static TGUISurrogate        gGUI        = null;
    private static final float          kLvlClip    = 0.95f;
    private static final float          kLvlWarn    = 0.7f;
    
    public static void CreateInstance (String id, int nMaxChanIn)
    {
        if (gGUI != null)
        {
            throw new IllegalStateException ("TGUISurrogate is already instantiated.");
        }
        gGUI = new TGUISurrogate (id, nMaxChanIn);
    }
    
    private TWndPPM             fGUI;
    
    private TGUISurrogate (String id, int nMaxChanIn)
    {
        super (id, nMaxChanIn, 0);
        fGUI = new TWndPPM (this   );
    }
    
    /* (non-Javadoc)
     * @see ppm_java._framework.typelib.VAudioProcessor#CreatePortIn(java.lang.String)
     */
    @Override
    public void CreatePort_In (String id)
    {
        int                 iPort;
        TGUI_Endpoint       port;
        
        iPort   = GetNumPortsIn ();
        port    = new TGUI_Endpoint (id, this, iPort);
        AddPortIn (port);
    }
    
    /* (non-Javadoc)
     * @see ppm_java._framework.typelib.VAudioProcessor#CreatePortOut(java.lang.String)
     */
    @Override
    public void CreatePort_Out (String id)
    {
        throw new IllegalStateException ("This is a front end class - it doesn't use output ports.");
    }
    
    /**
     * @param data
     */
    public void SetLevel (float level, int iChannel)
    {
        int lDisp;
        
        if (level >= kLvlClip)
        {
            fGUI.ClippingSet (EClipType.kError, iChannel);
        }
        else if (level >= kLvlWarn)
        {
            fGUI.ClippingSet (EClipType.kWarn, iChannel);
        }
        
        if (level < 0)
        {
            lDisp = 0;
        }
        else if (level > 1)
        {
            lDisp = 100;
        }
        else
        {
            lDisp = (int) (100.0f * level); /* TODO: make level logarithmic, in dB */
        }
        
        fGUI.SetLevel (lDisp, iChannel);
    }

    /* (non-Javadoc)
     * @see ppm_java._framework.typelib.IControllable#Start()
     */
    @Override
    public void Start ()
    {
        fGUI.setVisible (true);
        fGUI.setLocationRelativeTo (null);
    }
    
    /* (non-Javadoc)
     * @see ppm_java._framework.typelib.IControllable#Stop()
     */
    @Override
    public void Stop ()
    {
        // Do nothing
    }

    void OnSigClip_Click ()
    {
        fGUI.ClippingSet (EClipType.kClear, -1);
    }

    void OnTerminate ()
    {
        TController.OnTerminate (GetID ());
    }
}
