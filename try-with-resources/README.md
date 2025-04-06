# Try-with-resources Recipe

The `try`-with-resources statement, introduced in Java 7, is a language feature that ensures resources (objects that must be closed after use, such as streams, files, sockets, database connections) are closed automatically.

## Motivation

Before Java 7, ensuring resources were properly closed typically required a `try-finally` block. This often led to verbose and sometimes nested code, especially when dealing with multiple resources.

```java
// Pre-Java 7 resource handling
FileInputStream fis = null;
BufferedInputStream bis = null;
try {
    fis = new FileInputStream("input.txt");
    bis = new BufferedInputStream(fis);
    // ... read from bis ...
} catch (IOException e) {
    // Handle exception
} finally {
    try {
        if (bis != null) {
            bis.close(); // Close outer stream first
        }
    } catch (IOException e) {
        // Log or ignore
    }
    try {
        if (fis != null) {
            fis.close();
        }
    } catch (IOException e) {
        // Log or ignore
    }
}
```

This code is verbose, error-prone (e.g., forgetting to close, incorrect closing order, exceptions during close being swallowed or masking original exceptions).

## The `try`-with-resources Statement

The `try`-with-resources statement simplifies this significantly.

### Syntax:

```java
try (ResourceType resource1 = initialization1; 
     ResourceType resource2 = initialization2) {
    // Use resources
} catch (ExceptionType e) {
    // Handle exceptions from try block
}
// No finally block needed for closing resources
```

### Requirements:

*   The resources declared within the parentheses `(...)` must implement the `java.lang.AutoCloseable` interface (or its subinterface `java.io.Closeable`).
*   The `close()` method of `AutoCloseable` is called automatically at the end of the `try` block, in the reverse order of declaration.

### Example:

```java
// Java 7+ resource handling
try (FileInputStream fis = new FileInputStream("input.txt");
     BufferedInputStream bis = new BufferedInputStream(fis)) {

    // ... read from bis ...

} catch (IOException e) {
    // Handle exception related to file operations or closing
    // Exceptions thrown during close() are suppressed if an exception
    // was already thrown in the try block (added via addSuppressed).
}
// fis and bis are guaranteed to be closed here.
```

### Key Benefits:

*   **Conciseness**: Significantly reduces boilerplate code for resource management.
*   **Readability**: Makes the code easier to read and understand.
*   **Reliability**: Guarantees resources are closed, preventing resource leaks.
*   **Improved Exception Handling**: If exceptions occur both in the `try` block and during the automatic `close()`, the exception from the `try` block is thrown, and the exception(s) from `close()` are added as *suppressed* exceptions (accessible via `Throwable.getSuppressed()`). This prevents exceptions during cleanup from masking the original problem.

Any class implementing `AutoCloseable` can be used in a `try`-with-resources statement, making it applicable to files, streams, network sockets, database connections, locks, and custom resources. 