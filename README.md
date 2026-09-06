#  Banking Application

A simple **console-based Banking Management System built with Java** that demonstrates core banking operations using Object-Oriented Programming, layered architecture, Java Collections, custom exceptions, validation, and transaction management.

The application provides an interactive command-line menu for managing bank accounts and performing common banking operations such as opening accounts, deposits, withdrawals, fund transfers, account statements, account listing, and customer-name searches.

---

##  Features

*  Open a new bank account
*  Deposit money
*  Withdraw money
*  Transfer money between accounts
*  View account transaction statements
*  List all accounts
*  Search accounts by customer name
*  Account-not-found handling
*  Insufficient-balance validation
*  Input validation
*  Transaction tracking with timestamps
*  Automatic account-number generation
*  Layered application structure
*  In-memory data storage using Java Collections

---

##  Technologies Used

| Technology           | Purpose                                                       |
| -------------------- | ------------------------------------------------------------- |
| **Java**             | Core programming language                                     |
| **Java OOP**         | Encapsulation, abstraction and separation of responsibilities |
| **Java Collections** | In-memory storage using `HashMap` and `List`                  |
| **Java Streams**     | Sorting and filtering data                                    |
| **UUID**             | Customer and transaction identifiers                          |
| **LocalDateTime**    | Transaction timestamps                                        |
| **IntelliJ IDEA**    | Development environment                                       |
| **Git & GitHub**     | Version control and source-code hosting                       |

---

##  Project Architecture

The project follows a layered structure:

```text
Banking_Application/
│
├── .idea/
│
├── src/
│   │
│   ├── app/
│   │   └── Main.java
│   │
│   ├── domain/
│   │   ├── Account.java
│   │   ├── Customer.java
│   │   ├── Transaction.java
│   │   └── Type.java
│   │
│   ├── exceptions/
│   │   ├── AccountNotFoundException.java
│   │   ├── InsufficientFundsException.java
│   │   └── ValidationException.java
│   │
│   ├── repository/
│   │   ├── AccountRepository.java
│   │   ├── CustomerRepository.java
│   │   └── TransactionRepository.java
│   │
│   ├── service/
│   │   ├── BankService.java
│   │   └── impl/
│   │       └── BankServiceImpl.java
│   │
│   └── util/
│       └── Validation.java
│
├── .gitignore
└── Banking_Application.iml
```

The repository currently contains the six main source areas under `src`: `app`, `domain`, `exceptions`, `repository`, `service`, and `util`.

---

##  Package Responsibilities

### `app`

Contains the application entry point.

`Main.java` provides the interactive console menu and accepts user input for:

```text
1] Open Account
2] Deposit Money
3] Withdraw Money
4] Transfer Money
5] Account Statement
6] List Accounts
7] Search Account by Customer Name
0] Exit
```

The menu delegates banking operations to the `BankService` interface rather than directly manipulating repository data.

---

### `domain`

Contains the core business entities:

* `Account`
* `Customer`
* `Transaction`
* `Type`

These classes represent the main objects used by the banking system.

---

### `repository`

Provides in-memory data storage.

#### `AccountRepository`

Stores accounts in a `HashMap` and supports:

* Save account
* Find all accounts
* Find account by account number
* Find accounts belonging to a customer

#### `CustomerRepository`

Stores customers in a `HashMap` and supports saving and retrieving customers.

#### `TransactionRepository`

Maintains transactions grouped by account number and provides transaction lookup for an account.

---

### `service`

Contains the application's business logic.

`BankService` defines operations including:

```java
openAccount()
listAccounts()
deposit()
withdraw()
transfer()
getStatement()
searchAccountByCustomerName()
```

`BankServiceImpl` implements these operations and coordinates the domain objects and repositories.

---

### `exceptions`

The application defines custom exceptions for common banking errors:

* `AccountNotFoundException`
* `InsufficientFundsException`
* `ValidationException`

These are used by the service layer to handle invalid operations and invalid input.

---

### `util`

Contains reusable validation functionality through the `Validation` interface/functionality.

The service layer uses validation for customer names, email addresses, and account types.

---

##  Supported Account Types

The application currently supports:

```text
SAVING
CURRENT
```

When creating an account, the user provides the customer name, email, and account type.

Account numbers are automatically generated in the format:

```text
AC000001
AC000002
AC000003
...
```

The account number is generated from the current number of accounts in the repository.

---

## Banking Operations

### 1. Open Account

Creates a new customer and associated bank account.

Example:

```text
Customer Name: Abhishek Gupta
Customer email: abhishek@example.com
Account Type(SAVING/CURRENT): SAVING
Initial deposit: 5000
```

The system generates an account number automatically.

---

### 2. Deposit Money

Adds the specified amount to an existing account and records a transaction with a timestamp.

---

### 3. Withdraw Money

Withdraws money from an account after checking whether sufficient balance is available.

If the balance is insufficient, the application throws:

```text
InsufficientFundsException
```

---

### 4. Transfer Money

Transfers money from one account to another.

The system checks:

* Source account exists
* Destination account exists
* Source and destination are different
* Source account has sufficient balance

Transfer transactions use:

