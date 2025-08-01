# Managify - Project Management System

A comprehensive end-to-end project management system built with Spring Boot and PostgreSQL, featuring real-time communication, issue tracking, and user authentication with JWT tokens.

## 🚀 Features

- **User Authentication & Authorization**: JWT-based secure authentication system
- **Project Management**: Create, update, delete, and search projects
- **Issue Tracking**: Complete issue lifecycle management with status updates and assignments
- **Real-time Communication**: Project-based chat system with messaging
- **Comment System**: Add comments to issues for better collaboration
- **Caching**: Redis-based caching for improved performance
- **Search Functionality**: Advanced project search with keyword filtering
- **Payment Integration**: Subscription management system
- **Email Services**: Automated email notifications

## 🛠️ Tech Stack

### Backend
- **Spring Boot** - Main framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database operations
- **PostgreSQL** - Primary database
- **Redis** - Caching layer
- **JWT** - Token-based authentication
- **Maven** - Dependency management

### Testing
- **JUnit 5** - Unit testing framework
- **Mockito** - Mocking framework
- **Spring Boot Test** - Integration testing

## 📋 Prerequisites

- Java 17 or higher
- PostgreSQL 12+
- Redis Server
- Maven 3.6+

## ⚙️ Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/managify.git
cd managify
```

### 2. Database Configuration
Create a PostgreSQL database named `managify`:
```sql
CREATE DATABASE managify;
```

### 3. Application Configuration
Update `application.properties` with your database credentials:
```properties
spring.application.name=managify
spring.datasource.url=jdbc:postgresql://localhost:5432/managify
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

### 4. Install Dependencies
```bash
mvn clean install
```

### 5. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📊 Database Schema

### Core Entities

- **Users**: User management with profile information
- **Projects**: Project details, categories, and metadata
- **Issues**: Issue tracking with status, priority, and assignments
- **Comments**: Issue commenting system
- **Messages**: Real-time messaging for project communication
- **Chat**: Project-based chat rooms
- **Subscriptions**: User subscription and payment management

### Entity Relationships

- Users ↔ Projects (One-to-Many)
- Projects ↔ Issues (One-to-Many)
- Issues ↔ Comments (One-to-Many)
- Users ↔ Comments (One-to-Many)
- Projects ↔ Chat (One-to-One)
- Chat ↔ Messages (One-to-Many)

## 🔗 API Endpoints

### Authentication
| Endpoint            | Method | Description |
|---------------------|--------|-------------|
| `/auth/create-user` | POST | Register a new user |
| `/auth/login`       | POST | User login |

### User Management
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/users/profile` | GET | Get user profile |

### Project Management
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/projects` | POST | Create new project |
| `/api/projects` | GET | Get all projects |
| `/api/projects/{id}` | GET | Get project by ID |
| `/api/projects/{id}` | DELETE | Delete project |
| `/api/projects/search?keyword={keyword}` | GET | Search projects |

### Issue Management
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/issues` | POST | Create new issue |
| `/api/issues/{id}` | GET | Get issue by ID |
| `/api/issues/project/{projectId}` | GET | Get issues by project |
| `/api/issues/{id}/status/{status}` | PUT | Update issue status |
| `/api/issues/{id}/assignee/{userId}` | PUT | Assign issue to user |
| `/api/issues/{id}` | DELETE | Delete issue |

### Communication
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/projects/{id}/chat` | GET | Get project chat |
| `/api/messages/chat/{projectId}` | GET | Get chat messages |
| `/api/messages/send` | POST | Send message |
| `/api/comments/{id}` | GET | Get comment by ID |

### 🔗 Postman Collection
Access the complete API documentation: [Managify Postman Collection](https://www.postman.com/spacecraft-astronaut-67997412/managify/overview)

## 🧪 Testing

The project includes comprehensive test coverage:

### Unit Tests
- **User Service Tests**: Authentication and user management
- **Project Service Tests**: CRUD operations and validations
- **Issue Service Tests**: Issue lifecycle management
- **Comment Service Tests**: Comment creation and deletion

### Integration Tests
- Database integration tests
- Repository layer tests
- Service layer integration tests

### Run Tests
```bash
mvn test
```

## 🚀 Performance Features

### Caching Strategy
- **Project Listing**: Cached by user ID with category/tag filters
- **Project Details**: Cached by project ID
- **Search Results**: Cached search queries for improved response times
- **Cache Invalidation**: Automatic cache updates on data modifications

### Cache Operations
- `GET /api/projects` - Cached project listings
- `GET /api/projects/{id}` - Cached project details
- `PATCH /api/projects/{id}` - Cache update on modification
- `DELETE /api/projects/{id}` - Cache eviction on deletion

## 🔒 Security Features

- JWT-based authentication with 256-bit secure keys
- Password encryption using BCrypt
- Role-based access control
- Secure API endpoints with authentication middleware
- Input validation and sanitization

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/managify/
│   │   ├── configs/          # Configuration classes
│   │   ├── controllers/      # REST controllers
│   │   ├── dtos/            # Data Transfer Objects
│   │   ├── models/         # JPA entities
│   │   ├── repositories/     # Data repositories
│   │   ├── services/        # Business logic
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/managify/   # Test classes
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 Development Timeline

The project was developed over several iterations with continuous improvements:
- **Initial Setup** (Oct 20): Project initialization and database configuration
- **Authentication** (Oct 21-22): JWT implementation and user management
- **Core Features** (Oct 23-30): Entity relationships and business logic
- **Performance** (Nov 3): Caching implementation
- **Testing** (Nov 4-5): Comprehensive test coverage
- **Update** (Aug 1-3): Renamed all folders, enhanced exception handling 

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- Doğuhan İlter - Initial work

## 🆘 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the [API documentation](https://www.postman.com/spacecraft-astronaut-67997412/managify/overview)

