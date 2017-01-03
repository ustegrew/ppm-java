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
import ppm_java._aux.storage.TAtomicBuffer.EIfInvalidPolicy;
import ppm_java._aux.storage.TAtomicBuffer_Stats;
import ppm_java._aux.storage.TAtomicBuffer_Stats.TRecord;

/**
 * Test result:
 * 
Test running - will take a while. Go, make coffee!

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 1
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 46; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 46, contentions: 0

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 2
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 46; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 100; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 146, contentions: 0

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 3
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 75; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 73; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 246, contentions: 0

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 4
--------------------------------------------------------------------------------------
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 79; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 346, contentions: 0

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 5
--------------------------------------------------------------------------------------
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 446, contentions: 0

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 6
--------------------------------------------------------------------------------------
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 546, contentions: 0

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 7
--------------------------------------------------------------------------------------
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 646, contentions: 0

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 8
--------------------------------------------------------------------------------------
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 99; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 746, contentions: 0

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 9
--------------------------------------------------------------------------------------
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 846, contentions: 0

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 1; nConsumers = 10
--------------------------------------------------------------------------------------
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [9]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 946, contentions: 0

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 1
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 46; Consumed a few empty frames (not critical)
Stat counters: overruns: 31, underruns: 46, contentions: 50

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 2
--------------------------------------------------------------------------------------
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 74; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 72; Consumed a few empty frames (not critical)
Stat counters: overruns: 27, underruns: 146, contentions: 54

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 3
--------------------------------------------------------------------------------------
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 78; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 81; Consumed a few empty frames (not critical)
Stat counters: overruns: 56, underruns: 246, contentions: 25

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 4
--------------------------------------------------------------------------------------
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 82; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 84; Consumed a few empty frames (not critical)
Stat counters: overruns: 56, underruns: 346, contentions: 25

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 5
--------------------------------------------------------------------------------------
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 78; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 83; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 99; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 99; Consumed a few empty frames (not critical)
Stat counters: overruns: 58, underruns: 446, contentions: 23

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 6
--------------------------------------------------------------------------------------
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 80; Consumed a few empty frames (not critical)
Stat counters: overruns: 43, underruns: 546, contentions: 38

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 7
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 100; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Stat counters: overruns: 74, underruns: 646, contentions: 7

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 8
--------------------------------------------------------------------------------------
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 85; Consumed a few empty frames (not critical)
Stat counters: overruns: 47, underruns: 746, contentions: 34

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 9
--------------------------------------------------------------------------------------
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Stat counters: overruns: 52, underruns: 845, contentions: 28

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 2; nConsumers = 10
--------------------------------------------------------------------------------------
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [9]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Stat counters: overruns: 56, underruns: 946, contentions: 25

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 1
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 46; Consumed a few empty frames (not critical)
Stat counters: overruns: 112, underruns: 46, contentions: 37

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 2
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 68; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 78; Consumed a few empty frames (not critical)
Stat counters: overruns: 129, underruns: 146, contentions: 20

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 3
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 80; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 79; Consumed a few empty frames (not critical)
Stat counters: overruns: 118, underruns: 246, contentions: 31

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 4
--------------------------------------------------------------------------------------
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 84; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 85; Consumed a few empty frames (not critical)
Stat counters: overruns: 127, underruns: 346, contentions: 22

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 5
--------------------------------------------------------------------------------------
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 85; Consumed a few empty frames (not critical)
Stat counters: overruns: 121, underruns: 446, contentions: 28

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 6
--------------------------------------------------------------------------------------
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Stat counters: overruns: 119, underruns: 546, contentions: 30

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 7
--------------------------------------------------------------------------------------
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Stat counters: overruns: 120, underruns: 645, contentions: 28

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 8
--------------------------------------------------------------------------------------
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Stat counters: overruns: 143, underruns: 746, contentions: 6

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 9
--------------------------------------------------------------------------------------
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Stat counters: overruns: 113, underruns: 846, contentions: 36

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 3; nConsumers = 10
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 99; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [9]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 100; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Stat counters: overruns: 136, underruns: 945, contentions: 12

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 1
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 46; Consumed a few empty frames (not critical)
Stat counters: overruns: 203, underruns: 46, contentions: 14

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 2
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 75; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 71; Consumed a few empty frames (not critical)
Stat counters: overruns: 175, underruns: 146, contentions: 42

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 3
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 83; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 81; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 82; Consumed a few empty frames (not critical)
Stat counters: overruns: 160, underruns: 246, contentions: 57

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 4
--------------------------------------------------------------------------------------
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 85; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 86; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 84; Consumed a few empty frames (not critical)
Stat counters: overruns: 168, underruns: 345, contentions: 48

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 5
--------------------------------------------------------------------------------------
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 82; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Stat counters: overruns: 112, underruns: 446, contentions: 105

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 6
--------------------------------------------------------------------------------------
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Stat counters: overruns: 128, underruns: 546, contentions: 89

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 7
--------------------------------------------------------------------------------------
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Stat counters: overruns: 157, underruns: 646, contentions: 60

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 8
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 86; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Stat counters: overruns: 181, underruns: 746, contentions: 36

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 9
--------------------------------------------------------------------------------------
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 99; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Stat counters: overruns: 155, underruns: 845, contentions: 61

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 4; nConsumers = 10
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 100; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [9]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 86; Consumed a few empty frames (not critical)
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Stat counters: overruns: 136, underruns: 946, contentions: 81

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 1
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 46; Consumed a few empty frames (not critical)
Stat counters: overruns: 230, underruns: 46, contentions: 55

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 2
--------------------------------------------------------------------------------------
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 72; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 74; Consumed a few empty frames (not critical)
Stat counters: overruns: 197, underruns: 146, contentions: 88

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 3
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 76; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 82; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Stat counters: overruns: 240, underruns: 245, contentions: 44

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 4
--------------------------------------------------------------------------------------
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 82; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 84; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Stat counters: overruns: 238, underruns: 346, contentions: 47

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 5
--------------------------------------------------------------------------------------
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 86; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 82; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Stat counters: overruns: 178, underruns: 446, contentions: 107

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 6
--------------------------------------------------------------------------------------
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 83; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 86; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 99; Consumed a few empty frames (not critical)
Stat counters: overruns: 179, underruns: 546, contentions: 106

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 7
--------------------------------------------------------------------------------------
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 81; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Stat counters: overruns: 268, underruns: 644, contentions: 15

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 8
--------------------------------------------------------------------------------------
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 85; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 82; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Stat counters: overruns: 213, underruns: 745, contentions: 71

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 9
--------------------------------------------------------------------------------------
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Stat counters: overruns: 199, underruns: 846, contentions: 86

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnGet) - nProducers = 5; nConsumers = 10
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 80; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 100; Consumed a few empty frames (not critical)
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 100; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [9]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Stat counters: overruns: 231, underruns: 946, contentions: 54

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 1
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 46; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 46, contentions: 0

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 2
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 72; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 74; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 121, contentions: 25

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 3
--------------------------------------------------------------------------------------
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 82; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 72; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 148, contentions: 98

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 4
--------------------------------------------------------------------------------------
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 86; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 86; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 159, contentions: 187

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 5
--------------------------------------------------------------------------------------
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 83; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 275, contentions: 171

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 6
--------------------------------------------------------------------------------------
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 84; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 393, contentions: 153

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 7
--------------------------------------------------------------------------------------
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 84; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 99; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 485, contentions: 161

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 8
--------------------------------------------------------------------------------------
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 80; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 99; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 579, contentions: 167

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 9
--------------------------------------------------------------------------------------
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 100; Consumed a few empty frames (not critical)
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 100; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 77; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 100; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 100; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 78; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 648, contentions: 198

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 1; nConsumers = 10
--------------------------------------------------------------------------------------
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [9]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 99; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Stat counters: overruns: 13, underruns: 758, contentions: 188

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 1
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 46; Consumed a few empty frames (not critical)
Stat counters: overruns: 81, underruns: 46, contentions: 0

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 2
--------------------------------------------------------------------------------------
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 73; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 73; Consumed a few empty frames (not critical)
Stat counters: overruns: 81, underruns: 114, contentions: 32

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 3
--------------------------------------------------------------------------------------
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 86; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 74; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 86; Consumed a few empty frames (not critical)
Stat counters: overruns: 81, underruns: 169, contentions: 77

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 4
--------------------------------------------------------------------------------------
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 85; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 81; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Stat counters: overruns: 81, underruns: 169, contentions: 177

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 5
--------------------------------------------------------------------------------------
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 80; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 99; Consumed a few empty frames (not critical)
Stat counters: overruns: 81, underruns: 277, contentions: 169

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 6
--------------------------------------------------------------------------------------
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 84; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Stat counters: overruns: 81, underruns: 354, contentions: 192

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 7
--------------------------------------------------------------------------------------
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 82; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Stat counters: overruns: 81, underruns: 454, contentions: 192

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 8
--------------------------------------------------------------------------------------
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Stat counters: overruns: 81, underruns: 517, contentions: 229

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 9
--------------------------------------------------------------------------------------
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 84; Consumed a few empty frames (not critical)
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 100; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 100; Consumed a few empty frames (not critical)
Stat counters: overruns: 81, underruns: 675, contentions: 171

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 2; nConsumers = 10
--------------------------------------------------------------------------------------
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 100; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [9]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 100; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Stat counters: overruns: 81, underruns: 802, contentions: 144

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 1
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 46; Consumed a few empty frames (not critical)
Stat counters: overruns: 149, underruns: 46, contentions: 0

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 2
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 80; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 66; Consumed a few empty frames (not critical)
Stat counters: overruns: 149, underruns: 76, contentions: 70

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 3
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 73; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 77; Consumed a few empty frames (not critical)
Stat counters: overruns: 149, underruns: 167, contentions: 79

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 4
--------------------------------------------------------------------------------------
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 83; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 86; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 80; Consumed a few empty frames (not critical)
Stat counters: overruns: 149, underruns: 247, contentions: 99

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 5
--------------------------------------------------------------------------------------
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 85; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Stat counters: overruns: 148, underruns: 268, contentions: 177

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 6
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 77; Consumed a few empty frames (not critical)
Stat counters: overruns: 149, underruns: 437, contentions: 109

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 7
--------------------------------------------------------------------------------------
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 86; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Stat counters: overruns: 149, underruns: 470, contentions: 176

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 8
--------------------------------------------------------------------------------------
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 99; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Stat counters: overruns: 148, underruns: 594, contentions: 151

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 9
--------------------------------------------------------------------------------------
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 99; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Stat counters: overruns: 149, underruns: 663, contentions: 183

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 3; nConsumers = 10
--------------------------------------------------------------------------------------
Consumer [9]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 100; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 99; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 84; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 100; Consumed a few empty frames (not critical)
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Stat counters: overruns: 148, underruns: 812, contentions: 133

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 1
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 46; Consumed a few empty frames (not critical)
Stat counters: overruns: 217, underruns: 46, contentions: 0

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 2
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 70; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 76; Consumed a few empty frames (not critical)
Stat counters: overruns: 217, underruns: 95, contentions: 51

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 3
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 80; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 77; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Stat counters: overruns: 217, underruns: 155, contentions: 91

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 4
--------------------------------------------------------------------------------------
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 86; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 85; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 84; Consumed a few empty frames (not critical)
Stat counters: overruns: 217, underruns: 181, contentions: 165

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 5
--------------------------------------------------------------------------------------
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 81; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Stat counters: overruns: 217, underruns: 294, contentions: 152

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 6
--------------------------------------------------------------------------------------
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 78; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 99; Consumed a few empty frames (not critical)
Stat counters: overruns: 216, underruns: 334, contentions: 211

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 7
--------------------------------------------------------------------------------------
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 85; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 82; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 99; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Stat counters: overruns: 217, underruns: 482, contentions: 164

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 8
--------------------------------------------------------------------------------------
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 100; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Stat counters: overruns: 216, underruns: 579, contentions: 166

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 9
--------------------------------------------------------------------------------------
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Stat counters: overruns: 217, underruns: 664, contentions: 182

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 4; nConsumers = 10
--------------------------------------------------------------------------------------
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 99; Consumed a few empty frames (not critical)
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [9]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Stat counters: overruns: 217, underruns: 731, contentions: 215

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 1
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 46; Consumed a few empty frames (not critical)
Stat counters: overruns: 285, underruns: 45, contentions: 1

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 2
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 72; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 74; Consumed a few empty frames (not critical)
Stat counters: overruns: 285, underruns: 126, contentions: 20

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 3
--------------------------------------------------------------------------------------
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 65; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Stat counters: overruns: 285, underruns: 156, contentions: 90

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 4
--------------------------------------------------------------------------------------
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 82; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Stat counters: overruns: 285, underruns: 152, contentions: 194

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 5
--------------------------------------------------------------------------------------
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 86; Consumed a few empty frames (not critical)
Stat counters: overruns: 285, underruns: 275, contentions: 171

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 6
--------------------------------------------------------------------------------------
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 90; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Stat counters: overruns: 285, underruns: 382, contentions: 164

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 7
--------------------------------------------------------------------------------------
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 86; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Stat counters: overruns: 285, underruns: 455, contentions: 191

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 8
--------------------------------------------------------------------------------------
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 94; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Stat counters: overruns: 285, underruns: 555, contentions: 191

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 9
--------------------------------------------------------------------------------------
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 93; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 97; Consumed a few empty frames (not critical)
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 91; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 87; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 100; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 89; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Stat counters: overruns: 285, underruns: 692, contentions: 154

--------------------------------------------------------------------------------------
TAtomicBuffer (ECopyPolicy.kCopyOnSet) - nProducers = 5; nConsumers = 10
--------------------------------------------------------------------------------------
Consumer [8]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [0]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [3]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 88; Consumed a few empty frames (not critical)
Consumer [2]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 84; Consumed a few empty frames (not critical)
Consumer [1]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 92; Consumed a few empty frames (not critical)
Consumer [5]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Consumer [4]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 100; Consumed a few empty frames (not critical)
Consumer [6]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 96; Consumed a few empty frames (not critical)
Consumer [7]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 95; Consumed a few empty frames (not critical)
Consumer [9]: Finished (ok           ): nCorruptions: 0; nEmptyFrames: 98; Consumed a few empty frames (not critical)
Stat counters: overruns: 283, underruns: 760, contentions: 184
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
                     * sample[i-1] must be 1. If the tested buffer isn't 
                     * thread safe then with some likelihood there will be 
                     * another thread corrupting the order and the 
                     * aforementioned difference would be disturbed.
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
            
            logMessage = preamble + "nCorruptions: " + fNCorruptions + "; nEmptyFrames: " + fNEmptyFrames + postAmble;
            
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
        int                     i;
        Thread[]                prWorkers;
        Thread[]                cnWorkers;
        TAtomicBuffer           ab;
        int                     nRunningProducers;
        int                     nRunningConsumers;
        boolean                 doWait;
        TAtomicBuffer_Stats    stats;
        TRecord                 stRec;

        System.out.println ();
        System.out.println ("--------------------------------------------------------------------------------------");
        System.out.println (comment);
        System.out.println ("--------------------------------------------------------------------------------------");
        prWorkers   = new Thread [nProducers];
        cnWorkers   = new Thread [nConsumers];
        ab          = new TAtomicBuffer (cPol, EIfInvalidPolicy.kReturnEmpty);
        stats       = ab.StatsGet ();
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
        
        stRec = stats.GetRecord ();
        System.out.println 
        (
            "Stat counters: overruns: "     + stRec.fNumOverruns + 
            ", underruns: "                 + stRec.fNumUnderruns +
            ", contentions: "               + stRec.fNumContentions
        );
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