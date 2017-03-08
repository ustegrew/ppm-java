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

import ppm_java.typelib.IEvented;
import ppm_java.typelib.VBrowseable;

/**
 * A subscription of an {@link IEvented} (subscriber) to listen to 
 * events posted by another {@link VBrowseable} (subscribed). 
 * 
 * @author Peter Hoppe
 */
class TBrokerSubscription
{
    /**
     * The subscribed.
     */
    private VBrowseable         fSubscribed;
    
    /**
     * The subscriber
     */
    private IEvented            fSubscriber;
    
    /**
     * cTor. Sets subscribed and subscriber.
     * 
     * @param subscribed        The subscribed.
     * @param subscriber        The subscriber.
     */
    public TBrokerSubscription
    (
        VBrowseable         subscribed,
        IEvented            subscriber
    )
    {
        fSubscribed = subscribed;
        fSubscriber = subscriber;
    }

    /**
     * @return  The subscribed.
     */
    public VBrowseable GetSubscribed ()
    {
        return fSubscribed;
    }

    /**
     * @return  The subscriber.
     */
    public IEvented GetSubscriber ()
    {
        return fSubscriber;
    }
}
