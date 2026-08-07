# Banking Transactions API

A REST API for account management and financial transactions, built with Java 21 and Spring Boot. The project applies payment-specific fee rules, prevents negative balances, versions the database schema with Liquibase, and validates the main business flows with automated tests.

## Features

- Create and retrieve bank accounts
- Process Pix, debit card, and credit card transactions
- Apply payment fees through the Strategy pattern
- Prevent duplicate accounts and insufficient-balance transactions
- Manage schema changes and seed data with Liquibase
- Expose application health information through Spring Boot Actuator
- Run integration and unit tests with JUnit 5 and MockMvc

## Technology stack

- Java 21
- Spring Boot 3.5.4
- Spring Web
- Spring Data JPA
- Jakarta Bean Validation
- Liquibase
- H2 Database
- Maven Wrapper
- JUnit 5 and MockMvc
- Docker
- GitHub Actions

## Architecture

The codebase uses a layered structure inspired by Clean Architecture:

```text
src/main/java/br/com/ngbilling/DesafioTecnico/
├── api/              # REST controllers and DTOs
├── application/      # Application service implementations
├── domain/           # Domain models, contracts, and transaction strategies
├── infrastructure/   # Persistence adapters
└── config/           # Cross-cutting configuration and exception handling
```

### Patterns used

- **Strategy:** calculates fees for each payment method
- **Factory:** registers and selects the appropriate transaction strategy
- **Repository:** abstracts account persistence
- **DTO:** defines the API input and output contracts

## Business rules

- Account numbers must be unique
- An account balance cannot be negative
- Pix transactions have no fee
- Debit card transactions have a 3% fee
- Credit card transactions have a 5% fee
- A transaction is rejected when the account does not have enough balance

## Running locally

### Requirements

- Java 21+
- Git

Clone the repository using the URL shown in GitHub's **Code** menu, then enter the project directory.

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

### H2 console

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:ngbillingdb`
- Username: `root`
- Password: empty

## Running with Docker

Build the image:

```bash
docker build -t banking-transactions-api .
```

Run the container:

```bash
docker run --rm -p 8080:8080 banking-transactions-api
```

## API endpoints

### Create an account

```http
POST /conta
Content-Type: application/json

{
  "numeroConta": 234,
  "saldo": 180.37
}
```

Successful response: `201 Created`

```json
{
  "numeroConta": 234,
  "saldo": 180.37
}
```

### Retrieve an account

```http
GET /conta?numero_conta=234
```

Successful response: `200 OK`

```json
{
  "numeroConta": 234,
  "saldo": 180.37
}
```

### Process a transaction

```http
POST /transacao
Content-Type: application/json

{
  "formaPagamento": "DEBITO",
  "numeroConta": 234,
  "valor": 10.00
}
```

Accepted values for `formaPagamento`:

- `PIX`
- `DEBITO`
- `CREDITO`

Successful response: `201 Created`

```json
{
  "numeroConta": 234,
  "saldo": 170.07
}
```

Relevant error responses:

- `400 Bad Request`: invalid input
- `404 Not Found`: account not found
- `409 Conflict`: duplicate account
- `422 Unprocessable Entity`: insufficient balance

## Tests

Run the full test suite:

```bash
./mvnw test
```

The suite covers:

- Account creation and retrieval
- Missing accounts
- Pix, debit, and credit fee calculations
- Insufficient balance validation
- Strategy implementations

## Author

Developed by **Douglas Barcellos**.
