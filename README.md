# Spring Boot Web Application Showcase

This is a simple Spring Boot web application designed to showcase basic CRUD operations using **Employee** and **Department** entities. It includes features such as validation, search functionality, Swagger API documentation, and centralized exception handling.

## Features

- CRUD operations for **Employee** and **Department** entities.
- **Validation** to ensure correct data input.
- **Search API** using **Java Specification** and **Criteria API**.
- **Centralized exception handling** using `@RestControllerAdvice`.
- **Swagger** API documentation via **Springdoc OpenAPI**.
- Runs on **port 8080**.
- Preloads dummy data into the **H2 database** upon startup.

## Getting Started

### Prerequisites

- **Java 8** or higher
- **Maven**

### Running the Application

1. Clone the repository:
    ```bash
    git clone <repository-url>
    cd <repository-directory>
    ```

2. Build and run the application using Maven:
    ```bash
    mvn spring-boot:run
    ```

3. The application will be accessible at:
    ```bash
    http://localhost:8080
    ```

### H2 Database Configuration

The application uses an **H2 in-memory database**. Upon startup, dummy data is preloaded into the **Employee** and **Department** tables.


- **Username:** `sa`
- **Password:** `pass`

You can access the H2 console at:
```bash
http://localhost:8080/h2-console
```

API Documentation

Swagger UI is available for API documentation at:
```bash
http://localhost:8080/swagger-ui.html
```
## Technologies Used

- **Spring Boot** for building the application
- **Spring Data JPA** for database interactions
- **H2 Database** for in-memory data storage
- **Springdoc OpenAPI** for Swagger API documentation
- **Java Specification** and **Criteria API** for dynamic search functionality
- **ObjectMapper** for DTO mapping
- **RestControllerAdvice** for centralized exception handling
