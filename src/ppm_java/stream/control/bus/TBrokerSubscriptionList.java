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

package ppm_java.stream.control.bus;

import java.util.ArrayList;

import ppm_java._framework.typelib.VBrowseable;

/**
 * @author peter
 *
 */
class TBrokerSubscriptionList
{
    private ArrayList<TBrokerSubscription>  fMembers;
    private VBrowseable                     fSubscribed;
    
    public TBrokerSubscriptionList (VBrowseable subscribed)
    {
        fSubscribed     = subscribed;
        fMembers        = new ArrayList<> ();
    }
    
    public void Add (TBrokerSubscription subscription)
    {
        fMembers.add (subscription);
    }
    
    public TBrokerSubscription Get (int i)
    {
        TBrokerSubscription     ret;
        
        ret = fMembers.get (i);
        
        return ret;
    }
    
    public int GetNumElements ()
    {
        int ret;
        
        ret = fMembers.size ();
        
        return ret;
    }
    
    public VBrowseable GetSubscribed ()
    {
        return fSubscribed;
    }
}
