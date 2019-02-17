import org.jetbrains.kotlin.utils.addToStdlib.firstNotNullResult

plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.21")
    application
    java
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    mainClassName = "com.github.daggerok.AppKt"
}

defaultTasks("clean", "build", "installDist")

sourceSets {
    main {
        java.srcDir("src/main/kotlin")
    }
}

tasks {
    "wrapper"(Wrapper::class) {
        gradleVersion = "5.2.1"
    }
}

tasks {
    test {
        testLogging {
            showExceptions = true
            showStandardStreams = true
        }
    }
}
