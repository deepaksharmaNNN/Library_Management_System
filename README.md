# Library Management System

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)
- [Contact](#contact)

## Introduction

The Library Management System is a comprehensive application developed using Java and Spring Boot, designed to streamline library operations by managing books, users, and transactions. This project provides robust features to facilitate library management through a user-friendly interface.

## Features

- **Author Management:** CRUD operations for managing author information.
- **Book Management:** Comprehensive management of book records, including title, author, and availability.
- **User Management:** Handling user information and their interactions with the library system.
- **Transaction Management:** Managing the borrowing and returning of books, tracking user transactions.
- **Validation and Security:** Input validation with Jakarta Bean Validation and securing application endpoints with Spring Security.
- **Caching:** Utilizing Redis for caching frequently accessed data.
- **Testing:** Unit and integration tests to ensure reliability and correctness.
- **API Documentation:** Detailed documentation of API endpoints.

## Technologies Used

- **Java**
- **Spring Boot**
- **MySQL**
- **H2 (for testing)**
- **Redis**
- **Maven**
- **Jakarta Bean Validation**
- **Spring Security**
- **JUnit**
- **Spring Boot Starter Test**
- **Mockito**

## Usage

### API Endpoints

- **Author Management**
  - **Create Author:** `POST /api/authors`
  - **Get Author:** `GET /api/authors/{id}`
  - **Update Author:** `PUT /api/authors/{id}`
  - **Delete Author:** `DELETE /api/authors/{id}`

- **Book Management**
  - **Add Book:** `POST /api/books`
  - **Get Book:** `GET /api/books/{id}`
  - **Update Book:** `PUT /api/books/{id}`
  - **Delete Book:** `DELETE /api/books/{id}`

- **User Management**
  - **Register User:** `POST /api/users`
  - **Get User:** `GET /api/users/{id}`

- **Transaction Management**
  - **Borrow Book:** `POST /api/transactions/borrow`
  - **Return Book:** `POST /api/transactions/return`

## API Documentation

For detailed API documentation, you can use tools like Swagger or Postman. Ensure to provide sample requests and responses for better clarity.

## Contributing

Contributions are welcome! Please follow these steps:

1. **Fork the repository.**
2. **Create a new branch:**
   ```bash
   git checkout -b feature-branch
3. **Commit your changes:**
    ```bash
    git commit -m "Add new feature"
4. **Push to the branch:**
    ```bash
    git push origin feature-branch
5. **Create a Pull Request.**

## Contact

For any queries, feel free to reach out:

- **Name:** Deepak Sharma
- **Email:** dsharma2828@gmail.com
- **GitHub:** [deepaksharmaNNN](https://github.com/deepaksharmaNNN)
