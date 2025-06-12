// src/main/java/com/example/moviereviewmvp/config/AppConfig.java
package com.example.moviereviewmvp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // Đánh dấu đây là một lớp cấu hình
public class AppConfig {

    @Bean // Đánh dấu phương thức này tạo ra một bean được quản lý bởi Spring
    public RestTemplate restTemplate() {
        return new RestTemplate(); // (Tham khảo từ tài liệu PDF nếu có)
    }
}