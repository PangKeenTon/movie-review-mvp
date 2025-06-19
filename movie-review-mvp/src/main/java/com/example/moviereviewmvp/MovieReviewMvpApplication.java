package com.example.moviereviewmvp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MovieReviewMvpApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieReviewMvpApplication.class, args);
	}

}
