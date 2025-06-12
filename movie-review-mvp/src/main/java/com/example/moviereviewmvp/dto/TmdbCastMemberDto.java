// src/main/java/com/example/moviereviewmvp/dto/TmdbCastMemberDto.java
package com.example.moviereviewmvp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbCastMemberDto {
    private Integer id;
    private String name; // Tên diễn viên
    private String character; // Tên nhân vật

    @JsonProperty("profile_path")
    private String profilePath; // Đường dẫn ảnh đại diện của diễn viên

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCharacter() { return character; }
    public void setCharacter(String character) { this.character = character; }
    public String getProfilePath() { return profilePath; }
    public void setProfilePath(String profilePath) { this.profilePath = profilePath; }
}