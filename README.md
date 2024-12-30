# Rural Women Entrepreneurs Web Application

## Overview
This web application addresses the unique challenges faced by rural women entrepreneurs by providing an accessible platform that bridges technological and cultural gaps. The project focuses on delivering e-commerce capabilities while ensuring accessibility for users with varying levels of technical literacy and device capabilities.

### Core Objectives
- Enable rural women entrepreneurs to access mentorship and resources.
- Provide NGOs with tools to manage mentors and track entrepreneurs’ progress.
- Facilitate mentor-mentee relationships and resource sharing.
- Create a scalable and accessible platform for all stakeholders.

## System Architecture
The application follows a three-tier architecture implementing the MVC (Model-View-Controller) pattern.

### Three-Tier Architecture
#### Presentation Layer
- **Frontend**: React (Vite)
- **Styling**: Tailwind CSS
- **Type Safety**: TypeScript
- **Internationalization**: i18next (Urdu/English support)

#### Application Layer
- **Backend Framework**: Spring Boot
- **Authentication**: JWT-based
- **Business Logic**: RESTful API implementation

#### Data Layer
- **Database**: MySQL (JPA entities)
- **Cloud Integration**: Google Cloud Storage

### Component Interaction
Frontend components interact with backend services for authentication, resource management, and data retrieval, while storage services handle static files and database operations.

## Project Structure
The repository is organized as follows:

```
SheVolve/
├── frontend/              # React (Vite) frontend code
│   ├── src/
│   │   ├── components/    # Reusable UI components
│   │   ├── pages/         # Application pages
│   │   ├── assets/        # Static assets (images, icons, etc.)
│   │   ├── i18n/          # Localization files
│   └── package.json       # Frontend dependencies
├── backend/               # Spring Boot backend code
│   ├── src/main/java/     # Java source files
│   ├── src/main/resources/# Configuration files
│   └── pom.xml            # Backend dependencies
├── database/              # Database schema and migration scripts
├── docs/                  # Documentation files
├── CONTRIBUTING.md        # Contribution guidelines
└── README.md              # Project overview and setup
```

## Database Design

### Entity Relationship Diagram
The database is structured to manage users, mentors, entrepreneurs, resources, and mentorship programs efficiently. Key entities include:
- **UserModel**: Manages user details and roles.
- **Product**: Stores product information uploaded by entrepreneurs.
- **Resource**: Holds resources shared in mentorship programs.

### Product Management Flow
Entrepreneurs can upload and manage product details, ensuring unique product names and verified metadata storage.

## User Flows

### Authentication Flow
Users authenticate via JWT, with password hashing and role-based access control.

### Resource Management Flow
Mentors upload resources, stored securely in Google Cloud, and metadata is saved in the database.

## Technical Stack

### Frontend Technologies
- **React (Vite)**: Optimized performance and component-based architecture.
- **Tailwind CSS**: Utility-first framework for responsive designs.
- **TypeScript**: Static type checking for a robust development experience.
- **i18next**: Multilingual support with automatic translation capabilities.

### Backend Technologies
- **Spring Boot**: REST API and business logic implementation.
- **Spring Security**: JWT authentication and role-based access.
- **Spring Data JPA**: Database interaction.
- **Google Cloud Storage**: Secure and scalable file storage.

## Security Implementation

### Authentication
- JWT-based token authentication.
- Password hashing.
- Role-based access control.

### Data Security
- Encrypted data transmission.
- Input validation and sanitization.
- SQL injection prevention.

## Deployment Architecture

### Local Deployment
- **Server Requirements**: Java Runtime Environment 11+, Node.js 16+, MySQL 8.0+.
- **Setup**: Initialize database, configure application server, and manage static resources via Google Cloud Storage.

### Resource Management
- **Cloud Integration**: Google Cloud Buckets for reliable and scalable resource storage.
- **Access Control**: Ensures secure handling of uploaded/downloaded files.

## How to Setup the Project

### Prerequisites
Ensure the following software and tools are installed:
1. **Java Runtime Environment** (version 11 or higher).
2. **Node.js** (version 16 or higher).
3. **MySQL Server** (version 8.0 or higher).
4. **Git** for version control.

### Steps to Set Up the Project

#### Clone the Repository
```bash
git clone https://github.com/Ali-Haris-Chishti/SheVolve.git
cd SheVolve
```

#### Set Up Backend
1. Navigate to the backend directory:
   ```bash
   cd SheVolve-Backend
   ```
2. Configure the database in the `application.properties` file located under `src/main/resources`.
3. Install Maven dependencies and build the project:
   ```bash
   mvn clean install
   ```
4. Run the backend server:
   ```bash
   mvn spring-boot:run
   ```

#### Set Up Frontend
1. Navigate to the frontend directory:
   ```bash
   cd ../SheVolve-Frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```

#### Verify Setup
1. Open a browser and navigate to the frontend development server URL displayed in the terminal.
2. Ensure backend endpoints are accessible via the specified base URL.

## Project Setup Requirements

To set up the project locally:
1. Install **Java Runtime Environment** (version 11 or higher).
2. Install **Node.js** (version 16 or higher).
3. Install **MySQL Server** (version 8.0 or higher) and configure a database for the project.
4. Clone the repository from the project’s version control system.
5. Configure Google Cloud Storage credentials for resource handling.
6. Run the backend server using Spring Boot and the frontend server using Vite.
7. Verify database initialization and resource access control.

## Planned Features

### Upcoming Enhancements
- Mobile application development.
- Enhanced analytics dashboard.
- Offline capability implementation.

### Scalability Plans
- Database optimization and caching.
- Load balancing and performance monitoring.
- CI/CD pipeline implementation.

---

## Contribute to the Project

We welcome contributions to improve this project! Please follow these steps to get started:
1. Fork the repository on GitHub.
2. Create a new branch for your feature or bug fix.
3. Submit a pull request with a clear description of your changes.

For detailed guidelines, refer to `CONTRIBUTE.md` in the repository.

