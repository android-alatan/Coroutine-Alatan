import gradle.kotlin.dsl.accessors._fd51a528d94160d6b8d93e504c81f9ca.testImplementation
import gradle.kotlin.dsl.accessors._fd51a528d94160d6b8d93e504c81f9ca.testRuntimeOnly
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

plugins {
    kotlin("jvm")
    id("libs-detekt")
    id("lib-tasks")
}

val versionCatalog = project.extensions.getByType<VersionCatalogsExtension>()
val libs = versionCatalog.named("libs")

dependencies {
    testImplementation(libs.findLibrary("test.junit5").get())
    testImplementation(libs.findLibrary("test.mockito").get())
    testImplementation(libs.findLibrary("test.mockitoKotlin").get())
    testRuntimeOnly(libs.findLibrary("test.jupiterEngine").get())
    testRuntimeOnly(libs.findLibrary("test.jupiterVintage").get())
}