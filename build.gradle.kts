/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 */

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
}

allprojects {
    repositories {
        // Use jcenter for resolving dependencies.
        // You can declare any Maven/Ivy/file repository here.
        mavenCentral()
    }

    tasks.withType(JavaCompile::class).all {
        sourceCompatibility = "23"
        targetCompatibility = "23"
    }

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
        kotlinOptions.jvmTarget = "23"
    }

    tasks.withType(Test::class).configureEach {
        maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
    }

}
