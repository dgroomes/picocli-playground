# picocli-playground

NOT IMPLEMENTED Learning and exploring Picocli <https://picocli.info/>

## Description

This project uses Java 14 and the new `Records` language feature <https://openjdk.java.net/jeps/359>. Gradle itself 
cannot run on Java 14. It cannot use Java 14 for execution tasks like `test`. Fortunately, Gradle does support compiling
Java 14 code because it can fork a process using a different JDK to execute the compile task.

## Instructions

1. Set the environment variable `JAVA_14_HOME` to the path of a JDK 14 installation on your computer
1. Build the program run the tests with `./test.sh`
    * This uses the Standalone JUnit Console Launcher <https://junit.org/junit5/docs/current/user-guide/#running-tests-console-launcher>
      and Java 14
1. Build and run the program with `./gradlew installDist && JAVA_HOME="$JAVA_14_HOME" ./build/install/picocli-playground/bin/picocli-playground`