# **Automated Secondary Mortgage Acquisition and Management System**

### **Project Overview**

This project is a comprehensive **Loan Acquisition and Management System** designed to streamline the mortgage acquisition process, from loan application submission to underwriting, risk assessment, and final approval. The system ensures transparency, role-based access control, and compliance with industry standards, while providing essential automation and risk profiling for loan officers, managers, underwriters, and other stakeholders.

### **Table of Contents**
- [Project Overview](#project-overview)
- [Features](#features)
- [System Architecture](#system-architecture)
- [Tech Stack](#tech-stack)
- [Setup and Installation](#setup-and-installation)
- [Usage](#usage)
- [Endpoints (API Documentation)](#endpoints-api-documentation)
- [User Stories and Progress](#user-stories-and-progress)
- [Contributing](#contributing)
- [License](#license)

---

### **Features**
- **Secure User Authentication**: JWT-based stateless authentication system with role-based access control.
- **Loan Management**: Manage loan applications, track their status, and perform risk assessments.
- **Multi-Tier Approval**: Hierarchical loan approval structure for high-value loans.
- **Risk Profiling**: Evaluate borrower risk levels based on financial data like debt-to-income ratio and credit score.
- **Notifications**: Real-time notifications for loan application updates and approvals.
- **Reporting**: Generate reports on loan metrics, risk assessments, and approval status.

---

### **System Architecture**
The system follows a **monolithic architecture** with layered components:
- **Controller Layer**: Manages HTTP requests and routes them to the appropriate services.
- **Service Layer**: Contains business logic, communicating between the controllers and persistence layers.
- **Persistence Layer**: Uses **JPA repositories** to handle data operations with the **PostgreSQL** database.

---

### **Tech Stack**
- **Frontend**: Angular (Planned for future implementation).
- **Backend**: Spring Boot (Java) for handling business logic and APIs.
- **Database**: PostgreSQL for data storage.
- **Security**: JWT for authentication and authorization, bcrypt for password hashing.

---

### **Setup and Installation**

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/loan-acquisition.git
   cd loan-acquisition
   ```

2. **Install Dependencies**:
   Ensure you have **Java 11+** and **PostgreSQL** installed.
   ```bash
   ./mvnw install
   ```

3. **Database Setup**:
   Set up the PostgreSQL database:
   ```sql
   CREATE DATABASE loan_management;
   ```

   Run the schema provided in the `src/main/resources/schema.sql` to set up tables and test data.

4. **Environment Configuration**:
   Configure your `application.properties` file for database connection:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/loan_management
   spring.datasource.username=yourUsername
   spring.datasource.password=yourPassword
   ```

5. **Run the Application**:
   ```bash
   ./mvnw spring-boot:run
   ```

6. **Access the API**:
   The API will be available at `http://localhost:8080`.

---

### **Usage**

- **Authentication**:
   - Register users and login via `/api/auth/login` to receive a JWT token.
   - Use the JWT token for all subsequent API calls in the `Authorization` header.

- **Loan Management**:
   - Submit loan applications, perform risk assessments, and approve/reject loans via the various `/api/loan` endpoints.

- **Reports**:
   - Generate loan reports by accessing the `/api/reports` endpoints.

---

### **Endpoints (API Documentation)**

| HTTP Method | Endpoint                                | Description                                          |
|-------------|-----------------------------------------|------------------------------------------------------|
| POST        | `/api/auth/login`                       | User login and JWT generation                        |
| GET         | `/api/loans`                            | Retrieve all loan applications                       |
| POST        | `/api/loans`                            | Submit a new loan application                        |
| PUT         | `/api/loans/{id}/approve`               | Approve a loan application                           |
| POST        | `/api/risk/assess`                      | Perform risk assessment on a loan                    |
| GET         | `/api/reports/loan-summary`             | Generate a loan summary report                       |

For the full list of available endpoints and parameters, please refer to the API documentation in the `docs/` folder.

---

### **User Stories and Progress**

| **User Story ID** | **User Story Description**                                                   | **Backend Implementation Status** |
|-------------------|-------------------------------------------------------------------------------|-----------------------------------|
| **P1**            | Loan officers can input and track loan applications from submission to approval. | **Completed**                    |
| **P2**            | Upload borrower documents (credit score, income verification, etc.).            | **In Progress**                  |
| **P3**            | Underwriters perform risk assessments based on financial factors.               | **Completed**                    |
| **P4**            | Multi-tier approval for high-value loans based on risk factors.                 | **In Progress**                  |
| **P5**            | System administrators manage user roles and access.                            | **Completed**                    |

---

### **Contributing**

Contributions are welcome! Please follow these steps to contribute:
1. Fork the repository.
2. Create a feature branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a new pull request.

