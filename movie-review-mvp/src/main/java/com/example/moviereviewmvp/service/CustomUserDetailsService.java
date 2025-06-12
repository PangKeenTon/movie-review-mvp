// src/main/java/com/example/moviereviewmvp/service/CustomUserDetailsService.java
package com.example.moviereviewmvp.service;

import com.example.moviereviewmvp.entity.User;
import com.example.moviereviewmvp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service // Quan trọng!
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với tên đăng nhập: " + username));

        // Chuyển đổi chuỗi vai trò (ví dụ: "ROLE_USER,ROLE_ADMIN") thành Collection<GrantedAuthority>
        Collection<? extends GrantedAuthority> authorities =
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), // Mật khẩu đã được mã hóa trong CSDL
                authorities);
    }
}