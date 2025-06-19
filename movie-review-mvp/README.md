# Movie Review MVP

A modern web application for movie reviews, ratings, and recommendations built with Spring Boot and Thymeleaf.

---

## 🚀 Features

- 🎬 Movie browsing and searching with TMDB integration
- ⭐ User reviews and ratings
- 📝 Watchlist management
- 👤 User profiles and authentication
- 🔒 Secure authentication and authorization
- 📱 Responsive design for all devices
- 🛠️ Admin dashboard for user and review management

---

## 🛠️ Tech Stack

- **Backend:** Spring Boot 3.5.0, Java 21
- **Frontend:** Thymeleaf, HTML5, CSS3, Bootstrap
- **Database:** H2 (in-memory, for development)
- **Security:** Spring Security
- **API Integration:** TMDB (The Movie Database)
- **Build Tool:** Gradle

---

## ⚡ Quick Start

### 1. Clone the repository

```bash
git clone https://github.com/PangKeenTon/movie-review-mvp.git
cd movie-review-mvp
```

### 2. Configure TMDB API Key

- Register and get your API key from [TMDB](https://www.themoviedb.org/settings/api)
- Open `movie-review-mvp/src/main/resources/application.properties` and add:
  ```
  tmdb.api.key=YOUR_TMDB_API_KEY
  ```

### 3. Build and Run

```bash
./gradlew build
./gradlew bootRun
```

### 4. Access the Application

- Open your browser and go to: [http://localhost:8080](http://localhost:8080)

---

## 👤 Default Accounts (for demo)

| Role   | Username | Password   |
|--------|----------|------------|
| Admin  | admin    | admin123   |
| User   | user     | user123    |

*(Bạn có thể thay đổi hoặc tạo mới tài khoản qua giao diện đăng ký)*

---

## 📂 Project Structure

```
movie-review-mvp/
├── src/
│   ├── main/
│   │   ├── java/com/example/moviereviewmvp/
│   │   │   ├── config/         # Configuration classes
│   │   │   ├── controller/     # MVC Controllers
│   │   │   ├── dto/            # Data Transfer Objects
│   │   │   ├── entity/         # JPA Entities
│   │   │   ├── repository/     # Data Access Layer
│   │   │   ├── service/        # Business Logic
│   │   │   └── MovieReviewMvpApplication.java
│   │   └── resources/
│   │       ├── static/         # CSS, images
│   │       ├── templates/      # Thymeleaf templates
│   │       ├── application.properties
│   │       └── data.sql        # Initial data
│   └── test/                   # Test classes
└── build.gradle                # Gradle build file
```

---

## 📝 Development Notes

- **Database:** H2 is used for development. Data will reset on each restart unless configured otherwise.
- **API Key:** Required for TMDB features (movie search, details, posters).
- **Port:** Default is 8080. Change in `application.properties` if needed.
- **Static resources:** Place images in `src/main/resources/static/images/`.

---

## 🧪 Running Tests

```bash
./gradlew test
```

---

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- [The Movie Database (TMDB)](https://www.themoviedb.org/) for their API
- Spring Boot team for the amazing framework
- All contributors who have helped shape this project

---

## 📬 Contact

**Author:** Your Name  
**GitHub:** [@PangKeenTon](https://github.com/PangKeenTon)  
**Project Link:** [https://github.com/PangKeenTon/movie-review-mvp](https://github.com/PangKeenTon/movie-review-mvp) 