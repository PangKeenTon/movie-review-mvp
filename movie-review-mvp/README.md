# Movie Review MVP

A modern web application for movie reviews, ratings, and recommendations built with Spring Boot and Thymeleaf.

## Features

- 🎬 Movie browsing and searching with TMDB integration
- ⭐ User reviews and ratings
- 📝 Watchlist management
- 👤 User profiles and authentication
- 👥 Social features (following reviewers)
- 🎯 Personalized recommendations
- 🔒 Secure authentication and authorization
- 📱 Responsive design for all devices

## Tech Stack

- **Backend**: Spring Boot 3.5.0
- **Frontend**: Thymeleaf, HTML5, CSS3
- **Database**: H2 (Development)
- **Security**: Spring Security
- **API Integration**: TMDB (The Movie Database)
- **Build Tool**: Gradle
- **Java Version**: 21

## Prerequisites

- JDK 21 or higher
- Gradle 8.x
- TMDB API Key

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/PangKeenTon/movie-review-mvp.git
   cd movie-review-mvp
   ```

2. Configure TMDB API:
   - Get your API key from [TMDB](https://www.themoviedb.org/settings/api)
   - Add it to `src/main/resources/application.properties`:
     ```properties
     tmdb.api.key=your_api_key_here
     ```

3. Build the project:
   ```bash
   ./gradlew build
   ```

4. Run the application:
   ```bash
   ./gradlew bootRun
   ```

5. Access the application at `http://localhost:8080`

## Project Structure

```
movie-review-mvp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/moviereviewmvp/
│   │   │       ├── config/         # Configuration classes
│   │   │       ├── controller/     # MVC Controllers
│   │   │       ├── dto/           # Data Transfer Objects
│   │   │       ├── entity/        # JPA Entities
│   │   │       ├── repository/    # Data Access Layer
│   │   │       ├── service/       # Business Logic
│   │   │       └── MovieReviewMvpApplication.java
│   │   └── resources/
│   │       ├── static/           # Static resources (CSS, JS, images)
│   │       ├── templates/        # Thymeleaf templates
│   │       ├── application.properties
│   │       └── data.sql          # Initial data
│   └── test/                    # Test classes
└── build.gradle                # Gradle build file
```

## Features in Detail

### Movie Management
- Browse movies from TMDB
- Search functionality
- Detailed movie information
- Cast and crew details
- Trailers and videos

### User Features
- User registration and authentication
- Profile management
- Watchlist creation and management
- Movie reviews and ratings
- Social interactions

### Admin Features
- User management
- Content moderation
- System configuration
- Analytics dashboard

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [The Movie Database (TMDB)](https://www.themoviedb.org/) for their API
- Spring Boot team for the amazing framework
- All contributors who have helped shape this project

## Contact

Your Name - [@PangKeenTon](https://github.com/PangKeenTon)

Project Link: [https://github.com/PangKeenTon/movie-review-mvp](https://github.com/PangKeenTon/movie-review-mvp) 