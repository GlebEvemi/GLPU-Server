# GLPU Inventory API

A Spring Boot REST API for managing computer inventory with authentication and authorization using MariaDB database.

## Project Overview

GLPU Inventory is a backend API service built with Spring Boot that provides endpoints for managing and tracking computer information across a network. The system implements role-based access control with secure authentication.

### Key Features

- **Computer Management**: Create, read, and update computer inventory records
- **User Authentication**: Secure authentication with role-based access control (ADMIN role)
- **RESTful API**: Standard REST endpoints for all operations
- **Persistent Storage**: MariaDB database integration
- **Docker Support**: Pre-configured Docker and Docker Compose setup for easy deployment
- **SSL/TLS Support**: Built-in SSL configuration for secure communications

## Technology Stack

- **Framework**: Spring Boot 4.0.3
- **Language**: Java 21
- **Build Tool**: Maven
- **Database**: MariaDB
- **Security**: Spring Security with HTTP Basic Authentication
- **ORM**: Spring Data JPA with Jakarta Persistence
- **API Format**: JSON/REST
- **Containerization**: Docker & Docker Compose

## Prerequisites

- Java 21 or later
- Maven 3.6+
- Docker & Docker Compose (optional, for containerized deployment)
- MariaDB 10.5+ (if running locally without Docker)

## Project Structure

```
GLPUinventory/
├── src/
│   ├── main/
│   │   ├── java/com/skillissue/GLPUinventory/
│   │   │   ├── Application.java              # Main Spring Boot application entry point
│   │   │   ├── Controller/                   # REST API endpoints
│   │   │   │   └── RestApiPcController.java  # Computer API endpoints (CRUD operations)
│   │   │   ├── Entity/                       # JPA entities
│   │   │   │   ├── Computer.java             # Computer inventory model
│   │   │   │   └── User.java                 # User model with authentication
│   │   │   ├── Repository/                   # Data access layer
│   │   │   │   ├── ComputerRepository.java   # Computer database operations
│   │   │   │   └── UserRepository.java       # User database operations
│   │   │   ├── Service/                      # Business logic
│   │   │   │   └── MyUserDetailsService.java # Custom user details for Spring Security
│   │   │   ├── Security/                     # Security configuration
│   │   │   │   └── SecurityConfig.java       # Spring Security bean configuration
│   │   │   └── DataLoader.java               # Initial data loading utility
│   │   └── resources/
│   │       └── application.properties        # Spring Boot configuration
│   └── test/
│       └── java/com/skillissue/GLPUinventory/
│           └── ApplicationTests.java         # Integration tests
├── Dockerfile                                # Container image definition
├── docker-compose.yml                        # Multi-container orchestration
├── pom.xml                                   # Maven project configuration
└── certs/                                    # SSL/TLS certificates directory
```

## Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/GlebEvemi/GLPU-api.git
cd GLPUinventory
```

### 2. Environment Configuration

Create a `.env` file in the project root (copy from `.env.example`):

```bash
cp .env.example .env
```

Configure the following variables:

```env
# MariaDB Configuration
MARIADB_DATABASE=glpu_inventory
MARIADB_USER=glpu_user
MARIADB_PASSWORD=your_secure_password
MARIADB_ROOT_PASSWORD=root_password

# Spring Datasource Configuration
SPRING_DATASOURCE_URL=jdbc:mariadb://mariadb:3306/glpu_inventory
SPRING_DATASOURCE_USERNAME=glpu_user
SPRING_DATASOURCE_PASSWORD=your_secure_password

# SSL Configuration (optional)
SERVER_SSL_KEY_STORE_PASSWORD=your_keystore_password
SERVER_SSL_KEY_STORE=file:certs/keystore.jks
```

### 3. Build the Application

#### Using Maven Directly

```bash
# Clean and build the project
mvn clean package

# Run the application
java -jar target/GLPUinventory-0.0.1-SNAPSHOT.jar
```

#### Using Docker Compose (Recommended)

```bash
# Build and start all services
docker-compose up -d

# View logs
docker-compose logs -f app
```

The API will be available at `https://glpu-server.ivkh.ee/api/pc`

## API Documentation

### Base URL
```
https://glpu-server.ivkh.ee/api/pc
```

### Authentication
All API endpoints require **HTTP Basic Authentication** with ADMIN role.

**Example with curl:**
```bash
curl -u username:password https://glpu-server.ivkh.ee/api/pc
```

### Endpoints

#### 1. Get All Computers (List Hostnames)
```
GET /api/pc
```

**Response:**
```json
["PC-001", "PC-002", "PC-003"]
```

#### 2. Get Computer Details
```
GET /api/pc/{hostname}
```

