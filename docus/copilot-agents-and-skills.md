GitHub Copilot Agents vs Skills
Purpose
This guide explains when to use GitHub Copilot custom agents versus skills, and how to combine them effectively in a Java/Spring Boot workspace.
The core principle is:
> **Skills provide reusable knowledge and techniques. Agents provide specialized roles and workflows that use that knowledge.**
---
1. The Mental Model
   Think of the relationship as:
```text
                    Copilot
                       |
             +---------+---------+
             |                   |
          AGENTS              SKILLS
        "Who/what?"          "How?"
             |                   |
     specialist role       reusable expertise
             |                   |
             +---------+---------+
                       |
                      Task
```
An agent is responsible for accomplishing a job.
A skill teaches an agent how to perform a particular type of work.
---
2. What Is a Skill?
   A skill is reusable technical knowledge, methodology, or a repeatable technique.
   A skill answers:
> **"How should this kind of work be done?"**
Examples for a Spring Boot workspace:
```text
.github/skills/
├── junit-5/
│   └── SKILL.md
├── testcontainers/
│   └── SKILL.md
├── spring-boot-testing/
│   └── SKILL.md
├── postgres-testing/
│   └── SKILL.md
├── kafka-testing/
│   └── SKILL.md
├── c4-architecture/
│   └── SKILL.md
└── observability/
    └── SKILL.md
```
A `testcontainers` skill could describe:
PostgreSQL containers
Kafka containers
MongoDB containers
`@Container`
`@Testcontainers`
`@ServiceConnection`
`@DynamicPropertySource`
container lifecycle
database cleanup
integration-test isolation
asynchronous testing
avoiding flaky tests
The skill is reusable knowledge. Multiple agents can apply it.
---
3. What Is an Agent?
   An agent represents a specialist role with a defined objective and workflow.
   An agent answers:
> **"Who is responsible for doing this job?"**
Examples:
```text
.github/agents/
├── solution-architect.agent.md
├── architecture-reviewer.agent.md
├── junit-test-specialist.agent.md
├── integration-test-specialist.agent.md
├── security-reviewer.agent.md
├── observability-reviewer.agent.md
└── review-orchestrator.agent.md
```
A `junit-test-specialist` might be responsible for:
Inspecting the workspace.
Identifying the appropriate test level.
Inspecting existing tests.
Applying the JUnit testing skill.
Applying the Testcontainers skill where appropriate.
Implementing or reviewing tests.
Running tests.
Reporting results and remaining gaps.
That is an agent workflow, not merely technical knowledge.
---
4. The Key Difference
   Question	Use
   How should JUnit 5 tests be written?	Skill
   How should PostgreSQL Testcontainers be configured?	Skill
   How should C4 diagrams be designed?	Skill
   How should OpenTelemetry be configured?	Skill
   Who reviews JUnit tests?	Agent
   Who reviews application architecture?	Agent
   Who reviews security?	Agent
   Who coordinates all reviewers?	Agent
   Who creates the consolidated review report?	Agent
   A useful rule is:
> **Knowledge belongs in skills. Responsibility belongs in agents.**
---
5. Why Separate Them?
   Without skills, agents tend to duplicate knowledge.
   For example:
```text
junit-test-specialist
    ├── JUnit knowledge
    ├── Mockito knowledge
    ├── AssertJ knowledge
    ├── Testcontainers knowledge
    └── PostgreSQL knowledge

integration-test-specialist
    ├── JUnit knowledge
    ├── Testcontainers knowledge
    ├── PostgreSQL knowledge
    └── Kafka knowledge
```
This becomes difficult to maintain.
Instead, extract common knowledge:
```text
                    JUnit Skill
                       ^
                       |
             +---------+---------+
             |                   |
       JUnit Agent       Integration Agent
             |                   |
             +---------+---------+
                       |
                Testcontainers
                     Skill
```
Now both agents can use the same expertise.
---
6. When to Create a Skill
   Create a skill when the material is:
   reusable
   domain-specific
   procedural
   technical
   applicable to multiple agents
   likely to evolve independently
   Good skill candidates include:
   Testing
