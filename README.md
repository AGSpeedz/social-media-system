# Social Media System Spring Boot REST API


## Technology Stack

- Java 17
- Spring Boot, Spring Web, and Bean Validation
- Spring Data JPA / Hibernate
- H2 in-memory database
- Gradle (Groovy DSL)
- Swagger UI / OpenAPI (springdoc)
- JUnit 5 and Mockito for automated testing
- Postman for API testing

## Architecture

The application follows a layered Spring Boot architecture:

- **Presentation (`presentation`)**: REST controllers and HTTP responses.
- **Request DTOs (`presentation/request`)**: Incoming API data and validation.
- **Response DTOs (`presentation/response`)**: Outgoing API data.
- **Service (`service`)**: Business logic and transactional operations.
- **Data Layer (`datalayer`)**: JPA entities, relationships, and Spring Data JPA repositories.
- **Mapper (`mapper`)**: Entity and DTO conversions.
- **Exception (`exception`)**: Centralized API error handling.
- **Bootstrap (`bootstrap`)**: Automatic database seeding.
- **Config (`config`)**: OpenAPI/Swagger configuration.

I also now have Lombok annotations such as
@Getter, @Setter, and @NoArgsConstructor to reduce
boilerplate code.

### Data model

- **User** `id`, `username`, `email`, `displayName`
- **Post** `id`, `content`, `mediaUrl`, `createdAt`, `author`
- **Interaction** `id`, `type` (`LIKE` or `COMMENT`), `commentText`, `createdAt`, `user`, `post`

Relationships: one user can author many posts; one user can perform many interactions; one post can have many interactions.

## Run locally (Windows)

1. Install **JDK 17** or the Java version configured in `build.gradle` and IntelliJ IDEA.
2. Open the project folder containing `build.gradle` in IntelliJ.
3. Reload the Gradle project to download dependencies.
4. Start `SocialMediaSystemApplication.java`, or run the following in the project root:

   ```powershell
   .\gradlew.bat bootRun
   ```

5. The API is available at **http://localhost:8080**.

### H2 database

- Console: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:socialmedia`
- Username: `sa`
- Password: *(blank)*

The app uses an **in-memory** H2 database, configured with Hibernate `create-drop`. The `DataSeeder` adds **10 users, 50 posts, and 50 interactions** at startup. Data resets when the app restarts.

## API endpoints

All endpoints have the `/api` prefix. Replace `{id}` with a valid numeric resource ID.

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/users` | List users |
| GET | `/api/users/{id}` | Get user by ID |
| POST | `/api/users` | Create user |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |
| GET | `/api/users/{id}/posts` | Get a user with their posts |
| GET | `/api/posts` | List posts |
| GET | `/api/posts/{id}` | Get post by ID |
| GET | `/api/posts?authorId=1` | Filter posts by author |
| POST | `/api/posts` | Create post |
| PUT | `/api/posts/{id}` | Update post |
| DELETE | `/api/posts/{id}` | Delete post |
| GET | `/api/posts/{id}/author` | Get a post with its author |
| GET | `/api/posts/{id}/interactions` | List interactions for a post |
| GET | `/api/interactions` | List interactions |
| GET | `/api/interactions/{id}` | Get interaction by ID |
| POST | `/api/interactions` | Create an interaction |
| PUT | `/api/interactions/{id}` | Update an interaction |
| DELETE | `/api/interactions/{id}` | Delete an interaction |

### Example requests

Create a user:

```http
POST /api/users
Content-Type: application/json

{
  "username": "newuser",
  "email": "newuser@example.com",
  "displayName": "New User"
}
```

Create a post (after a valid user exists):

```http
POST /api/posts
Content-Type: application/json

{
  "content": "This is my first post!",
  "mediaUrl": "https://example.com/image.jpg",
  "authorId": 1
}
```

Create a comment (after a valid user and post exist):

```http
POST /api/interactions
Content-Type: application/json

{
  "type": "COMMENT",
  "commentText": "Nice post!",
  "userId": 2,
  "postId": 1
}
```

### HTTP responses and validation

- `200 OK`: successful GET and PUT
- `201 Created`: successful POST, including a `Location` header
- `204 No Content`: successful DELETE
- `400 Bad Request`: invalid request, such as blank required fields or a comment without text
- `404 Not Found`: missing user, post, or interaction
- `409 Conflict`: database constraint violations where applicable

Usernames and emails are unique. The server manages generated IDs and creation timestamps.

## Swagger and Postman

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI document: http://localhost:8080/v3/api-docs
- Postman collection: `Social_Media_System_Milestone_2_Postman_Collection.json` in the repository root

Import the Postman collection and run it while the Spring Boot app is running locally. Some requests create and delete temporary test data.

## Automated tests

Run tests from the project root:

```powershell
.\gradlew.bat test
```

Custom test classes:

- `UserControllerTest` controller behavior and HTTP response codes
- `UserServiceTest` user business logic and missing-resource/duplicate checks
- `SocialMediaIntegrationTest` H2 seed counts and user-to-post relationship

Additional generated Spring Boot tests may also run.

## Deployment

The Social Media System backend is successfully deployed on Render using Docker.

**Public URL:** https://social-media-system.onrender.com

### Live API Endpoints

- Users: https://social-media-system.onrender.com/api/users
- Posts: https://social-media-system.onrender.com/api/posts
- Interactions: https://social-media-system.onrender.com/api/interactions
- User Posts: https://social-media-system.onrender.com/api/users/1/posts
- Post Author: https://social-media-system.onrender.com/api/posts/1/author

### Swagger Documentation

https://social-media-system.onrender.com/swagger-ui.html

### Deployment Configuration

- Hosting Platform: Render
- Deployment Method: Docker
- Java Version: 17
- Database: H2 (in-memory)
- Database Seeding: Automatic using CommandLineRunner
- Public H2 Console: Disabled

### Sample Database Records

The deployed application automatically initializes:
- 10 Users
- 50 Posts
- 50 Interactions

### Important Notes

The application uses Render's free hosting service, which may take additional time to respond after inactivity.

Because H2 is an in-memory database, records reset when the application restarts. The DataSeeder automatically restores the demonstration data.

The deployment has been verified, including the REST API endpoints and Swagger documentation.

## Project files

- `src/` application code and tests
- `build.gradle` and `settings.gradle` Gradle configuration
- `gradle/`, `gradlew`, `gradlew.bat` Gradle wrapper
- `Milestone1_Design_Report.pdf` Milestone 1 design documentation
- `Social_Media_System_Milestone_2_Postman_Collection.json` API testing collection

## Limitations

This is a course demonstration backend, **not a production-ready social network**. It does not implement user authentication or authorization, and API responses currently include email addresses per the Milestone 1 DTO design. Do not use real personal data in this demonstration API.
