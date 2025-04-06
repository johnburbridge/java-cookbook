# Singleton Pattern Recipe

The Singleton pattern is a creational design pattern that ensures a class has only one instance and provides a global point of access to it.

## Intent

Ensure a class only has one instance, and provide a global point of access to it.

## Common Implementation Approaches in Java

There are several ways to implement the Singleton pattern in Java, each with pros and cons, especially concerning thread safety and lazy initialization.

1.  **Eager Initialization**:
    *   The instance is created when the class is loaded.
    *   Thread-safe by default (due to class loading guarantees).
    *   No lazy initialization (instance created even if not needed).
    ```java
    public class EagerSingleton {
        private static final EagerSingleton INSTANCE = new EagerSingleton();
        
        private EagerSingleton() { /* Private constructor */ }
        
        public static EagerSingleton getInstance() {
            return INSTANCE;
        }
        // ... other methods
    }
    ```

2.  **Static Block Initialization**:
    *   Similar to eager initialization but allows for exception handling during instance creation.
    *   Thread-safe.
    ```java
    public class StaticBlockSingleton {
        private static final StaticBlockSingleton INSTANCE;
        
        static {
            try {
                INSTANCE = new StaticBlockSingleton();
            } catch (Exception e) {
                throw new RuntimeException("Exception occurred in creating singleton instance");
            }
        }
        
        private StaticBlockSingleton() { /* Private constructor */ }
        
        public static StaticBlockSingleton getInstance() {
            return INSTANCE;
        }
    }
    ```

3.  **Lazy Initialization (with synchronized method)**:
    *   Instance created only when `getInstance()` is first called.
    *   Thread-safe due to `synchronized` method, but can have performance impact as synchronization is needed every time.
    ```java
    public class LazySynchronizedSingleton {
        private static LazySynchronizedSingleton instance;
        
        private LazySynchronizedSingleton() { /* Private constructor */ }
        
        public static synchronized LazySynchronizedSingleton getInstance() {
            if (instance == null) {
                instance = new LazySynchronizedSingleton();
            }
            return instance;
        }
    }
    ```

4.  **Double-Checked Locking**:
    *   Attempts to reduce synchronization overhead of lazy initialization.
    *   **Requires `volatile` keyword** on the instance variable since Java 5 to work correctly due to memory model intricacies.
    *   Generally considered tricky and often unnecessary with better alternatives.
    ```java
    public class DoubleCheckedSingleton {
        private static volatile DoubleCheckedSingleton instance;
        
        private DoubleCheckedSingleton() { /* Private constructor */ }
        
        public static DoubleCheckedSingleton getInstance() {
            if (instance == null) { // First check (no lock)
                synchronized (DoubleCheckedSingleton.class) {
                    if (instance == null) { // Second check (with lock)
                        instance = new DoubleCheckedSingleton();
                    }
                }
            }
            return instance;
        }
    }
    ```

5.  **Initialization-on-demand Holder Idiom (Bill Pugh Singleton)**:
    *   Leverages class loading guarantees for lazy initialization without explicit synchronization overhead on the `getInstance` method.
    *   Considered a preferred approach for lazy, thread-safe singletons.
    ```java
    public class BillPughSingleton {
        private BillPughSingleton() { /* Private constructor */ }
        
        private static class SingletonHelper {
            private static final BillPughSingleton INSTANCE = new BillPughSingleton();
        }
        
        public static BillPughSingleton getInstance() {
            return SingletonHelper.INSTANCE;
        }
    }
    ```

6.  **Enum Singleton (Effective Java)**:
    *   Recommended by Josh Bloch in "Effective Java".
    *   Provides inherent thread safety and serialization guarantees.
    *   Concise and robust against reflection attacks.
    *   Often the **best approach** if applicable.
    ```java
    public enum EnumSingleton {
        INSTANCE;
        
        // Optional: Add methods
        public void doSomething() {
            System.out.println("Enum Singleton doing something.");
        }
    }
    ```

## Use Cases

Singletons are used for managing shared resources (like configuration, logging, connection pools - though often managed by frameworks), utility classes, or objects where only one instance makes sense globally.

## Considerations

Overuse of Singletons can lead to tight coupling and make testing difficult. Dependency Injection frameworks often provide better ways to manage single-instance objects within an application context. 