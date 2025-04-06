# API Development in Java Recipe

Developing Application Programming Interfaces (APIs), particularly web APIs (HTTP-based), is a common task in Java for building web services, microservices, and backends for web/mobile applications.

## Key Concepts

*   **API (Application Programming Interface)**: A contract that defines how software components should interact.
*   **Web API / Web Service**: An API accessed over a network, typically using HTTP.
*   **REST (Representational State Transfer)**: An architectural style for designing networked applications. RESTful APIs are characterized by:
    *   **Statelessness**: Each request from a client contains all information needed to understand and process it.
    *   **Client-Server Architecture**: Separation of concerns between UI and data storage.
    *   **Cacheability**: Responses can be marked as cacheable.
    *   **Layered System**: Intermediaries (proxies, gateways) can be placed between client and server.
    *   **Uniform Interface**: Standardized ways of interacting (using HTTP methods like GET, POST, PUT, DELETE on resources identified by URIs, often using JSON/XML for representations).
*   **Resources**: The core concept in REST (e.g., a user, an order, a product). Identified by URIs (e.g., `/users`, `/users/123`).
*   **HTTP Methods**: Standard verbs used for operations on resources (GET: retrieve, POST: create, PUT: update/replace, DELETE: remove, PATCH: partial update).
*   **HTTP Status Codes**: Standard codes indicating the outcome of a request (e.g., 200 OK, 201 Created, 400 Bad Request, 404 Not Found, 500 Internal Server Error).
*   **Data Format**: JSON is the most common format for request/response bodies in modern web APIs.
*   **API Design**: Considerations include resource naming, URI structure, appropriate use of HTTP methods and status codes, versioning, security, documentation (e.g., OpenAPI/Swagger).

## Common Java Frameworks for API Development

Several frameworks simplify building web APIs in Java:

*   **Spring Boot / Spring WebMVC / Spring WebFlux**: The most popular choice in the Java ecosystem. Spring Boot provides rapid setup and configuration. Spring WebMVC is used for traditional servlet-based (blocking) APIs, while Spring WebFlux supports reactive (non-blocking) APIs.
    *   Uses annotations like `@RestController`, `@RequestMapping`, `@GetMapping`, `@PostMapping`, `@PathVariable`, `@RequestBody`, etc.
    *   Integrates seamlessly with the vast Spring ecosystem (Data, Security, Cloud, etc.).

*   **Jakarta EE (formerly Java EE)**: A set of specifications for enterprise Java development. Implementations include WildFly, GlassFish, Open Liberty.
    *   **JAX-RS (Jakarta RESTful Web Services)**: The standard specification for building RESTful APIs. Uses annotations like `@Path`, `@GET`, `@POST`, `@Produces`, `@Consumes`, `@PathParam`, etc.
    *   Other relevant specifications include CDI (Contexts and Dependency Injection), JPA (Persistence), Bean Validation.

*   **Quarkus**: A modern, Kubernetes-native Java framework optimized for GraalVM Native Image, offering fast startup and low memory usage. Supports standard APIs (JAX-RS, JPA, etc.) and its own extensions.

*   **Micronaut**: Another modern framework focused on fast startup and low memory footprint, achieving this through ahead-of-time (AOT) compilation and avoiding runtime reflection.

*   **SparkJava**: A lightweight micro-framework inspired by Sinatra (Ruby), suitable for smaller services or APIs where the overhead of larger frameworks is not desired.

## Basic Workflow (Conceptual Example with Spring Boot)

1.  **Setup Project**: Create a Spring Boot project with the `spring-boot-starter-web` dependency.
2.  **Define Controller**: Create a class annotated with `@RestController`.
3.  **Map Endpoints**: Define methods annotated with `@GetMapping`, `@PostMapping`, etc., specifying the path.
4.  **Handle Request/Response**: Use annotations like `@PathVariable`, `@RequestParam`, `@RequestBody` to access request data. Return values (often POJOs) are automatically serialized (e.g., to JSON via Jackson, which is included by default).

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    // In-memory storage for simplicity
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = users.get(id);
        return (user != null) ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        long id = counter.incrementAndGet();
        user.setId(id); // Assuming User has setId
        users.put(id, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // Define User record/class elsewhere
    // record User(Long id, String name, String email) { ... }
}
```

Building robust APIs involves handling data validation, security (authentication/authorization), error handling, logging, documentation, and testing. 