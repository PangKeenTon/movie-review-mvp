// src/main/java/com/example/moviereviewmvp/repository/GenreRepository.java
package com.example.moviereviewmvp.repository;

import com.example.moviereviewmvp.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(String name);

}