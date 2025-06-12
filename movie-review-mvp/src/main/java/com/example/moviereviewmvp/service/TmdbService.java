//// src/main/java/com/example/moviereviewmvp/service/TmdbService.java
//package com.example.moviereviewmvp.service;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.HttpStatus;
//import com.example.moviereviewmvp.dto.TmdbMovieDto;
//import com.example.moviereviewmvp.dto.TmdbMovieListResponseDto;
//import com.example.moviereviewmvp.dto.TmdbMovieDetailsDto;
//import org.slf4j.Logger; // Thêm logger
//import org.slf4j.LoggerFactory; // Thêm logger
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestClientException; // Xử lý lỗi API
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder; // Xây dựng URL an toàn
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.util.Collections; // Cho danh sách rỗng
//import java.util.List;
//
//@Service
//public class TmdbService {
//
//    private static final Logger logger = LoggerFactory.getLogger(TmdbService.class); // Logger
//
//    private final RestTemplate restTemplate;
//
//    // Inject các giá trị từ application.properties
//    @Value("${tmdb.api.key}")
//    private String tmdbApiKey;
//
//    @Value("${tmdb.api.base_url}")
//    private String tmdbApiBaseUrl;
//
//    @Autowired
//    public TmdbService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    /**
//     * Lấy danh sách phim phổ biến từ TMDB.
//     * @param page Số trang muốn lấy (mặc định là 1 nếu không truyền)
//     * @return Danh sách các TmdbMovieDto hoặc danh sách rỗng nếu có lỗi.
//     */
//    public List<TmdbMovieDto> getPopularMovies(int page) {
//        // Xây dựng URL một cách an toàn
//        String url = UriComponentsBuilder.fromHttpUrl(tmdbApiBaseUrl)
//                .path("/movie/popular")
//                .queryParam("api_key", tmdbApiKey)
//                .queryParam("language", "en-US") // Hoặc "vi-VN" nếu muốn tiếng Việt (TMDB có thể không hỗ trợ đầy đủ)
//                .queryParam("page", page)
//                .toUriString();
//
//        logger.debug("Fetching popular movies from URL: {}", url); // Log URL
//
//        try {
//            // Gọi API và ánh xạ kết quả vào TmdbMovieListResponseDto
//            TmdbMovieListResponseDto response = restTemplate.getForObject(url, TmdbMovieListResponseDto.class);
//            if (response != null && response.getResults() != null) {
//                return response.getResults();
//            }
//        } catch (RestClientException e) {
//            // Ghi log lỗi khi gọi API
//            logger.error("Error calling TMDB API for popular movies: {}", e.getMessage());
//        }
//        return Collections.emptyList(); // Trả về danh sách rỗng nếu có lỗi hoặc không có kết quả
//    }
//
//    /**
//     * Tìm kiếm phim trên TMDB.
//     * @param query Từ khóa tìm kiếm
//     * @param page Số trang muốn lấy
//     * @return TmdbMovieListResponseDto chứa kết quả và thông tin phân trang, hoặc null nếu lỗi.
//     */
//    public TmdbMovieListResponseDto searchMovies(String query, int page) {
//        String url = UriComponentsBuilder.fromHttpUrl(tmdbApiBaseUrl)
//                .path("/search/movie")
//                .queryParam("api_key", tmdbApiKey)
//                .queryParam("query", query)
//                .queryParam("language", "en-US") // Hoặc "vi-VN"
//                .queryParam("page", page)
//                .queryParam("include_adult", false) // Không bao gồm phim người lớn
//                .toUriString();
//
//        logger.debug("Searching movies with URL: {}", url);
//
//        try {
//            TmdbMovieListResponseDto response = restTemplate.getForObject(url, TmdbMovieListResponseDto.class);
//            return response; // Trả về toàn bộ response để controller có thể xử lý phân trang
//        } catch (RestClientException e) {
//            logger.error("Error calling TMDB API for movie search (query: {}): {}", query, e.getMessage());
//        }
//        return null; // Hoặc một TmdbMovieListResponseDto rỗng
//    }
//
//    // TODO (Tùy chọn cho Sprint sau): Triển khai phương thức lấy chi tiết phim
//
//    public TmdbMovieDetailsDto getMovieDetails(Long tmdbMovieId) {
//        String url = UriComponentsBuilder.fromHttpUrl(tmdbApiBaseUrl)
//                .pathSegment("movie", String.valueOf(tmdbMovieId)) // Path segment: /movie/{tmdbMovieId}
//                .queryParam("api_key", tmdbApiKey)
//                .queryParam("language", "en-US") // Hoặc "vi-VN"
//                .queryParam("append_to_response", "credits,videos,images") // Lấy thêm thông tin credits, videos, images
//                .toUriString();
//
////        logger.debug("Fetching movie details from URL: {}", url);
////
////        try {
////            // Cần tạo TmdbMovieDetailsDto để ánh xạ response này
////            // TmdbMovieDetailsDto response = restTemplate.getForObject(url, TmdbMovieDetailsDto.class);
////            // return response;
////        } catch (RestClientException e) {
////            logger.error("Error calling TMDB API for movie details (id: {}): {}", tmdbMovieId, e.getMessage());
////        }
////        return null;
//        logger.debug("Fetching movie details from URL: {}", url);
//
//        try {
//            // Bước 1: Lấy JSON dưới dạng String để kiểm tra
//            ResponseEntity<String> jsonResponseEntity = restTemplate.getForEntity(url, String.class); // Sử dụng getForEntity
//            if (jsonResponseEntity.getStatusCode() == HttpStatus.OK) {
//                String jsonBody = jsonResponseEntity.getBody();
//                logger.info("TMDB RAW JSON Response for movie ID {}: {}", tmdbMovieId, jsonBody); // In ra TOÀN BỘ JSON mà RestTemplate nhận được
//
//                // Bây giờ, thử parse thủ công bằng ObjectMapper để xem lỗi cụ thể nếu có
//                ObjectMapper objectMapper = new ObjectMapper();
//                // Quan trọng: Cấu hình ObjectMapper tương tự như mặc định của Spring Boot (bỏ qua unknown properties)
//                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//                try {
//                    TmdbMovieDetailsDto response = objectMapper.readValue(jsonBody, TmdbMovieDetailsDto.class);
//                    logger.info("Successfully parsed JSON to TmdbMovieDetailsDto for movie ID {}", tmdbMovieId);
//                    return response;
//                } catch (JsonProcessingException e) {
//                    logger.error("Jackson parsing error for movie ID {}: {}", tmdbMovieId, e.getMessage(), e); // Log lỗi parsing chi tiết
//                    // In ra cả jsonBody để đối chiếu khi có lỗi parsing
//                    logger.error("Problematic JSON body for movie ID {}: {}", tmdbMovieId, jsonBody);
//                    return null; // Trả về null nếu lỗi parsing
//                }
//
//            } else {
//                logger.error("TMDB API returned non-OK status: {} for movie ID: {}", jsonResponseEntity.getStatusCode(), tmdbMovieId);
//            }
//
//        } catch (RestClientException e) {
//            logger.error("RestClientException calling TMDB API for movie details (id: {}): {}", tmdbMovieId, e.getMessage(), e);
//        }
//        return null;
//    }
//
//}

