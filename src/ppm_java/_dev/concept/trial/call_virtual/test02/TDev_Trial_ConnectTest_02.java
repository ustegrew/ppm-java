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

/**
 * Connection simulation using double dispatch (visitor) pattern. Works! Output:
 *
 * TSource_A -> TTarget_A: SUCCESS
 * TSource_A -> TTarget_B: ILLEGAL
 * TSource_B -> TTarget_A: ILLEGAL
 * TSource_B -> TTarget_B: SUCCESS
 */
public class TDev_Trial_ConnectTest_02
{
    public static void main (String[] args)
    {
        VSource     src_a;
        VSource     src_b;
        VTarget     trg_a;
        VTarget     trg_b;
        
        src_a = new TSource_A ();
        src_b = new TSource_B ();
        trg_a = new TTarget_A ();
        trg_b = new TTarget_B ();
        
        src_a.ConnectTo (trg_a);
        src_a.ConnectTo (trg_b);
        src_b.ConnectTo (trg_a);
        src_b.ConnectTo (trg_b);
    }
}
