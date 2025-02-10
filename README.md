# Spring Books

Practice project for Spring Boot

## File Structure 

```
src/main/java/com/example/bookmanagement
├── controller // REST API endpoints
├── dto // Data Transfer Objects
├── entity // Database models
├── exception // Error handling
├── mapper // Object mapping
├── repository // JPA repository
└── service // Business logic
```

## Maven vs Gradle

Maven chosen for easy use :
- Build & Run: `mvn spring-boot:run`
- Test: `mvn test`

## Docs

Access API documentation at:
- Swagger UI: http://localhost:8080/swagger-ui.html

## CI

GitHub Actions workflows:

1. CI (ci.yml):
   - Builds project and runs tests
   - Runs SonarCloud analysis

2. Security Check (dependency-check.yml):
   - Runs weekly (Sunday at 00:00)
   - Can be manually triggered