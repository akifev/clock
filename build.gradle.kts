plugins {
    java
    kotlin("jvm") version "1.4.21"
}

group = "daniil.akifev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testCompile("junit", "junit", "4.12")
}
