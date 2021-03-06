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

package ppm_java.util.storage.atomicBuffer;

import java.nio.FloatBuffer;

/**
 * The Invalid data policy. Determines what will be returned when the consumer calls
 * {@link TAtomicBuffer#Get()} and we have no data to offer - either because nothing
 * has been {@link TAtomicBuffer#Set(FloatBuffer) set} since the last call to 
 * {@link TAtomicBuffer#Get()}, or because just in this moment the producer is 
 * {@link TAtomicBuffer#Set(FloatBuffer) setting} a new sample chunk so we have a
 * contention. To be supplied as argument to the constructor.
 * <dl>
 *      <dt>{@link #kReturnEmpty}</dt>
 *      <dd>
 *          (<b>default</b>). Return an empty sample chunk.
 *      </dd>
 *      <dt>{@link #kReturnNull}</dt>
 *      <dd>
 *          Return <code>null</code>. This may be interesting for 
 *          a consumer which wants to call {@link TAtomicBuffer#Get()}
 *          inside an endless loop as long as it returns <code>null</code>.
 *      </dd>
 * </dl>
 */
public enum EAtomicBuffer_IfInvalidPolicy
{
    kReturnEmpty,
    kReturnNull
}