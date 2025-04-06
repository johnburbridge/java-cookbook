# Metaprogramming in Java Recipe

Metaprogramming refers to writing code that operates on other code (or itself) as data. In Java, the primary mechanisms for metaprogramming are Reflection and Annotation Processing.

## Reflection (`java.lang.reflect`)

Reflection allows Java code to examine and manipulate itself at runtime. It provides the ability to inspect classes, interfaces, fields, and methods at runtime, without knowing their names at compile time. It also allows instantiating objects, invoking methods, and getting/setting field values dynamically.

### Key Classes:

*   **`Class<T>`**: Represents a class or interface. Obtained via `YourClass.class`, `object.getClass()`, or `Class.forName("fully.qualified.ClassName")`.
*   **`Field`**: Represents a class field. Allows reading/writing values (`get()`, `set()`) and examining modifiers/types. Access checks might need to be overridden (`setAccessible(true)`).
*   **`Method`**: Represents a class or instance method. Allows invoking the method (`invoke()`) and examining parameters, return types, modifiers.
*   **`Constructor<T>`**: Represents a class constructor. Allows creating new instances (`newInstance()`).
*   **`Parameter`**: (Java 8+) Represents a method or constructor parameter.
*   **`AnnotatedElement`**: Interface implemented by `Class`, `Method`, `Field`, etc., allowing inspection of runtime-visible annotations (`getAnnotation()`, `isAnnotationPresent()`).

### Use Cases:

*   Frameworks (Spring, Hibernate, JUnit): Extensively use reflection for dependency injection, ORM, test discovery/execution, etc.
*   Serialization/Deserialization Libraries (Jackson, Gson): Use reflection to map JSON/XML to/from object fields.
*   Debugging and Development Tools.
*   Dynamic Proxies (`java.lang.reflect.Proxy`): Creating proxy objects that intercept method calls.

### Downsides:

*   **Performance Overhead**: Reflection operations are generally slower than direct code access.
*   **Security Restrictions**: Can be restricted by the Security Manager.
*   **Type Safety**: Bypasses compile-time type checking, potentially leading to runtime errors (`ClassCastException`).
*   **Readability/Maintainability**: Can make code harder to understand and refactor.

```java
// Example: Inspecting and calling a method via reflection
public class Target {
    private String message = "Hello";
    private void printMessage(String prefix) {
        System.out.println(prefix + ": " + message);
    }
}

// ... elsewhere ...
Target target = new Target();
Class<?> clazz = target.getClass();

try {
    Method method = clazz.getDeclaredMethod("printMessage", String.class);
    method.setAccessible(true); // Access private method
    method.invoke(target, "Reflected Call"); // Calls target.printMessage("Reflected Call")
} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
    e.printStackTrace();
}
```

## Annotation Processing (`javax.annotation.processing`)

Annotation processing occurs during compilation (compile-time metaprogramming). Annotation processors can analyze annotations present in source code and generate additional source files (e.g., boilerplate code, configuration files, documentation).

### Key Concepts:

*   **Processor**: A class implementing the `javax.annotation.processing.Processor` interface.
*   **Processing Environment (`ProcessingEnvironment`)**: Provides utilities for the processor (messager, filer, element utils, type utils).
*   **Elements (`javax.lang.model.element`)**: Represent program elements like packages, classes, methods, fields.
*   **Types (`javax.lang.model.type`)**: Represent types in the Java language.
*   **Filer**: Interface for creating new source files, class files, or auxiliary resource files.
*   **Messager**: Interface for reporting errors, warnings, and notes during processing.

### Use Cases:

*   **Code Generation**: Lombok (getters/setters), MapStruct (bean mapping), Dagger (dependency injection), AutoValue.
*   **Validation**: Enforcing rules at compile time based on annotations.
*   **Configuration Generation**.

### Advantages over Reflection:

*   **Compile-Time Safety**: Errors are caught during compilation.
*   **No Runtime Overhead**: The generated code is standard Java code.

Annotation processing is a powerful tool for framework development and reducing boilerplate but involves a steeper learning curve than reflection.

Both reflection and annotation processing provide powerful metaprogramming capabilities in Java, used extensively by libraries and frameworks but should be used judiciously in application code due to their complexities and potential downsides. 