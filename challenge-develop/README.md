# User Registration API

## Overview

This project is a RESTful API for user registration built with **Spring Boot**. The API allows clients to register users by providing their details, including name, email, password, and a list of phone numbers. The application stores user data in an in-memory database (H2) and generates a JWT token upon successful registration.

## Features

- User registration with validation of email and password.
- Error handling for duplicate email registrations.
- JWT token generation upon successful user registration.
- In-memory database using H2.
- Simple and extensible architecture using Spring Boot, JPA, and Maven.

## Technologies Used

- **Spring Boot**
- **Docker**
- **JPA** for database persistence
- **H2** in-memory database
- **JWT** for authentication token generation
- **Maven** for project management and build
- **Java 17**

## Setup and Installation

### Prerequisites

- **Java 17+** installed.
- **Maven** installed.
- **Docker**

### Running the Application

1. Clone the repository:

    ```bash
    git clone git@github.com:Joel1401/bci.git
    ```

2. Build the project with Maven:

    ```bash
    mvn clean install
    ```

3. Run the application:

    ```bash
    mvn spring-boot:run
    ```

4. The application will start at `http://localhost:8080`.
5. Access to Swagger : http://localhost:8080/swagger-ui/index.html
6. # Building the docker image

* docker-compose build

### API Endpoints

#### Register User

- **Endpoint**: `POST /auth/user/signup`
- **Description**: Registers a new user in the system.
- **Request Body**:

    ```json
    {
        "name": "John Doe",
        "email": "john.doe@example.com",
        "password": "Password123",
        "phones": [
            {
                "number": "1234567",
                "citycode": "1",
                "contrycode": "57"
            }
        ]
    }
    ```

- **Success Response**:

    ```json
    {
        "id": "bfef6dcc-f67d-4b1f-93b8-554026c7fa9c",
        "created": "2024-10-10T10:15:30",
        "modified": "2024-10-10T10:15:30",
        "lastLogin": "2024-10-10T10:15:30",
        "token": "$2a$10$yWz5iJmc60WCPouLx4lyF.cKM12bNwsuutS59oT4LWTFY8KoHgq0K"
    }
    ```

- **Error Response (Duplicate Email)**:

    ```json
    {
        "mensaje": "El correo ya registrado"
    }
    ```

### Database Console

You can access the H2 database console via:

- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: *(leave empty)*

### Running Tests

To run the unit tests, use the following command:

```bash
mvn test


### Key Points of the README:

1. **Overview**: High-level description of the project.
2. **Features**: Highlights the key functionality.
3. **Technologies Used**: Lists the frameworks and tools used.
4. **Setup and Installation**: Step-by-step instructions to clone, build, and run the application.
5. **API Endpoints**: Documents the main API endpoint for user registration.
6. **H2 Database Console**: Instructions to access the in-memory database console.
7. **Running Tests**: Explains how to run tests.
8. **Project Structure**: Provides a simple structure for understanding the layout of the codebase.
9. **License**: Specifies the licensing details for the project.

Feel free to customize the repository URLs, names, and other specific information to match your setup.