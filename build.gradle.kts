import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val developmentOnly: Configuration by configurations.creating
val micronautVersion: String by project
val kotlinVersion: String by project

plugins {
    val kotlinVersion = "1.3.50"
    application
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("com.palantir.docker") version "0.25.0"
}

repositories {
    mavenCentral()
    maven("https://jcenter.bintray.com")
    maven("https://www.jitpack.io")
}

configurations {
    // for dependencies that are needed for development only
    developmentOnly
}

docker {
    name = "hub.docker.com/wuvist/blogapi:1.0"
    copySpec.from("build/libs").into("build/libs")
}

dependencies {
    implementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    implementation(kotlin("stdlib:$kotlinVersion"))
    implementation(kotlin("reflect:$kotlinVersion"))
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-management")
    kapt(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    kapt("io.micronaut:micronaut-inject-java")
    kapt("io.micronaut:micronaut-validation")
    kaptTest(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    kaptTest("io.micronaut:micronaut-inject-java")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
    runtimeOnly("ch.qos.logback:logback-classic:1.2.3")
    testImplementation(platform("io.micronaut:micronaut-bom:$$micronautVersion"))
    testImplementation("io.micronaut.test:micronaut-test-kotlintest")
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
    testImplementation("com.github.Wuvist:easywebmock:master-SNAPSHOT")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.3.1")
}

//test.classpath(+= configurations.developmentOnly)
application {
    version = "0.1"
    group = "blogwind.com"
    mainClassName = "blogwind.com.Application"
}

allOpen {
    annotation("io.micronaut.aop.Around")
}

tasks {
    named<ShadowJar>("shadowJar") {
        mergeServiceFiles()
    }

    withType<Test>{
        useJUnitPlatform()
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
            javaParameters = true
        }
    }

    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
            javaParameters = true
        }
    }

    named<JavaExec>("run") {
        doFirst {
            classpath = classpath.plus(configurations["developmentOnly"])
            jvmArgs = listOf("-noverify", "-XX:TieredStopAtLevel=1", "-Dcom.sun.management.jmxremote")
        }
    }
}