// src/main/java/com/example/moviereviewmvp/service/TmdbService.java
package com.example.moviereviewmvp.service;

import com.example.moviereviewmvp.dto.TmdbMovieDto;
import com.example.moviereviewmvp.dto.TmdbMovieListResponseDto;
import com.example.moviereviewmvp.dto.TmdbMovieDetailsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
public class TmdbService {

    private static final Logger logger = LoggerFactory.getLogger(TmdbService.class);

    private final RestTemplate restTemplate;

    @Value("${tmdb.api.key}")
    private String tmdbApiKey;

    @Value("${tmdb.api.base_url}")
    private String tmdbApiBaseUrl;

    @Autowired
    public TmdbService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // --- Các phương thức lấy danh sách phim ---

    public List<TmdbMovieDto> getPopularMovies(int page) {
        return getMoviesFromTmdbEndpoint("/movie/popular", page);
    }

    public List<TmdbMovieDto> getNowPlayingMovies(int page) {
        return getMoviesFromTmdbEndpoint("/movie/now_playing", page);
    }

    public List<TmdbMovieDto> getUpcomingMovies(int page) {
        return getMoviesFromTmdbEndpoint("/movie/upcoming", page);
    }

    public List<TmdbMovieDto> getTopRatedMovies(int page) {
        return getMoviesFromTmdbEndpoint("/movie/top_rated", page);
    }

