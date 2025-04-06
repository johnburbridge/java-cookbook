# Concurrent Processing in Java Recipe

Java has strong built-in support for concurrent programming, allowing applications to perform multiple tasks seemingly simultaneously, improving throughput and responsiveness.

## Key Concepts

*   **Process vs. Thread**: A *process* is an instance of a running program with its own memory space. A *thread* is the smallest unit of execution within a process; multiple threads can exist within a single process, sharing its memory space (heap), but each having its own stack.
*   **Concurrency vs. Parallelism**: *Concurrency* is about dealing with multiple tasks at once (structuring the code to handle multiple flows). *Parallelism* is about doing multiple tasks at once (simultaneous execution, typically requiring multiple CPU cores).
*   **`Thread` Class**: The basic way to create and manage threads. Typically involves creating a class that extends `Thread` or implements `Runnable` and passing the `Runnable` to a `Thread` constructor.
*   **`Runnable` Interface**: A functional interface (`run()` method) representing a task that can be executed by a thread.
*   **Synchronization**: Mechanisms to control access to shared resources by multiple threads to prevent data corruption or race conditions.
    *   **`synchronized` Keyword**: Can be applied to methods or blocks of code. Ensures that only one thread can execute the synchronized code on a given object instance (or class for static methods) at a time, using the object's intrinsic lock (monitor).
    *   **`volatile` Keyword**: Ensures that reads and writes to a variable are atomic for certain types and that changes are visible across threads (avoids caching issues), but doesn't provide mutual exclusion like `synchronized`.
    *   **`java.util.concurrent.locks`**: More flexible locking mechanisms (`Lock`, `ReentrantLock`, `ReadWriteLock`). Offer features like try-locking, interruptible locks, and fairness policies.
    *   **`java.util.concurrent.atomic`**: Classes providing atomic operations (e.g., `AtomicInteger`, `AtomicBoolean`, `AtomicReference`) without needing explicit locks, often using efficient hardware compare-and-swap (CAS) instructions.
*   **Thread Lifecycle**: Threads go through states like NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED.
*   **Thread Communication**: Mechanisms for threads to coordinate:
    *   `wait()`, `notify()`, `notifyAll()`: Low-level methods on `Object` used with intrinsic locks (`synchronized`) for thread coordination.
    *   `java.util.concurrent` Utilities: Higher-level constructs like `BlockingQueue`, `CountDownLatch`, `CyclicBarrier`, `Semaphore`, `Exchanger`.

## The `java.util.concurrent` Package (Executors Framework)

Directly managing `Thread` objects can be complex and error-prone. The Executors framework, introduced in Java 5, provides a higher-level abstraction for managing and executing tasks asynchronously.

*   **`Executor` Interface**: Simple interface for executing `Runnable` tasks.
*   **`ExecutorService` Interface**: Extends `Executor`. Manages a pool of threads and provides methods to submit tasks (`Runnable` or `Callable<V>`), manage the service lifecycle (`shutdown()`, `awaitTermination()`), and handle results via `Future<V>`.
*   **`Callable<V>` Interface**: Similar to `Runnable`, but its `call()` method can return a result (type `V`) and throw checked exceptions.
*   **`Future<V>` Interface**: Represents the result of an asynchronous computation. Provides methods to check if the computation is complete (`isDone()`), wait for its completion (`get()`), and retrieve the result or cancel the task (`cancel()`).
*   **`Executors` Class**: Factory class providing static methods to create common types of `ExecutorService` instances (e.g., `newFixedThreadPool()`, `newCachedThreadPool()`, `newSingleThreadExecutor()`).
*   **`ForkJoinPool` (Java 7+)**: An `ExecutorService` specialized for tasks that can be broken down into smaller pieces recursively (divide and conquer), using a work-stealing algorithm for efficiency. Used internally by parallel streams.

### Example (Using `ExecutorService`):

```java
import java.util.concurrent.*;

// ...

ExecutorService executor = Executors.newFixedThreadPool(4); // Pool of 4 threads

// Submit Runnable (no result)
executor.submit(() -> {
    System.out.println("Task running in thread: " + Thread.currentThread().getName());
});

// Submit Callable (with result)
Future<Integer> futureResult = executor.submit(() -> {
    TimeUnit.SECONDS.sleep(1); // Simulate work
    return 123;
});

try {
    Integer result = futureResult.get(); // Blocks until result is available
    System.out.println("Result: " + result);
} catch (InterruptedException | ExecutionException e) {
    e.printStackTrace();
}

executor.shutdown(); // Initiates shutdown, waits for running tasks
```

## Virtual Threads (Project Loom - Java 19+ preview, 21 standard)

Virtual threads are lightweight threads managed by the JVM, designed to dramatically increase the scalability of concurrent applications written in the simple thread-per-request style. They map many virtual threads onto a smaller number of OS platform threads.
*   Created via `Thread.startVirtualThread(Runnable)` or `Thread.ofVirtual().start(Runnable)` or using `Executors.newVirtualThreadPerTaskExecutor()`.
*   Ideal for I/O-bound tasks where threads spend much time waiting.

Understanding concurrency primitives and the Executors framework is essential for building responsive and performant Java applications. 