```text
junit-5
spring-boot-testing
testcontainers
postgres-testing
kafka-testing
rest-api-testing
test-data-builders
```
Architecture
```text
c4-architecture
microservices-architecture
event-driven-architecture
api-design
domain-driven-design
```
Platform
```text
kubernetes
helm
fluxcd
terraform
traefik
cert-manager
```
Observability
```text
opentelemetry
grafana
prometheus
loki
tempo
mimir
```
---
7. When to Create an Agent
   Create an agent when there is a distinct responsibility or workflow.
   Examples:
```text
solution-architect
architecture-reviewer
junit-test-specialist
integration-test-specialist
security-reviewer
database-reviewer
observability-reviewer
api-reviewer
review-orchestrator
```
The agent should define:
its role
its objective
what it should inspect
which skills it should use
how it should reason about the task
what actions it should take
what output it should produce
---
8. Recommended Architecture for a Spring Boot Workspace
   A strong structure is:
```text
.github/
|
+-- agents/
|   |
|   +-- solution-architect.agent.md
|   +-- architecture-reviewer.agent.md
|   +-- junit-test-specialist.agent.md
|   +-- integration-test-specialist.agent.md
|   +-- security-reviewer.agent.md
|   +-- observability-reviewer.agent.md
|   +-- database-reviewer.agent.md
|   +-- api-reviewer.agent.md
|   +-- review-orchestrator.agent.md
|
+-- skills/
    |
    +-- junit-5/
    |   +-- SKILL.md
    |
    +-- spring-boot-testing/
    |   +-- SKILL.md
    |
    +-- testcontainers/
    |   +-- SKILL.md
    |
    +-- postgres-testing/
    |   +-- SKILL.md
    |
    +-- kafka-testing/
    |   +-- SKILL.md
    |
    +-- c4-architecture/
    |   +-- SKILL.md
    |
    +-- spring-security/
    |   +-- SKILL.md
    |
    +-- opentelemetry/
        +-- SKILL.md
```
---
9. Example: JUnit and Testcontainers
   Instead of putting everything into one large agent, separate the reusable knowledge.
   JUnit Skill
```text
JUnit 5
AssertJ
Mockito
Arrange/Act/Assert
parameterized tests
test naming
test isolation
test lifecycle
test determinism
test pyramid
```
Testcontainers Skill
```text
PostgreSQLContainer
KafkaContainer
MongoDBContainer
@Container
@Testcontainers
@ServiceConnection
@DynamicPropertySource
container lifecycle
database cleanup
migration testing
eventual consistency
```
JUnit Test Specialist Agent
The agent can then say:
```text
You are responsible for reviewing and improving tests.

1. Inspect the workspace.
2. Determine the appropriate test level.
3. Inspect existing tests and conventions.
4. Apply the JUnit skill.
5. Apply the Spring Boot testing skill where appropriate.
6. Apply the Testcontainers skill when infrastructure is required.
7. Implement or review tests.
8. Execute the relevant tests.
9. Report results and remaining gaps.
```
The agent orchestrates the work while the skills provide the technical expertise.
---
10. Review Orchestration
    This separation becomes especially valuable for a multi-agent review architecture.
```text
                     review-orchestrator
                              |
       +----------------------+----------------------+
       |                      |                      |
solution-architect      testing specialists     security-reviewer
       |                      |                      |
 C4 Architecture       +------+-------+         Security Skill
     Skill              |              |
                        |              |
                 JUnit Agent     Integration Agent
                        |              |
                   JUnit Skill   Testcontainers Skill
                                      |
                              PostgreSQL/Kafka Skills
```
The orchestrator has one primary responsibility:
> Coordinate specialists and produce one consolidated review.
The specialists have one responsibility each.
The skills contain the reusable technical knowledge.
---
11. The Orchestrator Should Be an Agent
    A consolidated review orchestrator should be an agent, not a skill.
    Why?
    Because it has a workflow:
```text
1. Identify current feature branch
2. Determine changed source files
3. Delegate reviews
4. Collect specialist findings
5. Correlate duplicate findings
6. Resolve conflicts
7. Prioritize findings
8. Produce one consolidated report
```
That is clearly a responsibility/workflow.
A skill could provide the methodology for producing a good review report, but it should not own the orchestration responsibility.
---
12. Avoid Over-Specialization
    Not every small instruction needs its own skill or agent.
    Do not create:
