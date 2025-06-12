//// src/main/java/com/example/moviereviewmvp/controller/WatchlistController.java
//package com.example.moviereviewmvp.controller;
//
//import com.example.moviereviewmvp.service.WatchlistService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//@RequestMapping("/watchlist") // Tất cả các mapping sẽ bắt đầu bằng /watchlist
//public class WatchlistController {
//
//    private static final Logger logger = LoggerFactory.getLogger(WatchlistController.class);
//
//    private final WatchlistService watchlistService;
//
//    @Autowired
//    public WatchlistController(WatchlistService watchlistService) {
//        this.watchlistService = watchlistService;
//    }
//
//    /**
//     * Xử lý việc thêm một phim vào watchlist.
//     * @param movieId ID cục bộ của bộ phim.
//     * @param currentUser Người dùng đang đăng nhập.
//     * @param redirectAttributes Để gửi thông báo sau khi redirect.
//     * @return Chuyển hướng lại trang chi tiết phim.
//     */
//    @PostMapping("/add/{movieId}")
//    public String addMovieToWatchlist(@PathVariable Long movieId,
//                                      @AuthenticationPrincipal UserDetails currentUser,
//                                      RedirectAttributes redirectAttributes) {
//
//        if (currentUser == null) {
//            // Lớp bảo vệ, dù SecurityConfig đã chặn.
//            return "redirect:/login";
//        }
//
//        try {
//            watchlistService.addMovieToWatchlist(currentUser.getUsername(), movieId);
//            redirectAttributes.addFlashAttribute("successMessage", "Đã thêm phim vào Watchlist!");
//        } catch (Exception e) {
//            logger.error("Error adding movie {} to watchlist for user {}: {}", movieId, currentUser.getUsername(), e.getMessage());
//            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm phim vào Watchlist: " + e.getMessage());
//        }
//
//        return "redirect:/movies/" + movieId;
//    }
//
//    /**
//     * Xử lý việc xóa một phim khỏi watchlist.
//     * @param movieId ID cục bộ của bộ phim.
//     * @param currentUser Người dùng đang đăng nhập.
//     * @param redirectAttributes Để gửi thông báo sau khi redirect.
//     * @return Chuyển hướng lại trang chi tiết phim.
//     */
//    @PostMapping("/remove/{movieId}")
//    public String removeMovieFromWatchlist(@PathVariable Long movieId,
//                                           @AuthenticationPrincipal UserDetails currentUser,
//                                           RedirectAttributes redirectAttributes) {
//
//        if (currentUser == null) {
//            return "redirect:/login";
//        }
//
//        try {
//            watchlistService.removeMovieFromWatchlist(currentUser.getUsername(), movieId);
//            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa phim khỏi Watchlist!");
//        } catch (Exception e) {
//            logger.error("Error removing movie {} from watchlist for user {}: {}", movieId, currentUser.getUsername(), e.getMessage());
//            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa phim khỏi Watchlist: " + e.getMessage());
//        }
//
//        return "redirect:/movies/" + movieId;
//    }
//}
// src/main/java/com/example/moviereviewmvp/controller/WatchlistController.java
package com.example.moviereviewmvp.controller;

import com.example.moviereviewmvp.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/watchlist")
public class WatchlistController {

    private final WatchlistService watchlistService;

    @Autowired
    public WatchlistController(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    @PostMapping("/add/{movieId}")
    public String addMovieToWatchlist(@PathVariable Long movieId,
                                      @AuthenticationPrincipal UserDetails currentUser,
                                      RedirectAttributes redirectAttributes,
                                      @RequestHeader(value = "Referer", required = false) final String referer) {
        if (currentUser == null) return "redirect:/login";
        try {
            watchlistService.addMovieToWatchlist(currentUser.getUsername(), movieId);
            redirectAttributes.addFlashAttribute("successMessage", "Đã thêm phim vào Watchlist!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:" + (referer != null ? referer : "/");
    }

    @PostMapping("/remove/{movieId}")
    public String removeMovieFromWatchlist(@PathVariable Long movieId,
                                           @AuthenticationPrincipal UserDetails currentUser,
                                           RedirectAttributes redirectAttributes,
                                           @RequestHeader(value = "Referer", required = false) final String referer) {
        if (currentUser == null) return "redirect:/login";
        try {
            watchlistService.removeMovieFromWatchlist(currentUser.getUsername(), movieId);
            redirectAttributes.addFlashAttribute("successMessage", "Đã xóa phim khỏi Watchlist!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:" + (referer != null ? referer : "/");
    }
}