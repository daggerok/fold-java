plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.21")
    id("io.franzbecker.gradle-lombok").version("2.1")
    application
    java
}

repositories {
    jcenter()
}

dependencies {
    annotationProcessor(platform("org.projectlombok:lombok:1.18.6"))
    annotationProcessor("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
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

lombok.version = "1.18.6"

tasks {
    val delombok by registering(io.franzbecker.gradle.lombok.task.DelombokTask::class)

    delombok {
        dependsOn(compileJava)
        val outputDir by extra { file("$buildDir/delombok") }
        outputs.dir(outputDir)
        sourceSets.getByName("main").java.srcDirs.forEach {
            inputs.dir(it)
            args(it, "-d", outputDir)
        }
        doFirst {
            outputDir.delete()
        }
    }

    javadoc {
        dependsOn(delombok)
        val outputDir: File by delombok.get().extra
        source = fileTree(outputDir)
        isFailOnError = false
    }
}
