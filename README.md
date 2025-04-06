# Java Cookbook

This repository contains a collection of Java projects that exemplify common operations and design patterns in Java. It serves as a quick reference guide with practical examples (recipes) of how to implement frequently used libraries and design patterns in **Java 21+**.

## Recipes

*   **Strategy Pattern** (`strategy-pattern`)

*(More recipes to be added)*

### Design Patterns (Planned)

*   The Factory Pattern
*   The Builder Pattern
*   The Observable Pattern

### Beginner Topics

*   Generics in Java
*   Reading and writing JSON (e.g., using Jackson or Gson)
*   Reading and writing YAML (e.g., using SnakeYAML)
*   Handling HTTP Requests (e.g., using `java.net.http.HttpClient`)
*   Records (Java 14+)
*   Error Handling and Logging (e.g., using SLF4j + Logback/Log4j2)
*   Try-with-resources (AutoCloseable)
*   Annotations

### Intermediate Topics

*   Reading and writing from/to Postgres (using JDBC or JPA/Hibernate)
*   Reading and writing from/to MongoDB (using MongoDB Java Driver)
*   Reading and writing from/to Redis (using Jedis or Lettuce)
*   Type System and Generics Deep Dive
*   Functional Programming (Streams API, Lambdas)
*   Packaging and Distribution (JARs, WARs, Build Tools like Maven/Gradle)
*   API Development with Spring Boot / Jakarta EE / Quarkus
*   Concurrent Processing (`java.util.concurrent`, Executors, Fork/Join)
*   Property-Based Testing (e.g., using jqwik)
*   Event-Driven Programming (e.g., using Kafka, RabbitMQ, JMS)

### Advanced Topics

*   Asynchronous Programming (CompletableFuture)
*   Metaprogramming (Reflection, Annotation Processing)
*   Performance Optimization (JVM Tuning, Profiling)
*   Memory Management (Garbage Collection)
*   JNI/JNA (Java Native Interface/Access)

## Project Structure

This project is structured as a multi-module **Gradle** project. Each recipe resides in its own module.

```
.
├── build.gradle.kts        # Root Gradle build script
├── settings.gradle.kts     # Gradle settings (modules)
├── gradle/                 # Gradle wrapper files
├── gradlew                 # Gradle wrapper script (Linux/macOS)
├── gradlew.bat             # Gradle wrapper script (Windows)
├── <recipe_name>/          # Directory for a specific recipe module
│   ├── build.gradle.kts    # Module-specific Gradle build script
│   ├── README.md           # Detailed notes about the recipe
│   └── src/
│       ├── main/
│       │   ├── java/       # Main Java source code
│       │   └── resources/  # Main resources (config files, etc.)
│       └── test/
│           ├── java/       # Test Java source code
│           └── resources/  # Test resources
└── ...                   # Other recipe modules
```

Where:

*   `build.gradle.kts` and `settings.gradle.kts` define the build configuration, dependencies, and project structure.
*   `README.md` within each recipe module contains detailed notes about the recipe, including implementation details and usage examples.
*   Source code follows standard Gradle directory layout.

## Development Setup

### Requirements

*   Java Development Kit (JDK) **21 or later**
*   **Gradle 8.13+** (The project uses the Gradle wrapper, which will download the correct version automatically)
*   An IDE (IntelliJ IDEA, Eclipse, VS Code with Java/Gradle extensions) is recommended.

### Getting Started

1.  Clone the repository.
2.  Import the project into your IDE as a **Gradle** project.
3.  The IDE should automatically recognize the `build.gradle.kts` files and download dependencies.
4.  Build the entire project using the Gradle wrapper: `./gradlew build` (Linux/macOS) or `gradlew.bat build` (Windows).

### Build Automation

Common tasks are handled by Gradle (using the wrapper):

*   Compile: `./gradlew classes`
*   Run tests: `./gradlew test`
*   Package (create JARs): `./gradlew jar`
*   Clean build outputs: `./gradlew clean`
*   Build everything: `./gradlew build`
*   Run a specific recipe's main class (if applicable): This usually requires configuring the `application` plugin or a custom `JavaExec` task in the recipe's `build.gradle.kts`.

### Code Quality

This project aims to use the following tools for code quality (configuration to be added):

*   **Formatting**: Spotless (e.g., using `google-java-format`)
*   **Linting/Static Analysis**: Checkstyle, PMD
*   **Testing Framework**: **JUnit 5** (already configured)
*   **Test Coverage**: JaCoCo

Configuration for these tools will be added to the root and/or module `build.gradle.kts` files.

#### Git Hooks (Optional)

Consider setting up Git hooks (e.g., using Husky or pre-commit) to automate checks before committing or pushing.

### Continuous Integration

The project can use GitHub Actions for CI:

*   Set up a workflow (`.github/workflows/ci.yml`) to build and test using `./gradlew build` on pushes/pull requests.
*   Run tests against required Java versions (e.g., JDK 21).
*   Enforce code formatting and static analysis checks.
*   Optionally, integrate with Codecov or similar for coverage reporting. 