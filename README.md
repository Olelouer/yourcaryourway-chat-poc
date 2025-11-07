# Your Car Your Way - POC Chat Agent/Users

This POC is developed to test the agent/user chat functionality

## 📋 Prerequisites

- **Java** 21+
- **Node.js** 18+
- **MySQL** 8.0+
- **Maven** 3.9+
- **Angular CLI** 19

## 🗄️ Database Setup

### POC Database

The POC schema is available in:
`database/poc/schema.sql`

Test data is available in:
`database/poc/seed-data.sql`

### Complete Database

For reference, the complete schema is available in:
`database/schema.sql`

## 🚀 Backend Setup (Spring Boot)

1.  Go to the backend directory:
    ```bash
    cd backend
    ```
2.  Configure the database connection:
    ```bash
    # Edit src/main/resources/application.properties
    spring.datasource.url=jdbc:mysql://localhost:3306/yourcaryourway_chat_poc
    spring.datasource.username=your_user
    spring.datasource.password=your_password
    ```
3.  Install dependencies and build:
    ```bash
    mvn clean install
    ```
4.  Run the application:
    ```bash
    mvn spring-boot:run
    ```
    The backend will be accessible at `http://localhost:8080`

## 💻 Frontend Setup (Angular)

1.  Go to the frontend directory:
    ```bash
    cd frontend
    ```
2.  Install dependencies:
    ```bash
    npm install
    ```
3.  Configure the backend URL (if necessary):
    ```bash
    # Edit src/app/services/chat.service.ts
    private apiUrl = 'http://localhost:8080/api/chat';
    ```
4.  Run the application:
    ```bash
    npm start
    ```
    The frontend will be accessible at `http://localhost:4200`

## 📁 Project Structure

```
.
├── backend/                # Spring Boot Application
│   ├── src/
│   ├── pom.xml
│   └── ...
├── frontend/               # Angular 19 Application
│   ├── src/
│   ├── angular.json
│   ├── package.json
│   └── ...
└── database/              # SQL Scripts
    ├── schema.sql         # Complete production schema
    └── poc/
        ├── schema.sql     # POC Schema
        └── seed-data.sql  # Test data
```