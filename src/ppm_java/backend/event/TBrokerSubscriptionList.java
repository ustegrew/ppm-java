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

package ppm_java.backend.event;

import java.util.ArrayList;

import ppm_java.typelib.VBrowseable;

/**
 * List of subscribers listening for the events posted by a {@link VBrowseable} (subscribed).
 * 
 * @author Peter Hoppe
 */
class TBrokerSubscriptionList
{
    /**
     * List holding the subscriptions.
     */
    private ArrayList<TBrokerSubscription>  fMembers;
    
    /**
     * The object posting the events.
     */
    private VBrowseable                     fSubscribed;
    
    /**
     * cTor. Sets the object everyone is listening to.
     * 
     * @param subscribed    The object posting the events.
     */
    public TBrokerSubscriptionList (VBrowseable subscribed)
    {
        fSubscribed     = subscribed;
        fMembers        = new ArrayList<> ();
    }
    
    /**
     * Adds a new subscription.
     * 
     * @param subscription
     */
    public void Add (TBrokerSubscription subscription)
    {
        fMembers.add (subscription);
    }
    
    /**
     * Returns the i<sup>th</sup> subscription.
     * 
     * @param   i   Zero based index of the requested subscription.
     * @return      The subscription requested.
     */
    public TBrokerSubscription Get (int i)
    {
        TBrokerSubscription     ret;
        
        ret = fMembers.get (i);
        
        return ret;
    }
    
    /**
     * @return  The number of subscriptions registered.
     */
    public int GetNumElements ()
    {
        int ret;
        
        ret = fMembers.size ();
        
        return ret;
    }
    
    /**
     * @return  The subscribed, i.e. the object sending the events everyone listens for.
     */
    public VBrowseable GetSubscribed ()
    {
        return fSubscribed;
    }
}
