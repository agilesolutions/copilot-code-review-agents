# JUnit Skills

Reusable GitHub Copilot skills for JUnit 5 and Spring Boot testing.

## Included

- `junit-5/SKILL.md` — JUnit 5 test design, assertions, lifecycle, parameterized tests, Mockito and review.
- `spring-boot-testing/SKILL.md` — Spring Boot test scopes, slices, MVC/WebFlux, security, configuration and context management.
- `testcontainers/SKILL.md` — PostgreSQL, MongoDB, Kafka, RabbitMQ, migrations, isolation and asynchronous integration tests.
- `junit-test-strategy/SKILL.md` — test pyramid, risk-based testing, coverage, mutation-oriented thinking, regression protection and CI quality gates.

## Recommended architecture

```text
.github/
├── agents/
│   ├── junit-test-specialist.agent.md
│   ├── integration-test-specialist.agent.md
│   └── review-orchestrator.agent.md
└── skills/
    ├── junit-5/
    │   └── SKILL.md
    ├── spring-boot-testing/
    │   └── SKILL.md
    ├── testcontainers/
    │   └── SKILL.md
    └── junit-test-strategy/
        └── SKILL.md
```

## Separation of responsibilities

**Agents** define the role, workflow and review responsibility.

**Skills** define reusable testing knowledge and standards.

Example:

```text
junit-test-specialist
        │
        ├── junit-5
        ├── spring-boot-testing
        ├── testcontainers
        └── junit-test-strategy
```

This allows multiple specialist agents to reuse the same JUnit expertise without duplicating it in every agent definition.
