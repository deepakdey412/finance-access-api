# Finance Data Processing and Access Control System

A fintech-grade backend application built with Java Spring Boot that provides secure, role-based access to financial records with comprehensive user management and analytics capabilities.

## 🚀 Features

- **User Management**: Create, update, and manage users with role-based permissions
- **Role-Based Access Control**: Three roles with different permission levels
  - **VIEWER**: Read-only access to financial records
  - **ANALYST**: Read access + dashboard analytics
  - **ADMIN**: Full access including CRUD operations and user management
- **Financial Records Management**: Complete CRUD operations for financial transactions
- **Advanced Filtering**: Filter records by date range, type, category with pagination support
- **Dashboard Analytics**: Aggregated financial data including:
  - Total income and expense
  - Net balance
  - Category-wise totals
  - Monthly trends
  - Recent transactions
- **Security**: BCrypt password hashing, Spring Security authentication
- **API Documentation**: Interactive Swagger UI for testing endpoints
- **Data Persistence**: H2 in-memory database with automatic schema generation

## 🛠️ Tech Stack

- **Framework**: Spring Boot 3.2.0
- **Security**: Spring Security 6.x
- **Database**: H2 (in-memory for development)
- **ORM**: Spring Data JPA / Hibernate
- **Password Hashing**: BCrypt
- **API Documentation**: Springdoc OpenAPI 2.3.0
- **Validation**: Jakarta Bean Validation
- **Build Tool**: Maven
- **Java Version**: 17

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+

## 🏃 Running the Application

1. **Clone the repository** (or navigate to the project directory)

2. **Build the project**:
   
   On Windows:
   ```bash
   mvnw.cmd clean install
   ```
   
   On Linux/Mac:
   ```bash
   ./mvnw clean install
   ```

3. **Run the application**:
   
   On Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```
   
   On Linux/Mac:
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the application**:
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - H2 Console: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:mem:financedb`
     - Username: `sa`
     - Password: (leave empty)

## 👥 Default Users

The application initializes with three default users:

| Username | Password    | Role    | Permissions                                    |
|----------|-------------|---------|------------------------------------------------|
| admin    | admin123    | ADMIN   | Full access (CRUD + user management)           |
| analyst  | analyst123  | ANALYST | Read access + dashboard analytics              |
| viewer   | viewer123   | VIEWER  | Read-only access to financial records          |

## 📚 API Endpoints

### User Management (ADMIN only)

- `POST /api/users` - Create a new user
- `PUT /api/users/{id}` - Update user role/status
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `DELETE /api/users/{id}` - Delete user

### Financial Records

- `POST /api/records` - Create financial record (ADMIN)
- `PUT /api/records/{id}` - Update financial record (ADMIN)
- `DELETE /api/records/{id}` - Delete financial record (ADMIN)
- `GET /api/records/{id}` - Get record by ID (VIEWER, ANALYST, ADMIN)
- `GET /api/records` - Get filtered records with pagination (VIEWER, ANALYST, ADMIN)
  - Query params: `startDate`, `endDate`, `type`, `category`, `page`, `size`

### Dashboard Analytics (ANALYST, ADMIN)

- `GET /api/dashboard/summary` - Get complete dashboard summary
- `GET /api/dashboard/income` - Get total income
- `GET /api/dashboard/expense` - Get total expense
- `GET /api/dashboard/balance` - Get net balance
- `GET /api/dashboard/category-totals` - Get category-wise totals
- `GET /api/dashboard/monthly-trends` - Get monthly trends
- `GET /api/dashboard/recent` - Get recent transactions

## 🔐 Authentication

The API uses HTTP Basic Authentication. Include credentials in the Authorization header:

```
Authorization: Basic base64(username:password)
```

Or use the Swagger UI which provides an authentication dialog.

## 📖 API Documentation

Interactive API documentation is available at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

The Swagger UI allows you to:
- View all available endpoints
- See request/response schemas
- Test endpoints directly from the browser
- Authenticate with different user roles

