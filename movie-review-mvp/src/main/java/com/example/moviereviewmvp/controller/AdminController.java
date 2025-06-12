////// src/main/java/com/example/moviereviewmvp/controller/AdminController.java
////package com.example.moviereviewmvp.controller;
////
////import com.example.moviereviewmvp.entity.User; // THÊM IMPORT NÀY
////import com.example.moviereviewmvp.service.UserService; // THÊM IMPORT NÀY
////import org.springframework.beans.factory.annotation.Autowired; // THÊM IMPORT NÀY
////
////import org.springframework.stereotype.Controller;
////import org.springframework.ui.Model;
////import org.springframework.web.bind.annotation.GetMapping;
////import org.springframework.web.bind.annotation.RequestMapping;
////
////import java.util.List;
////
////@Controller
////@RequestMapping("/admin") // Tất cả các request đến /admin sẽ được xử lý bởi controller này
////public class AdminController {
////
////    // Phương thức xử lý cho trang dashboard admin
////    @GetMapping("/dashboard") // Sẽ map với /admin/dashboard
////    public String adminDashboard(Model model) {
////        // Bạn có thể thêm các thuộc tính vào model ở đây nếu cần
////        // Ví dụ: model.addAttribute("someData", "Thông tin cho admin");
////        return "admin/dashboard"; // Trả về template /templates/admin/dashboard.html
////    }
////    @GetMapping("/users/list") // Sẽ map với /admin/users/list
////    public String listUsers(Model model) {
////        List<User> userList = userService.getAllUsers();
////        model.addAttribute("users", userList); // Đưa danh sách người dùng vào model
////        return "admin/user-list"; // Trả về template /templates/admin/user-list.html
////    }
////    // Các phương thức khác cho việc quản lý user, movie, review sẽ được thêm vào đây sau
////}
//
//// src/main/java/com/example/moviereviewmvp/controller/AdminController.java
//// src/main/java/com/example/moviereviewmvp/controller/AdminController.java
//package com.example.moviereviewmvp.controller;
//
//import com.example.moviereviewmvp.entity.Review; // THÊM IMPORT NÀY
//import com.example.moviereviewmvp.entity.User;
//import com.example.moviereviewmvp.service.ReviewService; // THÊM IMPORT NÀY
//import com.example.moviereviewmvp.service.UserService;
//import org.slf4j.Logger; // THÊM IMPORT NÀY (nếu chưa có)
//import org.slf4j.LoggerFactory; // THÊM IMPORT NÀY (nếu chưa có)
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable; // THÊM IMPORT NÀY
//import org.springframework.web.bind.annotation.PostMapping; // THÊM IMPORT NÀY
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes; // THÊM IMPORT NÀY
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//
//    private static final Logger logger = LoggerFactory.getLogger(AdminController.class); // Logger
//
//    private final UserService userService;
//    private final ReviewService reviewService; // Inject ReviewService
//
//    @Autowired
//    public AdminController(UserService userService, ReviewService reviewService) { // Thêm ReviewService vào constructor
//        this.userService = userService;
//        this.reviewService = reviewService; // Khởi tạo ReviewService
//    }
//
//    @GetMapping("/dashboard")
//    public String adminDashboard(Model model) {
//        return "admin/dashboard";
//    }
//
//    @GetMapping("/users/list")
//    public String listUsers(Model model) {
//        List<User> userList = userService.getAllUsers();
//        model.addAttribute("users", userList);
//        return "admin/user-list";
//    }
//
//    // --- PHƯƠNG THỨC MỚI CHO QUẢN LÝ ĐÁNH GIÁ ---
//
//    @GetMapping("/reviews/list") // Map với /admin/reviews/list
//    public String listReviews(Model model) {
//        List<Review> reviewList = reviewService.getAllReviews();
//        model.addAttribute("reviews", reviewList);
//        // Thêm thông báo từ redirect (nếu có, ví dụ sau khi xóa)
//        if (model.containsAttribute("successMessage")) {
//            model.addAttribute("successMessage", model.getAttribute("successMessage"));
//        }
//        if (model.containsAttribute("errorMessage")) {
//            model.addAttribute("errorMessage", model.getAttribute("errorMessage"));
//        }
//        return "admin/review-list"; // Trả về template /templates/admin/review-list.html
//    }
//
//    @PostMapping("/reviews/delete/{reviewId}") // Map với POST request đến /admin/reviews/delete/{reviewId}
//    public String deleteReview(@PathVariable Long reviewId, RedirectAttributes redirectAttributes) {
//        try {
//            logger.info("Admin request to delete review with ID: {}", reviewId);
//            reviewService.deleteReviewById(reviewId);
//            redirectAttributes.addFlashAttribute("successMessage", "Đánh giá ID " + reviewId + " đã được xóa thành công!");
//        } catch (Exception e) {
//            logger.error("Error deleting review with ID {}: {}", reviewId, e.getMessage(), e);
//            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa đánh giá: " + e.getMessage());
//        }
//        return "redirect:/admin/reviews/list"; // Chuyển hướng về trang danh sách đánh giá
//    }
//}
// src/main/java/com/example/moviereviewmvp/controller/AdminController.java
package com.example.moviereviewmvp.controller;

