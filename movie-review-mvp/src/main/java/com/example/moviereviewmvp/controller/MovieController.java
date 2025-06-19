// src/main/java/com/example/moviereviewmvp/controller/MovieController.java
package com.example.moviereviewmvp.controller;

import com.example.moviereviewmvp.dto.ReviewDto;
import com.example.moviereviewmvp.dto.TmdbMovieDto;
import com.example.moviereviewmvp.dto.TmdbMovieListResponseDto;
import com.example.moviereviewmvp.dto.TmdbMovieDetailsDto;
import com.example.moviereviewmvp.dto.TmdbVideoDto;
import com.example.moviereviewmvp.dto.TmdbTvShowDto;
import com.example.moviereviewmvp.entity.Movie;
import com.example.moviereviewmvp.entity.Review;
import com.example.moviereviewmvp.service.MovieService;
import com.example.moviereviewmvp.service.ReviewService;
import com.example.moviereviewmvp.service.TmdbService;
import com.example.moviereviewmvp.service.WatchlistService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/movies")
public class MovieController { // <-- NGOẶC MỞ CỦA LỚP

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    private final MovieService movieService;
    private final ReviewService reviewService;
    private final TmdbService tmdbService;
    private final WatchlistService watchlistService;

    @Value("${tmdb.image.base_url}")
    private String tmdbImageBaseUrl;

    @Autowired
    public MovieController(MovieService movieService,
                           ReviewService reviewService,
                           TmdbService tmdbService,
                           WatchlistService watchlistService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
        this.tmdbService = tmdbService;
        this.watchlistService = watchlistService;
    }

