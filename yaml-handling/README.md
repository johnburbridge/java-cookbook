# YAML Handling Recipe

YAML (YAML Ain't Markup Language) is a human-friendly data serialization standard for all programming languages. It is commonly used for configuration files but can be used in many applications where data is being stored or transmitted.

## Overview

Similar to JSON handling, working with YAML in Java involves:

1.  **Serialization (Dumping)**: Converting Java objects into their YAML string representation.
2.  **Deserialization (Loading)**: Parsing YAML strings (or streams) and converting them into Java objects.

## Common Java Libraries for YAML

*   **SnakeYAML**: This is the de facto standard library for YAML processing in Java. It provides APIs for parsing YAML into Java objects (like `Map`, `List`, or custom POJOs) and dumping Java objects into YAML format.
    *   Core classes: `Yaml`, `DumperOptions`, `LoaderOptions`.

*   **Jackson Dataformat YAML**: Jackson also provides an extension (`jackson-dataformat-yaml`) that allows its `ObjectMapper` to read and write YAML, leveraging the same annotations and data binding capabilities used for JSON but using SnakeYAML under the hood for the actual YAML processing.
    *   Requires adding the `jackson-dataformat-yaml` dependency.
    *   Uses the same `ObjectMapper` as for JSON.

## Typical Process (Data Binding Example with Jackson)

Using Jackson's YAML dataformat extension is often convenient if you're already using Jackson for JSON.

1.  **Add Dependency**: Include `jackson-dataformat-yaml` in your build.
2.  **Define POJO**: Create the Java object structure.
3.  **Instantiate `ObjectMapper` for YAML**: Create an `ObjectMapper` instance, passing a `YAMLFactory` instance to its constructor.
4.  **Serialize**: Use `yamlMapper.writeValueAsString(yourObject)`.
5.  **Deserialize**: Use `yamlMapper.readValue(yamlString, YourPojo.class)`.

```java
// Assumes jackson-dataformat-yaml dependency is present

// 1. POJO
public class Config {
    public String serverUrl;
    public int serverPort;
    public List<String> enabledFeatures;
    // Constructor, getters, setters...
}

// 2. ObjectMapper for YAML
ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

// 3. Serialize
Config config = new Config();
config.serverUrl = "http://example.com";
config.serverPort = 8080;
config.enabledFeatures = List.of("feature1", "feature2");

String yaml = yamlMapper.writeValueAsString(config);
/* yaml ->
---
serverUrl: "http://example.com"
serverPort: 8080
enabledFeatures:
- "feature1"
- "feature2"
*/

// 4. Deserialize
String inputYaml = "serverUrl: https://test.com\nserverPort: 9090\nenabledFeatures:\n  - alpha\n  - beta";
Config deserializedConfig = yamlMapper.readValue(inputYaml, Config.class);
// deserializedConfig.getServerPort() -> 9090
```

Using SnakeYAML directly involves creating a `Yaml` instance and using its `load()` and `dump()` methods. 