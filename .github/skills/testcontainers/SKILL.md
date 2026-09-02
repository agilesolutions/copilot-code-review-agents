---
name: testcontainers
description: Expert guidance for reliable Testcontainers integration tests for PostgreSQL, MongoDB, Kafka, RabbitMQ, and other infrastructure used by Java and Spring Boot applications.
---

# Testcontainers Skill

## Purpose

Use Testcontainers when real infrastructure behavior is part of the behavior being verified.

Typical boundaries:

- PostgreSQL
- MongoDB
- Kafka
- RabbitMQ
- other external infrastructure

## When to use

Use containers when correctness depends on:

- SQL/database semantics
- constraints and indexes
- migrations
- transactions
- serialization
- broker behavior
- messaging
- infrastructure-specific configuration

Do not introduce containers into tests that can be meaningful pure unit tests.

## PostgreSQL

Prefer real PostgreSQL for repository and persistence integration tests when PostgreSQL behavior matters.

Verify:

- Flyway/Liquibase migrations
- schema constraints
- queries
- transactions
- generated identifiers
- indexes
- PostgreSQL-specific behavior

Avoid H2 as a substitute when production uses PostgreSQL-specific semantics.

## Lifecycle

Use stable lifecycle management:

```java
@Testcontainers
class RepositoryIT {

    @Container
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:<approved-version>");
}
```

Pin versions intentionally. Avoid `latest` in reproducible CI.

## Spring properties

Use supported Spring mechanisms such as `@DynamicPropertySource` when needed:

```java
@DynamicPropertySource
static void databaseProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
}
```

Prefer framework-supported integration over custom environment mutation.

## Database isolation

Choose isolation deliberately:

- separate containers when strong isolation is required
- rollback where transaction semantics permit it
- explicit cleanup
- schema recreation for selected suites

Do not assume rollback covers every database operation.

## Migrations

When migrations are part of application behavior, execute the real migration mechanism in integration tests.

Do not disable migrations simply to make tests easier.

Check:

- migration success
- expected schema
- constraints
- ordering
- compatibility

## Kafka

Use a real Kafka-compatible broker when broker behavior matters.

Test:

- serialization/deserialization
- topics
- producer behavior
- consumer behavior
- error handling
- retries
- offsets where relevant
- eventual consistency

Never synchronize asynchronous tests using arbitrary sleeps.

Prefer condition-based waiting:

```java
await()
    .atMost(Duration.ofSeconds(10))
    .untilAsserted(() ->
        assertThat(repository.findById(id)).isPresent());
```

The condition should represent observable completion.

## RabbitMQ

When broker semantics matter, verify:

- exchanges
- queues
- bindings
- routing
- acknowledgements
- retries
- dead-letter behavior

## Test data

Make data explicit and isolated.

Avoid dependence on:

- previous tests
- shared mutable database state
- previous Kafka messages
- global fixtures
- test execution order

## CI

Ensure container tests work consistently in CI.

Consider:

- Docker/container runtime availability
- image pulls
- resource limits
- startup times
- network restrictions
- parallel execution

## Performance

Optimize carefully without compromising isolation.

Possible approaches:

- static containers per test class
- controlled container reuse
- grouping expensive integration tests
- separating unit and integration tasks

## Review checklist

- Is the container necessary?
- Is the image version controlled?
- Is lifecycle correct?
- Are tests isolated?
- Are migrations executed?
- Is async behavior condition-based?
- Is cleanup reliable?
- Is CI reproducible?
- Is startup cost justified?

## Anti-patterns

Flag:

- `postgres:latest`
- `Thread.sleep(...)`
- disabled migrations
- mocked infrastructure in infrastructure tests
- shared mutable state
- execution-order dependencies
- production secrets in tests

## Definition of done

A Testcontainers test should exercise the real infrastructure behavior that matters while remaining deterministic, isolated, reproducible, and maintainable.