**Response:**
```json
{
  "Hostname": "PC-001",
  "OSName": "Windows",
  "OSVersion": "10",
  "BiosManufacturer": "Dell",
  "BiosStatus": "Ok",
  "CsDomain": "CORPORATE",
  "CsModel": "OptiPlex 7090",
  "csProcessorName": "Intel Core i7-10700K",
  "totalRam_Gb": 32.0,
  "diskTotalSize": 1024.0,
  "diskFreeGb": 512.0
}
```

#### 3. Create Computer
```
POST /api/pc
Content-Type: application/json
```

**Request Body:**
```json
{
  "Hostname": "NEW-PC-001",
  "OSName": "Windows",
  "OSVersion": "11",
  "BiosManufacturer": "Dell",
  "BiosStatus": "Ok",
  "CsDomain": "CORPORATE",
  "CsModel": "OptiPlex 7090",
  "csProcessorName": "Intel Core i7-10700K",
  "totalRam_Gb": 32.0,
  "diskTotalSize": 2048.0,
  "diskFreeGb": 1536.0
}
```

#### 4. Update Computer (Create or Update)
```
PUT /api/pc
Content-Type: application/json
```

Returns HTTP 201 (Created) if new computer, HTTP 200 (OK) if updated.

## User Management

Default users can be loaded via `DataLoader.java`. Users must have the `ROLE_ADMIN` role to access the API endpoints.

**User Structure:**
```java
{
  "id": "UUID",
  "username": "admin",
  "pass": "encrypted_password",
  "role": "ROLE_ADMIN"
}
```

## Database Schema

### Computer Table
```sql
CREATE TABLE computer (
  hostname VARCHAR(255) PRIMARY KEY,
  osName VARCHAR(255),
  osVersion VARCHAR(255),
  biosManufacturer VARCHAR(255),
  biosStatus VARCHAR(255),
  csDomain VARCHAR(255),
  csModel VARCHAR(255),
  csProcessorName VARCHAR(255),
  totalRam_Gb DOUBLE,
  diskTotalSize DOUBLE,
  diskFreeGb DOUBLE
);
```

### User Table
```sql
CREATE TABLE user (
  id VARCHAR(36) PRIMARY KEY,
  username VARCHAR(255) UNIQUE,
  password VARCHAR(255),
  role VARCHAR(50)
);
```

## Security Features

- **CSRF Protection**: Disabled for API (stateless authentication)
- **CORS**: Configure as needed in SecurityConfig.java
- **Role-Based Access Control**: All `/api/**` endpoints require ADMIN role
- **HTTP Basic Authentication**: Secure credentials transmission
- **SSL/TLS Support**: Optional HTTPS configuration available

## Development

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ApplicationTests
```

### Code Style

The project uses:
- **Lombok**: Reduces boilerplate code with annotations
- **Jakarta Persistence (JPA)**: Modern Java persistence framework
- **Spring Data JPA**: Simplified database operations

### Building for Production

```bash
# Create optimized JAR for production
mvn clean package -DskipTests

# Build Docker image
docker build -t glpu-inventory:latest .
```

## Docker Deployment

### Using Docker Compose

1. Ensure Docker and Docker Compose are installed
2. Configure `.env` file with your settings
3. Run: `docker-compose up -d`

### Manual Docker Build

```bash
# Build the project first
mvn clean package

# Build Docker image
docker build -t glpu-inventory:1.0 .

# Run container
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mariadb://mariadb:3306/glpu_inventory \
  -e SPRING_DATASOURCE_USERNAME=glpu_user \
  -e SPRING_DATASOURCE_PASSWORD=your_password \
  glpu-inventory:1.0
```

## Configuration Files

### application.properties
Located at `src/main/resources/application.properties` - contains Spring Boot configuration settings

### docker-compose.yml
Defines multi-container environment with:
- MariaDB database service
- Spring Boot application service
- Volume mounting for data persistence
- Network configuration

## Troubleshooting

### Connection Refused
- Verify MariaDB service is running
- Check database credentials in `.env`
- Ensure database hostname/port is correct

### Authentication Failed
- Verify user exists in database with ADMIN role
- Check username/password encoding
- Review Spring Security configuration

### Build Failures
- Ensure Java 21 is installed: `java -version`
- Clear Maven cache: `mvn clean`
- Rebuild: `mvn package`

## Future Enhancements

- [ ] JWT token-based authentication
- [ ] API documentation with Swagger/OpenAPI
- [ ] Computer status monitoring
- [ ] Audit logging
- [ ] Advanced filtering and search
- [ ] Batch import/export operations
- [ ] Performance metrics collection

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

---

**Last Updated**: May 2026  
**Version**: 0.0.1-SNAPSHOT