import java.util.HashSet;
import com.example.moviereviewmvp.entity.Genre; // THÊM IMPORT NÀY
import com.example.moviereviewmvp.entity.Movie; // THÊM IMPORT NÀY
import com.example.moviereviewmvp.entity.Review;
import com.example.moviereviewmvp.entity.User;
import com.example.moviereviewmvp.repository.GenreRepository; // THÊM IMPORT NÀY
import com.example.moviereviewmvp.service.MovieService; // THÊM IMPORT NÀY
import com.example.moviereviewmvp.service.ReviewService;
import com.example.moviereviewmvp.service.UserService;
import jakarta.validation.Valid; // THÊM IMPORT NÀY (nếu bạn dùng validation cho Movie entity/DTO)
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // THÊM IMPORT NÀY
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.Set; // THÊM IMPORT NÀY
import java.util.stream.Collectors; // THÊM IMPORT NÀY
import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final UserService userService;
    private final ReviewService reviewService;
    private final MovieService movieService; // Inject MovieService
    private final GenreRepository genreRepository; // Inject GenreRepository

    @Autowired
    public AdminController(UserService userService,
                           ReviewService reviewService,
                           MovieService movieService, // Thêm MovieService
                           GenreRepository genreRepository) { // Thêm GenreRepository
        this.userService = userService;
        this.reviewService = reviewService;
        this.movieService = movieService; // Khởi tạo MovieService
        this.genreRepository = genreRepository; // Khởi tạo GenreRepository
    }

    @GetMapping("/dashboard")
    public String adminDashboard(HttpServletRequest request, Model model) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        return "admin/dashboard";
    }

    // --- Quản Lý Người Dùng ---
    @GetMapping("/users/list")
    public String listUsers(HttpServletRequest request, Model model) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user-list";
    }

    // --- Quản Lý Đánh Giá ---
    @GetMapping("/reviews/list")
    public String listReviews(HttpServletRequest request, Model model) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "admin/review-list";
    }

    @PostMapping("/reviews/delete/{reviewId}")
    public String deleteReview(@PathVariable Long reviewId, RedirectAttributes redirectAttributes) {
        // ... (code deleteReview đã có) ...
        try {
            logger.info("Admin request to delete review with ID: {}", reviewId);
            reviewService.deleteReviewById(reviewId);
            redirectAttributes.addFlashAttribute("successMessage", "Đánh giá ID " + reviewId + " đã được xóa thành công!");
        } catch (Exception e) {
            logger.error("Error deleting review with ID {}: {}", reviewId, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa đánh giá: " + e.getMessage());
        }
        return "redirect:/admin/reviews/list";
    }

    // --- PHƯƠNG THỨC MỚI CHO QUẢN LÝ PHIM CỤC BỘ ---

    @GetMapping("/movies/list-local") // Phân biệt với /movies của người dùng
    public String listLocalMovies(HttpServletRequest request, Model model) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "admin/movie-list-local";
    }

    // Hiển thị form để thêm phim cục bộ mới
    @GetMapping("/movies/add")
    public String showAddMovieForm(HttpServletRequest request, Model model) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        model.addAttribute("movie", new Movie());
        model.addAttribute("allGenres", genreRepository.findAll());
        model.addAttribute("pageTitle", "Thêm Phim Mới");
        return "admin/movies/form-local";
    }

    // Hiển thị form để sửa phim cục bộ
    @GetMapping("/movies/edit/{id}")
    public String showEditMovieForm(@PathVariable Long id, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        Optional<Movie> movieOptional = movieService.getMovieById(id);
        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            // Đảm bảo genres được load để hiển thị đúng trong form (nếu là LAZY fetch)
            // Nếu genres là EAGER fetch trong Movie entity thì không cần dòng này.
            // Hibernate.initialize(movie.getGenres());
            model.addAttribute("movie", movie);
            model.addAttribute("allGenres", genreRepository.findAll());
            model.addAttribute("pageTitle", "Sửa Phim: " + movie.getTitle());
            return "admin/movies/form-local";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy phim với ID: " + id);
            return "redirect:/admin/movies/list-local";
        }
    }

    // Xử lý việc lưu phim (cả thêm mới và cập nhật)
    @PostMapping("/movies/save")
    public String saveLocalMovie(@Valid @ModelAttribute("movie") Movie movie,
                                 BindingResult bindingResult,
                                 @RequestParam(name = "genreIds", required = false) Set<Long> genreIds, // Nhận ID của các genre được chọn
                                 Model model, // Để trả lại form với lỗi nếu có
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            // Nếu có lỗi validation cơ bản từ @Valid trên Movie entity (nếu bạn có đặt)
            model.addAttribute("allGenres", genreRepository.findAll());
            model.addAttribute("pageTitle", movie.getId() == null ? "Thêm Phim Mới" : "Sửa Phim: " + movie.getTitle());
            return "admin/movies/form-local";
        }

        try {
            if (genreIds != null && !genreIds.isEmpty()) {
                Set<Genre> selectedGenres = genreRepository.findAllById(genreIds).stream().collect(Collectors.toSet());
                movie.setGenres(selectedGenres);
            } else {
                movie.setGenres(new HashSet<>()); // Xóa hết genres nếu không có gì được chọn
            }

            movieService.saveMovie(movie);
            redirectAttributes.addFlashAttribute("successMessage", "Phim \"" + movie.getTitle() + "\" đã được lưu thành công!");
            return "redirect:/admin/movies/list-local";
        } catch (Exception e) {
            logger.error("Error saving local movie: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi lưu phim: " + e.getMessage());
            // Nếu là thêm mới, quay lại form thêm mới. Nếu là sửa, quay lại form sửa.
            if (movie.getId() == null) {
                return "redirect:/admin/movies/add";
            } else {
                return "redirect:/admin/movies/edit/" + movie.getId();
            }
        }
    }

    // Xử lý việc xóa phim cục bộ
    @PostMapping("/movies/delete/{id}")
    public String deleteLocalMovie(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            movieService.deleteMovieById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Phim ID " + id + " đã được xóa thành công!");
        } catch (Exception e) {
            logger.error("Error deleting local movie with ID {}: {}", id, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa phim: " + e.getMessage());
        }
        return "redirect:/admin/movies/list-local";
    }
}