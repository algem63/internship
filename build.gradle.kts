plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val allureVersion = "2.29.0"
val cucumberVersion = "7.20.1"
val aspectJVersion = "1.9.22"


val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.platform:junit-platform-suite")

    testImplementation("io.rest-assured:rest-assured:6.0.1")
    implementation("io.rest-assured:json-schema-validator:6.0.1")

    implementation("org.postgresql:postgresql:42.7.13")

    testImplementation("org.testcontainers:postgresql:1.21.4")
    testImplementation("org.testcontainers:redpanda:1.21.4")
    testImplementation("org.testcontainers:junit-jupiter:1.21.4")

    implementation("ch.qos.logback:logback-classic:1.6.3")

    implementation("tools.jackson.core:jackson-core:3.2.2")
    implementation("tools.jackson.core:jackson-databind:3.2.2")
    implementation("tools.jackson.module:jackson-module-jaxb-annotations:3.2.2")
    implementation("tools.jackson.dataformat:jackson-dataformat-xml:3.2.2")

    implementation("org.seleniumhq.selenium:selenium-java:4.49.0")
    implementation("com.codeborne:selenide:7.18.1")

    implementation("org.aeonbits.owner:owner:1.0.12")

    agent("org.aspectj:aspectjweaver:${aspectJVersion}")

    implementation("org.apache.kafka:kafka-clients:4.3.1")

    testImplementation(platform("io.cucumber:cucumber-bom:$cucumberVersion"))
    testImplementation("io.cucumber:cucumber-java")
    testImplementation("io.cucumber:cucumber-junit-platform-engine")

    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-cucumber7-jvm")
    testImplementation("io.qameta.allure:allure-junit-platform")
}

tasks.test {
    jvmArgs = listOf(
        "-javaagent:${agent.singleFile}"
    )
    useJUnitPlatform()
}