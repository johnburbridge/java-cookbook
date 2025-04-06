# Event-Driven Programming Recipe

Event-Driven Architecture (EDA) is a software architecture paradigm promoting the production, detection, consumption of, and reaction to events. An event represents a significant change in state.

## Core Concepts

*   **Event**: A record of something significant that happened. Events are typically immutable facts.
*   **Event Producer (Publisher, Source)**: Generates or publishes events.
*   **Event Consumer (Subscriber, Sink, Listener)**: Receives and processes events.
*   **Event Channel (Broker, Bus, Router)**: The intermediary infrastructure that transports events from producers to consumers. This decouples producers from consumers.

## Models of Event Interaction

*   **Publish/Subscribe (Pub/Sub)**: Producers publish events to a topic/channel without specific knowledge of the consumers. Consumers subscribe to topics they are interested in and receive all events published to that topic. The event channel broadcasts the event to all active subscribers.
*   **Event Streaming**: Events are written to an immutable, ordered log (stream). Consumers read from the stream at their own pace, maintaining their position (offset). Events are often retained for a configurable period. Apache Kafka is a prime example.
*   **Event Queue**: Events are sent to a queue. Typically, a single consumer reads and processes an event from the queue (point-to-point). Used for distributing work among multiple instances of a consumer.

## Benefits of EDA

*   **Loose Coupling**: Producers and consumers are independent; they don't need direct knowledge of each other, only the event format and the channel.
*   **Scalability**: Consumers and producers can be scaled independently.
*   **Resilience**: If a consumer fails, other parts of the system (or other consumers) can continue operating. Events can often be reprocessed.
*   **Asynchronous Operations**: Producers can publish events without waiting for consumers to process them, improving responsiveness.
*   **Extensibility**: New consumers can be added to react to existing events without modifying producers.

## Common Technologies in Java

*   **Message Brokers (implement Pub/Sub and/or Queues)**:
    *   **Apache Kafka**: A distributed event streaming platform. High-throughput, fault-tolerant, persistent log.
    *   **RabbitMQ**: A popular, mature message broker supporting multiple messaging protocols (AMQP, MQTT, STOMP).
    *   **ActiveMQ**: Another robust message broker from Apache, supporting JMS, AMQP, MQTT, etc.
    *   **Cloud Provider Services**: AWS SQS/SNS, Google Pub/Sub, Azure Service Bus/Event Grid.
*   **Java Messaging Service (JMS)**: A standard Java API for message-oriented middleware (defines interfaces for Queues and Topics). Implementations are provided by brokers like ActiveMQ or built into Jakarta EE servers.
*   **Framework Integrations**: Frameworks often provide abstractions:
    *   **Spring Cloud Stream**: Simplifies building event-driven microservices connected to brokers like Kafka or RabbitMQ.
    *   **Spring Integration**: Provides components for building message-driven applications.
    *   **Akka / Pekko**: Actor-based toolkit well-suited for concurrent and event-driven systems.
    *   **Quarkus / Micronaut**: Often have extensions for Kafka, AMQP, etc.
*   **In-Process Event Buses**: Libraries like Guava's `EventBus` or dedicated application framework event systems (e.g., Spring Application Events) for handling events *within* a single application instance.

## Basic Workflow (Conceptual Kafka Producer/Consumer)

```java
// --- Producer ---
Properties props = new Properties();
props.put("bootstrap.servers", "localhost:9092");
props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

KafkaProducer<String, String> producer = new KafkaProducer<>(props);
try {
    producer.send(new ProducerRecord<>("my-topic", "key1", "Event Data 1"));
} finally {
    producer.close();
}

// --- Consumer ---
Properties props = new Properties();
props.put("bootstrap.servers", "localhost:9092");
props.put("group.id", "my-consumer-group");
props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
props.put("auto.offset.reset", "earliest");

KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
try {
    consumer.subscribe(Collections.singletonList("my-topic"));
    while (true) { // Poll loop
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
        for (ConsumerRecord<String, String> record : records) {
            System.out.printf("Received event: key=%s, value=%s, partition=%d, offset=%d\n",
                    record.key(), record.value(), record.partition(), record.offset());
        }
    }
} finally {
    consumer.close();
}
```

Event-driven architectures are powerful for building decoupled, scalable, and resilient systems. 