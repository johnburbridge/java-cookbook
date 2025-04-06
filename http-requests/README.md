# HTTP Requests Recipe

Handling HTTP requests is a fundamental task in modern software development, used for interacting with web services, APIs, and resources over the network.

## Standard Java HTTP Client (Java 11+)

Since Java 11, the standard library includes a modern, asynchronous, and feature-rich HTTP client API located in the `java.net.http` package. This is generally the recommended approach for new projects unless specific features from third-party libraries are required.

### Key Components:

*   **`HttpClient`**: The main entry point for sending requests. It can be configured with settings like redirect policy, proxy, authenticator, preferred protocol version (HTTP/1.1, HTTP/2), and executor for async operations.
    *   Instances are immutable and thread-safe.
    *   Typically created once and reused: `HttpClient client = HttpClient.newHttpClient();` or `HttpClient client = HttpClient.newBuilder().followRedirects(Redirect.NORMAL).build();`
*   **`HttpRequest`**: Represents the request to be sent. Built using a builder pattern.
    *   Specifies the URI, HTTP method (GET, POST, PUT, DELETE, etc.), headers, and request body.
    *   `HttpRequest.BodyPublishers` provides factory methods for creating request bodies from various sources (strings, byte arrays, files, input streams).
    *   Example: `HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://example.com")).GET().build();`
*   **`HttpResponse<T>`**: Represents the received response.
    *   Contains the status code, headers, response body, and a reference to the original request.
    *   The type parameter `T` defines the type of the response body.
    *   `HttpResponse.BodyHandlers` provides factory methods to handle the response body in different ways (as a String, byte array, file, stream, discarding, etc.).
*   **`send()` vs `sendAsync()`**: `HttpClient` provides both synchronous (`send`) and asynchronous (`sendAsync`) methods for dispatching requests.
    *   `send()`: Blocks until the response is received. Returns `HttpResponse<T>`.
    *   `sendAsync()`: Returns a `CompletableFuture<HttpResponse<T>>`, allowing non-blocking operations.

### Example (Synchronous GET):

```java
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// ...

HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create("https://jsonplaceholder.typicode.com/todos/1"))
      .header("Accept", "application/json")
      .GET() // Default method is GET
      .build();

try {
    // Send request synchronously, handle body as String
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println("Status Code: " + response.statusCode());
    System.out.println("Headers: " + response.headers());
    System.out.println("Body: " + response.body());

} catch (IOException | InterruptedException e) {
    e.printStackTrace();
}
```

## Third-Party Libraries

While the standard client is capable, other popular libraries exist, often providing higher-level abstractions or integration with specific frameworks:

*   **Apache HttpClient**: A mature and very widely used library, offering fine-grained control.
*   **OkHttp**: A popular, efficient library by Square, used extensively in Android and server-side applications.
*   **Spring WebClient**: Part of the Spring Framework's reactive stack, offering a non-blocking, fluent API.
*   **Retrofit**: A type-safe HTTP client for Android and Java, often used for consuming REST APIs by defining interfaces. 