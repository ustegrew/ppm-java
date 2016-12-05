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

package ppm_java._dev.concept.trial.TAtomicBuffer;

import java.nio.FloatBuffer;
import java.util.concurrent.atomic.AtomicInteger;

import ppm_java._aux.storage.TAtomicBuffer;
import ppm_java._aux.storage.TAtomicBuffer.ECopyPolicy;

/**
 * Test result:
 * 
 * Test running - will take a while. Go, make coffee!
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 1
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 2
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 3
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 4
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 5
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 6
 * --------------------------------------------------------------------------------------
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 7
 * --------------------------------------------------------------------------------------
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 8
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 9
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 10
 * --------------------------------------------------------------------------------------
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [9]: Finished (ok           ): Consumer #9; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 1
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 2
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 48; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 48; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 3
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 24; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 25; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 25; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 4
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 33; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 33; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 33; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 33; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 5
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 6
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 24; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 24; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 24; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 24; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 24; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 24; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 7
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 16; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 15; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 15; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 15; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 15; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 15; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 15; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 8
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 9
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 14; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 13; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 13; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 14; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 13; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 13; Consumed a few empty frames (not critical)
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 13; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 13; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 10
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [9]: Finished (ok           ): Consumer #9; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 1
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 21; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 2
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 31; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 31; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 3
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 33; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 32; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 32; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 4
 * --------------------------------------------------------------------------------------
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 5
 * --------------------------------------------------------------------------------------
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 30; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 30; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 30; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 30; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 30; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 6
 * --------------------------------------------------------------------------------------
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 26; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 26; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 26; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 26; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 26; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 26; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 7
 * --------------------------------------------------------------------------------------
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 8
 * --------------------------------------------------------------------------------------
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 15; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 15; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 15; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 15; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 16; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 15; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 15; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 15; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 9
 * --------------------------------------------------------------------------------------
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 10
 * --------------------------------------------------------------------------------------
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [9]: Finished (ok           ): Consumer #9; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 1
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 2
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 3
 * --------------------------------------------------------------------------------------
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 4
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 8; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 8; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 8; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 5
 * --------------------------------------------------------------------------------------
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 17; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 17; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 17; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 17; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 17; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 6
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 20; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 20; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 20; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 20; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 20; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 20; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 7
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 8
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 25; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 25; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 24; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 24; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 25; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 25; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 25; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 25; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 9
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 13; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 13; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 10
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 8; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [9]: Finished (ok           ): Consumer #9; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 1
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 2
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 3
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 4
 * --------------------------------------------------------------------------------------
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 5
 * --------------------------------------------------------------------------------------
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 17; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 16; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 17; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 17; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 17; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 6
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 7
 * --------------------------------------------------------------------------------------
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 15; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 16; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 16; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 15; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 16; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 15; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 16; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 8
 * --------------------------------------------------------------------------------------
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 9
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 10
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 16; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 16; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 16; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 16; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 16; Consumed a few empty frames (not critical)
 * Consumer [9]: Finished (ok           ): Consumer #9; nCorruptions: 0; nEmptyFrames: 16; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 16; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 16; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 15; Consumed a few empty frames (not critical)
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 16; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 1
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 2
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 17; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 3
 * --------------------------------------------------------------------------------------
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 4
 * --------------------------------------------------------------------------------------
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 14; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 17; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 5
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 6
 * --------------------------------------------------------------------------------------
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 8; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 8; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 7
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 8; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 8; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 8
 * --------------------------------------------------------------------------------------
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 9
 * --------------------------------------------------------------------------------------
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 8; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 10
 * --------------------------------------------------------------------------------------
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 17; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 13; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [9]: Finished (ok           ): Consumer #9; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 1
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 2
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 3
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 4
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 8; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 5
 * --------------------------------------------------------------------------------------
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 8; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 6
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 7
 * --------------------------------------------------------------------------------------
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 8; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 8
 * --------------------------------------------------------------------------------------
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 9
 * --------------------------------------------------------------------------------------
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 13; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 10
 * --------------------------------------------------------------------------------------
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 8; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [9]: Finished (ok           ): Consumer #9; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 1
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 2
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 3
 * --------------------------------------------------------------------------------------
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 8; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 4
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 14; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 5
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 6
 * --------------------------------------------------------------------------------------
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 7
 * --------------------------------------------------------------------------------------
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 8
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 9
 * --------------------------------------------------------------------------------------
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 10
 * --------------------------------------------------------------------------------------
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [9]: Finished (ok           ): Consumer #9; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 1
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 2
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 3
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 4
 * --------------------------------------------------------------------------------------
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 8; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 5
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 8; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 6
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 7
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 8
 * --------------------------------------------------------------------------------------
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 9
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 10
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [9]: Finished (ok           ): Consumer #9; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 8; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 1
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 2
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 3
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 10; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 4
 * --------------------------------------------------------------------------------------
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 12; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 5
 * --------------------------------------------------------------------------------------
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 13; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 6
 * --------------------------------------------------------------------------------------
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 15; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 7
 * --------------------------------------------------------------------------------------
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 9; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 7; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 8
 * --------------------------------------------------------------------------------------
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 11; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 9
 * --------------------------------------------------------------------------------------
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 5; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 3; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * 
 * --------------------------------------------------------------------------------------
 * TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 10
 * --------------------------------------------------------------------------------------
 * Consumer [3]: Finished (ok           ): Consumer #3; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 * Consumer [7]: Finished (ok           ): Consumer #7; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [8]: Finished (ok           ): Consumer #8; nCorruptions: 0; nEmptyFrames: 4; Consumed a few empty frames (not critical)
 * Consumer [1]: Finished (ok           ): Consumer #1; nCorruptions: 0; nEmptyFrames: 2; Consumed a few empty frames (not critical)
 * Consumer [5]: Finished (ok           ): Consumer #5; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [9]: Finished (ok           ): Consumer #9; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [0]: Finished (ok           ): Consumer #0; nCorruptions: 0; nEmptyFrames: 8; Consumed a few empty frames (not critical)
 * Consumer [2]: Finished (ok           ): Consumer #2; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [6]: Finished (ok           ): Consumer #6; nCorruptions: 0; nEmptyFrames: 1; Consumed a few empty frames (not critical)
 * Consumer [4]: Finished (ok           ): Consumer #4; nCorruptions: 0; nEmptyFrames: 6; Consumed a few empty frames (not critical)
 */

