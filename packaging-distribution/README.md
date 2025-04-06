# Java Packaging and Distribution Recipe

Once Java code is written and compiled, it needs to be packaged and potentially distributed for execution on other machines or deployment to servers.

## Key Concepts

*   **JAR (Java Archive)**: The standard format for packaging Java classes, associated metadata, and resources (like text, images) into a single file. JAR files use the ZIP file format.
    *   **Manifest File (`META-INF/MANIFEST.MF`)**: A special file within a JAR that contains metadata, such as the version, main class (for executable JARs), classpath dependencies, etc.
    *   **Executable JARs**: JAR files that can be run directly using `java -jar myapp.jar`. Requires the `Main-Class` attribute to be set in the manifest file.
    *   **Library JARs**: JAR files intended to be used as dependencies by other projects. They are placed on the classpath.
*   **WAR (Web Application Archive)**: A specialized JAR format used for packaging Java web applications (Servlets, JSPs, static files, classes, libraries) for deployment onto Servlet containers or application servers (like Tomcat, Jetty, WildFly).
*   **EAR (Enterprise Application Archive)**: A format used for packaging enterprise Java applications (Jakarta EE / Java EE), which may contain multiple modules like WAR files, EJB JARs, and library JARs.
*   **Classpath**: An environment variable or command-line argument that tells the JVM where to find user-defined classes and packages. It can include directories and JAR files.
*   **Modules (Java 9+)**: The Java Platform Module System (JPMS) allows for packaging code into modules with explicit dependencies and encapsulated internals (`module-info.java`). Modules can be packaged into *Modular JARs*.

## Build Tools (Maven & Gradle)

Modern Java development relies heavily on build tools like Maven and Gradle to automate the packaging process.

*   **Dependency Management**: They automatically download required library JARs (dependencies) from repositories like Maven Central.
*   **Packaging Tasks**: Provide tasks/goals to create different types of archives:
    *   **Gradle**: `jar` task creates a standard library JAR. The `application` plugin helps create distributable applications (often with scripts and dependencies) and executable JARs. The `war` plugin creates WAR files.
    *   **Maven**: `mvn package` goal typically creates a JAR or WAR depending on the project's `packaging` type (`<packaging>jar</packaging>` or `<packaging>war</packaging>`). Plugins like `maven-jar-plugin`, `maven-war-plugin`, and `maven-shade-plugin` (or `maven-assembly-plugin`) are used to configure manifests, include dependencies (creating "fat" or "uber" JARs), and create distributions.

*   **Creating Executable ("Fat"/"Uber") JARs**: Often, you want a single JAR file containing both your application code and all its dependencies. Build tools facilitate this:
    *   **Gradle**: Using the `application` plugin's `distTar`/`distZip` tasks or configuring the `jar` task to include dependencies (e.g., `from { configurations.runtimeClasspath.collect { it.isDirectory() ? it : zipTree(it) } }`). Shadow JAR plugin (`com.github.johnrengelman.shadow`) is a popular choice for creating fat JARs.
    *   **Maven**: Using the `maven-shade-plugin` or `maven-assembly-plugin` to create a JAR with dependencies included.

## Distribution Formats

Besides raw JAR/WAR files, applications can be distributed as:

*   **ZIP/TAR Archives**: Created by build tools (e.g., Gradle's `distZip`/`distTar`, Maven Assembly Plugin), containing the application JAR(s), dependency JARs, startup scripts (`.sh`, `.bat`), configuration files, etc.
*   **Native Images**: Tools like GraalVM Native Image can compile Java code ahead-of-time into a standalone executable, resulting in faster startup and lower memory usage, but with some limitations regarding Java's dynamic features.
*   **Container Images (Docker)**: Applications (especially web services) are frequently packaged into Docker images along with the required JRE/JDK and dependencies for easy deployment.
*   **Installers**: Tools can create platform-specific installers (e.g., `jpackage` tool in modern JDKs).

Understanding packaging options is crucial for deploying Java applications effectively. 