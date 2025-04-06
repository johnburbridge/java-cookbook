# Error Handling and Logging Recipe

Robust applications need effective strategies for handling errors and logging important information for debugging, monitoring, and auditing.

## Error Handling in Java (Exceptions)

Java uses exceptions to handle errors and other exceptional events that disrupt the normal flow of instruction execution.

### Key Concepts:

*   **`Throwable`**: The superclass for all errors and exceptions in Java.
*   **`Error`**: Represents serious problems that a reasonable application should not try to catch (e.g., `OutOfMemoryError`, `StackOverflowError`). Usually indicates unrecoverable conditions.
*   **`Exception`**: Represents conditions that a reasonable application might want to catch. `Exception` has two main subclasses:
    *   **Checked Exceptions**: Exceptions that must be declared in a method's `throws` clause or handled in a `try-catch` block at compile time (e.g., `IOException`, `SQLException`). They represent expected, potentially recoverable conditions external to the program (like file not found or network issues).
    *   **Unchecked Exceptions (Runtime Exceptions)**: Subclasses of `RuntimeException`. They do *not* need to be declared or caught explicitly (though they can be). They usually indicate programming errors (e.g., `NullPointerException`, `ArrayIndexOutOfBoundsException`, `IllegalArgumentException`).
*   **`try-catch-finally`**: The primary mechanism for handling exceptions.
    *   `try`: Encloses code that might throw an exception.
    *   `catch`: Handles specific types of exceptions thrown in the `try` block. Multiple `catch` blocks can handle different exception types.
    *   `finally`: Contains code that *always* executes after the `try` block (and any `catch` block), regardless of whether an exception was thrown or caught. Used for cleanup (e.g., closing resources before Java 7's try-with-resources).
*   **`throw`**: Used to manually throw an exception object.
*   **`throws`**: Used in a method signature to declare the types of checked exceptions the method might throw (or propagate).
*   **Try-with-resources (Java 7+)**: Simplifies cleanup for resources that implement `AutoCloseable`. Resources declared in the `try` statement are automatically closed at the end of the block (see `try-with-resources` recipe).

### Best Practices:

*   Catch specific exceptions rather than generic `Exception` or `Throwable` where possible.
*   Don't swallow exceptions (catch them and do nothing); at least log them.
*   Use checked exceptions for recoverable conditions and runtime exceptions for programming errors.
*   Clean up resources in `finally` blocks or use try-with-resources.
*   Provide informative messages when creating exceptions.

## Logging in Java

Logging provides insight into application behavior without relying solely on console output.

### Common Logging Frameworks:

*   **`java.util.logging` (JUL)**: The built-in JDK logging framework. Functional but often considered less flexible and performant than others.
*   **Logback**: Successor to Log4j 1.x, designed by the same founder. Powerful, performant, and widely used. Native implementation of SLF4j.
*   **Log4j 2**: A complete rewrite of Log4j 1.x. Offers significant improvements in performance and features, including asynchronous logging.
*   **SLF4j (Simple Logging Facade for Java)**: Not a logging implementation itself, but an *abstraction layer* (facade). It allows developers to code against the SLF4j API, and then plug in a specific logging implementation (like Logback, Log4j 2, or JUL) at deployment time. This is the **recommended approach** as it decouples the application code from the specific logging framework.

### Using SLF4j with Logback (Common Setup):

1.  **Dependencies**: Include `slf4j-api.jar` and `logback-classic.jar` (which includes `logback-core.jar`).
2.  **Get Logger**: Obtain a logger instance in your class: `private static final Logger logger = LoggerFactory.getLogger(YourClass.class);`
3.  **Log Messages**: Use logger methods (`trace`, `debug`, `info`, `warn`, `error`):
    ```java
    logger.info("User {} logged in successfully.", userId);
    try {
        // ... risky operation ...
    } catch (IOException e) {
        logger.error("Failed to process file: {}", filename, e);
    }
    ```
4.  **Configuration**: Configure Logback via `logback.xml` (or `logback-test.xml`) placed on the classpath. This file defines appenders (where logs go - console, file, etc.), layouts (how logs are formatted), and log levels for different parts of the application. 