# Cinema Tickets Booking System

## Overview

This project is the implementation of a cinema ticket purchasing service. The application validates ticket purchase requests, calculates payment and seat requirements, and delegates payment processing and seat reservation to the relevant services.

The solution focuses on clean code, separation of responsibilities, business rule validation, and automated unit testing.

## Technologies

- **Java 21** — application development
- **Maven** — build and dependency management
- **JUnit 5** — automated unit testing
- **Mockito** — mocking dependencies and verifying service interactions

## Key Services and Responsibilities

```text
TicketServiceImpl: Main service / purchase workflow
PurchaseValidator: Business rule and purchase validation
TicketCalculator: Payment and seat calculations
TicketPaymentService: External payment processing service
SeatReservationService: External seat booking service
```

- **TicketServiceImpl:** Coordinates the purchase process, from validation and calculation to payment and seat reservation.
- **PurchaseValidator:** Validates account IDs, ticket requests, purchase limits, and adult accompaniment requirements.
- **TicketCalculator:** Calculates the total ticket cost and the number of seats to reserve, excluding infants.
- **TicketPaymentService:** Processes payments through the provided payment service interface.
- **SeatReservationService:** Reserves the required seats through the provided seat booking service interface.

## Business Rules

| Ticket type | Price per ticket | Seat required |
| ----------- | ---------------: | ------------- |
| Adult       |              £25 | Yes           |
| Child       |              £15 | Yes           |
| Infant      |               £0 | No            |

Additional rules:

- Each purchase must have a valid account ID greater than zero.
- A purchase cannot exceed 25 tickets.
- Child and infant tickets require at least one adult ticket.
- Infant tickets cannot outnumber adult tickets.
- Invalid purchases are rejected before payment and seat reservation are attempted.

## Running the Project

### Prerequisites

- Java Development Kit (JDK) 21
- Apache Maven

Verify your installations:

```bash
java -version
mvn -version
```

### Run the tests

Execute the automated test suite from the project root:

```bash
mvn test
```

### Build the project

Compile and package the application using Maven:

```bash
mvn clean package
```

## Testing Strategy

The test suite uses JUnit 5 for assertions and Mockito to isolate the payment and seat reservation dependencies.

Tests cover:

- Successful adult and mixed ticket purchases.
- Invalid account IDs and ticket requests.
- Adult accompaniment requirements.
- Maximum ticket purchase limits.
- Correct payment amounts and seat reservation counts.
- Prevention of payment and reservation calls when validation fails.

## Design Approach

The implementation separates validation, calculation, and purchase orchestration into distinct responsibilities. This improves readability, maintainability, and testability while keeping the purchase workflow straightforward.

---

**Language:** Java 21
**Build tool:** Maven
**Testing:** JUnit 5 and Mockito
