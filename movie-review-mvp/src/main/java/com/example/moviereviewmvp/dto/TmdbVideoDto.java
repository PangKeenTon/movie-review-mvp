// src/main/java/com/example/moviereviewmvp/dto/TmdbVideoDto.java
package com.example.moviereviewmvp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbVideoDto {
    private String id; // ID của video trên TMDB

    @JsonProperty("iso_639_1")
    private String iso6391;

    @JsonProperty("iso_3166_1")
    private String iso31661;

    private String name; // Tên/tiêu đề của video
    private String key;  // Key của video, thường dùng cho YouTube (ví dụ: để tạo link https://www.youtube.com/watch?v=KEY)
    private String site; // Nền tảng video (ví dụ: "YouTube")
    private Integer size; // Độ phân giải (ví dụ: 1080)
    private String type; // Loại video (ví dụ: "Trailer", "Teaser", "Featurette")
    private Boolean official;

    @JsonProperty("published_at")
    private String publishedAt;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getIso6391() { return iso6391; }
    public void setIso6391(String iso6391) { this.iso6391 = iso6391; }
    public String getIso31661() { return iso31661; }
    public void setIso31661(String iso31661) { this.iso31661 = iso31661; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getSite() { return site; }
    public void setSite(String site) { this.site = site; }
    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Boolean getOfficial() { return official; }
    public void setOfficial(Boolean official) { this.official = official; }
    public String getPublishedAt() { return publishedAt; }
    public void setPublishedAt(String publishedAt) { this.publishedAt = publishedAt; }
}