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

package ppm_java._dev.concept.trial.FloatBuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/*
 * Output on an older laptop: (HP G62, Kubuntu 14).
 * Means: For our audio application, creating a float buffer 
 *        produces a negligible delay.
 *        The capacity of the created buffer has no effect on the delay.
 *        
 * Time delay per buffer create converges to about 2.9 ns.
 * ----------------------------------------------------------------------------
 * Performance test: Creating a FloatBuffer n times
 * ----------------------------------------------------------------------------
 * Buffer size: 1024
 * ----------------------------------------------------------------------------
 * Time total [ms]: 0; n = 1; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8; per run [ms]: 0.0
 * Time total [ms]: 0; n = 16; per run [ms]: 0.0
 * Time total [ms]: 0; n = 32; per run [ms]: 0.0
 * Time total [ms]: 0; n = 64; per run [ms]: 0.0
 * Time total [ms]: 0; n = 128; per run [ms]: 0.0
 * Time total [ms]: 1; n = 256; per run [ms]: 0.00390625
 * Time total [ms]: 0; n = 512; per run [ms]: 0.0
 * Time total [ms]: 1; n = 1024; per run [ms]: 9.765625E-4
 * Time total [ms]: 1; n = 2048; per run [ms]: 4.8828125E-4
 * Time total [ms]: 0; n = 4096; per run [ms]: 0.0
 * Time total [ms]: 1; n = 8192; per run [ms]: 1.220703125E-4
 * Time total [ms]: 2; n = 16384; per run [ms]: 1.220703125E-4
 * Time total [ms]: 3; n = 32768; per run [ms]: 9.1552734375E-5
 * Time total [ms]: 5; n = 65536; per run [ms]: 7.62939453125E-5
 * Time total [ms]: 9; n = 131072; per run [ms]: 6.866455078125E-5
 * Time total [ms]: 5; n = 262144; per run [ms]: 1.9073486328125E-5
 * Time total [ms]: 40; n = 524288; per run [ms]: 7.62939453125E-5
 * Time total [ms]: 21; n = 1048576; per run [ms]: 2.002716064453125E-5
 * Time total [ms]: 6; n = 2097152; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 12; n = 4194304; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 24; n = 8388608; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 55; n = 16777216; per run [ms]: 3.2782554626464844E-6
 * Time total [ms]: 124; n = 33554432; per run [ms]: 3.6954879760742188E-6
 * Time total [ms]: 212; n = 67108864; per run [ms]: 3.159046173095703E-6
 * Time total [ms]: 410; n = 134217728; per run [ms]: 3.0547380447387695E-6
 * Time total [ms]: 795; n = 268435456; per run [ms]: 2.9616057872772217E-6
 * Time total [ms]: 1591; n = 536870912; per run [ms]: 2.9634684324264526E-6
 * --------------------------------------------------------------------------
 * Buffer size: 2048
 * --------------------------------------------------------------------------
 * Time total [ms]: 0; n = 1; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8; per run [ms]: 0.0
 * Time total [ms]: 0; n = 16; per run [ms]: 0.0
 * Time total [ms]: 0; n = 32; per run [ms]: 0.0
 * Time total [ms]: 0; n = 64; per run [ms]: 0.0
 * Time total [ms]: 0; n = 128; per run [ms]: 0.0
 * Time total [ms]: 0; n = 256; per run [ms]: 0.0
 * Time total [ms]: 0; n = 512; per run [ms]: 0.0
 * Time total [ms]: 0; n = 1024; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2048; per run [ms]: 0.0
 * Time total [ms]: 1; n = 4096; per run [ms]: 2.44140625E-4
 * Time total [ms]: 0; n = 8192; per run [ms]: 0.0
 * Time total [ms]: 19; n = 16384; per run [ms]: 0.00115966796875
 * Time total [ms]: 6; n = 32768; per run [ms]: 1.8310546875E-4
 * Time total [ms]: 3; n = 65536; per run [ms]: 4.57763671875E-5
 * Time total [ms]: 15; n = 131072; per run [ms]: 1.1444091796875E-4
 * Time total [ms]: 11; n = 262144; per run [ms]: 4.1961669921875E-5
 * Time total [ms]: 38; n = 524288; per run [ms]: 7.2479248046875E-5
 * Time total [ms]: 4; n = 1048576; per run [ms]: 3.814697265625E-6
 * Time total [ms]: 7; n = 2097152; per run [ms]: 3.337860107421875E-6
 * Time total [ms]: 14; n = 4194304; per run [ms]: 3.337860107421875E-6
 * Time total [ms]: 26; n = 8388608; per run [ms]: 3.0994415283203125E-6
 * Time total [ms]: 49; n = 16777216; per run [ms]: 2.9206275939941406E-6
 * Time total [ms]: 141; n = 33554432; per run [ms]: 4.202127456665039E-6
 * Time total [ms]: 282; n = 67108864; per run [ms]: 4.202127456665039E-6
 * Time total [ms]: 500; n = 134217728; per run [ms]: 3.725290298461914E-6
 * Time total [ms]: 964; n = 268435456; per run [ms]: 3.591179847717285E-6
 * Time total [ms]: 1571; n = 536870912; per run [ms]: 2.9262155294418335E-6
 * --------------------------------------------------------------------------
 * Buffer size: 4096
 * --------------------------------------------------------------------------
 * Time total [ms]: 0; n = 1; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8; per run [ms]: 0.0
 * Time total [ms]: 0; n = 16; per run [ms]: 0.0
 * Time total [ms]: 0; n = 32; per run [ms]: 0.0
 * Time total [ms]: 0; n = 64; per run [ms]: 0.0
 * Time total [ms]: 0; n = 128; per run [ms]: 0.0
 * Time total [ms]: 0; n = 256; per run [ms]: 0.0
 * Time total [ms]: 0; n = 512; per run [ms]: 0.0
 * Time total [ms]: 0; n = 1024; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2048; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4096; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8192; per run [ms]: 0.0
 * Time total [ms]: 0; n = 16384; per run [ms]: 0.0
 * Time total [ms]: 0; n = 32768; per run [ms]: 0.0
 * Time total [ms]: 0; n = 65536; per run [ms]: 0.0
 * Time total [ms]: 0; n = 131072; per run [ms]: 0.0
 * Time total [ms]: 0; n = 262144; per run [ms]: 0.0
 * Time total [ms]: 2; n = 524288; per run [ms]: 3.814697265625E-6
 * Time total [ms]: 3; n = 1048576; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 6; n = 2097152; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 12; n = 4194304; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 25; n = 8388608; per run [ms]: 2.9802322387695312E-6
 * Time total [ms]: 49; n = 16777216; per run [ms]: 2.9206275939941406E-6
 * Time total [ms]: 111; n = 33554432; per run [ms]: 3.3080577850341797E-6
 * Time total [ms]: 213; n = 67108864; per run [ms]: 3.1739473342895508E-6
 * Time total [ms]: 392; n = 134217728; per run [ms]: 2.9206275939941406E-6
 * Time total [ms]: 780; n = 268435456; per run [ms]: 2.905726432800293E-6
 * Time total [ms]: 1566; n = 536870912; per run [ms]: 2.9169023036956787E-6
 * --------------------------------------------------------------------------
 * Buffer size: 8192
 * --------------------------------------------------------------------------
 * Time total [ms]: 0; n = 1; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8; per run [ms]: 0.0
 * Time total [ms]: 0; n = 16; per run [ms]: 0.0
 * Time total [ms]: 0; n = 32; per run [ms]: 0.0
 * Time total [ms]: 0; n = 64; per run [ms]: 0.0
 * Time total [ms]: 0; n = 128; per run [ms]: 0.0
 * Time total [ms]: 0; n = 256; per run [ms]: 0.0
 * Time total [ms]: 0; n = 512; per run [ms]: 0.0
 * Time total [ms]: 0; n = 1024; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2048; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4096; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8192; per run [ms]: 0.0
 * Time total [ms]: 0; n = 16384; per run [ms]: 0.0
 * Time total [ms]: 1; n = 32768; per run [ms]: 3.0517578125E-5
 * Time total [ms]: 0; n = 65536; per run [ms]: 0.0
 * Time total [ms]: 0; n = 131072; per run [ms]: 0.0
 * Time total [ms]: 1; n = 262144; per run [ms]: 3.814697265625E-6
 * Time total [ms]: 2; n = 524288; per run [ms]: 3.814697265625E-6
 * Time total [ms]: 3; n = 1048576; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 6; n = 2097152; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 13; n = 4194304; per run [ms]: 3.0994415283203125E-6
 * Time total [ms]: 24; n = 8388608; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 49; n = 16777216; per run [ms]: 2.9206275939941406E-6
 * Time total [ms]: 98; n = 33554432; per run [ms]: 2.9206275939941406E-6
 * Time total [ms]: 194; n = 67108864; per run [ms]: 2.8908252716064453E-6
 * Time total [ms]: 397; n = 134217728; per run [ms]: 2.9578804969787598E-6
 * Time total [ms]: 794; n = 268435456; per run [ms]: 2.9578804969787598E-6
 * Time total [ms]: 1549; n = 536870912; per run [ms]: 2.8852373361587524E-6
 * --------------------------------------------------------------------------
 * Buffer size: 16384
 * --------------------------------------------------------------------------
 * Time total [ms]: 0; n = 1; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8; per run [ms]: 0.0
 * Time total [ms]: 0; n = 16; per run [ms]: 0.0
 * Time total [ms]: 0; n = 32; per run [ms]: 0.0
 * Time total [ms]: 0; n = 64; per run [ms]: 0.0
 * Time total [ms]: 0; n = 128; per run [ms]: 0.0
 * Time total [ms]: 0; n = 256; per run [ms]: 0.0
 * Time total [ms]: 0; n = 512; per run [ms]: 0.0
 * Time total [ms]: 0; n = 1024; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2048; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4096; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8192; per run [ms]: 0.0
 * Time total [ms]: 1; n = 16384; per run [ms]: 6.103515625E-5
 * Time total [ms]: 0; n = 32768; per run [ms]: 0.0
 * Time total [ms]: 0; n = 65536; per run [ms]: 0.0
 * Time total [ms]: 0; n = 131072; per run [ms]: 0.0
 * Time total [ms]: 1; n = 262144; per run [ms]: 3.814697265625E-6
 * Time total [ms]: 2; n = 524288; per run [ms]: 3.814697265625E-6
 * Time total [ms]: 3; n = 1048576; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 6; n = 2097152; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 12; n = 4194304; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 24; n = 8388608; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 48; n = 16777216; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 97; n = 33554432; per run [ms]: 2.8908252716064453E-6
 * Time total [ms]: 193; n = 67108864; per run [ms]: 2.8759241104125977E-6
 * Time total [ms]: 389; n = 134217728; per run [ms]: 2.898275852203369E-6
 * Time total [ms]: 777; n = 268435456; per run [ms]: 2.8945505619049072E-6
 * Time total [ms]: 1540; n = 536870912; per run [ms]: 2.868473529815674E-6
 * --------------------------------------------------------------------------
 * Buffer size: 32768
 * --------------------------------------------------------------------------
 * Time total [ms]: 0; n = 1; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8; per run [ms]: 0.0
 * Time total [ms]: 0; n = 16; per run [ms]: 0.0
 * Time total [ms]: 0; n = 32; per run [ms]: 0.0
 * Time total [ms]: 0; n = 64; per run [ms]: 0.0
 * Time total [ms]: 0; n = 128; per run [ms]: 0.0
 * Time total [ms]: 0; n = 256; per run [ms]: 0.0
 * Time total [ms]: 0; n = 512; per run [ms]: 0.0
 * Time total [ms]: 0; n = 1024; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2048; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4096; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8192; per run [ms]: 0.0
 * Time total [ms]: 0; n = 16384; per run [ms]: 0.0
 * Time total [ms]: 0; n = 32768; per run [ms]: 0.0
 * Time total [ms]: 0; n = 65536; per run [ms]: 0.0
 * Time total [ms]: 0; n = 131072; per run [ms]: 0.0
 * Time total [ms]: 1; n = 262144; per run [ms]: 3.814697265625E-6
 * Time total [ms]: 2; n = 524288; per run [ms]: 3.814697265625E-6
 * Time total [ms]: 3; n = 1048576; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 6; n = 2097152; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 12; n = 4194304; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 24; n = 8388608; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 48; n = 16777216; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 96; n = 33554432; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 194; n = 67108864; per run [ms]: 2.8908252716064453E-6
 * Time total [ms]: 391; n = 134217728; per run [ms]: 2.913177013397217E-6
 * Time total [ms]: 775; n = 268435456; per run [ms]: 2.8870999813079834E-6
 * Time total [ms]: 1539; n = 536870912; per run [ms]: 2.866610884666443E-6
 * --------------------------------------------------------------------------
 * Buffer size: 65536
 * --------------------------------------------------------------------------
 * Time total [ms]: 0; n = 1; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8; per run [ms]: 0.0
 * Time total [ms]: 0; n = 16; per run [ms]: 0.0
 * Time total [ms]: 0; n = 32; per run [ms]: 0.0
 * Time total [ms]: 0; n = 64; per run [ms]: 0.0
 * Time total [ms]: 0; n = 128; per run [ms]: 0.0
 * Time total [ms]: 0; n = 256; per run [ms]: 0.0
 * Time total [ms]: 0; n = 512; per run [ms]: 0.0
 * Time total [ms]: 0; n = 1024; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2048; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4096; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8192; per run [ms]: 0.0
 * Time total [ms]: 0; n = 16384; per run [ms]: 0.0
 * Time total [ms]: 0; n = 32768; per run [ms]: 0.0
 * Time total [ms]: 1; n = 65536; per run [ms]: 1.52587890625E-5
 * Time total [ms]: 0; n = 131072; per run [ms]: 0.0
 * Time total [ms]: 1; n = 262144; per run [ms]: 3.814697265625E-6
 * Time total [ms]: 2; n = 524288; per run [ms]: 3.814697265625E-6
 * Time total [ms]: 3; n = 1048576; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 6; n = 2097152; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 12; n = 4194304; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 24; n = 8388608; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 47; n = 16777216; per run [ms]: 2.8014183044433594E-6
 * Time total [ms]: 95; n = 33554432; per run [ms]: 2.8312206268310547E-6
 * Time total [ms]: 193; n = 67108864; per run [ms]: 2.8759241104125977E-6
 * Time total [ms]: 384; n = 134217728; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 769; n = 268435456; per run [ms]: 2.864748239517212E-6
 * Time total [ms]: 1550; n = 536870912; per run [ms]: 2.8870999813079834E-6
 * --------------------------------------------------------------------------
 * Buffer size: 131072
 * --------------------------------------------------------------------------
 * Time total [ms]: 0; n = 1; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8; per run [ms]: 0.0
 * Time total [ms]: 0; n = 16; per run [ms]: 0.0
 * Time total [ms]: 0; n = 32; per run [ms]: 0.0
 * Time total [ms]: 0; n = 64; per run [ms]: 0.0
 * Time total [ms]: 0; n = 128; per run [ms]: 0.0
 * Time total [ms]: 0; n = 256; per run [ms]: 0.0
 * Time total [ms]: 0; n = 512; per run [ms]: 0.0
 * Time total [ms]: 0; n = 1024; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2048; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4096; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8192; per run [ms]: 0.0
 * Time total [ms]: 0; n = 16384; per run [ms]: 0.0
 * Time total [ms]: 0; n = 32768; per run [ms]: 0.0
 * Time total [ms]: 0; n = 65536; per run [ms]: 0.0
 * Time total [ms]: 1; n = 131072; per run [ms]: 7.62939453125E-6
 * Time total [ms]: 1; n = 262144; per run [ms]: 3.814697265625E-6
 * Time total [ms]: 1; n = 524288; per run [ms]: 1.9073486328125E-6
 * Time total [ms]: 3; n = 1048576; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 6; n = 2097152; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 12; n = 4194304; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 24; n = 8388608; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 50; n = 16777216; per run [ms]: 2.9802322387695312E-6
 * Time total [ms]: 100; n = 33554432; per run [ms]: 2.9802322387695312E-6
 * Time total [ms]: 207; n = 67108864; per run [ms]: 3.084540367126465E-6
 * Time total [ms]: 404; n = 134217728; per run [ms]: 3.0100345611572266E-6
 * Time total [ms]: 786; n = 268435456; per run [ms]: 2.9280781745910645E-6
 * Time total [ms]: 1529; n = 536870912; per run [ms]: 2.8479844331741333E-6
 * --------------------------------------------------------------------------
 * Buffer size: 262144
 * --------------------------------------------------------------------------
 * Time total [ms]: 0; n = 1; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8; per run [ms]: 0.0
 * Time total [ms]: 0; n = 16; per run [ms]: 0.0
 * Time total [ms]: 0; n = 32; per run [ms]: 0.0
 * Time total [ms]: 0; n = 64; per run [ms]: 0.0
 * Time total [ms]: 0; n = 128; per run [ms]: 0.0
 * Time total [ms]: 0; n = 256; per run [ms]: 0.0
 * Time total [ms]: 0; n = 512; per run [ms]: 0.0
 * Time total [ms]: 0; n = 1024; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2048; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4096; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8192; per run [ms]: 0.0
 * Time total [ms]: 0; n = 16384; per run [ms]: 0.0
 * Time total [ms]: 0; n = 32768; per run [ms]: 0.0
 * Time total [ms]: 0; n = 65536; per run [ms]: 0.0
 * Time total [ms]: 1; n = 131072; per run [ms]: 7.62939453125E-6
 * Time total [ms]: 1; n = 262144; per run [ms]: 3.814697265625E-6
 * Time total [ms]: 1; n = 524288; per run [ms]: 1.9073486328125E-6
 * Time total [ms]: 3; n = 1048576; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 7; n = 2097152; per run [ms]: 3.337860107421875E-6
 * Time total [ms]: 12; n = 4194304; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 24; n = 8388608; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 49; n = 16777216; per run [ms]: 2.9206275939941406E-6
 * Time total [ms]: 101; n = 33554432; per run [ms]: 3.0100345611572266E-6
 * Time total [ms]: 194; n = 67108864; per run [ms]: 2.8908252716064453E-6
 * Time total [ms]: 389; n = 134217728; per run [ms]: 2.898275852203369E-6
 * Time total [ms]: 780; n = 268435456; per run [ms]: 2.905726432800293E-6
 * Time total [ms]: 1552; n = 536870912; per run [ms]: 2.8908252716064453E-6
 * --------------------------------------------------------------------------
 * Buffer size: 524288
 * --------------------------------------------------------------------------
 * Time total [ms]: 0; n = 1; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8; per run [ms]: 0.0
 * Time total [ms]: 0; n = 16; per run [ms]: 0.0
 * Time total [ms]: 0; n = 32; per run [ms]: 0.0
 * Time total [ms]: 0; n = 64; per run [ms]: 0.0
 * Time total [ms]: 0; n = 128; per run [ms]: 0.0
 * Time total [ms]: 0; n = 256; per run [ms]: 0.0
 * Time total [ms]: 0; n = 512; per run [ms]: 0.0
 * Time total [ms]: 0; n = 1024; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2048; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4096; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8192; per run [ms]: 0.0
 * Time total [ms]: 0; n = 16384; per run [ms]: 0.0
 * Time total [ms]: 0; n = 32768; per run [ms]: 0.0
 * Time total [ms]: 0; n = 65536; per run [ms]: 0.0
 * Time total [ms]: 1; n = 131072; per run [ms]: 7.62939453125E-6
 * Time total [ms]: 1; n = 262144; per run [ms]: 3.814697265625E-6
 * Time total [ms]: 1; n = 524288; per run [ms]: 1.9073486328125E-6
 * Time total [ms]: 3; n = 1048576; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 6; n = 2097152; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 12; n = 4194304; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 24; n = 8388608; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 48; n = 16777216; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 136; n = 33554432; per run [ms]: 4.0531158447265625E-6
 * Time total [ms]: 237; n = 67108864; per run [ms]: 3.5315752029418945E-6
 * Time total [ms]: 387; n = 134217728; per run [ms]: 2.8833746910095215E-6
 * Time total [ms]: 779; n = 268435456; per run [ms]: 2.902001142501831E-6
 * Time total [ms]: 1548; n = 536870912; per run [ms]: 2.8833746910095215E-6
 * --------------------------------------------------------------------------
 * Buffer size: 1048576
 * --------------------------------------------------------------------------
 * Time total [ms]: 0; n = 1; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8; per run [ms]: 0.0
 * Time total [ms]: 0; n = 16; per run [ms]: 0.0
 * Time total [ms]: 0; n = 32; per run [ms]: 0.0
 * Time total [ms]: 0; n = 64; per run [ms]: 0.0
 * Time total [ms]: 0; n = 128; per run [ms]: 0.0
 * Time total [ms]: 0; n = 256; per run [ms]: 0.0
 * Time total [ms]: 0; n = 512; per run [ms]: 0.0
 * Time total [ms]: 0; n = 1024; per run [ms]: 0.0
 * Time total [ms]: 0; n = 2048; per run [ms]: 0.0
 * Time total [ms]: 0; n = 4096; per run [ms]: 0.0
 * Time total [ms]: 0; n = 8192; per run [ms]: 0.0
 * Time total [ms]: 0; n = 16384; per run [ms]: 0.0
 * Time total [ms]: 0; n = 32768; per run [ms]: 0.0
 * Time total [ms]: 0; n = 65536; per run [ms]: 0.0
 * Time total [ms]: 0; n = 131072; per run [ms]: 0.0
 * Time total [ms]: 1; n = 262144; per run [ms]: 3.814697265625E-6
 * Time total [ms]: 1; n = 524288; per run [ms]: 1.9073486328125E-6
 * Time total [ms]: 3; n = 1048576; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 6; n = 2097152; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 12; n = 4194304; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 24; n = 8388608; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 48; n = 16777216; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 100; n = 33554432; per run [ms]: 2.9802322387695312E-6
 * Time total [ms]: 192; n = 67108864; per run [ms]: 2.86102294921875E-6
 * Time total [ms]: 420; n = 134217728; per run [ms]: 3.129243850708008E-6
 * Time total [ms]: 796; n = 268435456; per run [ms]: 2.9653310775756836E-6
 * Time total [ms]: 1556; n = 536870912; per run [ms]: 2.898275852203369E-6
 */

