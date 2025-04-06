# JDBC - Connecting to PostgreSQL Recipe

JDBC (Java Database Connectivity) is the standard Java API for connecting to relational databases. This recipe focuses on connecting to and interacting with a PostgreSQL database.

## Key Concepts & Components

*   **`DriverManager`**: The basic service for managing a set of JDBC drivers. It's used to establish a connection to a database via a database URL.
*   **JDBC Driver**: A specific implementation of the JDBC API for a particular database vendor (e.g., PostgreSQL JDBC Driver - `pgjdbc`). The driver needs to be available on the classpath at runtime.
*   **Database URL**: A string that specifies the database connection details, typically in the format `jdbc:<subprotocol>:<subname>`. For PostgreSQL: `jdbc:postgresql://<host>:<port>/<databaseName>?<properties>`.
*   **`Connection`**: Represents a session with a specific database. SQL statements are executed and results are returned within the context of a `Connection`.
*   **`Statement`**: Used for executing static SQL statements without parameters.
*   **`PreparedStatement`**: Used for executing precompiled SQL statements with input parameters (`?` placeholders). This is generally preferred over `Statement` as it improves performance (for repeated execution) and helps prevent SQL injection vulnerabilities.
*   **`CallableStatement`**: Used for executing stored procedures.
*   **`ResultSet`**: Represents the result of a database query. It maintains a cursor pointing to its current row of data. You iterate through the `ResultSet` using `next()` and retrieve column values using `getXXX()` methods (e.g., `getString()`, `getInt()`, `getDate()`).
*   **Transactions**: By default, JDBC connections are in auto-commit mode (each statement is a transaction). You can disable auto-commit (`connection.setAutoCommit(false)`) to group multiple statements into a single transaction, which can then be committed (`connection.commit()`) or rolled back (`connection.rollback()`).
*   **Resource Management**: `Connection`, `Statement`, `PreparedStatement`, and `ResultSet` are resources that **must** be closed explicitly to release database and JVM resources. The `try`-with-resources statement is the best way to ensure this.

## Basic Workflow

1.  **Load Driver**: (Historically required `Class.forName()`, but usually automatic now with JDBC 4.0+ if the driver JAR is on the classpath).
2.  **Get Connection**: Use `DriverManager.getConnection(dbUrl, user, password)`.
3.  **Create Statement**: Create a `Statement` or `PreparedStatement` from the `Connection`.
4.  **Execute Query/Update**: Use methods like `executeQuery()` (for SELECT) or `executeUpdate()` (for INSERT, UPDATE, DELETE).
5.  **Process Results**: If using `executeQuery()`, iterate through the `ResultSet`.
6.  **Close Resources**: Close `ResultSet`, `Statement`/`PreparedStatement`, and `Connection` (ideally using `try`-with-resources).

```java
String dbUrl = "jdbc:postgresql://localhost:5432/mydatabase";
String user = "dbuser";
String password = "dbpass";
String sql = "SELECT id, name FROM users WHERE status = ?";

try (
    // 2. Get Connection
    Connection conn = DriverManager.getConnection(dbUrl, user, password);
    // 3. Create PreparedStatement
    PreparedStatement pstmt = conn.prepareStatement(sql)
) {
    pstmt.setString(1, "active"); // Set the parameter

    // 4. Execute Query
    try (ResultSet rs = pstmt.executeQuery()) { // 6. Close ResultSet
        // 5. Process Results
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            System.out.printf("ID: %d, Name: %s\n", id, name);
        }
    }
} catch (SQLException e) {
    // Handle database errors (e.g., log them)
    e.printStackTrace();
}
// 6. Connection and PreparedStatement are closed automatically by try-with-resources
```

## Alternatives

*   **Connection Pooling**: For performance in applications, instead of `DriverManager`, use a connection pool library (e.g., HikariCP, Apache Commons DBCP, c3p0) to manage and reuse connections.
*   **JPA (Jakarta Persistence API)**: A higher-level abstraction for object-relational mapping (ORM). Implementations like Hibernate or EclipseLink manage JDBC connections and translate Java object operations into SQL. See `jpa-hibernate` recipe (if created).
*   **Spring JDBC (`JdbcTemplate`)**: Spring Framework provides `JdbcTemplate` which simplifies JDBC usage by handling resource management, exception translation, and reducing boilerplate.
*   **jOOQ**: A library that generates Java code from your database schema, allowing you to build type-safe SQL queries in Java. 