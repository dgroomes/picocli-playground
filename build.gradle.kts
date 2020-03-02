/**
 * This Gradle build script uses the Kotlin form.
 *
 * The official documentation goes a long way in explaining the differences between a traditional Groovy Gradle build
 * script (build.gradle) and the Kotlin form (build.gradle.kts) https://docs.gradle.org/current/userguide/kotlin_dsl.html
 */

plugins {
    java
    application
}

val slf4jVersion = "1.7.26"
val junitPlatformVersion = "1.5.2"
val junitJupiterVersion = "5.5.1"
val picocliVersion = "4.2.0"
val jacksonVersion = "2.10.1"

repositories {
    mavenLocal()
    jcenter()
}

configurations.register("junitLauncher")

dependencies {
    "junitLauncher"("org.junit.platform:junit-platform-console-standalone:$junitPlatformVersion")

//    implementation("info.picocli:picocli:$picocliVersion")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")

    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")

    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
}

application {
    // Define the main class for the application.
    mainClassName = "App"
}

java {

    /**
     * This is how to configure the Java compilation task to use Java 14.
     * In this case, we are using Gradle 6, which supports up to Java 13. So, JavaVersion.VERSION_HIGHER resolves to the
     * "next version of Java after 13" which is 14. See https://github.com/gradle/gradle/blob/2af1a363f3d3b8d69f5bb1f07dde828b635edfe6/subprojects/base-services/src/main/java/org/gradle/api/JavaVersion.java#L58
     * There is no way to configure Java 15 or beyond until Gradle supports Java 14. And so on for future versions of
     * Java and Gradle.
     */
    sourceCompatibility = JavaVersion.VERSION_HIGHER
}

val java14Home = System.getenv("JAVA_14_HOME")
if (java14Home == null || java14Home.isBlank()) {
    throw IllegalArgumentException("JAVA_14_HOME environment variable must be set to the path of JDK14 but was not set")
}

val java14HomeFile = File(java14Home)
if (!java14HomeFile.exists()) {
    throw IllegalArgumentException("JAVA_14_HOME environment variable must be set to the path of JDK14 but was not a real file: $java14Home")
}

/**
 * Configure the compiler step to accommodate:
 * - Java 14 by forking a javac process using JDK 14 instead of the Java that is running the Gradle process (Gradle does not support 14)
 * - Java 14 preview features
 */
tasks {
    withType(JavaCompile::class.java) {
        options.isFork = true
        options.forkOptions.javaHome = java14HomeFile
        options.compilerArgs.addAll(arrayOf("--enable-preview"))
    }
}

/**
 * Print the path to the standalone JUnit Console Launcher JAR so it can be used later by "test.sh" to execute the JUnit
 * tests.
 *
 * Editorial: It's great to leverage Gradle for managing dependencies and setting up environment information for a
 * downstream (non-Gradle) process like "test.sh".
 */
tasks.register("printJunitLauncherPath") {
    doLast {
        configurations {
            "junitLauncher" {
                // "resolve" the configuration and do a "first()" because the "junitLauncher" configuration has only the
                // one dependency: "junit-platform-console-standalone.jar"
                val pathToJunitLauncherJar = resolve().first().toString()
                val pathFile = File(buildDir, "junit-launcher-path.txt")
                pathFile.writeText(pathToJunitLauncherJar)
            }
        }
    }
}

/**
 * Support the standalone JUnit Console Launcher by printing the test class path to a file. This task is used in
 * conjunction with 'printJunitLauncherPath'.
 */
tasks.register("printTestClassPath") {
    doLast {
        val sourceSetContainer = project.the<SourceSetContainer>()
        val classpathObj = sourceSetContainer["test"].runtimeClasspath
        val classpath = classpathObj.joinToString(separator = ":")
        val pathFile = File(buildDir, "test-classpath.txt")
        pathFile.writeText(classpath)
    }
}