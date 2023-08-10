
# Introduction
-----
The auth service is responsible for user authentication and permission management. Users can log in with a GitHub account or with a common ID and password. Successful login will result in a JWT token issued to help secure session management.

### Scope and Purpose

The purpose of aauth is to provide secure access by authenticating and authorizing users. GitHub login provides convenience, and regular login supports traditional methods to meet a wide range of user needs. Jwt authentication is required except for the functions required for login and membership registration.

### Components and Architecture
If you want Architecture in Details, then use "tree" command in terminal.

- **Spring Boot**: The framework underlying the backend service.
- **Spring Security**: A Spring Security module that handles user authentication and authorization.
- **GitHub OAuth**: GitHub OAuth authentication service for user login.
- **JWTUtil**: Utility responsible for generating and validating JWT.
- **Login and Validation Controller**: Controller for GitHub login and general login, which handles authentication and JWT issuance.

### Tech Skills

- **Spring Boot**: The framework underlying the backend service.
- **Spring Security**: A Spring Security module that handles user authentication and authorization.
- **GitHub OAuth**: GitHub OAuth authentication service for user login.
- **JWTUtil**: Utility responsible for generating and validating JWT.

Functions above are implemented by using these libraries.
```
implementation - Implementation only dependencies for source set 'main'. (n)
+--- org.springframework.boot:spring-boot-starter-data-jpa (n)
+--- org.springframework.boot:spring-boot-starter-oauth2-client (n)
+--- org.springframework.boot:spring-boot-starter-security (n)
+--- org.springframework.boot:spring-boot-starter-web (n)
+--- javax.xml.bind:jaxb-api:2.3.0 (n)
+--- org.springframework.cloud:spring-cloud-starter-openfeign:4.0.2 (n)
+--- com.fasterxml.jackson.core:jackson-databind:2.13.0 (n)
+--- org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2 (n)
+--- org.springframework.boot:spring-boot-starter-validation (n)
\--- io.jsonwebtoken:jjwt:0.9.1 (n)
```

# Details
----
### Service Flow
![image](https://github.com/KEA-ACCELER/kafka-druid-superset/assets/80394866/4aa97204-301e-443b-86c6-8428b9b36ee6)

1. The user clicks the GitHub login button to log in to the GitHub account, or goes to the general login page and enters the ID and password to log in.
2. User authentication is performed through GitHub OAuth or Spring Security, and user information is obtained.
3. Grants permissions and creates JWTs based on login information.
4. The issued JWT manages your sessions and enhances security.
5. Clients can use the issued JWT to access protected endpoints.


### APIs
![Screenshot from 2023-08-09 15-33-43](https://github.com/KEA-ACCELER/kafka-druid-superset/assets/80394866/a4bb515e-9d5d-4176-b83f-9901ba30200e)

You can check details by using Swagger
```
http://localhost:8087/auth/swagger
```

# Conclusion
----
### **If you try to improve quality**

- More security: You can secure user data and systems by introducing a more secure mechanism in the creation and validation of authentication procedures and JWT tokens.
- Improved user experience: Simplify login and authentication procedures and provide a user-friendly interface to increase user convenience and satisfaction.
- Monitoring and logging: Enhance monitoring and logging of authentication-related activities and security events to enhance the ability to monitor and respond to system health in real time.

### **If you try to improve your capabilities**

- Multiple Authentication: In addition to GitHub login and regular login, it also supports a variety of authentication methods to provide users with more choices.
- Enhanced password management: Enhance password security for users and introduce password management policies to encourage secure password use within the system.
- Add Social Features: Link to GitHub login to add social features between users to further promote communication and collaboration.

### **Summary**

**`auth'** service is an important part of user authentication and security. GitHub login and regular login enable users to use the service securely, and will further enhance the user experience through future security and functional enhancements and extensions.