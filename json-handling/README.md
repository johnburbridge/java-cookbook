# JSON Handling Recipe

JSON (JavaScript Object Notation) is a lightweight data-interchange format. It is easy for humans to read and write and easy for machines to parse and generate. It is based on a subset of the JavaScript Programming Language Standard ECMA-262 3rd Edition - December 1999.

## Overview

Working with JSON in Java typically involves:

1.  **Serialization (Marshalling)**: Converting Java objects into their JSON string representation.
2.  **Deserialization (Unmarshalling)**: Parsing JSON strings and converting them back into Java objects.

## Common Java Libraries for JSON

Several popular libraries facilitate JSON processing in Java:

*   **Jackson**: A high-performance, feature-rich library. It's one of the most widely used JSON libraries in the Java ecosystem. Offers data binding (POJOs to/from JSON), a tree model (for flexible access), and a streaming API (for low-level, high-performance parsing/generation).
    *   Key annotations: `@JsonProperty`, `@JsonIgnore`, `@JsonCreator`, `@JsonInclude`, etc.
    *   Core classes: `ObjectMapper`, `JsonNode`.

*   **Gson**: Developed by Google, Gson is another popular library focused on ease of use for converting Java Objects into JSON and back. It requires minimal configuration for simple cases.
    *   Core classes: `Gson`, `JsonElement`.

*   **JSON-P (Java API for JSON Processing)**: Part of the Jakarta EE standard (and included in Java EE). Provides object model (similar to DOM for XML) and streaming APIs for parsing and generating JSON. It doesn't include built-in data binding like Jackson or Gson, but libraries can be built on top of it.
    *   Core interfaces: `JsonReader`, `JsonWriter`, `JsonObject`, `JsonArray`, `JsonValue`.

*   **JSON-B (Java API for JSON Binding)**: Also part of Jakarta EE. Provides a standard binding layer for converting Java objects to and from JSON, similar to Jackson and Gson data binding but standardized. Implementations often use JSON-P underneath.
    *   Core interfaces/classes: `Jsonb`, `JsonbBuilder`.

## Typical Process (Data Binding Example with Jackson)

1.  **Define POJO**: Create a Plain Old Java Object (POJO) whose fields correspond to the JSON structure.
2.  **Instantiate `ObjectMapper`**: Create an instance of Jackson's `ObjectMapper`.
3.  **Serialize**: Use `objectMapper.writeValueAsString(yourObject)` to get the JSON string.
4.  **Deserialize**: Use `objectMapper.readValue(jsonString, YourPojo.class)` to create an object from JSON.

```java
// 1. POJO
public class Person {
    public String name;
    public int age;
    // Constructor, getters, setters...
}

// 2. ObjectMapper
ObjectMapper objectMapper = new ObjectMapper();

// 3. Serialize
Person person = new Person("John Doe", 30);
String json = objectMapper.writeValueAsString(person);
// json -> {"name":"John Doe","age":30}

// 4. Deserialize
String inputJson = "{\"name\":\"Jane Doe\",\"age\":25}";
Person deserializedPerson = objectMapper.readValue(inputJson, Person.class);
// deserializedPerson.getName() -> "Jane Doe"
``` 