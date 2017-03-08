#!/bin/bash
root=/home/peter/git/ppm_java/doc/javadoc
rm -frv $root/html
doxygen $root/Doxyfile
