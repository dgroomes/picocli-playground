plugins {
    java
    application
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

val slf4jVersion = "1.7.32" // SLF4J releases: http://www.slf4j.org/news.html
val junitVersion = "5.8.2" // JUnit releases: https://github.com/junit-team/junit5/releases
val assertJVersion = "3.21.0" // AssertJ releases: https://github.com/assertj/assertj-core/tags
val picocliVersion = "4.6.2" // picocli releases: https://github.com/remkop/picocli/releases

repositories {
    mavenCentral()
}

dependencies {
    implementation("info.picocli:picocli:$picocliVersion")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
}

application {
    mainClass.set("dgroomes.Main")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
