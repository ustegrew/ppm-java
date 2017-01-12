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
import ppm_java.util.storage.TArrayMap;

/**
 * @author peter
 *
 */
public class TRegistry
{
    private VBrowseable             fAudioDriver;
    private TArrayMap<VFrontend>    fFrontends;
    private TArrayMap<VBrowseable>  fObjectsMap;
    
    public TRegistry ()
    {
        fObjectsMap     = new TArrayMap<> ();
        fFrontends      = new TArrayMap<> ();
        fAudioDriver    = null;
    }
    
    public VBrowseable GetAudioDriver ()
    {
        return fAudioDriver;
    }
    
    public int GetNumFrontends ()
    {
        int ret;
        
        ret = fFrontends.GetNumElements ();
        
        return ret;
    }
    
    public int GetNumObjects ()
    {
        int ret;
        
        ret = fObjectsMap.GetNumElements ();
        
        return ret;
    }
    
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
    
    public void Register (VAudioDriver d)
    {
        String      id;
        
        id = d.GetID ();
        fObjectsMap.Set (id, d);
        if (fAudioDriver == null)
        {
            fAudioDriver = d;
            TLogger.LogMessage ("Audio driver registered", this, "Register (" + id + ")");
        }
        else
        {
            TLogger.LogError 
            (
                "Audio driver already assigned to module: '" + fAudioDriver.GetID () + 
                "'. Can't reassign to module '" + d.GetID () + "'", 
                this, 
                "Register (VBrowseable b)"
            );
        }
    }
    
    public void Register (VBrowseable b)
    {
        String      id;
        
        id      = b.GetID ();
        fObjectsMap.Set (id, b);
        TLogger.LogMessage ("Object registered", this, "Register (" + id + ")");
    }
    
    public void Register (VFrontend fe)
    {
        String      id;
        
        id = fe.GetID ();
        fObjectsMap.Set (id, fe);
        fFrontends.Set (id, fe);
        TLogger.LogMessage ("Frontend registered", this, "Register (" + id + ")");
    }
    
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
