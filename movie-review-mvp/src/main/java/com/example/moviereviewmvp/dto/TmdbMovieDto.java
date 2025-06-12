// src/main/java/com/example/moviereviewmvp/dto/TmdbMovieDto.java
package com.example.moviereviewmvp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
// import lombok.Getter; // Nếu dùng Lombok
// import lombok.Setter;

// @Getter // Lombok
// @Setter // Lombok
// @JsonIgnoreProperties(ignoreUnknown = true)  giúp bỏ qua các trường không xác định trong JSON
// khi chuyển đổi JSON sang đối tượng Java, tránh lỗi nếu API TMDB thêm trường mới.
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbMovieDto {

    private Long id; // ID của phim trên TMDB
    private String title;
    private String overview; // Mô tả phim

    @JsonProperty("poster_path") // Ánh xạ từ trường "poster_path" trong JSON
    private String posterPath;

    @JsonProperty("release_date") // Ánh xạ từ trường "release_date"
    private String releaseDate;

    @JsonProperty("vote_average") // Ánh xạ từ trường "vote_average"
    private Double voteAverage; // Điểm đánh giá trung bình

    // Constructors (không bắt buộc nếu chỉ dùng cho Jackson và có getter/setter)
    public TmdbMovieDto() {
    }

    // Getters and Setters (Rất quan trọng để Jackson có thể gán giá trị)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getOverview() { return overview; }
    public void setOverview(String overview) { this.overview = overview; }
    public String getPosterPath() { return posterPath; }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }
    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
    public Double getVoteAverage() { return voteAverage; }
    public void setVoteAverage(Double voteAverage) { this.voteAverage = voteAverage; }
}