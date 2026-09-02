---
name: junit-test-strategy
description: Expert guidance for defining and reviewing automated testing strategy for Java and Spring Boot projects, including test pyramid, risk-based coverage, regression protection, and quality gates.
---

# JUnit Test Strategy Skill

## Purpose

Use this skill when reviewing or designing the overall testing strategy rather than an individual test class.

## Test pyramid

Aim for a practical distribution:

```text
             /\
            /E2E\
           /----\
          /Integ.\
         /--------\
        /  Unit    \
       /------------\
```

The exact ratio is less important than having:

- many fast unit tests
- focused integration tests
- relatively few end-to-end tests
- appropriate API/event contract tests

## Risk-based testing

Prioritize tests around:

- critical business rules
- authorization/security
- financial calculations
- persistence correctness
- external integrations
- event processing
- retries and failures
- data integrity
- backward compatibility

High line coverage does not automatically mean high confidence.

## Coverage

Treat coverage as a signal.

Ask:

- Are important branches tested?
- Are failure paths tested?
- Are boundary conditions tested?
- Are authorization decisions tested?
- Are persistence constraints tested?
- Would a realistic defect be detected?

## Mutation-oriented thinking

Ask:

> If a developer changed this condition, operator, return value, or authorization rule incorrectly, would the tests fail?

For:

```java
if (amount > limit)
```

consider:

- below limit
- exactly at limit
- above limit

## Regression tests

Important production defects should normally result in regression tests.

The regression test should reproduce the original failure and protect the externally observable behavior.

## Test data

Prefer intention-revealing data:

```java
var customer = customerWithStatus(ACTIVE);
var order = orderFor(customer);
```

Avoid giant fixtures where important conditions become invisible.

## Boundary cases

Consider relevant:

- null
- empty
- blank
- zero
- negative values
- maximum values
- duplicates
- missing entities
- invalid state transitions
- malformed external responses
- date/time boundaries
- concurrency

Only add cases that are meaningful for the domain.

## API and event contracts

Protect compatibility at boundaries:

- required fields
- optional fields
- status codes
- error structures
- event schemas
- serialization
- version compatibility

## Quality gates

A practical CI strategy may include:

- compilation
- unit tests
- integration tests
- static analysis
- coverage thresholds where useful
- architecture checks where appropriate

Avoid thresholds that incentivize meaningless tests.

## Review questions

- What behavior is actually protected?
- Which important failure mode is untested?
- Is the test at the right pyramid level?
- Would a realistic defect be caught?
- Are integration boundaries covered?
- Are security decisions covered?
- Are regression tests added for important defects?

## Definition of done

A testing strategy is effective when it provides fast feedback for ordinary changes and strong confidence at critical business and integration boundaries.
