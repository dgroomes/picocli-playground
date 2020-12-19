# picocli-playground

NOT YET IMPLEMENTED

📚 Learning and exploring Picocli <https://picocli.info/>

## Gradle + Java 15

This project uses Java 15 and the new `Records` language feature <https://openjdk.java.net/jeps/359>.

Unfortunately, I can't figure out how to configure Gradle to successfully execute the "test" task using Java 15. Instead,
I am relying on the Standalone JUnit Console Launcher to work around this limitation. Read more at [Instructions](#instructions).  

## Instructions

1. Set the environment variable `JAVA_15_HOME` to the path of a JDK 15 installation on your computer
1. Build the program and run the tests with `./test.sh`
    * This uses the Standalone JUnit Console Launcher <https://junit.org/junit5/docs/current/user-guide/#running-tests-console-launcher>
      and Java 15
1. Build and run the program with `./gradlew run`
