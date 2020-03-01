# picocli-playground

NOT IMPLEMENTED Learning and exploring Picocli <https://picocli.info/>

PROBLEM. Why doesn't the JUnit Standalone Console Launcher find the test? I am mimicking <https://github.com/dgroomes/junit-playground>
closely but can't find the difference that matters. 

## Description

This project uses Java 14 and the new `Records` language feature <https://openjdk.java.net/jeps/359>. Gradle itself 
cannot run on Java 14. It cannot use Java 14 for execution tasks like `test`. Fortunately, Gradle does support compiling
Java 14 code because it can fork a process using a different JDK to execute the compile task.

## Requirements

* Java 11
* Java 14

## Instructions

1. Set the environment variable `JAVA_14_HOME` to the path of a JDK 14 installation on your computer
1. Run the tests with `./test.sh` (this will also build the project if not built already)
    * This uses the Standalone JUnit Console Launcher <https://junit.org/junit5/docs/current/user-guide/#running-tests-console-launcher>
      and Java 14