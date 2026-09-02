# Spring Boot Copilot Code Review Agents

A specialist **GitHub Copilot custom-agent suite for reviewing Java and Spring Boot applications**.

The purpose of this project is to provide a reusable set of AI-assisted software engineering agents that perform focused code reviews from different engineering perspectives.

Rather than relying on a single generic code-review prompt, the solution separates concerns into specialist reviewers for:

* Java and Spring Boot application code
* Spring Security
* Persistence and database design
* Observability and production diagnostics

The agents can be used independently or combined into a broader **architecture and engineering review workflow**.

---

# Table of Contents

* [Overview](#overview)
* [Why Specialist Review Agents](#why-specialist-review-agents)
* [Target Audience](#target-audience)
* [Repository Structure](#repository-structure)
* [Agent Responsibilities](#agent-responsibilities)
* [Review Architecture](#review-architecture)
* [1. Spring Boot Code Reviewer](#1-spring-boot-code-reviewer)
* [2. Spring Security Reviewer](#2-spring-security-reviewer)
* [3. Spring Persistence Reviewer](#3-spring-persistence-reviewer)
* [4. Spring Observability Reviewer](#4-spring-observability-reviewer)
* [Review Severity Model](#review-severity-model)
* [How to Use the Agents](#how-to-use-the-agents)
* [Recommended Review Workflow](#recommended-review-workflow)
* [Pull Request Review](#pull-request-review)
* [Feature Review](#feature-review)
* [Security Review](#security-review)
* [Persistence Review](#persistence-review)
* [Observability Review](#observability-review)
* [Full Specialist Review](#full-specialist-review)
* [Architecture Review](#architecture-review)
* [Agent Output](#agent-output)
* [Review Principles](#review-principles)
* [Example Prompts](#example-prompts)
* [Integration With Solution Architecture](#integration-with-solution-architecture)
* [Recommended Development Lifecycle](#recommended-development-lifecycle)
* [Quality Gates](#quality-gates)
* [Extending the Agent Suite](#extending-the-agent-suite)
* [Future Specialist Agents](#future-specialist-agents)
---

# Overview

This repository contains GitHub Copilot custom agents specifically designed for **professional Java and Spring Boot software engineering**.

The agents are intended to complement developers, reviewers and solution architects.

They should not replace human engineering judgement.

The primary objective is to identify problems that are easy to overlook during normal development:

* functional defects
* security vulnerabilities
* transaction problems
* persistence issues
* scalability risks
* resilience problems
* observability gaps
* architectural coupling
* insufficient tests
* production-readiness issues

The agents deliberately use different review perspectives so that a problem missed by one reviewer can potentially be identified by another.

---

# Why Specialist Review Agents?

A traditional generic code-review prompt often attempts to review everything simultaneously.

That creates several problems.

A generic reviewer may know that Spring Security exists, for example, but fail to recognize a subtle authorization flaw.

Likewise, it may identify that JPA is being used without recognizing:

* an N+1 query
* an incorrect transaction boundary
* a lost-update problem
* an unsafe Flyway migration

Specialist agents allow each reviewer to concentrate on a particular engineering concern.

The model becomes:

```text
                ┌───────────────────────────┐
                │     Software Change       │
                └─────────────┬─────────────┘
                              │
          ┌───────────────────┼───────────────────┐
          │                   │                   │
          ▼                   ▼                   ▼
   Spring Boot           Security            Persistence
   Code Review           Review               Review
          │                   │                   │
          └───────────────────┼───────────────────┘
                              │
                              ▼
                       Observability
                          Review
                              │
                              ▼
                     Consolidated Review
```

This provides a more structured engineering review.

---

# Target Audience

The agents are useful for several audiences.

## Developers

Use the agents during implementation to identify problems before creating a Pull Request.

Typical use:

```text
Review this service before I create the PR.
```

---

## Senior Developers

Use specialist reviews for complex changes involving:

* security
* persistence
* messaging
* distributed systems
* production diagnostics

---

## Software Engineers

Use the agents as an additional engineering review layer.

The agents can help identify issues that may otherwise only be discovered during:

* integration testing
* security testing
* performance testing
* production operation

---

## Solution Architects

Use the agents to validate implementation against architectural principles.

For example:

```text
Does this implementation preserve the intended
service boundary and dependency direction?
```

---

## Technical Leads

Use the suite as a repeatable review standard across multiple teams.

---

# Repository Structure

Recommended repository structure:

```text
.github/
└── agents/
    ├── solution-architect.agent.md
    │
    ├── springboot-code-reviewer.agent.md
    ├── spring-security-reviewer.agent.md
    ├── spring-persistence-reviewer.agent.md
    └── spring-observability-reviewer.agent.md
```

The four review agents are:

| Agent                           | Primary responsibility                                                     |
| ------------------------------- | -------------------------------------------------------------------------- |
| `springboot-code-reviewer`      | Java, Spring Boot, REST, architecture, testing, resilience and performance |
| `spring-security-reviewer`      | Authentication, authorization, OAuth2, OIDC, JWT and secrets               |
| `spring-persistence-reviewer`   | JPA, Hibernate, PostgreSQL, transactions and Flyway                        |
| `spring-observability-reviewer` | Metrics, logs, traces, OpenTelemetry and production diagnostics            |

---

# Agent Responsibilities

## Application Layer

Handled primarily by:

```text
springboot-code-reviewer
```

Focus:

* Java
* Spring Boot
* REST
* application architecture
* resilience
* testing
* performance

---

## Security Layer

Handled by:

```text
spring-security-reviewer
```

Focus:

* authentication
* authorization
* OAuth2
* OIDC
* JWT
* secrets
* API security

---

## Persistence Layer

Handled by:

```text
spring-persistence-reviewer
```

Focus:

* JPA
* Hibernate
* Spring Data
* PostgreSQL
* transactions
* concurrency
* Flyway
* database performance

---

## Observability Layer

Handled by:

```text
spring-observability-reviewer
```

Focus:

* Micrometer
* OpenTelemetry
* metrics
* logging
* distributed tracing
* health
* readiness
* production diagnostics

---

# Review Architecture

The recommended architecture is:

```text
                         Developer
                             │
                             ▼
                       Code Change
                             │
                             ▼
                ┌────────────────────────┐
                │ Spring Boot Reviewer   │
                └────────────┬───────────┘
                             │
              ┌──────────────┼──────────────┐
              │              │              │
              ▼              ▼              ▼
         Security       Persistence    Observability
          Review           Review          Review
              │              │              │
              └──────────────┼──────────────┘
                             │
                             ▼
                    Engineering Findings
                             │
                             ▼
                       Human Review
```

The human developer or architect remains responsible for the final decision.

---

# 1. Spring Boot Code Reviewer

File:

```text
.github/agents/springboot-code-reviewer.agent.md
```

This is the primary application-level reviewer.

## Responsibilities

Reviews:

* Java implementation
* Spring Boot design
* dependency injection
* REST APIs
* application services
* domain boundaries
* resilience
* testing
* concurrency
* performance
* maintainability

## Typical questions

```text
Is the implementation correct?

Is the class responsible for too many things?

Is business logic in the controller?

Are dependencies properly isolated?

Are external calls protected by timeouts?

Are failures handled correctly?

Are the tests meaningful?

Is the implementation production ready?
```

---

# 2. Spring Security Reviewer

File:

```text
.github/agents/spring-security-reviewer.agent.md
```

This is the security specialist.

## Responsibilities

Reviews:

* Spring Security
* SecurityFilterChain
* OAuth2
* OIDC
* JWT
* resource servers
* client credentials
* authorization
* roles
* authorities
* scopes
* secrets
* CORS
* CSRF
* actuator exposure

## Typical questions

```text
Can an unauthenticated user access this endpoint?

Can an authenticated user access another user's data?

Are authorization rules correctly enforced?

Is the JWT actually validated?

Are issuer and audience validated?

Are excessive OAuth2 scopes requested?

Are secrets stored safely?

Could sensitive information leak through logs?
```

Security findings should prioritize actual attack paths.

---

# 3. Spring Persistence Reviewer

File:

```text
.github/agents/spring-persistence-reviewer.agent.md
```

This is the database and persistence specialist.

## Responsibilities

Reviews:

* Spring Data
* JPA
* Hibernate
* PostgreSQL
* transactions
* locking
* concurrency
* database constraints
* Flyway
* query performance

## Typical questions

```text
Is the transaction boundary correct?

Could concurrent updates overwrite data?

Is this query causing N+1 behaviour?

Are result sets bounded?

Are database constraints enforcing important invariants?

Could this migration lock a production table?

Is the database connection pool at risk?

Are external calls occurring inside database transactions?
```

---

# 4. Spring Observability Reviewer

File:

```text
.github/agents/spring-observability-reviewer.agent.md
```

This is the production observability specialist.

It is particularly useful for applications using:

* Micrometer
* OpenTelemetry
* Grafana
* Prometheus/Mimir
* Loki
* Tempo
* Grafana Alloy

## Responsibilities

Reviews:

* metrics
* logs
* traces
* OpenTelemetry
* Micrometer
* correlation
* health endpoints
* readiness
* liveness
* alertability
* diagnostic capability

## Typical questions

```text
Can an operator determine why this request failed?

Can traces cross service boundaries?

Are metrics using bounded cardinality?

Are logs structured?

Are sensitive values excluded?

Can database connection exhaustion be detected?

Can downstream latency be measured?

Is readiness correctly separated from liveness?
```

---

# Review Severity Model

All agents use the same severity model.

## CRITICAL

Potential:

* security compromise
* authentication bypass
* authorization bypass
* data corruption
* data loss
* severe production outage

Action:

**Must be resolved before release.**

---

## HIGH

Potential:

* production failure
* serious correctness problem
* transaction inconsistency
* major performance issue
* significant reliability issue

Action:

**Should normally be resolved before merge/release.**

---

## MEDIUM

Meaningful engineering problem.

Examples:

* maintainability issue
* inadequate validation
* missing tests
* problematic coupling
* resilience gap

Action:

**Should be addressed, but release impact depends on context.**

---

## LOW

Minor improvement.

Examples:

* readability
* naming
* small duplication
* minor maintainability improvement

---

## INFO

Optional recommendation.

Examples:

* architectural improvement
* future optimization
* design observation

---

# How to Use the Agents

The agents are designed to be used directly from GitHub Copilot.

The exact invocation syntax depends on the GitHub Copilot client/version being used, but conceptually you select the appropriate custom agent and provide a review instruction.

---

# Basic Code Review

Use:

```text
Spring Boot Code Reviewer
```

Prompt:

```text
Review the current changes.

Focus on functional correctness,
Spring Boot design, REST APIs,
testing, resilience and maintainability.
```

---

# Security Review

Use:

```text
Spring Security Reviewer
```

Prompt:

```text
Perform a security review of the current implementation.

Focus on authentication, authorization,
OAuth2/OIDC, JWT validation, secrets,
endpoint exposure and privilege escalation.
```

---

# Persistence Review

Use:

```text
Spring Persistence Reviewer
```

Prompt:

```text
Review the persistence implementation.

Focus on JPA/Hibernate,
transaction boundaries,
PostgreSQL queries,
concurrency,
locking, Flyway migrations
and database performance.
```

---

# Observability Review

Use:

```text
Spring Observability Reviewer
```

Prompt:

```text
Review the observability implementation.

Focus on Micrometer, OpenTelemetry,
structured logging, metrics, tracing,
health/readiness and production diagnostics.
```

---

# Recommended Review Workflow

For a significant change, run all four reviews.

```text
                    Code Change
                         │
                         ▼
              ┌─────────────────────┐
              │ Spring Boot Review  │
              └──────────┬──────────┘
                         │
             ┌───────────┼───────────┐
             ▼           ▼           ▼
        Security    Persistence   Observability
          Review       Review        Review
             │           │           │
             └───────────┼───────────┘
                         ▼
                 Consolidate Findings
                         │
                         ▼
                  Developer Review
                         │
                         ▼
                    Fix Issues
                         │
                         ▼
                  Repeat if needed
```

---

# Pull Request Review

Before merging a Pull Request:

### Step 1 — Application Review

Run:

```text
Spring Boot Code Reviewer
```

Ask for:

* correctness
* architecture
* testing
* resilience
* performance

---

### Step 2 — Security Review

Run:

```text
Spring Security Reviewer
```

Especially important when changing:

* endpoints
* authentication
* authorization
* roles
* JWT handling
* OAuth2 clients

---

### Step 3 — Persistence Review

Run:

```text
Spring Persistence Reviewer
```

Especially important when changing:

* entities
* repositories
* queries
* transactions
* database schema
* Flyway migrations

---

### Step 4 — Observability Review

Run:

```text
Spring Observability Reviewer
```

Especially important when introducing:

* new APIs
* asynchronous processing
* messaging
* external integrations
* new services

---

# Feature Review

For a new feature, use the following sequence.

```text
Feature
  │
  ▼
Application Review
  │
  ▼
Security Review
  │
  ▼
Persistence Review
  │
  ▼
Observability Review
  │
  ▼
Integration Tests
  │
  ▼
Pull Request
```

This provides a useful "definition of done" for implementation quality.

---

# Security Review

Always perform a security specialist review when changing:

* authentication
* authorization
* OAuth2
* OIDC
* JWT
* user management
* API endpoints
* sensitive data
* secrets
* Keycloak integration

Example:

```text
Review all security implications of this change.

Do not limit the review to the changed Java class.
Inspect the complete authentication and authorization path.
```

---

# Persistence Review

Run the persistence specialist when changing:

```text
@Entity
@Repository
JpaRepository
@Transactional
Flyway
SQL
JPQL
database schema
```

Also run it when changing business operations that modify multiple records.

---

# Observability Review

Run the observability specialist when introducing:

```text
REST endpoint
Kafka consumer
Kafka producer
scheduled job
async processing
external API
database-heavy operation
new microservice
```

The objective is to ensure the new behaviour can be diagnosed in production.

---

# Full Specialist Review

For major Pull Requests:

```text
Run a complete specialist review.

Review this change from four perspectives:

1. Spring Boot application quality
2. Spring Security
3. Persistence and database
4. Observability

Inspect related source files, configuration and tests.

Do not duplicate findings unnecessarily.

Prioritize CRITICAL and HIGH findings.
```

The result should be treated as an engineering review rather than an automatic merge decision.

---

# Architecture Review

For architectural changes, combine the specialist agents with:

```text
solution-architect.agent.md
```

Recommended sequence:

```text
                 Solution Architect
                         │
                         ▼
                 Architectural Review
                         │
          ┌──────────────┼──────────────┐
          │              │              │
          ▼              ▼              ▼
       Security     Persistence    Observability
          │              │              │
          └──────────────┼──────────────┘
                         ▼
                Spring Boot Review
                         │
                         ▼
                  Final Assessment
```

The architect should establish:

* system boundaries
* service boundaries
* responsibilities
* integration patterns
* security boundaries
* data ownership

The specialist reviewers then validate the implementation.

---

# Agent Output

All agents use a common structure.

```text
## Review Summary

Overall assessment

Important findings

Architectural concerns

Security concerns

Testing concerns

Production readiness


## Findings

### [HIGH] Transaction boundary allows inconsistent state

Location:
SomeService.java:42

Problem:
...

Why it matters:
...

Recommendation:
...

Example:
...


## Overall Assessment

CHANGES REQUESTED


## Top 3 Actions

1. ...
2. ...
3. ...


## Positive Observations

1. ...
2. ...
```

This consistency makes findings easier to consolidate.

---

# Review Principles

The agents follow several important principles.

## Evidence First

The agent should inspect the repository before making conclusions.

It should prefer actual project evidence over generic advice.

---

## No Invented Problems

The agent must never invent:

* classes
* methods
* vulnerabilities
* configuration
* dependencies
* test results

If something cannot be verified:

```text
Unable to verify from the available repository context.
```

---

## Defect vs Recommendation

The agents distinguish between:

### Defect

Something demonstrably incorrect.

### Risk

Something potentially dangerous under specific conditions.

### Recommendation

An engineering improvement.

A subjective preference must never be presented as a defect.

---

# Minimal Change Principle

Recommendations should generally prefer the smallest safe change.

Avoid automatically recommending:

* complete rewrites
* unnecessary abstractions
* framework changes
* architecture migrations
* new dependencies

unless there is a concrete reason.

---

# Testing Principle

The agents must not claim:

```text
Tests pass
```

unless tests were actually executed and the result is known.

Likewise:

```text
No vulnerabilities exist
```

should never be claimed simply because the agent did not find one.

The correct statement is:

```text
No obvious vulnerability was identified during this review.
```

---

# Production Readiness

A production-ready Spring Boot service should be considered across multiple dimensions.

```text
                 Production Readiness
                         │
        ┌────────────────┼────────────────┐
        │                │                │
        ▼                ▼                ▼
    Correctness       Security       Reliability
        │                │                │
        ├────────────────┼────────────────┤
        │                │                │
        ▼                ▼                ▼
   Persistence       Testing       Observability
        │                │                │
        └────────────────┼────────────────┘
                         ▼
                    Operations
```

No single review dimension is sufficient.

---

# Quality Gates

The following gates are recommended.

## Gate 1 — Compile

The application must compile successfully.

---

## Gate 2 — Automated Tests

Relevant tests should pass.

Recommended layers:

```text
Unit Tests
Integration Tests
Security Tests
Persistence Tests
Contract Tests
```

---

## Gate 3 — Application Review

No unresolved:

```text
CRITICAL
HIGH
```

application findings.

---

## Gate 4 — Security Review

No unresolved:

```text
CRITICAL
HIGH
```

security findings.

---

## Gate 5 — Persistence Review

No unresolved data-integrity or transaction blockers.

---

## Gate 6 — Observability Review

Critical production paths should be observable.

---

# Example End-to-End Review

Suppose a new endpoint is introduced:

```text
POST /api/orders
```

The review should consider:

### Spring Boot

* controller design
* validation
* service boundary
* error handling
* idempotency
* tests

### Security

* authentication
* authorization
* ownership
* scopes
* JWT validation

### Persistence

* transaction boundary
* order persistence
* duplicate creation
* locking
* constraints

### Observability

* request trace
* business metrics
* structured logging
* downstream tracing
* failure diagnostics

This produces a much stronger review than simply asking:

```text
Review this controller.
```

---

# Example Prompts

This section provides practical example prompts for invoking the specialist code-review agents.

The prompts are intentionally structured around **review intent** rather than simply asking for a generic “code review”. This allows the appropriate specialist to focus on its engineering domain while avoiding unnecessary overlap with the other reviewers.

---

## 1. General Spring Boot Code Review

### Basic Review

```text
Review this Spring Boot implementation for correctness, maintainability,
Spring best practices, and production readiness.

Focus on:
- Java correctness
- Spring Boot conventions
- dependency injection
- REST API design
- exception handling
- separation of concerns
- maintainability
- testability

Do not review security, persistence internals, or observability unless
they directly affect the code-quality assessment.

Report findings using:
CRITICAL / HIGH / MEDIUM / LOW / INFO

For every finding provide:
- severity
- location
- problem
- why it matters
- recommended improvement
```

### Production Readiness

```text
Review this Spring Boot service as if it is about to be deployed to
production.

Evaluate:
- application structure
- error handling
- API behaviour
- concurrency
- resource management
- configuration
- resilience
- logging
- testing
- maintainability

Identify concrete production risks.

Do not invent runtime behaviour that cannot be established from the
repository.

Finish with:
APPROVE
APPROVE WITH COMMENTS
CHANGES REQUESTED
BLOCKED
```

---

# 2. Spring Security Review

### Security-Focused Review

```text
Perform a security review of this Spring Boot application.

Focus specifically on:
- authentication
- authorization
- Spring Security configuration
- OAuth2/OIDC
- JWT validation
- issuer and audience validation
- roles and authorities
- endpoint protection
- method-level security
- CORS
- CSRF
- security headers
- actuator exposure
- secret handling
- token propagation

Look specifically for:
- authentication bypass
- authorization bypass
- privilege escalation
- IDOR
- insecure direct object references
- token leakage
- weak JWT validation
- insecure configuration

Do not report general code-quality findings unless they create
a security consequence.

Only report vulnerabilities supported by evidence in the repository.
```

### OAuth2 Client Credentials

```text
Review the OAuth2 client-credentials implementation.

Verify:
- token acquisition
- client authentication
- token lifecycle
- scope handling
- audience validation
- credential storage
- token propagation
- error handling
- connection configuration
- accidental credential/token logging

Determine whether the implementation provides secure service-to-service
authentication.

Clearly distinguish:
1. confirmed security defects
2. security risks requiring verification
3. recommendations
```

### Authorization Review

```text
Review the authorization model of this application.

Trace an incoming request from:
HTTP endpoint
→ authentication
→ JWT
→ authorities/scopes
→ authorization rules
→ service layer
→ resource access

Determine whether a caller can access resources they should not be
allowed to access.

Pay particular attention to:
- horizontal privilege escalation
- vertical privilege escalation
- IDOR
- missing ownership checks
- incorrect authority mapping
- overly broad roles
- tenant isolation
```

---

# 3. Spring Persistence Review

### JPA/Hibernate Review

```text
Review the persistence layer of this Spring Boot application.

Focus on:
- JPA entity design
- relationships
- fetch strategies
- cascade configuration
- orphan removal
- entity lifecycle
- dirty checking
- equals/hashCode
- identifier design
- repository usage
- query design

Look specifically for:
- N+1 queries
- accidental eager loading
- excessive database round trips
- unbounded queries
- inefficient joins
- incorrect entity relationships
- concurrency problems

Do not report general Java or REST issues unless they directly
affect persistence behaviour.
```

### Transaction Review

```text
Perform a transaction-boundary review.

For every important transactional operation determine:

- where the transaction starts
- what resources it covers
- transaction propagation
- isolation requirements
- rollback behaviour
- transaction duration
- database locking
- external calls inside transactions
- concurrency implications

Look specifically for:
- transactions that are too large
- transactions that are too small
- external network calls inside transactions
- unexpected rollback behaviour
- lost updates
- race conditions
- incorrect isolation assumptions

Explain the reasoning behind every finding.
```

### Database Performance Review

```text
Review this application for database performance risks.

Trace the application flow from controller/service through repository
and JPA/Hibernate into PostgreSQL.

Look for:
- N+1 queries
- missing indexes
- inefficient queries
- unbounded result sets
- unnecessary joins
- excessive round trips
- inefficient pagination
- large object graphs
- locking contention
- unnecessary transactions

Do not claim that a database query is slow without sufficient evidence.
Classify assumptions separately from confirmed findings.
```

### Flyway Review

```text
Review all Flyway migrations introduced by this change.

Check:
- migration ordering
- naming
- schema compatibility
- indexes
- constraints
- foreign keys
- rollback implications
- destructive changes
- data migration safety
- locking
- production deployment impact
- backward compatibility with the application version

Identify migrations that could cause deployment failures or production
downtime.
```

---

# 4. Spring Observability Review

### General Observability Review

```text
Review the observability implementation of this Spring Boot service.

Focus on:
- metrics
- logs
- traces
- OpenTelemetry
- Micrometer
- trace context propagation
- structured logging
- health endpoints
- readiness
- liveness
- alertability

Evaluate whether an operations team could diagnose:
- request failures
- latency problems
- dependency failures
- database problems
- downstream service failures
- capacity problems

Do not simply recommend adding more telemetry.

Identify missing telemetry that prevents meaningful diagnosis.
```

### OpenTelemetry Review

```text
Review the OpenTelemetry implementation.

Assume the target architecture is:

Spring Boot
    ↓
OpenTelemetry / OTLP
    ↓
Grafana Alloy
    ↓
Mimir / Tempo / Loki
    ↓
Grafana

Verify:
- trace propagation
- span creation
- service naming
- resource attributes
- OTLP configuration
- metrics export
- trace export
- log correlation
- sampling
- failure handling

Look for telemetry that is missing, duplicated, misleading,
or excessively expensive.
```

### Metrics Cardinality Review

```text
Review all application metrics for cardinality risks.

For every custom metric inspect its labels/tags.

Pay particular attention to labels containing:
- user IDs
- request IDs
- UUIDs
- URLs
- arbitrary error messages
- database identifiers
- tenant identifiers
- unbounded business values

Determine whether any metric could create uncontrolled time-series
growth.

Explain:
- the problematic label
- why its cardinality is unbounded
- the operational consequence
- a safer alternative
```

---

# 5. API / REST Review

```text
Review the REST API design of this Spring Boot service.

Evaluate:
- resource modelling
- HTTP methods
- status codes
- request validation
- response contracts
- error responses
- pagination
- filtering
- sorting
- idempotency
- versioning
- backwards compatibility

Look for APIs that are:
- ambiguous
- inconsistent
- difficult to consume
- difficult to evolve
- unsafe under retries

Separate API design recommendations from actual defects.
```

### API Error Handling

```text
Review the API error-handling implementation.

Check:
- exception mapping
- HTTP status codes
- validation errors
- error response consistency
- correlation IDs
- sensitive information leakage
- logging behaviour
- unexpected exception handling

Determine whether clients can reliably distinguish between:
- invalid requests
- authentication failures
- authorization failures
- missing resources
- conflicts
- dependency failures
- internal errors
```

---

# 6. Testing Review

```text
Review the test strategy for this Spring Boot service.

Evaluate:
- unit tests
- integration tests
- controller tests
- repository tests
- Testcontainers
- security tests
- contract tests
- failure scenarios
- concurrency tests

Identify important behaviours that are currently untested.

Do not optimize for test count.

Prioritize tests based on:
1. business criticality
2. failure impact
3. architectural risk
4. regression probability
```

### Testcontainers Review

```text
Review the Testcontainers-based integration tests.

Check whether the tests realistically validate:
- PostgreSQL behaviour
- database migrations
- transactions
- persistence
- Kafka/RabbitMQ integration
- application configuration
- network dependencies

Identify cases where mocks could hide production defects.

Also identify cases where integration tests are unnecessarily expensive
and could be replaced with focused unit tests.
```

---

# 7. Microservices Architecture Review

```text
Review this service from a microservices architecture perspective.

Focus on:
- service boundaries
- ownership of data
- synchronous dependencies
- asynchronous communication
- coupling
- API contracts
- resilience
- failure isolation
- distributed transactions
- service autonomy

Look for:
- distributed monolith characteristics
- excessive synchronous dependencies
- shared database coupling
- circular dependencies
- chatty APIs
- inappropriate service decomposition

Do not redesign the entire system unless the evidence indicates
that the current architecture creates a significant problem.
```

---

# 8. Resilience Review

```text
Review this service for distributed-system resilience.

Analyze interactions with:
- databases
- REST services
- messaging systems
- authentication providers
- external APIs

Check:
- timeouts
- retries
- backoff
- circuit breakers
- bulkheads
- rate limiting
- connection pools
- failure propagation
- graceful degradation

For every retry recommendation determine whether the operation
is idempotent.

Identify failure modes that could cause:
- retry storms
- cascading failures
- thread exhaustion
- connection exhaustion
- message duplication
```

---

# 9. Configuration Review

```text
Review the application's configuration strategy.

Focus on:
- application.yaml
- application.properties
- environment variables
- profiles
- ConfigMaps
- Secrets
- external configuration
- default values
- configuration validation

Identify:
- secrets committed to source control
- unsafe defaults
- environment-specific configuration leakage
- missing configuration validation
- configuration that can cause production failures

Do not classify a missing configuration property as a defect unless
there is evidence that the application requires it.
```

---

# 10. Kubernetes Review

```text
Review the Kubernetes deployment configuration for this Spring Boot
service.

Focus on:
- Deployment
- Service
- Ingress
- ConfigMap
- Secret
- resource requests/limits
- probes
- replicas
- PodDisruptionBudget
- topology spread
- security context
- NetworkPolicy

Evaluate production resilience and operational safety.

Pay particular attention to:
- missing readiness probes
- incorrect liveness probes
- unrealistic resource settings
- missing disruption protection
- unsafe container permissions
- unrestricted network access
```

---

# 11. GitOps / FluxCD Review

```text
Review the FluxCD deployment configuration.

Focus on:
- HelmRelease
- HelmRepository
- Kustomization
- reconciliation
- dependencies
- namespaces
- secrets
- configuration
- upgrade behaviour
- rollback behaviour

Identify:
- dependency ordering problems
- reconciliation loops
- configuration drift risks
- unsafe automatic upgrades
- missing health checks
- environment coupling

Explain how a failure would appear operationally and how it should
be diagnosed.
```

---

# 12. Code + Security Combined Review

Use this when a change contains both application logic and security-sensitive functionality.

```text
Perform a combined code-quality and security review.

First review the implementation for normal Spring Boot code quality.

Then independently review:
- authentication
- authorization
- input validation
- sensitive data handling
- exception handling
- API exposure

Keep code-quality findings and security findings separate.

Do not duplicate the same finding in both categories.

Security findings take precedence when the same implementation issue
creates an exploitable vulnerability.
```

---

# 13. Code + Persistence Combined Review

```text
Review this change from both application-code and persistence
perspectives.

Code review should focus on:
- maintainability
- correctness
- separation of concerns
- Spring idioms
- testability

Persistence review should focus on:
- transactions
- JPA/Hibernate
- queries
- database access
- concurrency
- Flyway migrations

Keep findings separated by category and avoid duplicate findings.
```

---

# 14. Full Specialist Review

```text
Perform a comprehensive review of this change.

Use the following specialist perspectives:

1. Spring Boot Code Quality
2. Spring Security
3. Spring Persistence
4. Spring Observability

Each specialist should only report findings within its domain.

For every finding provide:

- Specialist
- Severity
- Location
- Evidence
- Problem
- Impact
- Recommendation
- Confidence

Deduplicate findings where multiple specialists identify the same
underlying problem.

Finish with:

## Critical Findings

## High Findings

## Medium Findings

## Low Findings

## Positive Findings

## Overall Assessment

Do not invent evidence.
Do not claim tests were executed unless they were actually executed.
Do not treat recommendations as defects.
```

---

# 15. Pull Request Review

```text
Review this pull request rather than the entire repository.

Prioritize:
1. correctness
2. security
3. data integrity
4. production risk
5. backwards compatibility

Focus primarily on changed code, but inspect surrounding code when
necessary to understand the impact.

Do not report unrelated legacy problems unless the pull request
introduces or materially increases the risk.

Conclude with:

APPROVE
APPROVE WITH COMMENTS
CHANGES REQUESTED
BLOCKED
```

---

# 16. Architecture-to-Code Review

```text
Review whether this implementation correctly realizes the intended
architecture.

Use the architecture documentation, ADRs, C4 diagrams, API contracts,
and source code where available.

Check for mismatches between:
- C4 architecture
- component responsibilities
- service boundaries
- API contracts
- persistence ownership
- deployment topology
- implementation

Report architecture deviations separately from implementation defects.

Do not assume that every architecture deviation is automatically a
defect. Explain the consequence of each deviation.
```

---

# 17. Regression Review

```text
Review this change specifically for regression risk.

Determine:
- what existing behaviour is changed
- which consumers could be affected
- whether APIs remain backwards compatible
- whether database compatibility is maintained
- whether configuration remains compatible
- whether existing tests cover the changed behaviour

Focus on unintended consequences rather than style improvements.
```

---

# 18. Performance Review

```text
Review this implementation for performance risks.

Consider:
- CPU usage
- memory allocation
- database access
- network calls
- serialization
- concurrency
- thread pools
- connection pools
- caching
- collection sizes
- algorithmic complexity

Prioritize findings that can materially affect production behaviour.

Do not recommend optimization without identifying the mechanism that
could cause a performance problem.
```

---

# 19. Maintainability Review

```text
Review this code specifically for long-term maintainability.

Evaluate:
- cohesion
- coupling
- naming
- complexity
- duplication
- abstraction quality
- dependency direction
- testability
- separation of concerns

Do not recommend abstraction merely because duplication exists.

Prefer the simplest design that keeps the code understandable and
evolvable.
```

---

# 20. "Find Only Blockers"

Useful when reviewing a release candidate.

```text
Review this change using a strict production-release gate.

Report ONLY issues that could reasonably justify blocking release.

Consider:
- security vulnerabilities
- data corruption
- transaction integrity
- serious concurrency problems
- production outages
- severe resilience failures
- backwards compatibility breaks
- operational blindness

Do not report:
- style issues
- minor refactoring
- optional improvements
- subjective preferences

For every blocker provide concrete evidence and explain why release
should be blocked.
```

---

# 21. "Find Improvements Only"

Useful after all defects have been resolved.

```text
Review this implementation for improvement opportunities only.

Do not report defects unless they are genuinely correctness,
security, data-integrity, or production risks.

Focus on:
- readability
- maintainability
- simplification
- testability
- API usability
- operational improvements
- architectural evolution

Rank recommendations by expected engineering value.
```

---

# 22. Evidence-First Review

```text
Perform an evidence-first code review.

For every finding provide:

Evidence:
What in the repository supports this conclusion?

Reasoning:
Why does this evidence indicate a problem?

Impact:
What could happen if the problem remains?

Recommendation:
What should be changed?

Confidence:
HIGH / MEDIUM / LOW

If the evidence is insufficient, do not report the issue as a confirmed
defect. Instead classify it as a question or verification item.
```

---

# 23. Review a Specific Class

The agents can also be prompted very narrowly.

### N+1 Detection

```text
Inspect this repository specifically for N+1 database query problems.

Trace:
controller → service → repository → entity relationships → queries.

Identify every credible N+1 scenario.

For each one explain:
- triggering operation
- relationship causing the issue
- expected query behaviour
- recommended solution

Do not report unrelated persistence findings.
```

### Authorization Bypass

```text
Inspect this application specifically for authorization bypasses.

Trace every externally accessible endpoint through the authorization
model and determine whether resource ownership and required
authorities are enforced.

Focus on:
- missing authorization
- incorrect role mapping
- IDOR
- tenant isolation
- privilege escalation

Only report findings supported by the implementation.
```

### Observability Blind Spots

```text
Inspect this service specifically for operational blind spots.

Assume the service is running in production and an operator receives
a report saying:

"Requests are failing intermittently."

Determine whether the available:
- logs
- metrics
- traces
- health checks
- correlation information

would allow the operator to determine the cause.

Identify missing telemetry that materially prevents diagnosis.
```

---

# 24. Ask the Reviewer to Explain a Finding

```text
Explain finding [FINDING-ID] in more detail.

Show:
1. the relevant code path
2. the underlying technical mechanism
3. why the current implementation is problematic
4. the production consequence
5. the recommended solution
6. whether the recommendation is mandatory or optional

Do not introduce unrelated findings.
```

---

# 25. Ask for a Minimal Fix

```text
For finding [FINDING-ID], propose the smallest change that resolves
the identified problem.

Constraints:
- preserve existing public APIs where possible
- preserve existing behaviour
- avoid introducing new frameworks
- avoid unnecessary refactoring
- follow existing project conventions

Explain why the proposed change is sufficient.
```

---

# 26. Ask for Alternatives

```text
For finding [FINDING-ID], provide up to three possible solutions.

For each solution describe:

- implementation complexity
- operational impact
- performance impact
- maintainability
- risks
- advantages
- disadvantages

Recommend one solution and explain the trade-off.

Do not assume that the most sophisticated solution is automatically
the best one.
```

---

# 27. Review After Changes

```text
Re-review the implementation after the reported findings have been
addressed.

For each previous finding determine:

- RESOLVED
- PARTIALLY RESOLVED
- NOT RESOLVED
- NO LONGER APPLICABLE

Check whether the fix introduced:
- regressions
- new security problems
- transaction problems
- performance problems
- observability gaps

Do not reopen unrelated findings unless the new implementation
creates a direct connection.
```

---

# 28. Recommended Prompt Pattern

For repeatable reviews, use the following structure:

```text
ROLE
You are reviewing this implementation as a [SPECIALIST].

OBJECTIVE
Determine whether the implementation satisfies [OBJECTIVE].

SCOPE
Focus on:
- ...
- ...
- ...

OUT OF SCOPE
Do not focus on:
- ...
- ...

EVIDENCE
Use only evidence available in:
- source code
- configuration
- tests
- architecture documentation
- build configuration
- deployment manifests

ANALYSIS
Trace the relevant execution path before reporting findings.

FINDINGS
For every finding provide:
- ID
- severity
- location
- evidence
- problem
- impact
- recommendation
- confidence

RULES
- Do not invent evidence.
- Do not claim tests were executed unless they were executed.
- Do not inflate severity.
- Do not duplicate findings.
- Distinguish defects from recommendations.
- State uncertainty explicitly.

FINAL ASSESSMENT
Provide the specialist's final assessment.
```

---

# 29. Prompt Selection Matrix

| Situation                     | Recommended Specialist           |
| ----------------------------- | -------------------------------- |
| General Java/Spring quality   | Spring Boot Code Reviewer        |
| REST API implementation       | Spring Boot Code Reviewer        |
| OAuth2/OIDC                   | Spring Security Reviewer         |
| JWT validation                | Spring Security Reviewer         |
| Authorization                 | Spring Security Reviewer         |
| JPA/Hibernate                 | Spring Persistence Reviewer      |
| Transactions                  | Spring Persistence Reviewer      |
| PostgreSQL performance        | Spring Persistence Reviewer      |
| Flyway migration              | Spring Persistence Reviewer      |
| Micrometer                    | Spring Observability Reviewer    |
| OpenTelemetry                 | Spring Observability Reviewer    |
| Grafana LGTM                  | Spring Observability Reviewer    |
| Metric cardinality            | Spring Observability Reviewer    |
| Kubernetes deployment         | Platform/Kubernetes Reviewer     |
| FluxCD                        | GitOps Reviewer                  |
| API compatibility             | Code/API Reviewer                |
| Distributed-system resilience | Architecture/Resilience Reviewer |
| Full PR                       | Review Orchestrator              |
| Release gate                  | Review Orchestrator              |
| Architecture compliance       | Solution Architect               |
| C4-to-code consistency        | Solution Architect               |

---

# 30. Recommended Multi-Agent Review

For an important production change, the preferred workflow is:

```text
                    Pull Request
                         │
                         ▼
                ┌─────────────────┐
                │ Review           │
                │ Orchestrator     │
                └────────┬────────┘
                         │
          ┌──────────────┼──────────────┐
          │              │              │
          ▼              ▼              ▼
   ┌────────────┐ ┌────────────┐ ┌────────────┐
   │ Spring     │ │ Security   │ │ Persistence│
   │ Code       │ │ Reviewer   │ │ Reviewer   │
   │ Reviewer   │ │            │ │            │
   └────────────┘ └────────────┘ └────────────┘
          │              │              │
          └──────────────┼──────────────┘
                         │
                         ▼
                ┌─────────────────┐
                │ Observability   │
                │ Reviewer        │
                └────────┬────────┘
                         │
                         ▼
                ┌─────────────────┐
                │ Deduplicate &   │
                │ Prioritize      │
                └────────┬────────┘
                         │
                         ▼
                ┌─────────────────┐
                │ Human Review    │
                │ & Decision      │
                └─────────────────┘
```

The important principle is that **specialist prompts should narrow the
reviewer's responsibility rather than encourage every agent to review
everything**.

This improves finding quality, reduces duplicated findings, and makes
the resulting review easier for engineers and architects to act upon.

---

# Integration With Solution Architecture

The specialist reviewers should be considered part of a larger engineering-agent ecosystem.

Recommended architecture:

```text
.github/agents/

solution-architect.agent.md
        │
        ├── Spring Boot Code Reviewer
        │
        ├── Spring Security Reviewer
        │
        ├── Spring Persistence Reviewer
        │
        └── Spring Observability Reviewer
```

The Solution Architect establishes architectural intent.

The specialist agents verify implementation quality.

---

# Recommended Development Lifecycle

A mature workflow can use the agents throughout the development lifecycle.

```text
Requirements
     │
     ▼
Architecture
     │
     ▼
C4 Architecture
     │
     ▼
Implementation
     │
     ▼
Developer Review
     │
     ▼
Specialist Reviews
     │
     ├── Code
     ├── Security
     ├── Persistence
     └── Observability
     │
     ▼
Automated Tests
     │
     ▼
Pull Request
     │
     ▼
Human Review
     │
     ▼
Merge
     │
     ▼
Deployment
```

---

# Extending the Agent Suite

The agents are intentionally modular.

Additional specialists can be added without changing the existing reviewers.

New agents should follow the same principles:

1. Define a clear area of responsibility.
2. Avoid excessive overlap.
3. Define explicit review priorities.
4. Use the common severity model.
5. Require evidence.
6. Avoid false positives.
7. Produce actionable recommendations.
8. End with a clear assessment.

---

# Future Specialist Agents

The following agents would be natural extensions.

## Spring Integration Reviewer

Focus:

* Kafka
* RabbitMQ
* messaging
* event-driven architecture
* retry
* dead-letter queues
* idempotency
* event contracts

---

## Spring API Reviewer

Focus:

* REST
* OpenAPI
* API versioning
* backwards compatibility
* pagination
* error contracts
* API governance

---

## Spring Resilience Reviewer

Focus:

* Resilience4j
* timeouts
* retries
* circuit breakers
* bulkheads
* rate limiting
* failure propagation

---

## Kubernetes Reviewer

Focus:

* Deployments
* Services
* Ingress
* probes
* resources
* PDB
* topology spread
* NetworkPolicy
* security context

---

## Helm / GitOps Reviewer

Focus:

* Helm
* FluxCD
* Kustomize
* GitOps
* environment overlays
* secrets
* deployment strategy

---

## Infrastructure Reviewer

Focus:

* Terraform
* Kubernetes infrastructure
* cloud architecture
* networking
* IAM
* infrastructure security

---

## Test Architecture Reviewer

Focus:

* JUnit
* Testcontainers
* integration testing
* contract testing
* test architecture
* test reliability

---

# Suggested Future Architecture

As the suite grows, the architecture can evolve into:

```text
                       Solution Architect
                               │
                               ▼
                    Architecture Decision
                               │
          ┌────────────────────┼────────────────────┐
          │                    │                    │
          ▼                    ▼                    ▼
     Application           Security              Data
       Review               Review               Review
          │                    │                    │
          ├──────────────┬─────┴──────┬─────────────┤
          │              │            │             │
          ▼              ▼            ▼             ▼
       API          Integration   Resilience   Observability
       Review          Review        Review        Review
          │              │            │             │
          └──────────────┴────────────┴─────────────┘
                               │
                               ▼
                        Kubernetes / GitOps
                               │
                               ▼
                         Final Assessment
```

This effectively creates an **AI-assisted software engineering review board**.

---

# Recommended Naming Convention

Use predictable names:

```text
springboot-code-reviewer.agent.md
spring-security-reviewer.agent.md
spring-persistence-reviewer.agent.md
spring-observability-reviewer.agent.md
```

For future agents:

```text
spring-integration-reviewer.agent.md
spring-api-reviewer.agent.md
spring-resilience-reviewer.agent.md
kubernetes-reviewer.agent.md
helm-gitops-reviewer.agent.md
terraform-reviewer.agent.md
test-architecture-reviewer.agent.md
```

---

# Best Practices

## Use the narrowest appropriate reviewer

Do not run every agent for every trivial change.

For example:

```text
Rename Java method
    ↓
Spring Boot Code Reviewer
```

Whereas:

```text
Introduce OAuth2 client credentials
    ↓
Spring Boot Code Reviewer
    +
Spring Security Reviewer
```

And:

```text
Introduce new PostgreSQL aggregate
    ↓
Spring Boot Code Reviewer
    +
Spring Persistence Reviewer
    +
Spring Observability Reviewer
```

---

# Human Review Remains Mandatory

These agents are decision-support tools.

They should not replace:

* code ownership
* security review
* architectural governance
* production testing
* performance testing
* compliance assessment
* human Pull Request approval

AI-generated findings must be evaluated by an engineer.

---

# Summary

This project provides a structured way to introduce AI-assisted code review into Java and Spring Boot development.

Instead of one generic reviewer:

```text
Generic Code Review
```

the project uses specialist engineering perspectives:

```text
Spring Boot
     +
Security
     +
Persistence
     +
Observability
```

Each reviewer has a specific responsibility and common output model.

The result is:

```text
             Better Code
                 │
        ┌────────┼────────┐
        │        │        │
        ▼        ▼        ▼
     Secure   Reliable  Observable
        │        │        │
        └────────┼────────┘
                 ▼
          Production Ready
```

The goal is not to generate more review comments.

The goal is to identify **fewer, higher-value findings** that materially improve the quality, security, reliability and operability of Spring Boot systems.
