# My Project

This project is a web application developed using Kotlin, Java, Spring Boot, and Gradle.

## Technologies Used

- **Kotlin**: A statically typed, cross-platform, general-purpose programming language with type inference.
- **Java**: A class-based, object-oriented programming language that is designed to have as few implementation
  dependencies as possible.
- **Spring Boot**: An open-source Java-based framework used to create a stand-alone, production-grade Spring based
  Applications with minimal effort.
- **Gradle**: An open-source build automation tool that is designed to be flexible enough to build almost any type of
  software.

## Features

- User authentication and authorization using JWT.
- Stateless session management.
- CORS configuration for development purposes.

## Getting Started

To get a local copy up and running, follow these simple steps:

1. Clone the repository

```bash
git clone https://github.com/wxxsfxyzm/my-project.git
```

2. Navigate to the project directory

```bash
cd my-project
```

3. Build the project using Gradle

```bash
./gradlew build
```

4. Run the application

```bash
./gradlew bootRun
```

## API Endpoints

- POST `/api/login`: Authenticate a user.
- POST `/api/register`: Register a new user.
- `/api/**`: All other API endpoints require authentication.

## Documentation

For more details about the project and how to use it, please refer to the `HELP.md` file.

## Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any
contributions you make are greatly appreciated.
