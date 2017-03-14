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

package ppm_java.backend;

import ppm_java.typelib.VAudioDriver;
import ppm_java.typelib.VBrowseable;
import ppm_java.typelib.VFrontend;
import ppm_java.util.logging.TLogger;
import ppm_java.util.storage.arrayMap.TArrayMap;

/**
 * A registry for {@link VBrowseable browseable} Objects. 
 * Every object is registered by unique ID (Registry won't
 * allow duplicate ID's). Clients can query and retrieve 
 * registered objects by their unique ID. 
 * 
 * @author Peter Hoppe
 */
public class TRegistry
{
    /**
     * The audio driver.
     */
    private VAudioDriver            fAudioDriver;
    
    /**
     * Active frontend(s). These are kept in the {@link #fObjectsMap main}
     * storage as well as in this storage. We need this extra bookkeeping 
     * so we can detect when the last frontend has been closed.
     * In practice we'll use one front end only. But we still cater for 
     * the possibility to support multiple frontends.  
     */
    private TArrayMap<VFrontend>    fFrontends;
    
    /**
     * The registry's main storage.
     */
    private TArrayMap<VBrowseable>  fObjectsMap;
    
    /**
     * cTor.
     */
    public TRegistry ()
    {
        fObjectsMap     = new TArrayMap<> ();
        fFrontends      = new TArrayMap<> ();
        fAudioDriver    = null;
    }
    
    /**
     * @return  The audio driver.
     */
    public VAudioDriver GetAudioDriver ()
    {
        return fAudioDriver;
    }
    
    /**
     * @return  The number of active front ends.
     */
    public int GetNumFrontends ()
    {
        int ret;
        
        ret = fFrontends.GetNumElements ();
        
        return ret;
    }
    
    /**
     * @return  The number of {@link VBrowseable browseables} stored.
     */
    public int GetNumObjects ()
    {
        int ret;
        
        ret = fObjectsMap.GetNumElements ();
        
        return ret;
    }
    
    /**
     * Queries the central storage for object <code>id</code>.
     * 
     * @param id    ID of the object searched for. 
     * @return      Queried object.
     */
    public VBrowseable GetObject (String id)
    {
        boolean         hasObject;
        VBrowseable     ret;
        
        hasObject = fObjectsMap.HasElement (id);
        if (hasObject)
        {
            ret = fObjectsMap.Get (id);
        }
        else
        {
            ret = null;
            TLogger.LogWarning ("Failed to get object", this, "GetObject (" + id + ")");
        }
        
        return ret;
    }
    
    /**
     * Registers the audio driver. If another audio driver instance is 
     * already registered we'll log an error instead.
     * 
     * @param d     The driver to be registered.
     */
    public void Register (VAudioDriver d)
    {
        String      id;
        
        if (fAudioDriver == null)
        {   /* No driver registered yet - go ahead. */
            id              = d.GetID ();
            fAudioDriver    = d;
            fObjectsMap.Set (id, d);
            TLogger.LogMessage ("Audio driver registered", this, "Register (" + id + ")");
        }
        else
        {   /* Another audio driver is already registered. */
            TLogger.LogError 
            (
                "Audio driver already assigned to module: '" + fAudioDriver.GetID () + 
                "'. Can't reassign to module '" + d.GetID () + "'", 
                this, 
                "Register (VBrowseable b)"
            );
        }
    }
    
    /**
     * Registers the given browseable.
     * 
     * @param b     Object to be registered.
     */
    public void Register (VBrowseable b)
    {
        String      id;
        
        id      = b.GetID ();
        fObjectsMap.Set (id, b);
        TLogger.LogMessage ("Object registered", this, "Register (" + id + ")");
    }
    
    /**
     * Registers the given frontend.
     * 
     * @param fe    Frontend to be registered.
     */
    public void Register (VFrontend fe)
    {
        String      id;
        
        id = fe.GetID ();
        fObjectsMap.Set (id, fe);
        fFrontends.Set (id, fe);
        TLogger.LogMessage ("Frontend registered", this, "Register (" + id + ")");
    }
    
    /**
     * Unregisters the object with the given ID.
     * 
     * @param id        ID of the object to be unregistered.
     */
    public void Unregister (String id)
    {
        boolean         isFrontend;
        boolean         hasEntry;
        
        hasEntry    = fObjectsMap.HasElement (id);
        isFrontend  = fFrontends.HasElement  (id);
        if (hasEntry)
        {
            fObjectsMap.Remove (id);
            if (isFrontend)
            {
                fFrontends.Remove (id);
            }

            if (isFrontend)
            {
                TLogger.LogMessage ("Frontend removed", this, "Unregister (" + id + ")");
            }
            else
            {
                TLogger.LogMessage ("Object removed", this, "Unregister (" + id + ")");
            }
        }
        else
        {
            TLogger.LogWarning ("Failed (Unknown id)", this, "Unregister (" + id + ")");
        }
    }
}
