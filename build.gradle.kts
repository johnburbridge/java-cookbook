import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.* // Import extensions
import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstyleExtension

plugins {
    id("com.diffplug.spotless") version "6.25.0" apply false
    java
    id("org.springframework.boot") version "3.4.4" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    kotlin("jvm") version "2.0.0-RC3" apply false
}

allprojects {
    group = "com.github.johnburbridge.javacookbook"
    version = "1.0-SNAPSHOT"
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "com.diffplug.spotless")
    apply(plugin = "checkstyle")

    the<JavaPluginExtension>().toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        // Common test dependencies
        "testImplementation"(platform("org.junit:junit-bom:5.10.2"))
        "testImplementation"("org.junit.jupiter:junit-jupiter")
        "testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
        "testImplementation"("org.assertj:assertj-core:3.24.2")

        // Optional: Add SLF4j for logging abstraction if desired across modules
        // "implementation"("org.slf4j:slf4j-api:2.0.13")
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        // options.compilerArgs.add("--enable-preview") // Uncomment if using preview features
    }

    // Configure Spotless (google-java-format)
    configure<SpotlessExtension> {
        java {
            importOrder() // Defaults to standard Java order
            removeUnusedImports()
            googleJavaFormat("1.18.1").aosp().reflowLongStrings() // google-java-format with AOSP style
            formatAnnotations()
            licenseHeaderFile(rootProject.file("config/spotless/copyright.txt")) // Specify license header
        }
    }
    // Configure Checkstyle
    configure<CheckstyleExtension> {
        toolVersion = "10.16.0"

        // Simple approach: use default config file location
        configDirectory.set(rootProject.file("config/checkstyle"))

        isShowViolations = true
        isIgnoreFailures = false
    }

    tasks.withType<Checkstyle>().configureEach {
        configFile = rootProject.file("config/checkstyle/checkstyle.xml")
    }

    // Make checkstyle task run as part of the 'check' lifecycle task
    tasks.named("check") {
        dependsOn(
            tasks.named("checkstyleMain", Checkstyle::class),
            tasks.named("checkstyleTest", Checkstyle::class),
            tasks.named("spotlessApply")
        )
    }
}