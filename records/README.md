# Records Recipe

Records, introduced as a standard feature in Java 16 (previewed in 14 and 15), provide a concise syntax for declaring classes that are transparent carriers for immutable data. They significantly reduce the boilerplate code often associated with simple data holder classes (POJOs).

## Motivation

Before records, creating a simple data carrier class required explicitly writing:

*   Private final fields
*   A constructor to initialize these fields
*   Getter methods for each field
*   Implementations of `equals()`, `hashCode()`, and `toString()`

This leads to verbose code for conceptually simple classes.

## Record Declaration

A record declaration specifies the name of the record and its components (the data it holds) in a header.

```java
// Declaration
public record Point(int x, int y) { }
```

This concise declaration automatically provides:

1.  **Private final fields**: `x` and `y`.
2.  **A canonical constructor**: A public constructor whose signature matches the record header (`Point(int x, int y)`), initializing the fields.
3.  **Public accessor methods**: Getter-like methods for each component (e.g., `x()`, `y()`). Note the naming convention differs from JavaBeans (`getX()`).
4.  **Implementations**: Sensible implementations of `equals()` (compares component values), `hashCode()` (based on component values), and `toString()` (prints components and their values).

## Key Characteristics

*   **Immutable by default**: The fields corresponding to the components are implicitly `final`.
*   **Implicitly `final`**: Records themselves cannot be declared `abstract`; they are implicitly `final` and cannot be extended.
*   **Cannot extend other classes**: Records implicitly extend `java.lang.Record` and cannot extend any other class (they can, however, implement interfaces).
*   **Can have additional members**: Records can declare static fields, static methods, instance methods, and constructors (though canonical constructors have restrictions). They *cannot* declare instance fields other than the private final ones corresponding to the state components.
*   **Customization**: While the canonical constructor and accessors are generated, you can provide your own implementations. You can also provide a *compact constructor* (which lacks parameters and implicitly assigns parameters to fields) primarily for validation or normalization.

```java
public record Range(int start, int end) {
    // Compact constructor for validation
    public Range {
        if (start > end) {
            throw new IllegalArgumentException("Start cannot be after end");
        }
    }

    // Additional instance method
    public int length() {
        return end - start;
    }
}
```

Records are ideal for modeling simple data aggregates like DTOs (Data Transfer Objects), coordinate points, color values, results from multi-value returns, etc., making Java code more readable and less verbose. 