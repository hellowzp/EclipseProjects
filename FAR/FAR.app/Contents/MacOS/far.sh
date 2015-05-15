#!/bin/sh
# Running FAR on Mac OSX
##
BASEDIR=`dirname $0`
cd $BASEDIR
cd ../Resources
java -Dapple.laf.useScreenMenuBar=true -Xdock:name="FAR - Find And Replace" -Xdock:icon=far.icns -Dcom.apple.mrj.application.live-resize=true -cp FAR-1.8.jar:lib/commons-logging-1.1.jar:lib/log4j-1.2.7.jar:lib/j6plugin-0.2.jar:conf net.pandoragames.far.ui.swing.FindAndReplace $*
