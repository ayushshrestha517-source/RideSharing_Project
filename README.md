# Ride Sharing Backend

A backend REST API for a ride-sharing application built with Java, Spring Boot, and PostgreSQL. The project implements user authentication, role-based access control, passenger and driver management, ride operations, ratings, and OTP-based password reset.

## Features

### Authentication and Authorization
- User registration and login
- JWT-based authentication
- Role-based access control for passengers, drivers, and administrators
- Password encryption using BCrypt
- Change-password and OTP-based password-reset workflows

### Passenger Management
- Passenger profile management
- Emergency contact management
- Passenger ride history
- Driver rating after a completed ride

### Driver Management
- Driver application and verification workflow
- Admin approval of driver applications
- Driver online and offline status
- Driver ride management

### Ride Management
- Request a ride
- Direct ride acceptance
- Fare proposal and passenger response
- Start and complete rides
- Ride cancellation
- Ride history and status-based filtering

### Additional Capabilities
- PostgreSQL database integration
- DTO-based request and response handling
- Input validation
- Centralized exception handling
- Email-based OTP delivery
- Scheduled cleanup of expired OTP records
- API documentation through Swagger UI, when enabled

## Technology Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming language |
| Spring Boot | Application framework |
| Spring Web | REST API development |
| Spring Security | Authentication and authorization |
| Spring Data JPA | Database access |
| Hibernate | ORM |
| PostgreSQL | Relational database |
| JWT | Token-based authentication |
| BCrypt | Password hashing |
| Maven | Dependency management and build |
| Lombok | Boilerplate reduction |
| MapStruct | Object mapping |
| Firebase Admin SDK | Firebase integration |

## Prerequisites

Install the following before running the application:

- JDK compatible with the Java version declared in `pom.xml`
- Maven, or use the included Maven Wrapper if available
- PostgreSQL
- Git

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/ayushshrestha517-source/RideSharing_Project.git
cd RideSharing_Project
```

### 2. Create the PostgreSQL database

Open PostgreSQL and execute:

```sql
CREATE DATABASE ridesharing_db;
```

### 3. Configure application properties

The repository includes an `application-example.properties` file for reference.

Create `src/main/resources/application.properties` by copying the example file.

On Windows PowerShell:

```powershell
Copy-Item src/main/resources/application-example.properties src/main/resources/application.properties
```

Update the configuration with your local values:

- PostgreSQL database URL
- Database username and password
- JWT signing secret and token expiration
- SMTP host, port, username, and app password for email OTP delivery
- Any additional settings required by the application

Do not commit `application.properties`, real passwords, JWT secrets, email credentials, or other sensitive configuration to GitHub.

### 4. Run the application

1. Open the project in IntelliJ IDEA.
2. Configure `src/main/resources/application.properties` with your local PostgreSQL database credentials and other required settings.
3. Make sure PostgreSQL is running and the `ridesharing_db` database exists.
4. Open the main Spring Boot application class.
5. Click the green **Run (▶)** button in IntelliJ IDEA.
6. Check the Run console to confirm that the application starts successfully.

The application runs on port `8080` by default, unless configured otherwise.

If Swagger UI is enabled, open:

http://localhost:8080/swagger-ui/index.html
The application is configured to use port `8080` unless changed in the configuration.

## API Documentation

If Swagger/OpenAPI is enabled, start the application and visit:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Use Swagger UI or a REST client such as Postman to test the endpoints.

For protected endpoints, obtain a JWT through the login endpoint and send it in the request header:

```http
Authorization: Bearer <your-jwt-token>
```

Public and protected endpoints depend on the application's security configuration.

## Project Structure

The application follows a layered Spring Boot architecture. The main source code is organized into packages for responsibilities such as:

- `config` — application configuration
- `controller` — REST API endpoints
- `dao` — business logic and data operations
- `dto` — request and response objects
- `entity` — JPA entities
- `enums` — application enums
- `exception` — custom exceptions and error handling
- `mapper` — DTO/entity mapping
- `repository` — database access
- `service` — supporting services
- `util` — JWT and security components

Refer to the source code for the exact package structure.

## Security Considerations

- Passwords should be stored as BCrypt hashes, never as plain text.
- Keep database credentials and signing secrets outside version control.
- Use HTTPS in production.
- Validate permissions on every protected operation.
- Restrict access to administrative endpoints.
- Use a strong, private JWT signing secret.
- Configure production database and email credentials through environment-specific secrets.

## Future Improvements

Potential areas for further development include:

- Automated unit and integration tests
- Docker-based local development
- Continuous integration with GitHub Actions
- Deployment to a cloud platform
- Production monitoring and structured logging

## Contributors

This project was developed collaboratively.

- **Ayush Shrestha (Project Maintainer)** — Led the overall backend development and implemented the majority of the application's features, including authentication, security, ride management, passenger management, ratings, and other core functionality.
- **Shreyash Adhikari** — Contributed to driver-related functionality and role management.
- **Nikash Shrestha** — Assisted with passenger controller functionality, including passenger creation and retrieval endpoints.

### GitHub Profiles

- [Ayush Shrestha](https://github.com/ayushshrestha517-source)
- [Shreyash Adhikari](https://github.com/shreyash-009)
- [Nikash Shrestha](https://github.com/Nikash-Shrestha)

## License

No license has been specified for this project yet.