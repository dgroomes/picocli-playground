plugins {
    id("common")
    application
}

application {
    mainClass.set("dgroomes.Main")
}

dependencies {
    implementation("org.slf4j:slf4j-api")
    implementation("org.slf4j:slf4j-simple")
    implementation("info.picocli:picocli")
    annotationProcessor("info.picocli:picocli-codegen")
    implementation(project(":gradle-detector"))
}

tasks {
    named<CreateStartScripts>("startScripts") {
        defaultJvmOpts = listOf("--enable-preview")
    }

    named<JavaExec>("run") {
        jvmArgs = listOf("--enable-preview")
    }
}
