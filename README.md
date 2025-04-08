# Java Cookbook

This repository contains a collection of Java projects that exemplify common operations and design patterns in Java. It serves as a quick reference guide with practical examples (recipes) of how to implement frequently used libraries and design patterns in **Java 21+**.

## Recipes

*(Check the boxes below as you complete the implementation for each recipe)*

### Design Patterns

- [X] [The Strategy Pattern](strategy-pattern/README.md)
- [ ] [The Factory Pattern (Factory Method)](factory-pattern/README.md)
- [X] [The Builder Pattern](builder-pattern/README.md)
- [ ] [The Observable Pattern](observable-pattern/README.md)
- [ ] [The Singleton Pattern](singleton-pattern/README.md)

### Beginner Topics

- [ ] [Generics in Java](generics/README.md)
- [ ] [Reading and writing JSON](json-handling/README.md)
- [ ] [Reading and writing YAML](yaml-handling/README.md)
- [ ] [Handling HTTP Requests](http-requests/README.md)
- [ ] [Records](records/README.md)
- [ ] [Error Handling and Logging](error-logging/README.md)
- [ ] [Try-with-resources](try-with-resources/README.md)
- [ ] [Annotations](annotations/README.md)

### Intermediate Topics

- [ ] [JDBC with Postgres](jdbc-postgres/README.md)
- [ ] [MongoDB Java Driver](mongodb-driver/README.md)
- [ ] [Redis Java Clients (Lettuce/Jedis)](redis-clients/README.md)
- [ ] [Generics Deep Dive](generics-deep-dive/README.md)
- [ ] [Functional Programming (Streams, Lambdas, Optional)](functional-programming/README.md)
- [ ] [Packaging and Distribution](packaging-distribution/README.md)
- [ ] [API Development (REST)](api-development/README.md)
- [ ] [Concurrent Processing (Executors, Threads)](concurrent-processing/README.md)
- [ ] [Property-Based Testing (jqwik)](property-based-testing/README.md)
- [ ] [Event-Driven Programming (Kafka, etc.)](event-driven-programming/README.md)

### Advanced Topics

- [ ] [Asynchronous Programming (CompletableFuture)](async-programming/README.md)
- [ ] [Metaprogramming (Reflection, Annotation Processing)](metaprogramming/README.md)
- [ ] [Performance Optimization (Profiling, JMH)](performance-optimization/README.md)
- [ ] [Memory Management (GC, Heap/Stack)](memory-management/README.md)
- [ ] [JNI/JNA (Native Interface/Access)](jni-jna/README.md)

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