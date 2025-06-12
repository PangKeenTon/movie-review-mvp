// src/main/java/com/example/moviereviewmvp/dto/TmdbMovieListResponseDto.java
package com.example.moviereviewmvp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
// import lombok.Getter; // Nếu dùng Lombok
// import lombok.Setter;

import java.util.List;

// @Getter // Lombok
// @Setter // Lombok
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbMovieListResponseDto {

    private Integer page; // Trang hiện tại của kết quả

    // Danh sách các đối tượng phim, mỗi đối tượng là một TmdbMovieDto
    private List<TmdbMovieDto> results;

    @JsonProperty("total_pages")
    private Integer totalPages; // Tổng số trang kết quả

    @JsonProperty("total_results")
    private Integer totalResults; // Tổng số kết quả (phim)

    // Constructors
    public TmdbMovieListResponseDto() {
    }

    // Getters and Setters
    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }
    public List<TmdbMovieDto> getResults() { return results; }
    public void setResults(List<TmdbMovieDto> results) { this.results = results; }
    public Integer getTotalPages() { return totalPages; }
    public void setTotalPages(Integer totalPages) { this.totalPages = totalPages; }
    public Integer getTotalResults() { return totalResults; }
    public void setTotalResults(Integer totalResults) { this.totalResults = totalResults; }
}