    // --- Các mapping cho phim lưu cục bộ ---
    @GetMapping
    public String listMovies(HttpServletRequest request, Model model) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "movies/list";
    }

    @GetMapping("/{id}")
    public String movieDetails(@PathVariable Long id, HttpServletRequest request, Model model) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        Optional<Movie> movieOptional = movieService.getMovieById(id);
        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            model.addAttribute("movie", movie);
            List<Review> reviews = reviewService.getReviewsForMovie(id);
            model.addAttribute("reviews", reviews);
            if (!model.containsAttribute("newReview")) {
                model.addAttribute("newReview", new ReviewDto());
            }
            boolean isInWatchlist = false;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal().toString())) {
                UserDetails currentUser = (UserDetails) authentication.getPrincipal();
                isInWatchlist = watchlistService.isMovieInWatchlist(currentUser.getUsername(), id);
            }
            model.addAttribute("isInWatchlist", isInWatchlist);
            return "movies/details";
        } else {
            return "redirect:/movies";
        }
    }

    @PostMapping("/{movieId}/reviews")
    public String submitReview(@PathVariable Long movieId,
                               @Valid @ModelAttribute("newReview") ReviewDto reviewDto,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal UserDetails currentUser,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        // ... (code phương thức này giữ nguyên)
        Optional<Movie> movieOptional = movieService.getMovieById(movieId);
        if (!movieOptional.isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy phim để đánh giá.");
            return "redirect:/movies";
        }
        Movie movie = movieOptional.get();

        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng đăng nhập để đánh giá.");
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("movie", movie);
            List<Review> reviews = reviewService.getReviewsForMovie(movieId);
            model.addAttribute("reviews", reviews);
            return "movies/details";
        }

        try {
            reviewService.addReview(movieId, currentUser.getUsername(), reviewDto);
            redirectAttributes.addFlashAttribute("successMessage", "Đánh giá của bạn đã được gửi thành công!");
        } catch (Exception e) {
            logger.error("Error submitting review for movie ID {}: {}", movieId, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi gửi đánh giá: " + e.getMessage());
        }

        if (movie.getTmdbId() != null) {
            return "redirect:/movies/tmdb/details/" + movie.getTmdbId();
        }
        return "redirect:/movies/" + movieId;
    }

    // --- CÁC MAPPING CHO TMDB API ---
    @GetMapping("/tmdb/popular")
    public String listPopularTmdbMovies(@RequestParam(name = "page", defaultValue = "1") int page,
                                        HttpServletRequest request, Model model) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        List<TmdbMovieDto> popularMovies = tmdbService.getPopularMovies(page);
        model.addAttribute("movies", popularMovies);
        model.addAttribute("tmdbImageBaseUrl", tmdbImageBaseUrl + "w500");
        model.addAttribute("currentPage", page);
        model.addAttribute("pageTitle", "Phim Phổ Biến (TMDB)");
        model.addAttribute("pageUrl", "/movies/tmdb/popular");
        return "movies/popular_tmdb";
    }

    @GetMapping("/tmdb/search")
    public String searchTmdbMovies(@RequestParam("query") String query,
                                   @RequestParam(name = "page", defaultValue = "1") int page,
                                   HttpServletRequest request, Model model) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        if (query == null || query.trim().isEmpty()) {
            return "redirect:/";
        }
        TmdbMovieListResponseDto searchResult = tmdbService.searchMovies(query, page);
        model.addAttribute("searchResult", searchResult);
        model.addAttribute("query", query);
        model.addAttribute("tmdbImageBaseUrl", tmdbImageBaseUrl + "w342");
        model.addAttribute("currentPage", page);
        return "movies/search_results_tmdb";
    }

    @GetMapping("/tmdb/details/{tmdbMovieId}")
    public String tmdbMovieDetailsPage(@PathVariable Long tmdbMovieId,
                                       Model model,
                                       RedirectAttributes redirectAttributes,
                                       HttpServletRequest request) { // Thêm HttpServletRequest vào tham số
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);

        logger.info("Fetching details for TMDB movie ID: {}", tmdbMovieId);
        TmdbMovieDetailsDto tmdbDetails = tmdbService.getMovieDetails(tmdbMovieId);

        if (tmdbDetails == null) {
            logger.warn("Could not fetch movie details from TMDB for ID: {}", tmdbMovieId);
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể tải thông tin chi tiết phim từ TMDB.");
            return "redirect:/movies/tmdb/popular";
        }

        logger.info("Successfully fetched TMDB details for movie: {}", tmdbDetails.getTitle());

        // Tìm hoặc tạo Movie entity cục bộ
        Movie localMovie = movieService.findOrCreateMovieByTmdbDetails(tmdbDetails);
        logger.info("Local movie (ID: {}, TMDB_ID: {}) associated with TMDB movie: {}", localMovie.getId(), localMovie.getTmdbId(), localMovie.getTitle());

        model.addAttribute("tmdbMovie", tmdbDetails);
        model.addAttribute("movie", localMovie);
        model.addAttribute("tmdbImageBaseUrl", tmdbImageBaseUrl);

        // Lấy danh sách review cho phim này (dựa trên ID cục bộ của phim)
        // Đảm bảo ReviewService.getReviewsForMovie() dùng JOIN FETCH để tránh LazyInitializationException
        List<Review> reviews = reviewService.getReviewsForMovie(localMovie.getId());
        model.addAttribute("reviews", reviews);
        logger.info("Found {} reviews for local movie ID: {}", reviews.size(), localMovie.getId());

        if (!model.containsAttribute("newReview")) {
            model.addAttribute("newReview", new ReviewDto());
        }

        // Logic tìm trailer
        Optional<TmdbVideoDto> trailer = Optional.empty();
        if (tmdbDetails.getVideos() != null && tmdbDetails.getVideos().getResults() != null) {
            trailer = tmdbDetails.getVideos().getResults().stream()
                    .filter(v -> "YouTube".equalsIgnoreCase(v.getSite()) && "Trailer".equalsIgnoreCase(v.getType()) && Boolean.TRUE.equals(v.getOfficial()))
                    .findFirst()
                    .or(() -> tmdbDetails.getVideos().getResults().stream()
                            .filter(v -> "YouTube".equalsIgnoreCase(v.getSite()) && "Trailer".equalsIgnoreCase(v.getType()))
                            .findFirst());
        }
        model.addAttribute("trailer", trailer);

        // Logic kiểm tra watchlist
        boolean isInWatchlist = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal().toString())) {
            UserDetails currentUser = (UserDetails) authentication.getPrincipal();
            isInWatchlist = watchlistService.isMovieInWatchlist(currentUser.getUsername(), localMovie.getId());
        }
        model.addAttribute("isInWatchlist", isInWatchlist);

        return "movies/details_tmdb";
    }

    // --- CÁC PHƯƠNG THỨC MỚI CHO TÍNH NĂNG KHÁM PHÁ ---

    @GetMapping("/tmdb/now-playing")
    public String listNowPlayingMovies(@RequestParam(name = "page", defaultValue = "1") int page,
                                       HttpServletRequest request, Model model) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        List<TmdbMovieDto> nowPlayingMovies = tmdbService.getNowPlayingMovies(page);
        model.addAttribute("movies", nowPlayingMovies);
        model.addAttribute("tmdbImageBaseUrl", tmdbImageBaseUrl + "w500");
        model.addAttribute("currentPage", page);
        model.addAttribute("pageTitle", "Phim Đang Chiếu (TMDB)");
        model.addAttribute("pageUrl", "/movies/tmdb/now-playing");
        return "movies/popular_tmdb";
    }

    @GetMapping("/tmdb/upcoming")
    public String listUpcomingMovies(@RequestParam(name = "page", defaultValue = "1") int page,
                                     HttpServletRequest request, Model model) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        List<TmdbMovieDto> upcomingMovies = tmdbService.getUpcomingMovies(page);
        model.addAttribute("movies", upcomingMovies);
        model.addAttribute("tmdbImageBaseUrl", tmdbImageBaseUrl + "w500");
        model.addAttribute("currentPage", page);
        model.addAttribute("pageTitle", "Phim Sắp Chiếu (TMDB)");
        model.addAttribute("pageUrl", "/movies/tmdb/upcoming");
        return "movies/popular_tmdb";
    }

    @GetMapping("/tmdb/top-rated")
    public String listTopRatedMovies(@RequestParam(name = "page", defaultValue = "1") int page,
                                     HttpServletRequest request, Model model) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        List<TmdbMovieDto> topRatedMovies = tmdbService.getTopRatedMovies(page);
        model.addAttribute("movies", topRatedMovies);
        model.addAttribute("tmdbImageBaseUrl", tmdbImageBaseUrl + "w500");
        model.addAttribute("currentPage", page);
        model.addAttribute("pageTitle", "Phim Được Đánh Giá Cao Nhất (TMDB)");
        model.addAttribute("pageUrl", "/movies/tmdb/top-rated");
        return "movies/popular_tmdb";
    }

    // --- CÁC MAPPING CHO CHƯƠNG TRÌNH TV TMDB ---

    @GetMapping("/tmdb/tv/popular")
    public String listPopularTvShows(@RequestParam(name = "page", defaultValue = "1") int page,
                                     HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        List<TmdbTvShowDto> popularTvShows = tmdbService.getPopularTvShows(page);
        model.addAttribute("tvShows", popularTvShows);
        model.addAttribute("tmdbImageBaseUrl", tmdbImageBaseUrl + "w500");
        model.addAttribute("currentPage", page);
        model.addAttribute("pageTitle", "Chương Trình TV Phổ Biến (TMDB)");
        model.addAttribute("pageUrl", "/movies/tmdb/tv/popular");
        return "movies/popular_tv_tmdb";
    }

    // --- CHI TIẾT CHƯƠNG TRÌNH TV TMDB ---
    @GetMapping("/tmdb/tv/details/{tvShowId}")
    public String tmdbTvShowDetailsPage(@PathVariable Long tvShowId,
                                         Model model,
                                         RedirectAttributes redirectAttributes,
                                         HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);

        logger.info("Fetching details for TMDB TV show ID: {}", tvShowId);
        TmdbTvShowDto tvShowDetails = tmdbService.getTvShowDetails(tvShowId);

        if (tvShowDetails == null) {
            logger.warn("Could not fetch TV show details from TMDB for ID: {}", tvShowId);
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể tải thông tin chi tiết chương trình TV từ TMDB.");
            return "redirect:/movies/tmdb/tv/popular";
        }

        model.addAttribute("tvShow", tvShowDetails);
        model.addAttribute("tmdbImageBaseUrl", tmdbImageBaseUrl);
        return "movies/details_tv_tmdb";
    }

} // <-- NGOẶC ĐÓNG CUỐI CÙNG CỦA LỚP MovieController. Đảm bảo tất cả phương thức nằm trên dòng này.