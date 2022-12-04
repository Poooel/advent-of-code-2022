import com.diffplug.gradle.spotless.SpotlessExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
    id("com.diffplug.spotless") version "6.12.0"
}

group = "com.github"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.ajalt.clikt:clikt:3.5.0")
    implementation("com.github.ajalt.mordant:mordant:2.0.0-beta9")
    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation("org.slf4j:slf4j-nop:2.0.5")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

application {
    mainClass.set("com.github.advent.of.code.AppKt")
}

configure<SpotlessExtension> {
    format("misc") {
        target("*.md", ".gitignore")

        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
    kotlin {
        ktlint()
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}
