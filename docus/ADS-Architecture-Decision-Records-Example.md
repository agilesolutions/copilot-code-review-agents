### ADR 004: Adopting Microservices for Core E-Commerce Services

* **Status:** Accepted
* **Date:** 2026-09-01
* **Authors:** Jane Doe (Principal Architect)
* **Deciders:** Architecture Review Board, Engineering Leadership

### Context and Problem Statement

Our current web application is built as a single, large Ruby on Rails monolith. While this served us well during our startup phase, our engineering team has grown to 50 developers across 4 distinct product squads (Checkout, Inventory, Recommendations, and User Management). 

The monolith is now a bottleneck. Deployment times have stretched to over 45 minutes because of a massive, fragile test suite. Code changes made by the Recommendations team regularly cause accidental regressions in the Checkout pipeline, leading to broken builds and delayed releases. We need a system structure that allows teams to deploy independently without stepping on each other's toes. 

### Decision Drivers

* **Team Autonomy:** Product teams must be able to deploy updates independently without coordinating release schedules.
* **Scalability:** The Inventory and Checkout systems experience extreme traffic spikes during sales events, while Recommendations traffic remains predictable.
* **Fault Isolation:** A crash or memory leak in the recommendation engine must not bring down the checkout pipeline.

### Considered Options

1. **Option 1: Modular Monolith.** Keep the single codebase but enforce strict, decoupled module boundaries using language-native engines or private packages.
2. **Option 2: Distributed Microservices.** Break the application apart into independent, loosely coupled services (e.g., Checkout Service, Inventory Service) communicated via REST APIs and an event bus.

### Decision Outcome

Chosen option: **Option 2: Distributed Microservices**. 

While a modular monolith reduces code coupling, it does not solve our deployment queue bottlenecks, nor does it allow independent scaling or isolated runtime environments. Breaking into microservices directly addresses our primary drivers of team autonomy, fault isolation, and targeted scalability, despite the added operational complexity. 

### Positive Consequences

* **Independent Deployments:** Teams can now deploy code multiple times a day without waiting on other squads.
* **Targeted Scaling:** We can scale up the instances of the Checkout service during high-traffic sales without paying to scale the entire application.
* **Technological Flexibility:** The Recommendations team can now rewrite their service in Python to leverage machine learning libraries, rather than being forced to use Ruby.

### Negative Consequences

* **Increased Operational Overhead:** The DevOps team must now manage distributed logging, tracing, and an API Gateway.
* **Data Consistency Challenges:** Moving away from a single database means we must handle eventual consistency and distributed transactions using patterns like the Saga Pattern.
* **Network Latency:** Internal communication now requires network calls (HTTP/gRPC), which adds small latency overhead to requests.

### Pros and Cons of Options

### Option 1: Modular Monolith

* **Good, because:** No network overhead; remains a single deployment pipeline; simple database transactions.
* **Good, because:** Lower initial operational complexity for our infrastructure team.
* **Bad, because:** Does not solve the single point of failure at runtime.
* **Bad, because:** All teams are still locked into the same deployment queue and the same programming language.

### Option 2: Distributed Microservices

* **Good, because:** Complete deployment and runtime isolation between business domains.
* **Good, because:** High alignment with our team structure (one team owns one or two services completely).
* **Bad, because:** Significantly more difficult to debug and trace bugs across service boundaries.
* **Bad, because:** Requires the engineering team to adopt automated CI/CD practices immediately.