    // Phương thức chung để gọi các endpoint trả về danh sách phim
    private List<TmdbMovieDto> getMoviesFromTmdbEndpoint(String path, int page) {
        String url = UriComponentsBuilder.fromHttpUrl(tmdbApiBaseUrl)
                .path(path)
                .queryParam("api_key", tmdbApiKey)
                .queryParam("language", "en-US")
                .queryParam("page", page)
                .toUriString();
        logger.debug("Fetching movies from TMDB URL: {}", url);
        try {
            TmdbMovieListResponseDto response = restTemplate.getForObject(url, TmdbMovieListResponseDto.class);
            if (response != null && response.getResults() != null) {
                return response.getResults();
            }
        } catch (RestClientException e) {
            logger.error("Error calling TMDB API for path {}: {}", path, e.getMessage());
        }
        return Collections.emptyList();
    }


    // --- Các phương thức tìm kiếm và chi tiết ---

    public TmdbMovieListResponseDto searchMovies(String query, int page) {
        String url = UriComponentsBuilder.fromHttpUrl(tmdbApiBaseUrl)
                .path("/search/movie")
                .queryParam("api_key", tmdbApiKey)
                .queryParam("query", query)
                .queryParam("language", "en-US")
                .queryParam("page", page)
                .queryParam("include_adult", false)
                .toUriString();
        logger.debug("Searching movies with URL: {}", url);
        try {
            return restTemplate.getForObject(url, TmdbMovieListResponseDto.class);
        } catch (RestClientException e) {
            logger.error("Error calling TMDB API for movie search (query: {}): {}", query, e.getMessage());
        }
        return null;
    }

    /**
     * Lấy thông tin chi tiết của một bộ phim từ TMDB.
     * Phiên bản đã được dọn dẹp và đơn giản hóa.
     * @param tmdbMovieId ID của phim trên TMDB
     * @return TmdbMovieDetailsDto chứa thông tin chi tiết, hoặc null nếu có lỗi.
     */
    public TmdbMovieDetailsDto getMovieDetails(Long tmdbMovieId) {
        String url = UriComponentsBuilder.fromHttpUrl(tmdbApiBaseUrl)
                .pathSegment("movie", String.valueOf(tmdbMovieId))
                .queryParam("api_key", tmdbApiKey)
                .queryParam("language", "en-US")
                .queryParam("append_to_response", "videos,credits") // Lấy thêm video và credits
                .toUriString();

        logger.debug("Fetching movie details from URL: {}", url);

        try {
            // Gọi trực tiếp và để Jackson tự động parse JSON sang DTO
            TmdbMovieDetailsDto response = restTemplate.getForObject(url, TmdbMovieDetailsDto.class);
            if (response != null) {
                logger.info("Successfully fetched and parsed details for movie ID {}", tmdbMovieId);
            }
            return response;
        } catch (RestClientException e) {
            // Lỗi này thường xảy ra khi có vấn đề về mạng hoặc API trả về lỗi (4xx, 5xx)
            // hoặc khi Jackson không thể parse JSON (HttpMessageNotReadableException)
            logger.error("Error fetching/parsing movie details for ID {}: {}", tmdbMovieId, e.getMessage(), e);
        }
        return null;
    }
}