# chokmai
Chokma is a schema-first SaaS extraction platform designed to turn unstructured documents into reliable, validated JSON using Large Language Models (LLMs), Vision-Language Models (VLMs), and third-party AI APIs
# Chokma — Schema-Driven AI Extraction Platform

Chokma is a **JSON-schema–first SaaS extraction platform** that uses **LLMs**, **VLMs**, and third-party AI APIs (including **Chokma AI**) to extract, classify, and validate structured data from unstructured documents.

Unlike prompt-only approaches, Chokma enforces **JSON Schema contracts**, enabling deterministic, auditable, and production-grade AI workflows.

---

## ✨ Core Capabilities

- **Schema-First Extraction**
  - Define extraction targets using JSON Schema
  - Enforce types, enums, required fields, and constraints
  - Deterministic validation and error handling

- **AI Orchestration**
  - LLMs for semantic understanding
  - VLMs for document & image extraction
  - Provider/model fallback and retries
  - Integration with third-party AI APIs

- **Multi-Tenant SaaS**
  - Tenant isolation
  - Role-based access (viewer, extractor, admin, sysadmin)
  - Per-tenant model preferences and budgets

- **Observability & Auditability**
  - Audit logs
  - Token usage and cost tracking
  - RFC 7807 `application/problem+json` errors

---

## 🏗️ Architecture

| Layer | Technology |
|----|----|
| API | Quarkus (RESTEasy Reactive) |
| Security | JWT (SmallRye JWT) |
| API Docs | OpenAPI 3.0 + Swagger UI |
| Database | PostgreSQL 16 |
| Migrations | Flyway |
| ORM | Hibernate ORM |
| Runtime | Java **JDK 25** |

---

## 📦 Repository Structure


---

## 🔐 Security Model

### Authentication
- JWT Bearer tokens
- Tokens include:
  - `sub`
  - `roles`
  - `tenant_id`

### Roles

| Role | Scope | Capabilities |
|----|----|----|
| viewer | tenant | read-only |
| extractor | tenant | submit extraction jobs |
| admin | tenant | full tenant administration |
| sysadmin | global | cross-tenant access |

Tenant isolation is enforced at runtime:

> Non-sysadmin users can only access resources matching their `tenant_id`.

---

## 📘 API Documentation

Once running, the API is fully documented via OpenAPI.

- **OpenAPI JSON:**  
  http://localhost:8080/q/openapi

- **Swagger UI:**  
  http://localhost:8080/q/swagger-ui

All endpoints:
- Are grouped by feature
- Declare required roles
- Document error responses
- Return `application/problem+json` on failures

---

## ⚙️ Requirements

### System Requirements
- **JDK 25** (required)
- **PostgreSQL 16**
- Maven **3.9+**
- Docker (optional but recommended)

---

## ☕ Installing JDK 25

### Option 1: SDKMAN (recommended)

```bash
curl -s "https://get.sdkman.io" | bash
sdk install java 25-open
sdk use java 25-open