```text
TRANSFER_OUT
TRANSFER_IN
```

---

### 5. Account Statement

Displays transactions associated with an account, including:

* Timestamp
* Transaction type
* Amount
* Note

The service sorts transactions by timestamp.

---

### 6. List Accounts

Displays all accounts sorted by account number.

Example:

```text
AC000001|SAVING|5000.0
AC000002|CURRENT|2500.0
```

---

### 7. Search Account by Customer Name

Allows users to search for accounts using part of a customer's name.

For example:

```text
Customer name contains: Abhishek
```

The search is case-insensitive and returns matching customer accounts.

---

##  Application Flow

The basic flow of the application is:

```text
User
  │
  ▼
Main.java
  │
  ▼
BankService
  │
  ▼
BankServiceImpl
  │
  ├───────────────┐
  ▼               ▼
Repositories     Domain Objects
  │
  ├── AccountRepository
  ├── CustomerRepository
  └── TransactionRepository
```

This separation keeps the console interface, business logic, data models, and data storage responsibilities separate.

---

##  Data Storage

This version of the application uses **in-memory storage**.

The repositories use Java collections such as:

```java
HashMap
ArrayList
List
Map
```

Therefore, the application does **not currently use MySQL, PostgreSQL, MongoDB, or another external database**.

### Important

Because the data is stored in memory, account and transaction data will be lost when the application terminates.

---

## Getting Started

### Prerequisites

Install:

* Java JDK
* IntelliJ IDEA or another Java IDE
* Git (optional, for cloning the repository)

The code uses modern Java syntax such as text blocks and arrow-style switch cases, so use a sufficiently recent JDK.

---

## Clone the Repository

```bash
git clone https://github.com/abhishekguptaji/banking_application.git
```

Move into the project:

```bash
cd banking_application
```

---

##  Run the Application

Open the project in IntelliJ IDEA.

Navigate to:

```text
src
└── app
    └── Main.java
```

Run:

```text
Main.java
```

The application starts with:

```text
Welcome to Console Banking:
```

and displays the banking menu.

---

## Example Menu

```text
Welcome to Console Banking:

1] Open Account
2] Deposit Money
3] Withdraw Money
4] Transfer Money
5] Account Statement
6] List Accounts
7] Search Account by Customer Name
0] Exit

Choose:
```

---

##  OOP Concepts Demonstrated

This project is designed around several important Java concepts:

### Encapsulation

Banking entities such as `Account`, `Customer`, and `Transaction` encapsulate their data and expose methods for interacting with it.

### Abstraction

The `BankService` interface defines banking operations independently from their implementation.

### Separation of Responsibilities

Different packages handle:

```text
Application
     ↓
Service
     ↓
Repository
     ↓
Domain
```

### Exception Handling

Custom exceptions are used for situations such as:

* Missing accounts
* Insufficient funds
* Invalid input

### Collections

The project uses Java collections for temporary data storage.

### Lambda Expressions

Validation logic is implemented using functional-style validation.

### Streams

Java Stream APIs are used for sorting and processing collections.

### UUID

UUIDs are used for customer and transaction identifiers.

### LocalDateTime

Transaction timestamps are generated using Java's date/time API.

---

##  Current Limitations

This project is currently a **learning/console-based banking application**, rather than a production banking system.

Current limitations include:

* Data is stored only in memory.
* No database persistence.
* No user authentication or authorization.
* No REST API.
* No graphical/web interface.
* No automated test suite visible in the repository.
* Input/error handling in the console layer can be improved.
* Financial values currently use `Double`; production financial systems should generally use `BigDecimal`.
* Transaction rollback/atomicity can be improved.
* The current implementation contains areas that would benefit from additional validation and testing.

---

## 🔮 Future Improvements

Possible improvements include:

* [ ] Add MySQL/PostgreSQL database integration
* [ ] Add Spring Boot REST APIs
* [ ] Add Spring Data JPA/Hibernate
* [ ] Add user authentication and authorization
* [ ] Add password encryption
* [ ] Add a web frontend using React
* [ ] Replace `Double` with `BigDecimal` for monetary values
* [ ] Add comprehensive unit and integration tests
* [ ] Add transaction rollback/atomicity
* [ ] Add logging
* [ ] Add API documentation with Swagger/OpenAPI
* [ ] Add Docker support
* [ ] Add CI/CD using GitHub Actions
* [ ] Add persistent transaction history
* [ ] Add account deletion and account-update functionality
* [ ] Improve console input validation and error messages

---

##  Learning Objectives

This project is useful for practicing:

* Core Java
* Object-Oriented Programming
* Java Collections Framework
* Interfaces
* Lambda expressions
* Streams
* Exception handling
* Generics
* UUID
* Date and Time API
* Layered architecture
* Repository pattern
* Service-layer design
* Git and GitHub

---

##  Author

**Abhishek Gupta**

GitHub:
https://github.com/abhishekguptaji

---

##  License

This project currently does not specify a license.

If you intend to make the project open source, consider adding an appropriate license such as MIT.

---

## Support

If you find this project useful for learning Java and banking-system design, consider giving the repository a star on GitHub.

**Repository:**
https://github.com/abhishekguptaji/banking_application
