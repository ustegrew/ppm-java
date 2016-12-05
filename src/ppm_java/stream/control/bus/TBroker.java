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

import ppm_java._aux.storage.TAtomicArrayMap;
import ppm_java._framework.typelib.IEvented;
import ppm_java._framework.typelib.VBrowseable;
import ppm_java._framework.typelib.VEvent;

/**
 * Receives events and sends them to subscribed clients.
 * This is the backbone of the runtime control messaging  
 * system connecting the various modules.
 * 
 * @author peter
 */
public class TBroker
{
    private TAtomicArrayMap<TBrokerSubscriptionList>    fSubscriptions;
    
    public TBroker ()
    {
        fSubscriptions = new TAtomicArrayMap<> ();
    }
    
    public void Broker (VEvent e)
    {
        String                  keyFrom;
        TBrokerSubscription     subscr;
        TBrokerSubscriptionList subscrList;
        int                     i;
        int                     n;
        IEvented                receiver;
        
        keyFrom     = e.GetSource ();
        subscrList  = fSubscriptions.Get (keyFrom);
        n           = subscrList.GetNumElements ();
        if (n >= 1)
        {
            for (i = 0; i < n; i++)
            {
                subscr      = subscrList.Get (i);
                receiver    = (IEvented) subscr.GetSubscriber ();
                receiver.OnEvent (e);
            }
        }
    }
    
    public void Subscribe (VBrowseable subscribed, IEvented subscriber)
    {
        TBrokerSubscriptionList     subscrList;
        TBrokerSubscription         subscr;
        String                      key;
        boolean                     hasKey;
        
        key         = subscribed.GetID ();
        hasKey      = fSubscriptions.HasElement (key);
        if (hasKey)
        {
            subscrList = fSubscriptions.Get (key);
        }
        else
        {
            subscrList = new TBrokerSubscriptionList (subscribed);
            fSubscriptions.Set (key, subscrList);
        }
        
        subscr = new TBrokerSubscription (subscribed, subscriber);
        subscrList.Add (subscr);  
    }
}
