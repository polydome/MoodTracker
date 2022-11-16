plugins {
    kotlin("jvm") version "1.7.20"
    id("org.jetbrains.compose") version "1.2.1"
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(project(":domain"))
    implementation(project(":remote-backup"))
    implementation(project(":local-backup"))
    implementation(project(":data"))
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}