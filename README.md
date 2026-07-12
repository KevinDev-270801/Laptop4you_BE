# Laptop4You Backend

[![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.16-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-Build-C71A36?logo=apachemaven&logoColor=white)](https://maven.apache.org/)

Backend REST API for **Laptop4You**, an e-commerce platform for managing laptops, customers, orders, inventory, promotions, suppliers, and reviews.

> This project is currently under active development.

## Features

- Brand management with complete CRUD operations
- Brand search, sorting, and pagination
- Request validation using Jakarta Validation
- Centralized exception handling with consistent error responses
- Entity-to-DTO mapping with MapStruct
- MySQL persistence using Spring Data JPA
- Auditable base entities
- Health and application monitoring support with Spring Boot Actuator

## Database Schema Diagram
<img width="1934" height="1697" alt="diagram" src="https://github.com/user-attachments/assets/7226bd0d-b7fe-4877-b398-b0e20d5fc619" />


## Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot 3.5.16 | Backend framework |
| Spring Web | REST API development |
| Spring Data JPA | Database access and persistence |
| MySQL | Relational database |
| Jakarta Validation | Request validation |
| MapStruct 1.6.3 | Entity and DTO mapping |
| Lombok 1.18.38 | Boilerplate reduction |
| Maven | Dependency management and build tool |

## Project Structure

```text
src/main/java/com/kevin/be_laptop4you
├── controller/     # REST API endpoints
├── dto/
│   ├── request/    # Incoming request models
│   └── response/   # Outgoing response models
├── entity/         # JPA entities
├── enums/          # Domain enumerations
├── exception/      # Error codes and global exception handling
├── mapper/         # MapStruct mappers
├── repository/     # Spring Data JPA repositories
├── service/        # Business service contracts
└── service/impl/   # Service implementations
```

## Getting Started

### Prerequisites

Make sure the following tools are installed:

- JDK 21 or later
- MySQL 8 or later
- Git

Maven does not need to be installed separately because the project includes Maven Wrapper.

### 1. Clone the repository

```bash
git clone https://github.com/KevinDev-270801/Laptop4you_BE.git
cd Laptop4you_BE
```

### 2. Create the database

```sql
CREATE DATABASE laptop4you
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
```

### 3. Configure environment variables

The application reads the database credentials from environment variables:

#### Linux/macOS

```bash
export DB_USERNAME=root
export DB_PASSWORD=your_password
```

#### Windows PowerShell

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_password"
```

The default database URL is configured as:

```text
jdbc:mysql://localhost:3306/laptop4you
```

### 4. Run the application

#### Linux/macOS

```bash
./mvnw spring-boot:run
```

#### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```
## 5.Development Roadmap


### Phase 1 — Project Foundation

- [x] Initialize the Spring Boot project
- [x] Configure Java 21 and Maven
- [x] Configure MySQL and Spring Data JPA
- [x] Design the initial database entities and relationships
- [x] Create request and response DTOs
- [x] Configure Lombok and MapStruct
- [x] Implement a reusable base entity for auditing
- [x] Add request validation with Jakarta Validation
- [x] Define application error codes
- [x] Implement custom application exceptions
- [x] Implement centralized exception handling
- [x] Add HTTP request and error logging
- [ ] Standardize successful API responses
- [ ] Add reusable pagination and sorting conventions
- [ ] Separate local, development, and production configurations

### Phase 2 — Brand Management

- [x] Create the Brand entity and repository
- [x] Create Brand request and response DTOs
- [x] Implement Brand mapping with MapStruct
- [x] Create a new brand
- [x] Update an existing brand
- [x] Get a brand by ID
- [x] Get paginated brands
- [x] Search brands by name
- [x] Sort brand results
- [x] Delete or deactivate a brand
- [x] Prevent duplicate brand names
- [x] Handle brand validation and business errors
- [ ] Add automated tests for Brand APIs

### Phase 3 — Supplier Management

- [ ] Complete the Supplier entity and database mapping
- [ ] Create Supplier request and response DTOs
- [ ] Implement Supplier mapping with MapStruct
- [ ] Create a supplier
- [ ] Update supplier information
- [ ] Get a supplier by ID
- [ ] Get paginated suppliers
- [ ] Search suppliers by name, phone number, or email
- [ ] Activate or deactivate a supplier
- [ ] Prevent duplicate supplier information
- [ ] Prevent deletion when a supplier is referenced by purchase orders
- [ ] Add validation and exception handling
- [ ] Add automated tests for Supplier APIs

### Phase 4 — Promotion Management

- [ ] Complete the Promotion entity and database mapping
- [ ] Create Promotion request and response DTOs
- [ ] Create, update, and view promotions
- [ ] Get paginated promotions
- [ ] Search promotions by name
- [ ] Validate promotion start and end dates
- [ ] Validate discount values and discount types
- [ ] Automatically determine promotion availability
- [ ] Activate, deactivate, or expire promotions
- [ ] Prevent overlapping or invalid promotion periods
- [ ] Add automated tests for Promotion APIs

### Phase 5 — Account and User Profile Management

- [ ] Complete the Account entity and repository
- [ ] Complete the Customer entity and repository
- [ ] Complete the Employee entity and repository
- [ ] Complete the Address entity and repository
- [ ] Create DTOs and mappers for accounts and user profiles
- [ ] Create customer accounts
- [ ] Create employee accounts
- [ ] Get and update customer profiles
- [ ] Get and update employee profiles
- [ ] Manage multiple customer addresses
- [ ] Set a default delivery address
- [ ] Validate unique usernames, emails, and phone numbers
- [ ] Encode account passwords securely
- [ ] Activate, deactivate, and lock accounts
- [ ] Add automated tests for account and profile APIs

### Phase 6 — Product Catalog Management

- [ ] Complete the Product entity and database relationships
- [ ] Complete the ProductImage entity and relationship
- [ ] Create Product request and response DTOs
- [ ] Implement Product mapping with MapStruct
- [ ] Create a product linked to a brand
- [ ] Update product information
- [ ] Get product details by ID
- [ ] Get paginated products
- [ ] Search products by name
- [ ] Filter products by brand
- [ ] Filter products by price range
- [ ] Filter products by availability and status
- [ ] Support multiple sorting options
- [ ] Manage product specifications
- [ ] Add and remove product images
- [ ] Select a primary product image
- [ ] Activate, deactivate, and hide products
- [ ] Validate product price and stock data
- [ ] Prevent deletion of products referenced by transactions
- [ ] Add automated tests for Product APIs

### Phase 7 — Product Promotion Integration

- [ ] Assign promotions to eligible products
- [ ] Remove promotions from products
- [ ] Display active promotions in product responses
- [ ] Calculate the effective selling price
- [ ] Validate whether a promotion is currently applicable
- [ ] Handle expired and inactive promotions
- [ ] Define behavior when multiple promotions apply
- [ ] Preserve original prices for order calculations
- [ ] Add tests for promotional price calculations

### Phase 8 — Authentication and Authorization

- [ ] Integrate Spring Security
- [ ] Implement customer registration
- [ ] Implement login for customers and employees
- [ ] Generate and validate JWT access tokens
- [ ] Implement refresh tokens
- [ ] Implement logout and token invalidation
- [ ] Configure role-based authorization
- [ ] Restrict administrative APIs to employees
- [ ] Restrict customer resources to their owners
- [ ] Add password change functionality
- [ ] Add forgot-password and password-reset workflows
- [ ] Handle disabled and locked accounts
- [ ] Add authentication and authorization tests

### Phase 9 — Inventory and Purchase Order Management

- [ ] Complete the PurchaseOrder entity
- [ ] Complete the PurchaseOrderItem entity
- [ ] Create purchase order DTOs and mappers
- [ ] Create a purchase order for a supplier
- [ ] Add multiple products to a purchase order
- [ ] Validate purchase quantities and import prices
- [ ] Calculate purchase order totals
- [ ] Define purchase order statuses
- [ ] Update purchase orders before confirmation
- [ ] Confirm received purchase orders
- [ ] Increase product stock after receiving goods
- [ ] Prevent repeated stock updates
- [ ] Cancel valid purchase orders
- [ ] Prevent cancellation after goods are received
- [ ] Store historical import prices
- [ ] Add tests for inventory update transactions

### Phase 10 — Shopping Cart Management

- [ ] Complete the Cart entity
- [ ] Complete the CartItem entity
- [ ] Automatically create a cart for each customer
- [ ] Get the current customer cart
- [ ] Add a product to the cart
- [ ] Update a cart item quantity
- [ ] Remove an item from the cart
- [ ] Clear all items from the cart
- [ ] Merge quantities when adding an existing product
- [ ] Validate product availability
- [ ] Validate requested quantities against current stock
- [ ] Recalculate cart totals
- [ ] Apply current promotional prices
- [ ] Handle inactive or deleted products
- [ ] Add automated tests for Cart APIs

### Phase 11 — Order and Checkout Management

- [ ] Complete the Order entity
- [ ] Complete the OrderItem entity
- [ ] Create Order request and response DTOs
- [ ] Create an order from the shopping cart
- [ ] Validate the customer delivery address
- [ ] Copy product information into order items
- [ ] Preserve product prices at checkout time
- [ ] Apply valid promotions at checkout time
- [ ] Calculate subtotal, discount, shipping fee, and final total
- [ ] Validate stock before creating an order
- [ ] Reduce stock within a database transaction
- [ ] Clear purchased items from the cart
- [ ] Generate a unique order code
- [ ] Get customer order history
- [ ] Get detailed order information
- [ ] Allow employees to search and filter orders
- [ ] Define valid order status transitions
- [ ] Confirm, prepare, ship, deliver, and complete orders
- [ ] Allow customers to cancel eligible orders
- [ ] Restore stock after a valid cancellation
- [ ] Prevent invalid status transitions
- [ ] Add transactional and concurrency tests

### Phase 12 — Payment Management

- [ ] Support cash on delivery
- [ ] Define payment methods and payment statuses
- [ ] Record payment information for each order
- [ ] Integrate an online payment gateway
- [ ] Generate payment requests
- [ ] Process payment return URLs
- [ ] Process asynchronous payment callbacks
- [ ] Verify callback signatures
- [ ] Prevent duplicate payment processing
- [ ] Update order status after successful payment
- [ ] Handle failed and expired payments
- [ ] Implement payment reconciliation
- [ ] Implement refund processing for cancelled orders
- [ ] Add payment integration tests

### Phase 13 — Product Review Management

- [ ] Complete the Review entity and relationships
- [ ] Create Review request and response DTOs
- [ ] Allow customers to review purchased products
- [ ] Verify that an order was successfully completed
- [ ] Prevent duplicate reviews for the same order item
- [ ] Validate rating values
- [ ] Update and delete customer reviews
- [ ] Get paginated reviews for a product
- [ ] Calculate the average product rating
- [ ] Calculate rating distribution
- [ ] Allow employees to hide inappropriate reviews
- [ ] Add automated tests for Review APIs

### Phase 14 — Administration and Reporting

- [ ] Build an administrative dashboard API
- [ ] Report revenue by day, month, and year
- [ ] Report order counts by status
- [ ] Report best-selling products
- [ ] Report low-stock and out-of-stock products
- [ ] Report top customers
- [ ] Report import costs and estimated profit
- [ ] Report promotion performance
- [ ] Export reports to CSV or Excel
- [ ] Add filters for configurable reporting periods
- [ ] Optimize reporting queries

### Phase 15 — API Documentation and Quality Assurance

- [ ] Integrate OpenAPI and Swagger UI
- [ ] Document all endpoints and request parameters
- [ ] Document validation and error responses
- [ ] Add Postman collections and environments
- [ ] Add unit tests for service classes
- [ ] Add integration tests for repositories and controllers
- [ ] Add Testcontainers for MySQL integration tests
- [ ] Define a minimum test coverage target
- [ ] Add code formatting and static analysis
- [ ] Add database migration management with Flyway or Liquibase
- [ ] Review transaction boundaries and concurrency handling

### Phase 16 — Deployment and Operations

- [ ] Create production-ready configuration
- [ ] Move all secrets to environment variables
- [ ] Add Docker support for the backend
- [ ] Add Docker Compose for the backend and MySQL
- [ ] Configure structured application logging
- [ ] Configure log rotation and retention
- [ ] Add Spring Boot Actuator health checks
- [ ] Configure CORS for the frontend application
- [ ] Create a CI pipeline for building and testing
- [ ] Create a CD pipeline for deployment
- [ ] Configure database backup and recovery
- [ ] Add production monitoring and alerting
- [ ] Perform security and performance testing
- [ ] Deploy the first production release

## Author

Developed by [Kevin](https://github.com/KevinDev-270801).

## License

This project is developed for educational and graduation-thesis purposes.
