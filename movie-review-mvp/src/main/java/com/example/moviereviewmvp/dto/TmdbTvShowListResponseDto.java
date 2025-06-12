package com.example.moviereviewmvp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbTvShowListResponseDto {

    private Integer page;
    private List<TmdbTvShowDto> results;

    @JsonProperty("total_pages")
    private Integer totalPages;

    @JsonProperty("total_results")
    private Integer totalResults;

    // Constructors
    public TmdbTvShowListResponseDto() {}

    // Getters and Setters
    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }
    public List<TmdbTvShowDto> getResults() { return results; }
    public void setResults(List<TmdbTvShowDto> results) { this.results = results; }
    public Integer getTotalPages() { return totalPages; }
    public void setTotalPages(Integer totalPages) { this.totalPages = totalPages; }
    public Integer getTotalResults() { return totalResults; }
    public void setTotalResults(Integer totalResults) { this.totalResults = totalResults; }
} 