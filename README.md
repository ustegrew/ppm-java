A prototype of the [PPM](https://github.com/ustegrew/PPM) project. I find it easier to use Java for prototyping. I intend to port to C++/QT later.
 
2016-12-12: Redesign of streaming API needed. Concept so far - every processing 
step is designed as a separate module. Unfortunately, this will lead to a lot of 
unnecessary data copying (nSamplesPerFrame * nStepsProcessing). The scope of this
project isn't a new audio streaming language (there are already other brilliant 
implementations), but a simple PPM meter. Redesign needs simplification:

*   We keep the atomic buffer, as we need separation between the time critical
    JackD (system) audio thread and the less time critical GUI thread. 
*   All PPM related processing (calculations and GUI) will now be done in 
    one single module (and running in a single thread).
*   We'll keep the timer module to provide the clock for the GUI thread.


 