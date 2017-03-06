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
 * Base class for all audio ports.
 * 
 * An audio port is always associated with a hosting {@link VAudioProcessor}. 
 * 
 * @author Peter Hoppe
 */
public abstract class VAudioPort extends VAudioObject
{
    /**
     * The audio processor hosting this port.
     */
    private VAudioProcessor         fHost;
    
    /**
     * cTor.
     * 
     * @param id            Unique ID as which we register this port.
     * @param host          The audio processor hosting this port.
     */
    public VAudioPort (String id, VAudioProcessor host)
    {
        super (id);
        
        fHost = host;
    }
    
    /**
     * @return  The audio processor hosting this port.
     */
    protected VAudioProcessor _GetHost ()
    {
        return fHost;
    }

    /**
     * Returns textual information of a concrete child's direct super type. 
     * To accomodate a special case where clients connect incompatible endpoints;
     * We then throw an exception which reports the incompatibility with 
     * details about the conflicting types.
     * 
     * If this application remains a Java application we could use
     * Java introspection. However, it's going to be ported to C++, and there
     * we'd have to resort to ominous tricks to get the name of the supertype
     * of a class. It's much more straight forward to provide a short method 
     * that returns a string. It's just one of them things saying "ouch"...
     * 
     * @return Type information as text  
     */
    protected abstract String _GetType ();
}
