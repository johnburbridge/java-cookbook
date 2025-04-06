# Asynchronous Programming in Java Recipe

Asynchronous programming allows a program to perform tasks without waiting for long-running operations (like I/O: network requests, file access, database queries) to complete. This improves responsiveness and resource utilization, especially in applications handling many concurrent requests or operations.

## Key Concepts

*   **Synchronous vs. Asynchronous**: In synchronous execution, tasks are performed one after another; the caller waits (blocks) until the called operation finishes. In asynchronous execution, the caller initiates an operation and continues with other work; the result or completion status of the operation is handled later (e.g., via callbacks, futures, or reactive streams).
*   **Blocking vs. Non-Blocking**: Refers to how I/O operations are handled. Blocking I/O waits for the operation to complete. Non-blocking I/O initiates the operation and returns immediately, allowing the thread to do other work; completion is checked later or notified via events.

## `CompletableFuture<T>` (Java 8+)

`CompletableFuture` is the cornerstone of modern asynchronous programming in Java. It represents a future result of an asynchronous computation – a value that will eventually be available.

### Core Features:

*   **Represents Future Results**: Holds a value (or exception) that will be available upon completion of an async task.
*   **Completion Stages**: Allows chaining dependent actions that execute when the future completes, without blocking.
*   **Combining Futures**: Provides methods to combine multiple futures (e.g., wait for all to complete, wait for the first one to complete).
*   **Explicit Completion**: Can be explicitly completed with a value or an exception.
*   **Executor Configuration**: Allows specifying the `Executor` (thread pool) to run asynchronous tasks.

### Common Methods:

*   **Creating**: `supplyAsync(Supplier<U>, Executor)`, `runAsync(Runnable, Executor)` (or versions without Executor using `ForkJoinPool.commonPool()` by default).
*   **Chaining Computations (Mapping)**:
    *   `thenApply(Function<? super T,? extends U>)`: Transforms the result when available (sync execution in potentially another thread).
    *   `thenApplyAsync(Function<? super T,? extends U>, Executor)`: Transforms the result asynchronously using the specified executor.
    *   `thenCompose(Function<? super T,? extends CompletionStage<U>>)`: Chains another async operation that depends on the result of the first (like `flatMap`).
    *   `thenComposeAsync(...)`
*   **Chaining Actions (Consuming)**:
    *   `thenAccept(Consumer<? super T>)`: Performs an action with the result (no return value).
    *   `thenAcceptAsync(...)`
    *   `thenRun(Runnable)`: Performs an action when complete (doesn't use the result).
    *   `thenRunAsync(...)`
*   **Combining**: `thenCombine(CompletionStage<? extends U>, BiFunction<? super T,? super U,? extends V>)`, `thenAcceptBoth(...)`, `runAfterBoth(...)`, `applyToEither(...)`, `acceptEither(...)`, `runAfterEither(...)`, `allOf(CompletableFuture<?>...)`, `anyOf(CompletableFuture<?>...)`.
*   **Exception Handling**: `exceptionally(Function<Throwable,? extends T>)`, `handle(BiFunction<? super T, Throwable, ? extends U>)`, `whenComplete(BiConsumer<? super T, ? super Throwable>)`.
*   **Retrieving Result**: `get()` (blocking, throws checked exceptions), `join()` (blocking, throws unchecked exceptions), `getNow(T valueIfAbsent)` (non-blocking).

### Example:

```java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// ...
ExecutorService executor = Executors.newFixedThreadPool(4);

CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    // Simulate long-running task
    try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { }
    return "Task Result";
}, executor);

// Chain actions without blocking the main thread
future.thenApply(result -> result.toUpperCase())
      .thenAccept(upperResult -> System.out.println("Processed Result: " + upperResult))
      .exceptionally(ex -> {
          System.err.println("Error: " + ex.getMessage());
          return null; // Handle exception
      })
      .whenComplete((res, ex) -> {
          System.out.println("Future completed.");
          executor.shutdown(); // Shutdown executor when done
      });

System.out.println("Main thread continues...");
```

## Other Approaches

*   **Reactive Streams / Libraries**: Frameworks like Project Reactor (Mono/Flux) and RxJava (Observable/Flowable) provide rich APIs for composing asynchronous and event-driven operations based on the Reactive Streams specification.
*   **Callbacks**: Older style where a callback function/object is passed to an asynchronous method to be invoked upon completion.
*   **Virtual Threads**: While not strictly asynchronous programming *primitives*, virtual threads allow writing *synchronous-looking* blocking code that scales efficiently for I/O-bound tasks, often simplifying development compared to explicit async APIs like `CompletableFuture`.

`CompletableFuture` provides a powerful and flexible way to manage asynchronous operations in standard Java. 