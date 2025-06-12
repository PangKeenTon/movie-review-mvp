// src/main/java/com/example/moviereviewmvp/repository/MovieRepository.java
package com.example.moviereviewmvp.repository;

import com.example.moviereviewmvp.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Thêm import

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTmdbId(Long tmdbId);
}