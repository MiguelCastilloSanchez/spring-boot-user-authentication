# Spring Boot User Authentication Service

This is a simple Spring Boot application that implements a user authentication service using Spring Security and JWT (JSON Web Tokens). It also incorporates a blacklist to manage all revoked tokens. This code used as a base the repository: https://github.com/SpackiGabriel/spring-boot-user-authentication/blob/main/readme.MD?plain=1

### Running the service prerequisites

- Java Development Kit (JDK) 21
- Maven

### Database requirements

- MongoDB: Used for managing user data and authentication. Ensure that MongoDB is running and accessible.

- Redis: Utilized for managing revoked tokens. Make sure Redis is up and running to handle token blacklisting.

### Installing

1. Clone the repository:

```bash
git clone https://github.com/MiguelCastilloSanchez/spring-boot-user-authentication
```

2. Navigate into the project directory:

```bash
cd spring-boot-user-authentication
```

3. Build the project using Maven:

```bash
mvn clean install
```

### Running the Application

You can run the application using Maven:

```bash
mvn spring-boot:run
```

The application will start at `http://localhost:8080`.

## Usage

Once the application is running, you can interact with it using any HTTP client such as cURL or Postman. Below are some example requests:

### Register a User

Send a POST request to `/auth/register` with a JSON body containing the user details:

```json
{
    "name": "Example User",
    "email": "exampleuser@example.com",
    "password": "examplepassword",
    "role": "USER"
}
```

If you want to use your own user example, make sure to accomplish the requirements defined in RegisterDTO. 

### Authenticate User

Send a POST request to `/auth/login` with the user credentials:

```json
{
    "email": "exampleuser@example.com",
    "password": "examplepassword"
}
```

### Accesing a protected endpoint

Send a GET request to `/auth/test-api` with the token. You should get a body with the text "Hello World"

To access a protected resource, include the JWT token in the `Authorization` header of your requests:

```makefile
Authorization: Bearer <JWT token>
```

### Logging out 

Send a POST request to `/auth/logout` with the token. Make sure it's a valid one.