/**
 * TAtomicBuffer contention trial. I have one thread producing
 * new frames and n threads consuming them. Would we get corrupted 
 * data? 
 * 
 * Motivation: Find out whether the mutex free synchronization 
 * in TAtomicBuffer is working. If we get corrupted data we have 
 * a race condition problem, due to faulty synchronization 
 * technique.
 * 
 * Assumption: We won't test whether AtomicInteger is truly atomic - 
 * all documentation indicates an AtomicInteger is atomic 
 * across multiple threads using it, therefore we just follow
 * the documentation and presume that it is so.
 * This test just establishes whether we use the AtomicInteger 
 * synchronization technique correctly and what could be done 
 * better if we don't.
 * 
 * Result: Trial showed that data was never corrupted, even in 
 * heavily contended situations. Therefore our synchronization 
 * with atomic integer does work. 
 * 
 * In fact, when investigating an earlier version of TAtomicBuffer 
 * with debugger and breaking execution flow of the the producer
 * thread I found that the consumer threads stopped as well - 
 * which shows that the atomic integer is indeed atomic and can 
 * be used to synchronize multiple threads. This crude break trial 
 * wouldn't work with the present version of TAtomicBuffer. 
 * The present version won't block the high priority thread when 
 * the low priority thread gets blocked. To enable detection of 
 * blockages of the low priority thread we now have an underrun 
 * counter which clients can query 
 * ({@link TAtomicBuffer#GetNumUnderruns()}). 
 * If everything works, the underrun counter should stay at zero value.
 * 
 * 
 * 
 * one doesn't stop the consumer thread 
 * when the producer thread hits a breakpoint - but it has a design
 * it'll just 
 * increment the underrun counter and deliver empty frames. 
 * 
 * @author peter
 */
