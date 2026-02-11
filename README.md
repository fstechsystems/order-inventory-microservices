# Order & Inventory Microservices

This project contains two Spring Boot microservices that communicate via REST APIs.

- **Inventory Service** – Maintains product batches and handles stock reservation.
- **Order Service** – Accepts customer orders and coordinates with Inventory Service.

The system is designed using **layered architecture**, **Factory + Strategy patterns**, and is easily extensible for future business rules.

---

## Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 In-Memory Database
- Liquibase
- WebClient (service-to-service communication)
- Lombok
- JUnit 5 & Mockito

---

## Architecture Overview

Client -> Order Service (8082) -> REST -> Inventory Service (8081) -> H2 Database

---

## Services

### Inventory Service
Responsible for:
- Maintaining product batches
- Returning batches sorted by expiry
- Reserving inventory when order is placed
- Providing traceability of which batches were used

### Order Service
Responsible for:
- Accepting orders
- Calling inventory to reserve stock
- Persisting order details
- Returning enriched response to client

---

---

# Project Setup Instructions

## Prerequisites

- Java 17
- Maven 3.8+
- IDE (IntelliJ / VS Code / Eclipse)

---

## Clone Repository

```bash
git clone -b feature/order-inventory-integration https://github.com/fstechsystems/order-inventory-microservices.git
```

---

---

## Database & Liquibase
Liquibase runs automatically at application startup.

Location of changelogs:
- src/main/resources/db/changelog/

It performs:
- Table creation
- CSV data loading
- Identify reset for auto-generated IDs

---

---

# API Documentation

## Inventory Service

### Get inventory batches by expiry
- GET /inventory/{productId}

#### Example:
- GET /inventory/1005

### Reserve /Update Inventory
- POST /inventory/update

#### Request:
```json
{
    "productId": 1002,
    "quantity": 3
}
```

#### Response:
```json
{
    "productName": "Smartphone",
    "reservedBatchIds": [3],
    "message": "Inventory reserved." 
}
```

---

## Order Service

### Place an order
- POST /order

#### Request:
```json
{
    "productId": 1005,
    "quantity": 100
}
```

#### Response:
```json
{
    "message": "Order placed. Inventory reserved.",
    "orderId": 14,
    "productId": 1005,
    "productName": "Smartwatch",
    "quantity": 100,
    "reservedFromBatchIds": [
        5,
        7,
        2
    ],
    "status": "PLACED"
}
```

---

---

# Testing Instructions

## Run Application

1. Clone repository - Download zip.
2. Extract all files.
3. Open folder in IDE.
4. Start Inventory Service
   - Runs on:
   ```code
   http://localhost:8081
   ```
   - H2 Console:
   ```code
   http://localhost:8081/h2-console
   ```
5. Start Order Service
   - Runs on:
   ```code
   http://localhost:8082
   ```
   - H2 Console:
   ```code
   http://localhost:8082/h2-console
   ```

## Run Test Files

### Run All Tests
```bash
mvn test
```

### Unit Tests
- Mockito used for mocking dependencies.
- Focus on service-layer business logic.
- Example:
  ```code
  InventoryServiceTest
  OrderServiceTest
  ```

### Integration Tests
- @SpringBootTest
- H2 database
- REST endpoints verified
- Example:
  ```code
  InventoryControllerTest
  OrderControllerTest
  ```
