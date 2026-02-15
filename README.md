# Incident Monitoring and Failure Prediction System

A real-time incident monitoring and failure prediction platform built using Spring Boot, Apache Kafka, and PostgreSQL.  
The platform ingests high-frequency system metrics, processes them through distributed Kafka pipelines, performs risk analysis and anomaly detection, and exposes secure APIs for operational control and auditing.

It reflects real-world monitoring and observability systems used in large-scale microservices architectures, emphasizing scalability, reliability, and secure access control.

---

## Features

- Real-time system metrics ingestion using REST APIs
- Event streaming with Apache Kafka
- Automated failure detection and risk scoring
- Incident resolution workflow with audit history
- Role-based authentication and authorization (JWT)
- AI-powered failure analysis and summaries
- Root cause analysis and recommendations
- Advanced search, filtering, and pagination
- Dashboard analytics and statistics APIs
- Secure admin-only operations

---
## Tech Stack

| Layer            | Technology               |
|------------------|--------------------------|
| Language         | Java                     |
| Framework        | Spring Boot              |
| Messaging Queue  | Apache Kafka             |
| Database         | PostgreSQL               |
| ORM              | Hibernate / JPA          |
| Security         | Spring Security, JWT     |
| API Docs         | Swagger / OpenAPI        |
| Containerization | Docker                   |
| Build Tool       | Maven                    |
| Testing Tool     | Postman                  |
---

## System Architecture
```

Client / Monitoring Agent --> REST API (Spring Boot) --> Kafka Producer (Metrics Ingestion) --> Apache Kafka Topics --> Kafka Consumer --> Failure Analysis Engine --> PostgreSQL Database --> Secure REST APIs --> Admin / User Dashboard (Postman / UI)

```

1. Clients send system metrics via REST APIs
2. Metrics are published to Kafka topics
3. Consumers process and analyze metrics
4. Failures are detected and stored in PostgreSQL
5. AI services generate summaries and predictions
6. Secure APIs expose insights and incident controls

---

## Project Structure

```
src/main/java/com/failureprediction
├── controller
├── service
├── repository
├── entity
├── security
├── config
├── exception
└── dto

```
## Workflow

### 1. User Authentication
- User logs in via API.
- Server returns a JWT token.

### 2. Metrics Ingestion
- Metrics are ingested via REST API.
- Data is published to Apache Kafka topics.

### 3. Stream Processing
- Kafka streams process incoming data in real time.
- Stream consumers analyze metrics for anomalies.

### 4. Failure Detection
- Failures are detected automatically.
- When thresholds are exceeded, an incident is created.

### 5. Incident Management
- Admin users can:
  - View active incidents
  - Resolve incidents
  - Add resolution notes

### 6. Audit History
- All actions are recorded.
- Incident lifecycle history is maintained for auditing.

### 7. Analytics APIs
- Provide insights such as:
  - Failure trends
  - Incident resolution time

---

## Authentication & Authorization

- JWT-based authentication
- Two roles supported:
  - ADMIN
  - USER
- Access Control:
  - Admin can resolve incidents
  - Users have read-only access
- Token validation via custom JWT filter

---

## API Modules

| Module              | Description                             |
|---------------------|-----------------------------------------|
| Auth Controller     | Login and registration                  |
| Metrics Controller  | System metrics ingestion                |
| Failure Controller  | Failure management APIs                 |
| Dashboard Controller| Analytics and summary                   |
| Root Cause          | Root cause analysis                     |
| AI Controller       | AI summary and forecasting              |
| Explanation         | Failure explanation APIs                |
| Analytics           | Timeline and trend analysis             |
| Health              | Application health check                |

---

## API Endpoints

### Auth
- `POST /auth/login`
- `POST /auth/register`

### Metrics
- `POST /metrics`
- `GET /metrics/{id}`

### Incidents
- `GET /incidents`
- `PATCH /incidents/{id}/resolve`

### Analytics
- `GET /analytics/failures`
- `GET /analytics/trends`

---
## Setup Instructions

### Prerequisites

- Java 17+
- Docker
- Maven
- PostgreSQL
- Apache Kafka

---
### Local Setup

1. Clone repository

```bash
git clone https://github.com/NikhilaManogna/incident-monitoring-system.git
```
2. Start Kafka & PostgreSQL
```bash
docker compose up -d
```
3. Build project
```bash
mvn clean install
```
4. Run application
```bash
mvn spring-boot:run
```
5. Access Swagger
```bash
http://localhost:8080/swagger-ui.html
```
---
## Testing

- APIs tested using Postman
- Swagger UI used for API documentation
- Role-based access testing (Admin/User scenarios)

---
## Performance & Scalability

- Asynchronous processing using Kafka
- Stateless authentication
- Horizontal scaling support
- Optimized database queries
- Pagination for large datasets

---
## Future Enhancements

- Frontend dashboard (React)
- Prometheus & Grafana integration
- Alert notifications (Email/SMS)
- Auto-healing services
- Machine learning model integration
- Kubernetes deployment

---