public class TDev_Trial_AtomicBuffer_Contention_01
{
    private static final int        gkNNums             = 16384;
    private static final int        gkNRunsConsumer     = 100;
    private static final int        gkNRunsProducer     = 101;
    private static Object           gLock               = new Object ();
    
    private static AtomicInteger    gNConsumersRunning  = new AtomicInteger (0);
    private static AtomicInteger    gNProducersRunning  = new AtomicInteger (0);

    private static class TProducer implements Runnable
    {
        private TAtomicBuffer           fAtBuffer;
        private int                     fDelay;
        
        public TProducer (TAtomicBuffer b)
        {
            fAtBuffer   = b;
            fDelay      = 1000; 
        }
        
        @Override
        public void run ()
        {
            int         iRun;
            int         iNum;
            float []    xx;

            gNProducersRunning.incrementAndGet ();
            xx = new float [gkNNums];
            for (iRun = 0; iRun < gkNRunsProducer; iRun++)
            {
                FloatBuffer fb;

                for (iNum = 0; iNum < gkNNums; iNum++)
                {
                    xx[iNum] = iRun + iNum;
                }
                
                fb = FloatBuffer.allocate (gkNNums);
                fb.put (xx);
                fAtBuffer.Set (fb);

                try {Thread.sleep (fDelay);} catch (InterruptedException e) {}
                fDelay -= 10;
            }
            gNProducersRunning.decrementAndGet ();
        }
    }
    
    private static class TConsumer implements Runnable
    {
        private TAtomicBuffer               fAtBuffer;
        private int                         fID;
        private int                         fDelay;
        private int                         fNCorruptions;
        private int                         fNEmptyFrames;
        
        public TConsumer (int id, TAtomicBuffer b)
        {
            fAtBuffer                   = b;
            fID                         = id;
            fDelay                      = 1;
            fNCorruptions               = 0;
            fNEmptyFrames               = 0;
        }
        
        @Override
        public void run ()
        {
            int i;
            
            gNConsumersRunning.incrementAndGet ();
            for (i = 0; i < gkNRunsConsumer; i++)
            {
                TestNums ();
                try {Thread.sleep (fDelay);} catch (InterruptedException e) {}
                fDelay += 9;
            }
            PrintReport ();
            gNConsumersRunning.decrementAndGet ();
        }
        
        private void TestNums ()
        {
            int                 i;
            int                 n;
            float               x0;
            float               x1;
            float               dx;
            FloatBuffer         fb;
            
            fb              = fAtBuffer.Get ();
            n               = fb.capacity ();
            if (n == gkNNums)
            {
                for (i = gkNNums-1; i >= 1; i--)
                {
                    /* Corruption check - difference between sample[i] and 
                     * sample[i-1] must be 1.
                     */
                    x0      = fb.get (i-1);
                    x1      = fb.get (i);
                    dx      = x1 - x0;
                    if (dx != 1.0)
                    {
                        fNCorruptions++;
                        PrintCorrupted (i, x0, x1);
                    }
                }
            }
            else
            {
                fNEmptyFrames++;
            }
        }
        
        private void PrintCorrupted (int iSample, float x0, float x1)
        {
            String logEntry;
            
            logEntry = "Data corruption: iSample=" + iSample + "; x[iSample]=" + x1 + "; x[iSample-1]=" + x0;
            PrintLogEntry (logEntry);
        }
        
        private void PrintLogEntry (String message)
        {
            String le;
            
            le = "Consumer [" + fID + "]: " + message;
            synchronized (gLock)                /* [100] */ 
            {
                System.out.println (le);
            }
        }
        
        private void PrintReport ()
        {
            String preamble;
            String postAmble;
            String logMessage;
            
            if (fNCorruptions >= 1)
            {
                preamble = "Finished (fatal problem): ";
            }
            else
            {
                preamble = "Finished (ok           ): ";
            }
            
            if (fNEmptyFrames >= 1)
            {
                postAmble = "; Consumed a few empty frames (not critical)";
            }
            else
            {
                postAmble = "";
            }
            
            logMessage = preamble + "Consumer #" + fID + "; nCorruptions: " + fNCorruptions + "; nEmptyFrames: " + fNEmptyFrames + postAmble;
            
            PrintLogEntry (logMessage);
        }
    }
    
