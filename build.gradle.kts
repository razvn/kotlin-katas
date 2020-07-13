plugins {
    java
    kotlin("jvm") version "1.3.72"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

val kotestVersion by extra { "4.1.1" }
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion") // for kotest framework
    testImplementation("io.kotest:kotest-runner-console-jvm:$kotestVersion") // for kotest framework
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion") // for kotest core jvm assertions
    testImplementation("io.kotest:kotest-property-jvm:$kotestVersion") // for kotest property test
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
