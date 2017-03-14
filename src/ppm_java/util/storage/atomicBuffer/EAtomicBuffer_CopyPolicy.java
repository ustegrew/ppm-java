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
 * The Copy policy. To be supplied as argument to the constructor.
 * <dl>
 *     <dt>{@link #kCopyOnGet}</dt>
 *     <dd>
 *          Data will be deep copied when the consumer thread retrieves it. 
 *          This means that the data won't be copied upon setting it 
 *          ({@link TAtomicBuffer#Set(FloatBuffer)}) which
 *          means that setting data costs the least time. Use this policy 
 *          when the producer thread has a higher priority than the consumer thread. 
 *     </dd>
 *     <dt>{@link #kCopyOnSet}</dt>
 *     <dd>
 *          (<b>default</b>). Data will be deep copied when the producer thread sets it. 
 *          This means that the data won't be copied upon getting it 
 *          ({@link TAtomicBuffer#Get()) which means that getting the data 
 *          costs the least time. Use this policy when the consumer thread has 
 *          a higher priority than the producer thread.
 *     </dd>
 *     <dt>{@link #kNoCopy}</dt>
 *     <dd>
 *          Data won't be copied, but we pass on the original data frame objects.
 *          This policy is risky and should only ever be used if either the producer
 *          or the consumer guarantee their own data copy. This policy is necessary
 *          in some situations to prevent unnecessary multiple copying of data frames
 *          (e.g. when data is passed from one module to another).
 *     </dd>
 * </dl> 
 */
public enum EAtomicBuffer_CopyPolicy
{
    kCopyOnGet,
    kCopyOnSet,
    kNoCopy
}