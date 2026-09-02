# Copilot instructions for Spring Boot microservices engineering

- Prefer clean, layered Java architecture: controller -> service -> repository/model -> DTOs.
- Keep Spring Boot applications production-ready: configuration externalization, validation, health checks, structured logging, and secure defaults.
- Use Java 25+ idioms and Spring Boot 4 conventions.
- Favor dependency injection, small cohesive services, and explicit contracts.
- Validate request payloads with Jakarta Validation annotations.
- Use meaningful package names and domain-focused classes.
- Write unit tests for business logic and integration tests for HTTP + persistence flows.
- Prefer constructor injection and immutable DTOs when practical.
- For microservices, design APIs with clear boundaries, idempotency awareness, and versioning strategy.
- When adding persistence, prefer JPA with repository abstractions and migration tooling.
- Keep secrets, environment-specific values, and connection settings in configuration properties or environment variables.
- Document public APIs with clear endpoint descriptions and error semantics.

# Java Spring Boot Code Generation Rules
- **Stack:** Java 25+, Spring Boot 4.x, Spring Data JPA, Lombok (no @Data on entities).
- **Architecture:** Maintain strict separation of Controller -> Service -> Repository layers.
- **API Contracts:** Never return JPA Entities from Controllers. Always map to Java `record` objects as DTOs.
- **Dependency Injection:** Always use constructor injection instead of `@Autowired` field injection.
- **Validation:** Always use `jakarta.validation.constraints` on inputs and validate via `@Valid` in controllers.

// add coding standards, logging, exception handling, and testing guidelines as needed
# Coding Standards
- Follow standard Java naming conventions for classes, methods, and variables.
- Use meaningful names for classes, methods, and variables.
- Keep methods short and focused on a single responsibility.
- Avoid magic numbers and hard-coded values; use constants or configuration properties instead.
- Use `final` for variables that should not be reassigned.
- Use `@Slf4j` for logging and log at appropriate levels (INFO, DEBUG, WARN, ERROR).
- Handle exceptions gracefully and provide meaningful error messages to clients.
- Use `@ControllerAdvice` for global exception handling and return standardized error responses.
# Testing Guidelines
- Write unit tests for service layer logic using JUnit 5 and Mockito.
- Write integration tests for controller endpoints using Spring Boot Test and MockMvc.
- Use `@SpringBootTest` for full application context tests and `@DataJpaTest` for repository layer tests.
- Ensure tests are isolated, repeatable, and do not depend on external systems or state.
- Use test data builders or factory methods to create test entities and DTOs.
- Use assertions to verify expected outcomes and edge cases in tests.
- Use code coverage tools to ensure critical paths are tested, but prioritize meaningful tests over coverage percentage.
- Use `@Transactional` in tests when necessary to roll back changes after each test method.
- Use `@BeforeEach` and `@AfterEach` for setup and teardown logic in tests to maintain test isolation.
- Use parameterized tests for testing multiple input scenarios with JUnit 5's `@ParameterizedTest` and `@ValueSource` or `@CsvSource`.
- Use `@MockBean` to mock dependencies in Spring Boot tests when needed, and verify interactions with mocks using Mockito's `verify()` method.
- Use `@TestConfiguration` to define test-specific beans and configurations for integration tests.
- Use `@Nested` test classes to group related tests and improve test organization and readability.
- Use `@DisplayName` to provide descriptive names for test methods and classes, making test reports more readable.
- Use `@ExtendWith(SpringExtension.class)` to integrate Spring TestContext Framework with JUnit 5 for better test support.
- Use `@TestInstance(TestInstance.Lifecycle.PER_CLASS)` for test classes that require shared state or setup across multiple test methods.
- Use `@DirtiesContext` to indicate that the application context should be reset after a test method or class, if necessary, to avoid side effects on other tests.
- Use `@ActiveProfiles` to specify different Spring profiles for testing different configurations or environments.
- Use `@Sql` or `@SqlGroup` to execute SQL scripts before or after test methods for setting up or cleaning up test data in the database.
- Use `@DynamicPropertySource` to dynamically set properties for tests, such as database URLs or API keys, based on the test environment or configuration.
- Use `@Testcontainers` to manage Docker containers for integration tests that require external services, such as databases or message brokers.
- Use `@MockMvc` to perform HTTP requests and assertions on controller endpoints in integration tests, and configure it with `@AutoConfigureMockMvc` for automatic setup.
- Use `@WebMvcTest` to test only the web layer of the application, focusing on controller behavior and request/response handling, without loading the full application context.

# Logging Guidelines
- Use structured logging with key-value pairs for better log analysis and searching.
- Log at appropriate levels: INFO for general information, DEBUG for detailed debugging information, WARN for potential issues, and ERROR for critical failures.
- Include relevant context in log messages, such as request IDs, user IDs, and transaction IDs, to facilitate tracing and debugging.
- Avoid logging sensitive information, such as passwords, API keys, or personally identifiable information (PII).
- Use MDC (Mapped Diagnostic Context) to include contextual information in log messages, such as request IDs or user IDs, and ensure that MDC is cleared after each request to prevent data leakage between requests.
- Use log rotation and retention policies to manage log file sizes and prevent disk space issues.
- Use correlation IDs to trace requests across microservices and log entries, enabling easier debugging and monitoring of distributed systems.
- Use structured logging libraries, such as Logback or Log4j2, to format log messages in JSON or other structured formats for better integration with log management systems.
- Use logging frameworks that support asynchronous logging to improve application performance and reduce the impact of logging on request processing times.
- Use log aggregation and monitoring tools, such as ELK Stack (Elasticsearch, Logstash, Kibana) or Grafana Loki, to centralize and analyze logs from multiple microservices and gain insights into application behavior and performance