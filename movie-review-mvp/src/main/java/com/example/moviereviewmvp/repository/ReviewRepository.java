// src/main/java/com/example/moviereviewmvp/repository/ReviewRepository.java
package com.example.moviereviewmvp.repository;

import com.example.moviereviewmvp.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // THÊM IMPORT NÀY
import org.springframework.data.repository.query.Param; // THÊM IMPORT NÀY
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Đánh dấu đây là một Spring Data repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Phương thức để tìm tất cả các đánh giá cho một bộ phim cụ thể,
    // sắp xếp theo ngày đánh giá giảm dần (đánh giá mới nhất lên đầu).
    // Spring Data JPA sẽ tự động tạo truy vấn dựa trên tên phương thức.
    @Query("SELECT r FROM Review r JOIN FETCH r.user WHERE r.movie.id = :movieId ORDER BY r.reviewDate DESC")
    List<Review> findByMovieIdWithUserOrderByReviewDateDesc(@Param("movieId") Long movieId);

    @Query("SELECT r FROM Review r JOIN FETCH r.user WHERE r.tvShowId = :tvShowId ORDER BY r.reviewDate DESC")
    List<Review> findByTvShowIdWithUserOrderByReviewDateDesc(@Param("tvShowId") Long tvShowId);

    //List<Review> findByMovieIdOrderByReviewDateDesc(Long movieId);

    // Bạn có thể thêm các phương thức truy vấn tùy chỉnh khác ở đây nếu cần, ví dụ:
    // List<Review> findByUserId(Long userId);
}