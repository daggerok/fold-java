plugins {
  id("org.jetbrains.kotlin.jvm").version("1.3.21")
  id("io.franzbecker.gradle-lombok").version("2.1")
  application
}

repositories {
  jcenter()
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  testImplementation("org.jetbrains.kotlin:kotlin-test")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
  // lombok for java classes... yes, I'm lazy:
  annotationProcessor(platform("org.projectlombok:lombok:1.18.6"))
  annotationProcessor("org.projectlombok:lombok")
  testAnnotationProcessor("org.projectlombok:lombok")
  // spekframework
  val spekVersion: String by lazy { "2.0.0" }
  testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")  {
    exclude(group = "org.jetbrains.kotlin")
  }
  testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion") {
    exclude(group = "org.jetbrains.kotlin")
    exclude(group = "org.junit.platform")
  }
  // spek requires kotlin-reflect, can be omitted if already in the classpath
  testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect")
}

application {
  mainClassName = "com.github.daggerok.AppKt"
}

// bash build/install/fold-java/bin/fold-java
defaultTasks("clean", "build", "installDist")

// java files location in src/main/kotlin directory workaround
sourceSets {
  main {
    java.srcDir("src/main/kotlin")
  }
}

// gradle wrapper configuration workaround
tasks {
  "wrapper"(Wrapper::class) {
    gradleVersion = "5.2.1"
  }
}

// gradle tests output stdOut workaround
tasks {
  test {
    testLogging {
      showExceptions = true
      showStandardStreams = true
      //showCauses = true
      //showStackTraces = true
      //logger.quiet("$events")
      useJUnitPlatform {
        includeEngines.add("spek2")
      }
      useJUnit()
    }
  }
}

/*tasks.withType<Test> {
  addTestOutputListener { testDescriptor: TestDescriptor, outputEvent: TestOutputEvent ->
    logger.quiet("${testDescriptor.parent}\n\t${testDescriptor.name}")
    logger.quiet("${outputEvent.destination}")
    logger.quiet("${outputEvent.message}")
  }
}*/

lombok.version = "1.18.6"

// you don't need this lombok stuff...
/*tasks {
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
}*/
