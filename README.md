# Transaction API

A Spring Boot REST API built with Kotlin for managing financial transactions, accounts, and operation types. The application provides endpoints for account management, transaction processing, and operation type queries, with full validation and persistence using PostgreSQL (or H2 for tests).

## Features

- Spring Boot 3.4.0
- Kotlin 2.2.20
- Spring Web for REST API
- Jackson for JSON processing
- PostgreSQL integration (via Docker Compose)
- H2 in-memory database for integration tests
- Flyway for database migrations
- MockK and JUnit for unit and integration testing

## Running the Application

You can use the provided Makefile for easy automation:

### Start the application (with database)
```bash
make run
```
This will start the PostgreSQL database using Docker Compose and then run the Spring Boot application. The API will be available at `http://localhost:8080`.

### Run tests
```bash
make test
```
This will start the database and execute all unit and integration tests.

### Start only the database
```bash
make up
```

### Stop the database
```bash
make down
```

### Stop the database and remove volumes
```bash
make down-volumes
```

### Clean the project
```bash
make clean
```

## API Endpoints

### Health Check
- `GET /api/transactions/health` — Returns application health status.

### Accounts
- `POST /api/accounts` — Create a new account.
  - Request body:
    ```json
    {
      "document_number": "12345678900"
    }
    ```
  - Response: 201 Created
    ```json
    {
      "account_id": "1",
      "document_number": "12345678900"
    }
    ```
- `GET /api/accounts/{accountId}` — Get account by ID.
  - Response: 200 OK
    ```json
    {
      "account_id": "1",
      "document_number": "12345678900"
    }
    ```
  - If not found: 404 Not Found

### Operation Types
- `GET /api/operation-types` — List all operation types.
  - Response: 200 OK
    ```json
    [
      { "operation_type_id": 1, "description": "Normal Purchase" },
      { "operation_type_id": 2, "description": "Purchase with installments" },
      { "operation_type_id": 3, "description": "Withdrawal" },
      { "operation_type_id": 4, "description": "Credit Voucher" }
    ]
    ```

### Transactions
- `POST /api/transactions` — Create a new transaction.
  - Request body:
    ```json
    {
      "account_id": 1,
      "operation_type_id": 1,
      "amount": 100.0
    }
    ```
  - Response: 201 Created
    ```json
    {
      "transaction_id": 1,
      "account_id": 1,
      "operation_type_id": 1,
      "amount": 100.0,
      "event_date": "2025-12-03T12:00:00Z"
    }
    ```
- `GET /api/transactions/{transactionId}` — Get transaction by ID.
  - Response: 200 OK
    ```json
    {
      "transaction_id": 1,
      "account_id": 1,
      "operation_type_id": 1,
      "amount": 100.0,
      "event_date": "2025-12-03T12:00:00Z"
    }
    ```
  - If not found: 404 Not Found

## Configuration

The application is configured through `application.properties`:
- Server port: 8080
- Database connection settings

## How it works

Transaction API manages accounts, operation types, and financial transactions. It validates all input data, prevents duplicate accounts, and ensures referential integrity. All database changes are managed via Flyway migrations. The application is ready for production and testing environments, with automated scripts for setup and teardown.

For more details, see the source code and Makefile in the project root.
