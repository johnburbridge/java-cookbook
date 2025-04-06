# Functional Programming in Java Recipe

While Java is primarily an object-oriented language, features introduced starting in Java 8 provide significant support for functional programming paradigms. This allows for writing more concise, expressive, and potentially parallelizable code.

## Key Concepts & Features

*   **Lambda Expressions**: Anonymous functions that can be treated as values. They provide a compact syntax for implementing functional interfaces (interfaces with a single abstract method).
    *   Syntax: `(parameters) -> expression` or `(parameters) -> { statements; }`
    *   Examples:
        *   `() -> System.out.println("Hello")` (No parameters)
        *   `x -> x * x` (Single parameter, type inferred)
        *   `(int x, int y) -> x + y` (Multiple parameters, types declared)
        *   `(String s) -> { System.out.println(s); return s.length(); }` (Block body)

*   **Functional Interfaces**: Interfaces with exactly one abstract method (though they can have default and static methods). They serve as the target type for lambda expressions and method references.
    *   The `java.util.function` package provides many standard functional interfaces (e.g., `Predicate<T>`, `Function<T, R>`, `Consumer<T>`, `Supplier<T>`, `BinaryOperator<T>`).
    *   The `@FunctionalInterface` annotation is optional but recommended; it causes the compiler to verify that the interface meets the requirements.

*   **Method References**: A shorthand syntax for lambda expressions that execute a single existing method.
    *   Types:
        *   Static methods: `ClassName::staticMethodName` (e.g., `Math::abs`)
        *   Instance methods of a particular object: `object::instanceMethodName` (e.g., `System.out::println`)
        *   Instance methods of an arbitrary object of a particular type: `ClassName::instanceMethodName` (e.g., `String::toUpperCase`)
        *   Constructors: `ClassName::new` (e.g., `ArrayList::new`)

*   **Streams API (`java.util.stream`)**: Provides a fluent API for processing sequences of elements (from collections, arrays, I/O channels, etc.) in a functional style. Streams support aggregate operations like filter, map, reduce, find, match, sort, etc.
    *   **Characteristics**: Streams don't store elements; they operate on a source. Operations are often lazy (computation happens only when a terminal operation is invoked). They can be sequential or parallel.
    *   **Pipeline**: Typically consists of:
        1.  A *source* (e.g., `collection.stream()`, `Arrays.stream(array)`).
        2.  Zero or more *intermediate operations* (which return a new stream, e.g., `filter()`, `map()`, `sorted()`).
        3.  A *terminal operation* (which produces a result or side effect, e.g., `forEach()`, `collect()`, `reduce()`, `count()`).
    *   Example:
        ```java
        List<String> names = List.of("Alice", "Bob", "Charlie", "Anna");
        List<String> filteredNames = names.stream() // Source
                                          .filter(s -> s.startsWith("A")) // Intermediate
                                          .map(String::toUpperCase)     // Intermediate
                                          .sorted()                   // Intermediate
                                          .collect(Collectors.toList()); // Terminal
        // filteredNames -> ["ALICE", "ANNA"]
        ```

*   **`Optional<T>`**: A container object which may or may not contain a non-null value. Used to represent the potential absence of a value more explicitly than returning `null`, avoiding `NullPointerException`s when used correctly. Provides functional-style methods like `map()`, `flatMap()`, `filter()`, `ifPresent()`, `orElse()`.

## Benefits

*   **Conciseness**: Reduces boilerplate code, especially with lambdas and streams.
*   **Readability**: Functional pipelines can often express complex operations more clearly.
*   **Immutability**: Functional programming encourages working with immutable data, leading to safer concurrent code.
*   **Parallelism**: Streams API provides easy parallelization (`collection.parallelStream()`).

These features allow Java developers to blend object-oriented and functional styles effectively. 