```text
assertj-skill
mockito-skill
junit-test-name-skill
junit-lifecycle-skill
```
unless these topics genuinely need independent reuse.
Prefer:
```text
junit-5/
    SKILL.md
```
containing the related JUnit knowledge.
Similarly, do not create an agent for every technology.
You generally don't need:
```text
assertj-agent
mockito-agent
postgres-agent
```
Instead:
```text
junit-test-specialist
integration-test-specialist
```
can use the relevant skills.
---
13. Skill vs Agent Decision Tree
    Use this decision process:
```text
                Do I need to add expertise?
                         |
                        Yes
                         |
             Will multiple agents use it?
                    /                              Yes             No
                   |               |
               SKILL         Could belong
                              in an agent

                Do I need a specialist
                responsibility/workflow?
                         |
                        Yes
                         |
                       AGENT
```
Another simple test:
> If you can phrase it as **"How to..."**, it is probably a skill.
Examples:
```text
How to test PostgreSQL with Testcontainers
How to design C4 diagrams
How to test Kafka consumers
How to configure OpenTelemetry
```
If you can phrase it as:
> **"Who is responsible for..."**
it is probably an agent.
Examples:
```text
Who reviews architecture?
Who reviews tests?
Who reviews security?
Who coordinates the complete review?
```
---
14. Recommended Design Principles
    Principle 1 — Keep knowledge reusable
    Put broadly applicable technical knowledge into skills.
    Principle 2 — Keep responsibilities explicit
    Put roles and workflows into agents.
    Principle 3 — Avoid duplication
    Multiple agents should reuse the same skills.
    Principle 4 — Keep agents focused
    A specialist should have a clearly bounded responsibility.
    Principle 5 — Keep orchestration separate
    The orchestrator should coordinate specialists rather than becoming another technical specialist.
    Principle 6 — Prefer composition
    Build sophisticated behavior by combining:
```text
Agent + Skills + Workspace
```
rather than creating increasingly large agent prompts.
---
15. Recommended Spring Boot Agent/Skill Model
    For a mature Java/Spring Boot architecture workspace:
```text
                         REVIEW ORCHESTRATOR
                                  |
        +-------------------------+-------------------------+
        |                         |                         |
  ARCHITECTURE                TESTING                  PLATFORM
     AGENT                     AGENTS                    AGENTS
        |                         |                         |
   +----+----+             +------+------+          +------+------+
   |         |             |             |          |             |
 C4 Skill  DDD Skill   JUnit Agent  Integration   Kubernetes   Terraform
                                      Agent          Skill       Skill
   |                         |             |
   |                    +----+----+        |
   |                    |         |        |
   |                 JUnit    Spring Boot  |
   |                 Skill      Testing    |
   |                           Skill       |
   |                                      |
   |                               Testcontainers
   |                                   Skill
   |
   +---------------------------------------------------------+
                              |
                       Consolidated
                       Review Report
```
This architecture scales much better than putting all technical knowledge into every agent.
---
16. Practical Rule of Thumb
    Use this concise rule when deciding:
    Use a Skill when:
> **"This is knowledge that several specialists may need."**
Use an Agent when:
> **"This is a specialist responsible for performing a job."**
Use both when:
> **"A specialist needs reusable domain expertise to perform its job."**
For example:
```text
JUnit Test Specialist Agent
            |
            +-- JUnit 5 Skill
            +-- Spring Boot Testing Skill
            +-- Testcontainers Skill
```
That is usually the cleanest design.
---
17. Final Recommendation
    For a Spring Boot architecture/testing workspace, prefer a small number of focused agents backed by a larger set of reusable skills.
    A good target is:
```text
Agents
------
5-10 focused specialists

Skills
------
10-30 reusable technical/domain capabilities
```
The exact numbers are less important than the separation of concerns.
The resulting model is:
```text
             AGENTS
        "Who does the work?"
                 |
                 v
              SKILLS
        "How is it done?"
                 |
                 v
             WORKSPACE
       "What is actually here?"
                 |
                 v
             RESULT
```
This provides a maintainable foundation for a GitHub Copilot workspace containing architecture, testing, security, observability, Kubernetes, DevOps and code-review specialists.