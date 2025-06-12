// src/main/java/com/example/moviereviewmvp/dto/TmdbMovieDetailsDto.java
package com.example.moviereviewmvp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
// import lombok.Getter;
// import lombok.Setter;

import java.util.List;

// @Getter
// @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbMovieDetailsDto {
    private Long id;
    private String title;
    private String overview;
    private String tagline; // Câu khẩu hiệu của phim

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("backdrop_path")
    private String backdropPath; // Ảnh nền lớn

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("vote_average")
    private Double voteAverage;

    private Integer runtime; // Thời lượng phim (phút)

    private List<TmdbGenreDto> genres; // Danh sách các thể loại

    // Thông tin video (trailer, teaser, ...)
    // API TMDB trả về một đối tượng có key "videos", bên trong có mảng "results"
    private TmdbVideosResponseDto videos;

    // Thông tin diễn viên và đội ngũ sản xuất
    // API TMDB trả về một đối tượng có key "credits", bên trong có mảng "cast" và "crew"
    private TmdbCreditsDto credits;

    // Constructors
    public TmdbMovieDetailsDto() {
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getOverview() { return overview; }
    public void setOverview(String overview) { this.overview = overview; }
    public String getTagline() { return tagline; }
    public void setTagline(String tagline) { this.tagline = tagline; }
    public String getPosterPath() { return posterPath; }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }
    public String getBackdropPath() { return backdropPath; }
    public void setBackdropPath(String backdropPath) { this.backdropPath = backdropPath; }
    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
    public Double getVoteAverage() { return voteAverage; }
    public void setVoteAverage(Double voteAverage) { this.voteAverage = voteAverage; }
    public Integer getRuntime() { return runtime; }
    public void setRuntime(Integer runtime) { this.runtime = runtime; }
    public List<TmdbGenreDto> getGenres() { return genres; }
    public void setGenres(List<TmdbGenreDto> genres) { this.genres = genres; }
    public TmdbVideosResponseDto getVideos() { return videos; }
    public void setVideos(TmdbVideosResponseDto videos) { this.videos = videos; }
    public TmdbCreditsDto getCredits() { return credits; }
    public void setCredits(TmdbCreditsDto credits) { this.credits = credits; }
}