/**
 * Performance trial: How long does it take to create a new FloatBuffer?
 * I wanted to see what performance impact the creation of a new FloatBuffer has.
 *  
 * Results above. Motivated by observation that JJack creates a new 
 * FloatBuffer with every iteration of the process() loop. From the results
 * I concluded that the impact is negligible; therefore there's no problem
 * with creating a new FloatBuffer with every iteration of the audio 
 * processing loop.
 * 
 * @author peter
 */
public class TDev_Trial_Performance_FloatBufferCreate_01
{
    private static final int            gkNMaxTests     = Integer.MAX_VALUE / 2;
    private static final int            gkNMaxBlocks    = 1024;
    
    /**
     * 
     * @param args
     */
    public static void main (String[] args)
    {
        long                t0;
        long                t1;
        long                dt;
        int                 n;
        int                 i;
        int                 xSize;
        int                 size;
        ByteBuffer          bb;
        @SuppressWarnings ("unused")
        FloatBuffer         fb;

        System.out.println ("----------------------------------------------------------------------------");
        System.out.println ("Performance test: Creating a FloatBuffer n times");
        for (xSize = 1; xSize <= gkNMaxBlocks; xSize = 2 * xSize)
        {
            size    = 1024 * xSize;
            bb      = ByteBuffer.allocateDirect (size).order (ByteOrder.LITTLE_ENDIAN);

            System.out.println ("----------------------------------------------------------------------------");
            System.out.println ("Buffer size: " + size);
            System.out.println ("----------------------------------------------------------------------------");
            for (n = 1; n < gkNMaxTests; n = 2 * n)
            {
                t0 = System.currentTimeMillis ();
                for (i = 1; i <= n; i++)
                {
                    fb = bb.asFloatBuffer ();
                }
                t1 = System.currentTimeMillis ();
                dt = t1 - t0;
                System.out.println ("Time total [ms]: " + dt + "; n = " + n + "; per run [ms]: " + (((double)dt) / ((double)n)));
            }
        }
    }
}
