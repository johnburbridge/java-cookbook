# Annotations Recipe

Annotations are a form of metadata that can be added to Java code elements like classes, methods, fields, parameters, and local variables. They provide additional information about the code element they annotate but do not directly affect the execution of the code itself.

## Purpose

Annotations serve various purposes:

*   **Information for the compiler**: Used by the compiler to detect errors or suppress warnings (e.g., `@Override`, `@Deprecated`, `@SuppressWarnings`).
*   **Compile-time and deployment-time processing**: Annotation processors can generate code, XML files, etc., based on annotations during compilation (e.g., used in Lombok, Dagger, MapStruct).
*   **Runtime processing**: Annotations can be examined at runtime via reflection to alter program behavior (e.g., used extensively in frameworks like Spring, JUnit, Jackson, JPA).

## Declaring an Annotation Type

Annotation types are defined using the `@interface` keyword.

```java
import java.lang.annotation.*;

// Meta-annotations specifying where and how the annotation can be used
@Retention(RetentionPolicy.RUNTIME) // Available at runtime via reflection
@Target(ElementType.METHOD)        // Can only be applied to methods
public @interface MyAnnotation {
    // Annotation elements (optional)
    String description() default "Default description";
    int value();
    String[] tags() default {};
}
```

*   **Meta-Annotations**: Annotations applied to an annotation type definition:
    *   `@Retention`: Specifies how long the annotation should be retained (`SOURCE`, `CLASS` (default), `RUNTIME`).
    *   `@Target`: Specifies the kinds of elements the annotation can be applied to (`TYPE`, `METHOD`, `FIELD`, `PARAMETER`, `CONSTRUCTOR`, `LOCAL_VARIABLE`, etc.).
    *   `@Documented`: Indicates that the annotation should be documented by Javadoc.
    *   `@Inherited`: Indicates that the annotation should be inherited by subclasses of an annotated class (only applies to class annotations).
    *   `@Repeatable`: (Java 8+) Allows the same annotation to be applied multiple times to the same element.
*   **Elements**: Annotations can have elements (methods declared in the `@interface`). These can have default values.
    *   The element named `value` has a special shorthand: if it's the only element specified, you can omit `value =` (e.g., `@SuppressWarnings("unchecked")`).

## Using Annotations

Annotations are applied using the `@` symbol followed by the annotation type name.

```java
public class Example {

    @Deprecated // Built-in annotation
    public void oldMethod() { /* ... */ }

    @Override // Built-in annotation
    public String toString() { /* ... */ }

    @MyAnnotation(value = 123, description = "Test method annotation")
    public void annotatedMethod() {
        // ...
    }

    @SuppressWarnings("rawtypes") // Built-in: Suppress specific warnings
    public void methodWithWarnings() {
        java.util.List list = new java.util.ArrayList();
        list.add("hello");
    }
}
```

## Processing Annotations

*   **Compile Time**: Using Annotation Processors (implementing `javax.annotation.processing.Processor`).
*   **Runtime**: Using Java Reflection (`java.lang.reflect` package). Code can inspect classes, methods, fields, etc., check for the presence of annotations, and retrieve their element values.

```java
// Runtime processing example
Method method = Example.class.getMethod("annotatedMethod");
if (method.isAnnotationPresent(MyAnnotation.class)) {
    MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
    System.out.println("Value: " + annotation.value());
    System.out.println("Description: " + annotation.description());
}
```

Annotations are a cornerstone of modern Java development, enabling powerful frameworks and reducing boilerplate code. 