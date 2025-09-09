# Food Order Backend Service

A scalable backend service for processing food delivery orders built with Java, Spring Boot, and MySQL/PostgreSQL. Features asynchronous order processing using an in-memory queue and includes comprehensive API documentation with Swagger.

## Features

- **RESTful APIs** for order management
- **Asynchronous order processing** with in-memory queue
- **Database persistence** with MySQL/PostgreSQL support
- **Input validation** and comprehensive exception handling
- **Pagination** support for order listing
- **Swagger/OpenAPI** documentation
- **Order status tracking** and manual updates
- **Spring Boot best practices** with service layer abstraction

## Tech Stack

- **Java 17**
- **Spring Boot 3.5.5**
- **Spring Data JPA**
- **PostgreSQL
- **Swagger/OpenAPI 3**
- **Lombok**
- **Maven**

## Project Structure

```
src/
├── main/
│   ├── java/com/lime_tray/Food_Order/
│   │   ├── controller/          # REST controllers
│   │   ├── service/             # Business logic
│   │   ├── repository/          # Data access layer
│   │   ├── entity/              # JPA entities
│   │   ├── dto/                 # Data transfer objects
│   │   ├── queue/               # In-memory queue implementation
│   │   ├── async/               # Async configuration
│   │   ├── exception/           # Exception handling
│   │   └── FoodOrderApplication.java
│   └── resources/
│       ├── application.properties           # PostgreSQL config
│       └── application-mysql.properties     # MySQL config
└── test/
    └── java/                    # Test classes
```

## Setup Instructions

### Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8.0** or **PostgreSQL 12+**
- **Git**

### Database Setup


#### PostgreSQL

1. Install PostgreSQL and create database:
```sql
CREATE DATABASE food_order_db;
```

2. Run with default profile:
```bash
mvn spring-boot:run
```

### Application Setup

1. **Clone the repository:**
```bash
git clone <repository-url>
cd Food_Order
```

2. **Configure database connection:**
    - For PostgreSQL: Update `src/main/resources/application.properties`

3. **Install dependencies:**
```bash
mvn clean install
```

4. **Run the application:**
```bash
mvn spring-boot:run
```

5. **Access the application:**
    - API Base URL: `http://localhost:8080`
    - Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## API Documentation

### 1. Place Order
- **POST** `/api/orders`
- **Description:** Place a new food order
- **Request Body:**
```json
{
    "customerName": "John Doe",
    "items": ["Pizza", "Coke"],
    "totalAmount": 25.50
}
```
- **Response:**
```json
{
    "id": 1,
    "customerName": "John Doe",
    "items": ["Pizza", "Coke"],
    "totalAmount": 25.50,
    "orderTime": "2024-01-15T10:30:00",
    "status": "PENDING"
}
```

### 2. Get All Orders
- **GET** `/api/orders?page=0&size=10`
- **Description:** Retrieve all orders with pagination
- **Query Parameters:**
    - `page`: Page number (default: 0)
    - `size`: Page size (default: 5)
- **Response:**
```json
{
    "content": [
        {
            "id": 1,
            "customerName": "John Doe",
            "items": ["Pizza", "Coke"],
            "totalAmount": 25.50,
            "orderTime": "2024-01-15T10:30:00",
            "status": "PENDING"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10
    },
    "totalElements": 1
}
```

### 3. Get Order Status
- **GET** `/api/orders/{id}/status`
- **Description:** Get status of a specific order
- **Path Parameters:**
    - `id`: Order ID
- **Response:**
```json
{
    "id": 1,
    "customerName": "John Doe",
    "items": ["Pizza", "Coke"],
    "totalAmount": 25.50,
    "orderTime": "2024-01-15T10:30:00",
    "status": "PROCESSED"
}
```

### 4. Update Order Status
- **PUT** `/api/orders/{id}/status?status=PROCESSED`
- **Description:** Manually update order status
- **Path Parameters:**
    - `id`: Order ID
- **Query Parameters:**
    - `status`: New status (PENDING or PROCESSED)
- **Response:**
```json
{
    "id": 1,
    "customerName": "John Doe",
    "items": ["Pizza", "Coke"],
    "totalAmount": 25.50,
    "orderTime": "2024-01-15T10:30:00",
    "status": "PROCESSED"
}
```

## Testing the Queue System

The application includes an in-memory queue system that automatically processes orders asynchronously:

1. **Place an order** using the POST endpoint
2. **Check order status** immediately - it should be "PENDING"
3. **Wait 10 seconds** for background processing
4. **Check order status again** - it should now be "PROCESSED"
5. **Monitor console logs** to see processing messages

### Testing with cURL

```bash
# Place an order
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Cust1",
    "items": ["Pizza", "Coke"],
    "totalAmount": 25.50
  }'

# Get all orders
curl "http://localhost:8080/api/orders?page=0&size=10"

# Get order status
curl http://localhost:8080/api/orders/1/status

# Update order status
curl -X PUT "http://localhost:8080/api/orders/1/status?status=PROCESSED"
```

## Architecture Features

### Asynchronous Processing
- Orders are queued immediately after creation
- Background processor runs every 2 seconds
- Automatic status update from PENDING to PROCESSED
- Thread pool configuration for scalability


### Validation & Exception Handling
- Input validation with Bean Validation
- Global exception handler for consistent error responses
- Custom error messages for better user experience

### Best Practices
- Service layer abstraction
- DTO pattern for data transfer
- Repository pattern for data access
- Configuration externalization
- Swagger documentation

## Configuration

### Application Properties

Key configurations in `application.properties`:

```properties
# Server
server.port=8080

# Database (PostgreSQL)
spring.datasource.url=jdbc:postgresql://localhost:5432/food_order_db
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Async Processing
spring.task.execution.pool.core-size=2
spring.task.execution.pool.max-size=5
spring.task.execution.pool.queue-capacity=50
```


## Development

### Build Project
```bash
mvn clean compile
```

### Run Tests
```bash
mvn test
```

### Package Application
```bash
mvn clean package
```

### Run JAR
```bash
java -jar target/Food_Order-0.0.1-SNAPSHOT.jar
```


