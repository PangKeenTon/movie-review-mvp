// src/main/java/com/example/moviereviewmvp/service/WatchlistService.java
package com.example.moviereviewmvp.service;

import com.example.moviereviewmvp.entity.Movie;
import com.example.moviereviewmvp.entity.User;
import com.example.moviereviewmvp.repository.MovieRepository;
import com.example.moviereviewmvp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

@Service
public class WatchlistService {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public WatchlistService(UserRepository userRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Transactional
    public void addMovieToWatchlist(String username, Long movieId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng: " + username));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phim với ID: " + movieId));

        user.getWatchlist().add(movie); // Thêm phim vào Set watchlist của user
        // userRepository.save(user); // Vì User entity quản lý mối quan hệ, việc lưu lại User sẽ cập nhật bảng trung gian.
        // @Transactional sẽ tự động commit thay đổi khi phương thức kết thúc thành công.
    }

    @Transactional
    public void removeMovieFromWatchlist(String username, Long movieId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng: " + username));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phim với ID: " + movieId));

        user.getWatchlist().remove(movie); // Xóa phim khỏi Set watchlist của user
        // userRepository.save(user); // Tương tự như trên, @Transactional sẽ xử lý việc lưu
    }

    @Transactional(readOnly = true) // readOnly=true để tối ưu cho các thao tác chỉ đọc
    public Set<Movie> getWatchlistForUser(String username) {
        return userRepository.findByUsername(username)
                .map(User::getWatchlist) // Sử dụng method reference để lấy watchlist
                .orElse(Collections.emptySet()); // Trả về một Set rỗng nếu không tìm thấy user
    }

    @Transactional(readOnly = true)
    public boolean isMovieInWatchlist(String username, Long movieId) {
        return userRepository.findByUsername(username)
                .map(user -> user.getWatchlist().stream()
                        .anyMatch(movie -> movie.getId().equals(movieId)))
                .orElse(false); // Trả về false nếu không tìm thấy user
    }
}