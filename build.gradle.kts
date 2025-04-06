import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.* // Import extensions
import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstyleExtension

plugins {
    id("com.diffplug.spotless") version "6.25.0" apply false
}

allprojects {
    group = "com.example.javacookbook" // Replace with your desired group ID
    version = "1.0-SNAPSHOT"
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "com.diffplug.spotless")
    apply(plugin = "checkstyle")

    // Configure the Java plugin extension
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
            googleJavaFormat().aosp().reflowLongStrings() // google-java-format with AOSP style
            formatAnnotations()
            licenseHeaderFile(rootProject.file("config/spotless/copyright.txt")) // Specify license header
        }
    }

    // Configure Checkstyle
    configure<CheckstyleExtension> {
        toolVersion = "10.16.0" // Use a recent Checkstyle version
        configFile = rootProject.file("config/checkstyle/google_checks.xml")
        configProperties["checkstyle.cache.file"] = rootProject.layout.buildDirectory.file("checkstyle/checkstyle.cache").get().asFile
        isShowViolations = true
        isIgnoreFailures = false // Fail build on violations
    }

    // Make checkstyle task run as part of the 'check' lifecycle task
    tasks.named("check") {
        dependsOn(tasks.named("checkstyleMain", Checkstyle::class), tasks.named("checkstyleTest", Checkstyle::class))
    }
} 