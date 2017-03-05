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

package ppm_java._dev.concept.trial.call_virtual.test02;

class TSource_A extends VSource
{
    /* (non-Javadoc)
     * @see ppm_java._dev.concept.trial.call_virtual.test02.VSource#Visit(ppm_java._dev.concept.trial.call_virtual.test02.TTarget_A)
     */
    @Override
    void Visit (TTarget_A s)
    {
        System.out.println ("TSource_A -> TTarget_A: SUCCESS");
    }

    /* (non-Javadoc)
     * @see ppm_java._dev.concept.trial.call_virtual.test02.VSource#Visit(ppm_java._dev.concept.trial.call_virtual.test02.TTarget_B)
     */
    @Override
    void Visit (TTarget_B s)
    {
        System.out.println ("TSource_A -> TTarget_B: ILLEGAL");
    }
}
