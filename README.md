# picocli-playground

NOT IMPLEMENTED Learning and exploring Picocli <https://picocli.info/>

## Gradle + Java 14 

This project uses Java 14 and the new `Records` language feature <https://openjdk.java.net/jeps/359>. Gradle itself 
cannot run on Java 14. Fortunately, Gradle does support compiling Java 14 code because it can fork a process using a
different JDK to execute the compile task. Similarly, Gradle can be configured to use alternative JDKs like Java 14 to
execute some "execution" tasks. Read the `build.gradle.kts` file with care to see how the build is configured to use
Java 14 to compile and execute the program.

Unfortunately, I can't figure out how to configure Gradle to sucessfully execute the "test" task using Java 14. Instead,
I am relying on the Standalone JUnit Console Launcher to work around this limitation. Read more at [Instructions](#instructions).  

## Instructions

1. Set the environment variable `JAVA_14_HOME` to the path of a JDK 14 installation on your computer
1. Build the program and run the tests with `./test.sh`
    * This uses the Standalone JUnit Console Launcher <https://junit.org/junit5/docs/current/user-guide/#running-tests-console-launcher>
      and Java 14
1. Build and run the program with `./gradlew run`