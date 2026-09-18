# Task Management & Collaboration API

A secure RESTful Task Management and Collaboration API built with Java and Spring Boot.

The application provides user authentication, JWT-based authorization, role-based access control, task management, task assignment, search, filtering, pagination, and sorting.

---

## 🚀 Live Application

**Deployed API:**

https://taskmanagement-v6wu.onrender.com

**Swagger UI:**

https://taskmanagement-v6wu.onrender.com/swagger-ui/index.html

---

## 📌 Project Overview

The **Task Management & Collaboration API** is a secure backend application built with **Java and Spring Boot** that provides REST APIs for user authentication and task management.

The application implements:

* User registration and login
* JWT-based authentication
* Role-based authorization (`USER`, `ADMIN`)
* Task CRUD operations
* Task assignment and ownership management
* Task status and priority management
* Deadline management
* Search and filtering
* Pagination and sorting
* Input validation
* Global exception handling
* Secure password hashing using BCrypt
* PostgreSQL database integration with Neon
* Swagger/OpenAPI API documentation
* Docker containerization
* Cloud deployment using Render

The project demonstrates a layered backend architecture using **REST Controllers, Service Layer, Repository Layer, Spring Data JPA, Spring Security, and PostgreSQL**.


---

## ✨ Features

### 🔐 Authentication & Authorization

- User registration
- User login
- Password encryption using BCrypt
- JWT token generation
- JWT-based authentication
- Protected REST APIs
- Role-based access control
- `USER` and `ADMIN` roles

### 📝 Task Management

Users can:

- Create tasks
- View tasks
- View task by ID
- Update tasks
- Delete tasks
- Assign tasks
- Change task status
- Set task priority
- Set task deadline

### 🔎 Search, Filtering & Pagination

The application supports:

- Search tasks
- Filter by status
- Filter by priority
- Filter by assigned user
- Pagination
- Sorting
- Sort-field validation
- Invalid sorting exception handling

### 👨‍💼 Admin Features

Administrators can manage users and tasks according to their assigned role and configured permissions.

### 🛡️ Validation & Exception Handling

The application handles:

- Invalid request data
- Missing required fields
- Invalid email addresses
- Password validation
- Duplicate email registration
- Task not found
- User not found
- Unauthorized access
- Access denied
- Invalid sorting fields
- Global exception responses

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 25 | Programming Language |
| Spring Boot 4.1 | Backend Framework |
| Spring Web | REST APIs |
| Spring Data JPA | Database Access |
| Spring Security | Authentication & Authorization |
| JWT | Token-based Authentication |
| BCrypt | Password Hashing |
| PostgreSQL | Relational Database |
| Neon PostgreSQL | Cloud Database |
| Hibernate | ORM |
| Swagger / OpenAPI | API Documentation |
| Maven | Dependency Management |
| Lombok | Boilerplate Code Reduction |
| Docker | Containerization |
| Render | Cloud Deployment |
| Git & GitHub | Version Control |

---

## 🏗️ Architecture

The application follows a layered architecture:

```text
Client / Swagger / Postman
          |
          v
    REST Controller
          |
          v
      Service Layer
          |
          v
    Repository Layer
          |
          v
    PostgreSQL Database
          |
          v
      Neon PostgreSQL

### 🔐 Security Flow

```text
User
 |
 | Register
 v
BCrypt Password Hash
 |
 v
PostgreSQL

User
 |
 | Login
 v
Password Verification
 |
 v
JWT Token
 |
 v
Protected API Request
 |
 v
JWT Authentication Filter
 |
 v
Spring Security
 |
 v
Controller
```

---

## 📂 Project Structure

```text
taskmanagement/
│
├── Dockerfile
├── .dockerignore
├── .gitignore
├── README.md
├── pom.xml
├── mvnw
├── mvnw.cmd
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/taskmanagement/
│   │   │
│   │   │       ├── config/
│   │   │       │   ├── OpenAPIConfig.java
│   │   │       │   └── SecurityConfig.java
│   │   │       │
│   │   │       ├── controller/
│   │   │       │   ├── AdminController.java
│   │   │       │   ├── AuthController.java
│   │   │       │   └── TaskController.java
│   │   │       │
│   │   │       ├── dto/
│   │   │       │   ├── LoginRequest.java
│   │   │       │   ├── LoginResponse.java
│   │   │       │   ├── PageResponse.java
│   │   │       │   ├── RegisterRequest.java
│   │   │       │   ├── TaskRequest.java
│   │   │       │   ├── TaskResponse.java
│   │   │       │   └── UserResponse.java
│   │   │       │
│   │   │       ├── entity/
│   │   │       │   ├── Role.java
│   │   │       │   ├── Task.java
│   │   │       │   ├── TaskPriority.java
│   │   │       │   ├── TaskStatus.java
│   │   │       │   └── User.java
│   │   │       │
│   │   │       ├── exception/
│   │   │       │   ├── GlobalExceptionHandler.java
│   │   │       │   ├── TaskNotFoundException.java
│   │   │       │   └── UserNotFoundException.java
│   │   │       │
│   │   │       ├── repository/
│   │   │       │   ├── TaskRepository.java
│   │   │       │   └── UserRepository.java
│   │   │       │
│   │   │       ├── security/
│   │   │       │   ├── JwtAuthenticationFilter.java
│   │   │       │   └── JwtService.java
│   │   │       │
│   │   │       └── service/
│   │   │           ├── AdminService.java
│   │   │           ├── AdminServiceImpl.java
│   │   │           ├── AuthService.java
│   │   │           ├── AuthServiceImpl.java
│   │   │           ├── TaskService.java
│   │   │           └── TaskServiceImpl.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
```

---

## 🔑 Authentication APIs

### Register User

```http
POST /api/auth/register
```

Example request:

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "Password@123"
}
```

