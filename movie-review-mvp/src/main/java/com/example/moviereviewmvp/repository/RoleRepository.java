// src/main/java/com/example/moviereviewmvp/repository/RoleRepository.java
package com.example.moviereviewmvp.repository;

import com.example.moviereviewmvp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name); // Tìm vai trò theo tên
}