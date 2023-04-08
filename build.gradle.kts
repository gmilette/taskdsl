import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    application
}

group = "root"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

//dependencies {
//    implementation(project(":network"))
//    implementation(project(":util"))
//}

dependencies {
    testImplementation(kotlin("test"))
    implementation("co.touchlab:kermit:1.1.3")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.5.0")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.17")
    implementation("io.ktor:ktor-server-html-builder:2.2.2")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
    kotlinOptions.freeCompilerArgs += "-Xcontext-receivers"
}

application {
    mainClass.set("MainKt")
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}