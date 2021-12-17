import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    java
}

val slf4jVersion = "1.7.32" // SLF4J releases: http://www.slf4j.org/news.html
val picocliVersion = "4.6.2" // picocli releases: https://github.com/remkop/picocli/releases
val junitVersion = "5.8.2" // JUnit releases: https://github.com/junit-team/junit5/releases
val assertJVersion = "3.21.0" // AssertJ releases: https://github.com/assertj/assertj-core/tags


repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    constraints {
        implementation("org.slf4j:slf4j-api:$slf4jVersion")
        implementation("org.slf4j:slf4j-simple:$slf4jVersion")
        implementation("info.picocli:picocli:$picocliVersion")
        annotationProcessor("info.picocli:picocli-codegen:$picocliVersion")
    }

    // Include the same testing dependencies in all projects. We can be more generous with test dependencies than
    // main runtime dependencies.
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
}

/**
 * Configure Java tasks to enable Java language "Preview Features"
 */
tasks {
    withType(JavaCompile::class.java) {
        options.compilerArgs.addAll(arrayOf("--enable-preview"))
    }

    test {
        jvmArgs = listOf("--enable-preview")
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
            events = setOf(TestLogEvent.PASSED, TestLogEvent.FAILED, TestLogEvent.SKIPPED)
            exceptionFormat = TestExceptionFormat.FULL
        }
    }
}
