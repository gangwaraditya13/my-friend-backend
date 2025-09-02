# My Friend Backend

This repository contains the backend service for the "My Friend" application. It is a robust Spring Boot application that provides RESTful APIs for user management, authentication, post creation, and image handling with Cloudinary.

## Features

*   **User Authentication:** Secure user registration and login endpoints using Spring Security.
*   **Profile Management:** Allows authenticated users to update their profile details, including password, username, and profile picture.
*   **Post Management:** Full CRUD (Create, Read, Update, Delete) functionality for user posts, which can include a title, content, and an associated image.
*   **Cloud Image Storage:** Integrates with Cloudinary to handle image uploads, storage, and deletion, keeping the application's media management efficient and scalable.
*   **Role-Based Access Control:** Implements role-based security (`USER`, `ADMIN`) to restrict access to sensitive administrative endpoints.
*   **Transactional Operations:** Ensures data integrity for complex operations involving multiple database writes.

## Technologies Used

*   **Framework:** Spring Boot 3
*   **Language:** Java 24
*   **Database:** MongoDB
*   **Build Tool:** Maven
*   **Security:** Spring Security
*   **Image Service:** Cloudinary

## Getting Started

### Prerequisites

*   JDK 24 or later
*   Maven
*   A running MongoDB instance
*   A Cloudinary account

### Configuration

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/gangwaraditya13/my-friend-backend.git
    cd my-friend-backend
    ```

2.  **Configure MongoDB:**
    Update the MongoDB connection URI in `src/main/resources/application.yml`:
    ```yaml
    spring:
      data:
        mongodb:
          uri: <YOUR_MONGODB_URI> # e.g., mongodb+srv://user:pass@cluster.mongodb.net/myfriend
          database: myfriend
    ```
    For production environments, the application can be configured to use the `MONGODB_URI` environment variable as specified in `src/main/resources/application-pord.properties`.

3.  **Configure Cloudinary:**
    Update your Cloudinary credentials in `src/main/java/com/adish/myfriend/config/ProjectConfig.java`:
    ```java
    @Configuration
    public class ProjectConfig {

        @Bean
        public Cloudinary getCloudinary(){
            Map conifg = new HashMap();
            conifg.put("cloud_name", "YOUR_CLOUD_NAME");
            conifg.put("api_key", "YOUR_API_KEY");
            conifg.put("api_secret", "YOUR_API_SECRET");
            conifg.put("secure", true);
            return new Cloudinary(conifg);
        }
    }
    ```

### Running the Application

You can run the application using the Maven wrapper included in the repository:

```sh
./mvnw spring-boot:run
```

The server will start on port 8080 by default.

## API Endpoints

The following are the primary API endpoints provided by the application.

### Public Endpoints (`/public`)

No authentication is required for these endpoints.

| Method | Endpoint         | Description              |
|--------|------------------|--------------------------|
| `POST` | `/public/login`  | Authenticates a user.    |
| `POST` | `/public/create` | Registers a new user.    |

### User Endpoints (`/user`)

Authentication is required for these endpoints. The user must be logged in.

| Method   | Endpoint                  | Description                                      |
|----------|---------------------------|--------------------------------------------------|
| `POST`   | `/user/image-upload`      | Uploads an image to Cloudinary and returns its URL. |
| `GET`    | `/user/user-info`         | Retrieves details of the authenticated user.     |
| `PUT`    | `/user/update-password`   | Updates the authenticated user's password.       |
| `PUT`    | `/user/update-photo`      | Updates the authenticated user's profile photo.  |
| `PUT`    | `/user/update-user`       | Updates the user's username or Gmail ID.         |
| `DELETE` | `/user/delete-user`       | Deletes the authenticated user's account.        |

### User Post Endpoints (`/user-post`)

Authentication is required for these endpoints.

| Method   | Endpoint                      | Description                                |
|----------|-------------------------------|--------------------------------------------|
| `POST`   | `/user-post`                  | Creates a new post for the authenticated user. |
| `GET`    | `/user-post/get-user-posts`   | Retrieves all posts for the authenticated user. |
| `GET`    | `/user-post/get-user-post/{id}` | Retrieves a specific post by its ID.       |
| `PUT`    | `/user-post/update/{id}`        | Updates an existing post.                  |
| `DELETE` | `/user-post/delete-post/{id}` | Deletes a post by its ID.                  |

### Admin Endpoints (`/admin`)

Requires `ADMIN` role for access.

| Method   | Endpoint             | Description                                |
|----------|----------------------|--------------------------------------------|
| `GET`    | `/admin/all-users`   | Retrieves a list of all registered users.  |
| `PUT`    | `/admin/m-admin`     | Assigns a role (e.g., `ADMIN`) to a user.  |
| `DELETE` | `/admin/remove-auth` | Removes a role from a user.                |


