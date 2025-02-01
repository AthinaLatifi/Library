# Library
The project is a Spring Boot and MySQL-based application designed for managing books in a library. It supports features such as adding, editing, and deleting books, user management, loan tracking, and reporting. The system includes RBAC for security, Spring Security for authentication, and data export capabilities in PDF/Excel.

## Technologies Used
- Java 17
- SpringBoot
  - SpringBoot DevTools
  - SpringBoot Starter Thymeleaf
  - SpringBoot Starter Security
  - SpringBoot Starter Test
  - SpringBoot Data JPA
- MySQL (via MySQL Connector/J)
- Lombok
- iText PDF
- Spring Session
- WAMP Server (for database management)

## Installation

1. Clone the repository at your console or PowerShell:
   git clone https://github.com/AthinaLatifi/Library.git
2. Navigate to the project directory:
   cd your-repo-name
3. Open the project in IntelliJ IDEA.
4. Make a new project.
5. Choose SpringBoot for the framework.
6. Ensure you have Java 17 installed.
7. Choose the type maven.
8. For dependencies add the above technologies all except from java 17 and wamp server. 

##Database Setup
Import the provided SQL file (mybook(1).sql) into your WAMP Server's MySQL database.
Ensure that the database connection settings in your application properties match your WAMP Server configuration.

##USAGE
Start the WAMP Server to ensure the MySQL database is running.
Configure your application properties (e.g., application.properties or application.yml) to connect to your MySQL database.
Access the application in your web browser at http://localhost:8080.
