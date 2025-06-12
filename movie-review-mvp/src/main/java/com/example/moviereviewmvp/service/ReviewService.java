// src/main/java/com/example/moviereviewmvp/service/ReviewService.java
package com.example.moviereviewmvp.service;

import com.example.moviereviewmvp.dto.ReviewDto;
import com.example.moviereviewmvp.entity.Movie;
import com.example.moviereviewmvp.entity.Review;
import com.example.moviereviewmvp.entity.User;
import com.example.moviereviewmvp.repository.MovieRepository;
import com.example.moviereviewmvp.repository.ReviewRepository;
import com.example.moviereviewmvp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService { // <-- DẤU NGOẶC MỞ CỦA LỚP

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                         MovieRepository movieRepository,
                         UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public List<Review> getReviewsForMovie(Long movieId) {
        return reviewRepository.findByMovieIdWithUserOrderByReviewDateDesc(movieId);
    }

    @Transactional
    public Review addReview(Long movieId, String username, ReviewDto reviewDto) throws Exception {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new Exception("Không tìm thấy phim với ID: " + movieId));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("Không tìm thấy người dùng với username: " + username));

        Review newReview = new Review();
        newReview.setMovie(movie);
        newReview.setUser(user);
        newReview.setRatingValue(reviewDto.getRatingValue());
        newReview.setCommentText(reviewDto.getCommentText());

        return reviewRepository.save(newReview);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll(Sort.by(Sort.Direction.DESC, "reviewDate"));
    }

    @Transactional
    public void deleteReviewById(Long reviewId) throws Exception {
        if (!reviewRepository.existsById(reviewId)) {
            throw new Exception("Không tìm thấy đánh giá với ID: " + reviewId);
        }
        reviewRepository.deleteById(reviewId);
    }

    /**
     * Lấy tất cả đánh giá của một người dùng, sắp xếp theo ngày mới nhất.
     * @param username Tên đăng nhập của người dùng.
     * @return Danh sách các đánh giá.
     */
    @Transactional(readOnly = true)
    public List<Review> getReviewsByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> user.getReviews().stream()
                        .sorted(Comparator.comparing(Review::getReviewDate).reversed())
                        .collect(Collectors.toList())
                )
                .orElse(new ArrayList<>());
    }

} // <-- DẤU NGOẶC ĐÓNG CUỐI CÙNG CỦA LỚP ReviewService. Đảm bảo phương thức mới nằm TRÊN dòng này.