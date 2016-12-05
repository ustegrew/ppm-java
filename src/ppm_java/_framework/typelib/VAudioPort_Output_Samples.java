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

package ppm_java._framework.typelib;

/**
 * @author peter
 *
 */
public abstract class VAudioPort_Output_Samples extends VAudioPort_Output
{
    public static final EConnectibleType        kConnType = EConnectibleType.kConn_Samples;

    public VAudioPort_Output_Samples (String id, VAudioProcessor host)
    {
        super (id, host);
    }

    public void PushSample (float sample)
    {
        VAudioPort_Input_Samples target;
        
        target = (VAudioPort_Input_Samples) _GetTarget ();
        target.ReceiveSample (sample);
    }
    
    public void SetTarget (VAudioPort_Input_Samples target)
    {
        super.SetTarget (target);
    }
}
