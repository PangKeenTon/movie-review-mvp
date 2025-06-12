// src/main/java/com/example/moviereviewmvp/repository/UserRepository.java
package com.example.moviereviewmvp.repository;

import com.example.moviereviewmvp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email); // Thêm dòng này
}