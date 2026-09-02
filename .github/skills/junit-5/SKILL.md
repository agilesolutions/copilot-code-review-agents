---
name: junit-5
description: Expert guidance for designing, implementing, reviewing, and improving JUnit 5 tests in modern Java applications.
---

# JUnit 5 Skill

## Purpose

Use this skill for JUnit 5 test design, implementation, review, and refactoring in Java applications.

Priorities:

- behavior-focused tests
- deterministic execution
- clear failure diagnostics
- minimal and appropriate setup
- maintainable test suites
- fast feedback where possible

## Core principles

1. Test observable behavior rather than implementation details.
2. Give each test a clear reason to fail.
3. Use descriptive test names.
4. Prefer the smallest appropriate test scope.
5. Avoid unnecessary mocking.
6. Never use arbitrary sleeps for synchronization.
7. Keep tests independent and deterministic.
8. Prefer AssertJ for expressive assertions.
9. Use parameterized tests for meaningful input variations.
10. Do not weaken production behavior merely to make a test pass.

## Test naming

Prefer:

```java
@Test
void shouldRejectOrderWhenCustomerIsUnknown() {
}
```

A useful convention is:

```text
should<ExpectedBehavior>When<Condition>
```

Avoid generic names such as `test1`, `testService`, or `testMethod`.

## Arrange / Act / Assert

Keep the behavior visible:

```java
@Test
void shouldCalculateTotalIncludingTax() {
    // Arrange
    var order = new Order(...);

    // Act
    var total = calculator.calculate(order);

    // Assert
    assertThat(total).isEqualTo(expected);
}
```

Avoid hiding important conditions inside large shared fixtures.

## Assertions

Prefer AssertJ:

```java
assertThat(result)
    .isNotNull()
    .extracting(Order::status)
    .isEqualTo(OrderStatus.APPROVED);
```

Collections:

```java
assertThat(results)
    .hasSize(2)
    .extracting(Order::id)
    .containsExactly(id1, id2);
```

Exceptions:

```java
assertThatThrownBy(() -> service.execute(command))
    .isInstanceOf(DomainException.class)
    .hasMessageContaining("unknown customer");
```

Assertions should be strong enough that an incorrect implementation is unlikely to pass.

## Parameterized tests

Use `@ParameterizedTest` when the same behavior must be validated for multiple representative inputs.

Use:

- `@ValueSource` for simple values
- `@CsvSource` for compact pairs/tuples
- `@MethodSource` for richer domain objects
- `@EnumSource` for enum variations

Do not turn unrelated behaviors into one parameterized test merely to reduce line count.

## Lifecycle

Use `@BeforeEach` only for genuinely common setup.

Avoid:

- huge shared fixtures
- mutable static state
- hidden test dependencies
- order-dependent setup

Use `@BeforeAll` sparingly because shared state can reduce isolation.

## Nested tests

Use `@Nested` when it makes business conditions easier to understand:

```java
@Nested
class WhenCustomerIsUnknown {

    @Test
    void shouldRejectOrder() {
    }
}
```

Do not create deep nesting without semantic value.

## Mockito

Mock collaborators when isolation is appropriate.

Good candidates include:

- external services
- gateways
- unstable infrastructure
- expensive collaborators
- dependencies outside the unit's responsibility

Do not mock:

- the class under test
- simple value objects
- every dependency automatically

Verify interactions only when the interaction is part of the behavior.

Avoid tests that only prove that a mock method was called while failing to verify the resulting behavior.

## JUnit extensions

Use extensions for reusable infrastructure and lifecycle concerns.

Prefer ordinary JUnit mechanisms when they are simpler.

## Determinism

Tests must not depend on:

- wall-clock timing without control
- network availability unless explicitly testing integration
- random uncontrolled values
- test execution order
- mutable global state

Control time and randomness when those concepts affect behavior.

## Review checklist

For every test ask:

- What behavior does this protect?
- Is the test name clear?
- Is the scope appropriate?
- Are the assertions meaningful?
- Could a broken implementation still pass?
- Is the test deterministic?
- Is mocking justified?
- Is shared state avoided?
- Are important edge cases covered?
- Does failure identify the problem quickly?

## Anti-patterns

Flag:

- `Thread.sleep(...)`
- empty tests
- weak assertions
- excessive mocks
- implementation-detail assertions
- giant setup methods
- disabled tests without justification
- random uncontrolled test data
- execution-order dependencies
- testing private methods directly

## Definition of done

A JUnit test is complete when it clearly communicates the behavior being protected, fails for the right reason, and provides durable regression protection.
