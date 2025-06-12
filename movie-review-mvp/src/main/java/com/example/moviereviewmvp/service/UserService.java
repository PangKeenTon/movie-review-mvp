// src/main/java/com/example/moviereviewmvp/service/UserService.java
package com.example.moviereviewmvp.service;

import com.example.moviereviewmvp.dto.UserRegistrationDto;
import com.example.moviereviewmvp.entity.Role; // THÊM IMPORT NÀY
import com.example.moviereviewmvp.entity.User;
import com.example.moviereviewmvp.repository.RoleRepository; // THÊM IMPORT NÀY
import com.example.moviereviewmvp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections; // THÊM IMPORT NÀY
import java.util.HashSet;   // THÊM IMPORT NÀY
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository; // Inject RoleRepository

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository) { // Thêm RoleRepository vào constructor
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository; // Khởi tạo
    }

    public User registerNewUser(UserRegistrationDto registrationDto) throws Exception {
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            throw new Exception("Mật khẩu và xác nhận mật khẩu không khớp!");
        }
        if (userRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            throw new Exception("Tên đăng nhập đã tồn tại: " + registrationDto.getUsername());
        }
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new Exception("Email đã được sử dụng: " + registrationDto.getEmail());
        }

        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setEmail(registrationDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        // **PHẦN SỬA LỖI NẰM Ở ĐÂY**
        // 1. Tìm vai trò ROLE_USER trong CSDL
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            // Nếu vai trò chưa có trong CSDL (trường hợp hiếm gặp nếu data.sql chạy đúng)
            // thì tạo mới nó.
            userRole = roleRepository.save(new Role("ROLE_USER"));
        }

        // 2. Gán vai trò đó cho người dùng mới
        newUser.setRoles(new HashSet<>(Collections.singletonList(userRole)));

        return userRepository.save(newUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}