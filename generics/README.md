# Generics Recipe

Generics allow you to parameterize types. This means you can define a class, interface, or method that operates on types specified as parameters. Using generics provides stronger type checks at compile time and eliminates the need for casting.

## Key Concepts

*   **Type Parameters**: Placeholder names for types (e.g., `T`, `E`, `K`, `V`). Conventionally, single uppercase letters are used.
*   **Generic Types**: Classes or interfaces parameterized over types. Example: `List<E>` (List of elements of type `E`).
*   **Generic Methods**: Methods with their own type parameters. These are often static methods.
*   **Bounded Type Parameters**: Restrict the types that can be used as type arguments using `extends` (for upper bounds, allowing the specified type or its subtypes) or `super` (for lower bounds, allowing the specified type or its supertypes).
*   **Wildcards (`?`)**: Represent unknown types. Can be unbounded (`?`), have an upper bound (`? extends Type`), or a lower bound (`? super Type`). Wildcards are particularly useful when working with collections of unknown types, especially in method parameters.
    *   `? extends Type` (Upper Bounded Wildcard): Represents an unknown type that is a subtype of `Type`. Useful when you only need to *read* from a collection (Producer Extends - PE).
    *   `? super Type` (Lower Bounded Wildcard): Represents an unknown type that is a supertype of `Type`. Useful when you only need to *write* to a collection (Consumer Super - CS).
*   **Type Erasure**: During compilation, generic type information is removed (erased) and replaced with bounds or `Object`. This ensures backward compatibility with older Java code that didn't use generics.

## Benefits

*   **Compile-Time Safety**: Catches invalid type usage at compile time rather than runtime (avoiding `ClassCastException`).
*   **Code Reusability**: Write code that works with different types without duplication.
*   **Readability**: Explicitly states the intended types, making code easier to understand.

## Example Snippet (Conceptual)

```java
// Generic Class
class Box<T> {
    private T t;
    public void set(T t) { this.t = t; }
    public T get() { return t; }
}

// Generic Method
class Util {
    public static <K, V> boolean compare(Pair<K, V> p1, Pair<K, V> p2) {
        return p1.getKey().equals(p2.getKey()) &&
               p1.getValue().equals(p2.getValue());
    }
}

// Bounded Type Parameter
public <N extends Number> void processNumber(N number) { /* ... */ }

// Wildcard Usage
public void processList(List<? extends Number> list) { // Can read Numbers
    for (Number n : list) {
        // ...
    }
    // Cannot add elements (except null) due to unknown specific type
    // list.add(Integer.valueOf(5)); // Compile error
}

public void addToList(List<? super Integer> list) { // Can add Integers or subtypes
    list.add(Integer.valueOf(10));
    list.add(null);
    // Cannot guarantee type when reading (except Object)
    // Integer i = list.get(0); // Compile error (returns Object)
}
``` 