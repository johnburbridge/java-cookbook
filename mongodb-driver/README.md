# MongoDB Java Driver Recipe

MongoDB is a popular NoSQL document database. This recipe focuses on using the official MongoDB Java Driver to interact with a MongoDB instance.

## Key Concepts (MongoDB & Driver)

*   **Document**: The basic unit of data in MongoDB, analogous to a row in relational databases but much more flexible. Documents are BSON (Binary JSON) objects.
*   **Collection**: A grouping of MongoDB documents, analogous to a table in relational databases.
*   **Database**: A container for collections.
*   **`MongoClient`**: The main entry point for the driver. Represents a pool of connections to the database; typically, you create one instance for your application.
*   **`MongoDatabase`**: Represents a specific database within the MongoDB instance.
*   **`MongoCollection<TDocument>`**: Represents a specific collection. The type parameter `TDocument` specifies the class used to represent documents (often `org.bson.Document`, a generic map-like BSON representation, or a specific POJO class).
*   **CRUD Operations**: Create, Read, Update, Delete.
    *   **Create**: `insertOne()`, `insertMany()`
    *   **Read**: `find()`, `countDocuments()`. `find()` returns a `FindIterable` which can be iterated or customized (filtering, sorting, projecting, limiting).
    *   **Update**: `updateOne()`, `updateMany()`, `replaceOne()`.
    *   **Delete**: `deleteOne()`, `deleteMany()`.
*   **Filters**: Used to specify query criteria (e.g., `Filters.eq("name", "John")`, `Filters.gt("age", 30)`).
*   **Updates**: Used to specify modifications for update operations (e.g., `Updates.set("status", "inactive")`, `Updates.inc("count", 1)`).
*   **Projections**: Used to specify which fields should be returned in query results (e.g., `Projections.include("name", "email")`, `Projections.excludeId()`).
*   **POJO Mapping**: The driver supports mapping BSON documents directly to Plain Old Java Objects (POJOs) using a CodecRegistry. This often requires configuring the registry or ensuring POJOs follow conventions.

## Basic Workflow

1.  **Add Dependency**: Include the MongoDB Java driver (`org.mongodb:mongodb-driver-sync` or `mongodb-driver-reactivestreams`).
2.  **Create `MongoClient`**: Connect to the MongoDB instance using a connection string.
3.  **Get `MongoDatabase`**: Get a reference to the target database.
4.  **Get `MongoCollection`**: Get a reference to the target collection, specifying the document type (e.g., `Document.class` or your POJO class).
5.  **Perform Operations**: Use methods like `find()`, `insertOne()`, `updateOne()`, etc., often using helper classes like `Filters`, `Updates`, `Projections`.
6.  **Process Results**: Iterate through `FindIterable` or handle results as needed.
7.  **Close `MongoClient`**: Close the client when the application shuts down to release resources.

```java
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

// ...

String connectionString = "mongodb://localhost:27017";

try (MongoClient mongoClient = MongoClients.create(connectionString)) { // 7. Close client

    // 3. Get Database
    MongoDatabase database = mongoClient.getDatabase("mydatabase");

    // 4. Get Collection (using generic Document type)
    MongoCollection<Document> collection = database.getCollection("users");

    // 5. Create (Insert)
    Document newUser = new Document("name", "Jane Doe")
                           .append("age", 28)
                           .append("city", "New York");
    collection.insertOne(newUser);
    System.out.println("Inserted document ID: " + newUser.getObjectId("_id"));

    // 5. Read (Find)
    System.out.println("Finding users older than 25:");
    try (var cursor = collection.find(gt("age", 25)).iterator()) { // 6. Process
        while (cursor.hasNext()) {
            System.out.println(cursor.next().toJson());
        }
    }

    // 5. Update
    collection.updateOne(eq("name", "Jane Doe"), set("city", "San Francisco"));

    // 5. Delete
    // collection.deleteOne(eq("name", "Jane Doe"));

} catch (Exception e) {
    e.printStackTrace();
}
```

## Considerations

*   **Synchronous vs. Asynchronous**: The driver offers both synchronous (`mongodb-driver-sync`) and reactive (`mongodb-driver-reactivestreams`) APIs.
*   **POJO Codecs**: For seamless POJO mapping, you might need to configure a `CodecRegistry`.
*   **Error Handling**: MongoDB operations can throw specific exceptions.
*   **Connection Pooling**: `MongoClient` handles connection pooling internally. 