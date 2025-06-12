// src/main/java/com/example/moviereviewmvp/controller/ProfileController.java
package com.example.moviereviewmvp.controller;

import com.example.moviereviewmvp.entity.Movie;
import com.example.moviereviewmvp.entity.Review;             // THÊM IMPORT NÀY
import com.example.moviereviewmvp.service.ReviewService;     // THÊM IMPORT NÀY
import com.example.moviereviewmvp.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List; // THÊM IMPORT NÀY
import java.util.Set;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final WatchlistService watchlistService;
    private final ReviewService reviewService; // Thêm trường cho ReviewService

    @Value("${tmdb.image.base_url}")
    private String tmdbImageBaseUrl;

    @Autowired
    public ProfileController(WatchlistService watchlistService, ReviewService reviewService) { // Thêm ReviewService vào constructor
        this.watchlistService = watchlistService;
        this.reviewService = reviewService; // Khởi tạo
    }

    // Phương thức hiển thị trang profile chính
    @GetMapping
    public String userProfile(HttpServletRequest request, Model model, @AuthenticationPrincipal UserDetails currentUser) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        model.addAttribute("currentUser", currentUser);
        return "profile/index";
    }

    // Phương thức hiển thị watchlist
    @GetMapping("/watchlist")
    public String userWatchlist(HttpServletRequest request, Model model, @AuthenticationPrincipal UserDetails currentUser) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        if (currentUser != null) {
            Set<Movie> watchlistMovies = watchlistService.getWatchlistForUser(currentUser.getUsername());
            model.addAttribute("watchlistMovies", watchlistMovies);
            model.addAttribute("tmdbImageBaseUrl", tmdbImageBaseUrl + "w500");
        }
        return "profile/watchlist";
    }

    // Phương thức hiển thị các đánh giá của user
    @GetMapping("/reviews")
    public String userReviews(HttpServletRequest request, Model model, @AuthenticationPrincipal UserDetails currentUser) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        if (currentUser != null) {
            List<Review> userReviews = reviewService.getReviewsByUsername(currentUser.getUsername());
            model.addAttribute("userReviews", userReviews);
            // Thêm URL ảnh để Thymeleaf có thể sử dụng cho poster phim trong danh sách review
            model.addAttribute("tmdbImageBaseUrl", tmdbImageBaseUrl);
        }
        return "profile/reviews"; // Trả về template /templates/profile/reviews.html
    }
}
