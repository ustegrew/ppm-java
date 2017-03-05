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
 * Base class for all browseable objects.
 * 
 * Will automatically register with the global registry 
 * and can thus be accessed with an opaque ID. The ID must
 * be unique; the registry will reject any double entries.
 * 
 * Every module, endpoint and connection in ppm-java is registered 
 * with the global registry and can be retrieved using its unique ID.
 * This helps especially during session setup, as it enables us 
 * to use a simple and clean API to create and connect modules 
 * during the session setup.
 * 
 * The disadvantage is that browseables must be retrieved by their  
 * unique ID from the registry when they need to be accessed. This 
 * introduces a weak type system as the IDs carry no information 
 * about the type of the object retrieved. Also, it introduces 
 * the extra overhead of having to query the registry when retrieving
 * a browseable. We can overcome these disadvantages by caching objects 
 * where they are used (e.g. private class fields). For example, 
 * endpoints keep their hosting module object as private field 
 * <code>fHost</code>. 
 * 
 * The advantage of a simple syntax for session setup outweighs the 
 * disadvantage as it makes session setup much easier for the client.  
 * 
 * @author Peter Hoppe
 */
public abstract class VBrowseable
{
    /**
     * The unique ID under which this object can be found in the registry.
     */
    private String                      fID;
    
    /**
     * cTor
     * 
     * @param id        The unique ID under which we register this object 
     *                  with the registry.  
     */
    public VBrowseable (String id)
    {
        fID         = id;
        _Register ();
    }
    
    /**
     * @return The unique ID associated with this browseable.
     */
    public String GetID ()
    {
        return fID;
    }
    
    /**
     * Registers this object with the global registry.
     * We leave this as a virtual method, because inheriting 
     * classes might have unique requirements.
     */
    protected abstract void _Register ();
}
