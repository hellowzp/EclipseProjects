FAR - Find And Replace 1.8 (01-07-2014)

A GUI for find and replace operations across multiple files in entire directory trees. 
With or without regular expressions. Special support for expressions spanning multiple lines.


BUILDING FAR FROM SOURCES

System Requirements:
You need Java 1.6 or later to compile the sources, Java 1.5 or later to run FAR. 


Library Depencies:
-- commons-logging-1.1.jar
-- j6plugin-0.1.1.jar
-- log4j-1.2.7.jar (or equivalent logging implementation for commons-logging)
-- junit-3.8.1.jar (optional)
These libraries are not part of the source distribution (but included in the binary distribution).

To create windows binaries with Ant, you will furthermore want to get launch4j from
http://launch4j.sourceforge.net. See the Ant integration instructions below.


Unpacking the Sources:
Extract the tarball into a directory of you choice. It will create a subdirectory "far" that
contains the sources. You can extract source and binary version into the same directory but
you are not encouraged to do so.

You will get a Maven style source tree:

far/
    build.xml
    doc/
    licenses/
    src/
        main/
             java/
             resources/
             licenses/
             script/
             txt/
        test/
             java/
             testfiles/


IDE integration:
To compile the sources, you must put the libraries and everything from src/main/java
and src/main/resources into the classpath. To compile and run the tests, add JUnit and the 
two test source directories src/test/java and src/test/testfiles to the classpath.


Running Ant
To run the Ant build script, you may need to change the library pathes in the first lines
of the build script. If you use a Maven style repository, just set the "repository.dir" 
property to the location of that directory. 
Otherwise set it to your "lib" directory and adapt the values of the "commons-logging.lib"
and the "log4j.lib" property (you will want to remove the directory path in front of
the jar file name).

To create windows binaries with Ant (target win-bin), get launch4j from 
http://launch4j.sourceforge.net, unpack it and set the Ant property "launch4j.dir" accordingly.

Note that the default target "release" creates all binary variants plus the source release.
You will most likely rather want to call one of the other targets: win-bin, x-bin or mac-bin.

There is a subtle difference between the build file from SVN and the build file from the 
tarball: The SVN build file will ask you interactively to specify a version number, while
the tarball build file uses a fixed version.


http://findandreplace.sourceforge.net