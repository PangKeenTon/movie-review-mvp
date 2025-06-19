//// src/main/java/com/example/moviereviewmvp/config/SecurityConfig.java
//package com.example.moviereviewmvp.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers("/", "/index.html", "/css/**", "/js/**", "/images/**", "/h2-console/**").permitAll()
//                                .requestMatchers("/login", "/register").permitAll()
//                                .requestMatchers("/movies/tmdb/**").permitAll()
//                                .requestMatchers("/movies/{id}").permitAll()
//                                .requestMatchers("/movies").permitAll()
//
//                                // QUY TẮC CHO ADMIN
//                                .requestMatchers("/admin/**").hasRole("ADMIN")
//
//                                // === DÒNG MỚI THÊM VÀO ĐỂ BẢO VỆ WATCHLIST ===
//                                .requestMatchers("/watchlist/**").authenticated()
//                                // ==========================================
//
//                                .anyRequest().authenticated() // Tất cả các request khác cần xác thực
//                )
//                .formLogin(formLogin ->
//                        formLogin
//                                .loginPage("/login")
//                                .loginProcessingUrl("/perform_login")
//                                .defaultSuccessUrl("/", true)
//                                .failureUrl("/login?error=true")
//                                .permitAll()
//                )
//                .logout(logout ->
//                        logout
//                                .logoutUrl("/perform_logout")
//                                .logoutSuccessUrl("/login?logout")
//                                .permitAll()
//                )
//                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
//                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));
//
//        return http.build();
//    }
//
//}

// src/main/java/com/example/moviereviewmvp/config/SecurityConfig.java
package com.example.moviereviewmvp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // === Các URL cho phép tất cả (cả khách và người đã đăng nhập) ===
                                .requestMatchers(
                                        "/", "/index.html",
                                        "/css/**", "/js/**", "/images/**",
                                        "/h2-console/**",
                                        "/movies/tmdb/**", // Xem phim TMDB
                                        "/movies/{id}",      // Xem chi tiết phim local
                                        "/movies",           // Xem danh sách phim local
                                        "/error",             // Cho phép trang lỗi mặc định của Spring Boot
                                        "/login", "/register" // Các trang đăng nhập/đăng ký
                                ).permitAll()

                                // === Các URL yêu cầu vai trò ADMIN ===
                                .requestMatchers("/admin/**").hasRole("ADMIN")

                                // === Các URL yêu cầu đã đăng nhập (vai trò bất kỳ) ===
                                .requestMatchers("/watchlist/**").authenticated()
                                .requestMatchers("/profile/**").authenticated()

                                // Tất cả các request khác chưa được định nghĩa ở trên đều cần xác thực
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .loginProcessingUrl("/perform_login")
                                .defaultSuccessUrl("/", true)
                                .failureUrl("/login?error=true")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/perform_logout")
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }

}