    /**
     * @param args
     */
    public static void main (String[] args)
    {
        int                 nProducers;
        int                 nConsumers;
        
        /* Coffee break! */
        System.out.println ("Test running - will take a while. Go, make coffee!");

        /* Test 1: Producer threads are assumed to be high priority (i,.e. time critical), 
         * Consumer threads are assumed to be low priority (i.e. not time critical, can 
         * afford to lose some data frames if they can't keep up) */
        for (nProducers = 1; nProducers <= 5; nProducers++)
        {
            for (nConsumers = 1; nConsumers <= 10; nConsumers++)
            {
                _RunTest 
                (
                    "TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = " + nProducers + "; nConsumers = " + nConsumers,
                    nProducers,
                    nConsumers,
                    ECopyPolicy.kCopyOnGet
                );
            }
        }
        
        /* Test 2: Producer threads are assumed to be low priority 
         * (i,.e. not time critical), Consumer threads are assumed to 
         * be high priority (i.e. time critical, will unfortunately 
         * lose some data frames if the producer can't keep up) */
        for (nProducers = 1; nProducers <= 5; nProducers++)
        {
            for (nConsumers = 1; nConsumers <= 10; nConsumers++)
            {
                _RunTest 
                (
                    "TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = " + nProducers + "; nConsumers = " + nConsumers,
                    nProducers,
                    nConsumers,
                    ECopyPolicy.kCopyOnSet
                );
            }
        }
    }
    
    private static void _RunTest (String comment, int nProducers, int nConsumers, ECopyPolicy cPol)
    {
        int                 i;
        Thread[]            prWorkers;
        Thread[]            cnWorkers;
        TAtomicBuffer       ab;
        int                 nRunningProducers;
        int                 nRunningConsumers;
        boolean             doWait;

        System.out.println ();
        System.out.println ("--------------------------------------------------------------------------------------");
        System.out.println (comment);
        System.out.println ("--------------------------------------------------------------------------------------");
        prWorkers   = new Thread [nProducers];
        cnWorkers   = new Thread [nConsumers];
        ab          = new TAtomicBuffer (cPol);
        for (i = 0; i < nProducers; i++)
        {
            prWorkers [i] = new Thread (new TProducer (ab));
        }
        for (i = 0; i < nConsumers; i++)
        {
            cnWorkers [i] = new Thread (new TConsumer (i, ab));
        }
        
        for (i = 0; i < nConsumers; i++)
        {
            cnWorkers[i].start ();
        }

        for (i = 0; i < nProducers; i++)
        {
            prWorkers[i].start ();
        }
        
        /* Run in infinite loop until all producers and consumers have stopped. */
        doWait = true;
        while (doWait)
        {
            try {Thread.sleep (1000);} catch (InterruptedException e) {}
            nRunningProducers   = gNConsumersRunning.addAndGet (0);     /* [110] */
            nRunningConsumers   = gNConsumersRunning.addAndGet (0);
            if (nRunningProducers == 0  &&  nRunningConsumers == 0)
            {
                doWait = false;
            }
        }
    }
}

/*
[100]       Synchronized block. Shouldn't interfere with the test even
            with the different synchronization technique used by TAtomicBuffer.
            Supposed, the synchronized block would interfere with the test:
            The Print() function will only be called when we found data 
            corruption. Hence, when it's called we have already proven 
            that TAtomicBuffer doesn't work; therefore it wouldn't matter 
            anyway.

[110]       Bit weird way of retrieving the value of the atomic integer; we just 
            add zero and return result. But according to the docs, addAndGet
            is an atomic operation; therefore the data state will be consistent
            across multiple threads.
            I don't think it's a problem that we use two separate atomic integers,
            one for the producer count, one for the consumer count. In other 
            situations using two separate synchronization primitives across
            the same set of threads can actually lead to race conditions/deadlocks etc.
            I think, it's safe here - by the time the if-condition is reached we 
            have two definite results in, and each counter converges towards zero,
            hence when we reach the if-condition and both counters are indeed zero, then 
            we can safely assume that all running threads are in fact finished.
*/