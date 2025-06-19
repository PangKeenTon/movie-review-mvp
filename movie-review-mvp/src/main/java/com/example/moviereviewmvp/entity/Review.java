// src/main/java/com/example/moviereviewmvp/entity/Review.java
package com.example.moviereviewmvp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating_value", nullable = false)
    private Integer ratingValue; // Điểm đánh giá, ví dụ từ 1 đến 5

    @Lob // Cho phép lưu trữ văn bản dài
    @Column(name = "comment_text", columnDefinition = "TEXT")
    private String commentText;

    @Column(name = "review_date", nullable = false)
    private LocalDateTime reviewDate;

    // Mối quan hệ Many-to-One với User
    // Một người dùng có thể có nhiều đánh giá, nhưng một đánh giá chỉ thuộc về một người dùng.
    @ManyToOne(fetch = FetchType.LAZY) // LAZY: chỉ tải User khi thực sự cần
    @JoinColumn(name = "user_id", nullable = false) // Khóa ngoại trong bảng reviews
    private User user;

    // Mối quan hệ Many-to-One với Movie
    // Một bộ phim có thể có nhiều đánh giá, nhưng một đánh giá chỉ cho một bộ phim.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id", nullable = false) // Khóa ngoại trong bảng reviews
    private Movie movie;

    // Constructors
    public Review() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Integer ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    // Phương thức tiện ích được gọi trước khi persist để đặt ngày giờ hiện tại
    @PrePersist
    protected void onCreate() {
        this.reviewDate = LocalDateTime.now();
    }
}