## 🏗️ Architecture

The application follows clean layered architecture:

```
┌─────────────────────────────────────────┐
│         Controller Layer                │  ← REST API endpoints
├─────────────────────────────────────────┤
│         Service Layer                   │  ← Business logic
├─────────────────────────────────────────┤
│         Repository Layer                │  ← Data access
├─────────────────────────────────────────┤
│         Entity Layer                    │  ← Database models
└─────────────────────────────────────────┘

         Cross-Cutting Concerns:
    ┌──────────────────────────────┐
    │  Security Layer              │
    │  Exception Handling Layer    │
    │  DTO/Validation Layer        │
    └──────────────────────────────┘
```

### Package Structure

```
com.finance.system
├── controller          # REST controllers
├── service            # Business logic services
├── repository         # JPA repositories
├── entity             # JPA entities
├── dto                # Data Transfer Objects
│   ├── request        # Request DTOs
│   └── response       # Response DTOs
├── security           # Security configuration
├── exception          # Exception classes and handlers
└── config             # Application configuration
```

## 🔒 Security Features

- **Password Hashing**: BCrypt with work factor of 12
- **Authentication**: HTTP Basic Auth (extensible to JWT)
- **Authorization**: Method-level security with @PreAuthorize
- **Session Management**: Stateless (no server-side sessions)
- **CSRF Protection**: Disabled for API usage
- **Input Validation**: Comprehensive DTO validation
- **Error Handling**: Secure error messages without sensitive data exposure

## 📊 Database Schema

### Users Table
- `id` (BIGINT, Primary Key)
- `username` (VARCHAR, Unique, Indexed)
- `password` (VARCHAR, BCrypt hashed)
- `role` (VARCHAR: VIEWER, ANALYST, ADMIN)
- `active` (BOOLEAN)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

### Financial Records Table
- `id` (BIGINT, Primary Key)
- `amount` (DECIMAL)
- `type` (VARCHAR: INCOME, EXPENSE, Indexed)
- `category` (VARCHAR, Indexed)
- `date` (DATE, Indexed)
- `description` (VARCHAR)
- `is_deleted` (BOOLEAN, for soft delete)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

## 🧪 Testing

Run tests with:

On Windows:
```bash
mvnw.cmd test
```

On Linux/Mac:
```bash
./mvnw test
```

## 📝 Example Usage

### 1. Create a Financial Record (as ADMIN)

```bash
curl -X POST http://localhost:8080/api/records \
  -u admin:admin123 \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 1500.00,
    "type": "INCOME",
    "category": "Salary",
    "date": "2024-01-15",
    "description": "Monthly salary"
  }'
```

### 2. Get Dashboard Summary (as ANALYST)

```bash
curl -X GET http://localhost:8080/api/dashboard/summary \
  -u analyst:analyst123
```

### 3. Filter Records (as VIEWER)

```bash
curl -X GET "http://localhost:8080/api/records?startDate=2024-01-01&endDate=2024-12-31&type=INCOME&page=0&size=10" \
  -u viewer:viewer123
```

## 🎯 Design Principles

- **Clean Architecture**: Clear separation of concerns across layers
- **SOLID Principles**: Single responsibility, dependency injection
- **RESTful API Design**: Standard HTTP methods and status codes
- **Security First**: Fintech-grade security implementation
- **Scalability**: Efficient queries, pagination, indexing
- **Maintainability**: Modular code, comprehensive documentation

## 🚧 Future Enhancements

- JWT-based authentication
- Advanced search functionality
- Export data to CSV/PDF
- Email notifications
- Audit logging
- Rate limiting
- API versioning
- Docker containerization
- CI/CD pipeline

## 📄 License

This project is for educational and demonstration purposes.

## 👨‍💻 Author

Finance System Development Team

---

**Note**: This is a development setup using H2 in-memory database. For production, configure a persistent database (PostgreSQL, MySQL, etc.) and implement additional security measures.
