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
* [What the Agents Should Not Do](#what-the-agents-should-not-do)
* [Example Prompts](#example-prompts)
* [Integration With Solution Architecture](#integration-with-solution-architecture)
* [Recommended Development Lifecycle](#recommended-development-lifecycle)
* [Quality Gates](#quality-gates)
* [Extending the Agent Suite](#extending-the-agent-suite)
* [Future Specialist Agents](#future-specialist-agents)
* [Contributing](#contributing)
* [License](#license)

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
