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
val junitPlatformVersion = "1.6.0"
val junitJupiterVersion = "5.6.0"
val picocliVersion = "4.2.0"
val jacksonVersion = "2.10.1"

repositories {
    mavenCentral()
}

configurations.register("junitLauncher")

dependencies {
    "junitLauncher"("org.junit.platform:junit-platform-console-standalone:$junitPlatformVersion")

    implementation("info.picocli:picocli:$picocliVersion")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")

    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")

    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
}

application {
    // Define the main class for the application.
    mainClassName = "dgroomes.Main"
}

java {
    sourceCompatibility = JavaVersion.VERSION_15
}

tasks {
    /**
     * Configure the compiler step to accommodate Java preview features
     */
    withType(JavaCompile::class.java) {
        options.compilerArgs.addAll(arrayOf("--enable-preview"))
    }

    named<CreateStartScripts>("startScripts") {
        defaultJvmOpts = listOf("--enable-preview")
    }

    named<JavaExec>("run") {
        jvmArgs = listOf("--enable-preview")
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
