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

package ppm_java.typelib;

/**
 * @author peter
 *
 */
public abstract class VAudioPort extends VAudioObject
{
    public static enum EConnectibleType
    {
        kConn_Chunks,
        kConn_Samples
    }

    private VAudioProcessor         fHost;
    
    public VAudioPort (String id, VAudioProcessor host)
    {
        super (id);
        fHost = host;
    }
    
    protected VAudioProcessor _GetHost ()
    {
        return fHost;
    }
}