
plugins {
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.1.3"
    kotlin("plugin.serialization") version "2.1.10"
}

application {
    mainClass.set("com.example.bvgserver.MCPServerKt")
}

group = "com.example"
version = "0.0.1"


repositories {
    mavenCentral()
}

dependencies {
    implementation("io.modelcontextprotocol:kotlin-sdk:0.3.0")
    implementation("io.ktor:ktor-client-core:3.1.3")
    implementation("io.ktor:ktor-client-cio:3.1.3")
    implementation("io.ktor:ktor-client-content-negotiation:3.1.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.3")
    implementation("org.slf4j:slf4j-nop:2.0.9")

    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-client-mock:3.1.3")

}
