---
name: spring-boot-testing
description: Expert guidance for testing Spring Boot applications using unit tests, Spring test slices, full-context integration tests, MVC/WebFlux testing, security, configuration, and application-context management.
---

# Spring Boot Testing Skill

## Purpose

Use this skill for testing Spring Boot framework integration and application boundaries.

Choose the smallest test scope that provides meaningful confidence.

## Test scope

Prefer this hierarchy:

1. Plain JUnit unit test
2. Spring test slice
3. Integration test
4. End-to-end/system test

Do not use `@SpringBootTest` by default.

## Unit tests

Use plain JUnit when Spring is not part of the behavior:

```java
class PricingServiceTest {
}
```

This gives fast feedback and avoids application-context startup.

## Spring test slices

Use focused slices when appropriate:

- `@WebMvcTest` — MVC controllers
- `@WebFluxTest` — WebFlux endpoints
- `@DataJpaTest` — JPA repositories
- `@JdbcTest` — JDBC behavior
- `@JsonTest` — JSON serialization

Keep slices focused. Do not recreate the entire application context unnecessarily.

## Full context

Use `@SpringBootTest` when behavior genuinely crosses Spring-managed boundaries.

Typical examples:

- service + repository integration
- application configuration
- security configuration
- multiple collaborating beans
- startup wiring

Do not use full-context tests for pure business logic.

## MVC and REST testing

Use `MockMvc` for MVC HTTP-boundary tests when an actual server is not required.

Verify:

- status
- response body
- validation
- relevant headers
- error behavior
- security behavior

Example:

```java
mockMvc.perform(post("/orders")
        .contentType(MediaType.APPLICATION_JSON)
        .content(request))
    .andExpect(status().isCreated())
    .andExpect(jsonPath("$.id").exists());
```

Use `WebTestClient` for reactive applications where appropriate.

## Security

Security behavior is part of the application contract.

Cover relevant cases:

- unauthenticated request
- authenticated request
- missing authority
- valid authority
- invalid/missing credentials

Do not disable security merely to simplify tests.

## Application context hygiene

Review for:

- excessive `@MockBean` usage
- unnecessary context customization
- test profiles that disable important production behavior
- duplicate test configuration
- context-cache fragmentation
- hidden test ordering dependencies

Keep test configuration minimal and explicit.

## Database testing

When persistence behavior matters, use a real database through Testcontainers rather than mocking repositories.

Verify:

- migrations
- mappings
- constraints
- transactions
- queries
- indexes where relevant
- database-specific behavior

Do not replace PostgreSQL-specific production behavior with H2 merely for convenience.

## Configuration

Test important configuration and startup behavior.

Watch for:

- missing properties
- incorrect profiles
- invalid bean wiring
- accidental production defaults
- environment-specific behavior

## Review checklist

- Is the test scope correct?
- Could it be a plain JUnit test?
- Could a Spring slice be used?
- Is framework behavior actually being tested?
- Is security tested realistically?
- Are persistence boundaries tested realistically?
- Is test configuration minimal?
- Is Spring context reuse preserved?

## Anti-patterns

Flag:

- `@SpringBootTest` everywhere
- disabling security
- mocking repositories in repository integration tests
- mocking every collaborator in integration tests
- giant test configurations
- arbitrary sleeps
- test profiles that remove critical production behavior

## Definition of done

Spring Boot tests should use the narrowest meaningful scope and verify framework behavior only where framework integration matters.
