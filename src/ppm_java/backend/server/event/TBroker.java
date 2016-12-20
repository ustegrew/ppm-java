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

package ppm_java.backend.server.event;

import ppm_java._aux.storage.TAtomicArrayMap;
import ppm_java._aux.typelib.IEvented;
import ppm_java._aux.typelib.VBrowseable;

/**
 * Receives events and sends them to subscribed clients.
 * This is the backbone of the runtime control messaging  
 * system connecting the various modules.
 * 
 * @author peter
 */
public class TBroker
{
    /* [100] */
    private static final int    gkArgsNone              = 0;
    private static final int    gkArgsOneInt            = 10;
    private static final int    gkArgsOneString         = 20;
    
    private TAtomicArrayMap<TBrokerSubscriptionList>    fSubscriptions;
    
    public TBroker ()
    {
        fSubscriptions = new TAtomicArrayMap<> ();
    }
    
    public void Broker (int e, String idSource)
    {
        _Broker (e, idSource, gkArgsNone, 0, null);
    }
    
    public void Broker (int e, String idSource, int arg0)
    {
        _Broker (e, idSource, gkArgsOneInt, arg0, null);
    }
    
    public void Broker (int e, String idSource, String arg0)
    {
        _Broker (e, idSource, gkArgsOneString, 0, arg0);
    }
    
    private void _Broker (int e, String idSource, int argsType, int arg_0_0, String arg_1_0)
    {
        TBrokerSubscription     subscr;
        TBrokerSubscriptionList subscrList;
        int                     i;
        int                     n;
        IEvented                receiver;
        
        subscrList  = fSubscriptions.Get (idSource);
        n           = subscrList.GetNumElements ();
        if (n >= 1)
        {
            for (i = 0; i < n; i++)
            {
                subscr      = subscrList.Get (i);
                receiver    = (IEvented) subscr.GetSubscriber ();
                if (argsType == gkArgsNone)                             /* [100] */
                {
                    receiver.OnEvent (e);
                }
                else if (argsType == gkArgsOneInt)
                {
                    receiver.OnEvent (e, arg_0_0);
                }
                else if (argsType == gkArgsOneString)
                {
                    receiver.OnEvent (e, arg_1_0);
                }
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

/*
[100]   Bit daft to have to use a flag to denote which method to call, but 
        we chose a design where events are passed as integer ID plus optional
        parameters, and with this design, I had to have _Broker(...) receive all
        possible parameters - i.e. can't overload _Broker (...).
        I didn't want to write three public Broker(...) methods with different
        function signatures - that would have meant a threefold repetition of
        the event routing code (even more daft!).      
*/