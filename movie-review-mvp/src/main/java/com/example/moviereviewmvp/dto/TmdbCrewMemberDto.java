// src/main/java/com/example/moviereviewmvp/dto/TmdbCrewMemberDto.java
package com.example.moviereviewmvp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbCrewMemberDto {
    private Integer id;
    private String name; // Tên thành viên
    private String job;  // Vai trò (ví dụ: "Director", "Producer")

    @JsonProperty("profile_path")
    private String profilePath;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getJob() { return job; }
    public void setJob(String job) { this.job = job; }
    public String getProfilePath() { return profilePath; }
    public void setProfilePath(String profilePath) { this.profilePath = profilePath; }
}