# sku-service

Spring Boot REST service exposing a GET API to look up SKU details by SKU ID,
backed by a mocked service layer. Includes a servlet filter that logs each
request/response with HTTP status, status code, and time taken (ms).

## Requirements
- Java 17
- Maven 3.8+

## Running locally

```powershell
mvn spring-boot:run
```

The app starts on `http://localhost:8080`.

## API

### GET /api/sku/{skuId}

Returns mocked SKU details.

**Known SKUs:** `SKU1001`, `SKU1002`, `SKU1003`

Example:
```powershell
curl http://localhost:8080/api/sku/SKU1001
```

Response (200 OK):
```json
{
  "skuId": "SKU1001",
  "productName": "Wireless Mouse",
  "price": 19.99,
  "quantityAvailable": 150,
  "found": true
}
```

Unknown SKU returns `404 Not Found` with `found: false`.

## Request/Response Logging Filter

`RequestResponseLoggingFilter` wraps every request/response and logs:
- HTTP method and URI
- Response status code and status text
- Time taken to process the request (ms)

## Build, Test & Coverage

```powershell
mvn clean verify
```

- Runs unit and integration tests (JUnit 5, Mockito, Spring Boot Test).
- JaCoCo agent collects coverage during `test` phase and generates an HTML/XML report at `target/site/jacoco`.
- `jacoco-maven-plugin:check` runs during the `verify` phase and **fails the build** if branch coverage falls below **80%**.

## Jenkins Pipeline

The `Jenkinsfile` defines a declarative pipeline with these stages:
1. **Checkout** – pulls source from SCM.
2. **Build & Unit Test** – `mvn clean test`.
3. **Coverage Check** – `mvn verify`, which fails the build if JaCoCo branch coverage < 80%.
4. **SonarQube Analysis** – runs `sonar:sonar`, pushing the JaCoCo XML report for coverage visibility in SonarQube.
5. **Quality Gate** – waits on the SonarQube Quality Gate result and aborts the pipeline on failure.

> **Note:** SonarQube host URL and auth token in the `Jenkinsfile`/`pom.xml` are placeholders
> (`SONAR_HOST_PLACEHOLDER`, `SONAR_TOKEN_PLACEHOLDER`). Update the Jenkins credential ID,
> the `withSonarQubeEnv` server name (`SonarQubeServer`), and Maven/JDK tool names
> (`Maven3`, `JDK17`) to match your local Jenkins configuration.

