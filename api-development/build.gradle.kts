// This module inherits configuration from the root build.gradle.kts
// Add module-specific dependencies or configurations here if needed.
apply(plugin="java")
apply(plugin = "org.springframework.boot")
apply(plugin = "io.spring.dependency-management")

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
