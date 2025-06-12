// src/main/java/com/example/moviereviewmvp/dto/TmdbVideosResponseDto.java
package com.example.moviereviewmvp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// import lombok.Getter;
// import lombok.Setter;

import java.util.List;

// @Getter
// @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbVideosResponseDto {
    // API của TMDB trả về một mảng "results" bên trong đối tượng "videos"
    private List<TmdbVideoDto> results;

    // Getters and Setters
    public List<TmdbVideoDto> getResults() {
        return results;
    }

    public void setResults(List<TmdbVideoDto> results) {
        this.results = results;
    }
}