### Login User

```http
POST /api/auth/login
```

Example request:

```json
{
  "email": "john@example.com",
  "password": "Password@123"
}
```

The login endpoint returns a JWT token.

For protected endpoints, provide the token using:

```text
Authorization: Bearer <JWT_TOKEN>
```

---

## 📋 Task APIs

### Create Task

```http
POST /api/tasks
```

### Get All Tasks

```http
GET /api/tasks
```

### Get Task By ID

```http
GET /api/tasks/{id}
```

### Update Task

```http
PUT /api/tasks/{id}
```

### Delete Task

```http
DELETE /api/tasks/{id}
```

### Update Task Status

```http
PUT /api/tasks/{id}/status
```

### Search and Filter Tasks

```http
GET /api/tasks/search
```

Supported parameters:

```text
status
priority
assignedUserId
search
page
size
sortBy
sortDir
```

Example:

```text
/api/tasks/search?status=TODO&priority=HIGH&page=0&size=5&sortBy=title&sortDir=asc
```

---

## 👥 Roles

### USER

A normal user can:

- Register
- Login
- Create tasks
- View accessible tasks
- Update accessible tasks
- Delete accessible tasks
- Update task status
- Assign tasks according to the application's access rules

### ADMIN

An administrator can manage users and tasks according to the configured administrator permissions.

---

## 🌍 Environment Variables

Sensitive configuration is not stored directly in `application.properties`.

Required environment variables:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_SECRET
```

Example:

```text
DB_URL=jdbc:postgresql://<neon-host>/<database>?sslmode=require
DB_USERNAME=<database-username>
DB_PASSWORD=<database-password>
JWT_SECRET=<strong-random-secret>
```

> Never commit real passwords, database credentials, or JWT secrets to GitHub.

---

## 💻 Local Setup

### 1. Clone the Repository

```bash
git clone https://github.com/azadvipin850/taskmanagement.git
```

### 2. Open the Project

Open the project in IntelliJ IDEA or VS Code.

### 3. Configure Environment Variables

Configure:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_SECRET
```

### 4. Build the Project

Windows PowerShell:

```powershell
.\mvnw clean package -DskipTests
```

### 5. Run the Application

```powershell
.\mvnw spring-boot:run
```

The application normally runs on:

```text
http://localhost:8080
```

Swagger:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## 🐳 Docker

The project includes a `Dockerfile` for containerized deployment.

### Build the Docker Image

```bash
docker build -t taskmanagement .
```

### Run the Docker Container

```bash
docker run -p 8080:8080 taskmanagement
```

Environment variables should be supplied securely when running the container.

---

## ☁️ Deployment

The application is deployed using:

- Render
- Neon PostgreSQL

### Production Application

https://taskmanagement-v6wu.onrender.com

### Production Swagger

https://taskmanagement-v6wu.onrender.com/swagger-ui/index.html

Render provides the application hosting environment, while Neon provides the PostgreSQL database.

The application uses:

```properties
server.port=${PORT:8080}
```

This allows Spring Boot to use Render's assigned port while retaining port `8080` for local development.

---

## 📖 API Documentation

Swagger/OpenAPI documentation is available at:

https://taskmanagement-v6wu.onrender.com/swagger-ui/index.html

Swagger can be used to:

- Register users
- Login
- Authorize using JWT
- Create tasks
- Update tasks
- Delete tasks
- Assign tasks
- Update task status
- Search and filter tasks
- Test pagination
- Test sorting
- Test protected endpoints

---

## ⚠️ Error Handling

The application includes centralized exception handling using:

```java
@RestControllerAdvice
```

Validation errors are returned with appropriate HTTP status codes and meaningful error messages.

Examples include:

- Invalid request data
- Validation errors
- User not found
- Task not found
- Unauthorized access
- Access denied
- Invalid sorting field

---

## 🧪 Production Testing

The deployed application has been tested for:

- User registration
- User login
- JWT authentication
- Protected endpoints
- Task creation
- Task retrieval
- Task update
- Task deletion
- Task status update
- Task assignment
- Assigned-user access
- Search
- Filtering
- Pagination
- Sorting
- Invalid sorting validation

---

## 🛡️ GitHub Security

Sensitive files and configuration are excluded from Git.

The project uses:

```text
.env
```

for local secrets and includes `.env` in `.gitignore`.

The Docker configuration also excludes sensitive files using:

```text
.dockerignore
```

Never commit:

- Database passwords
- JWT secrets
- API keys
- `.env` files
- Other credentials

---

## 🎯 Project Highlights

This project demonstrates practical experience with:

```text
Java
Spring Boot
Spring Security
JWT
REST APIs
Spring Data JPA
Hibernate
PostgreSQL
Neon PostgreSQL
Docker
Render
Git
GitHub
Swagger/OpenAPI
BCrypt
DTOs
Validation
Exception Handling
Pagination
Filtering
Sorting
Role-Based Authorization
```

---

## 👨‍💻 Author

**Vipin Azad**

B.Tech — Computer Science & Engineering

Java | Spring Boot | Backend Development

GitHub:

https://github.com/azadvipin850

---

## 📜 License

This project is created for learning, development, and portfolio purposes.