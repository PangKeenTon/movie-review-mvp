// src/main/java/com/example/moviereviewmvp/dto/TmdbGenreDto.java
package com.example.moviereviewmvp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// import lombok.Getter; // Nếu dùng Lombok
// import lombok.Setter;

// @Getter
// @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbGenreDto {
    private Integer id; // ID của thể loại trên TMDB
    private String name; // Tên thể loại, ví dụ: "Action", "Comedy"

    // Constructors
    public TmdbGenreDto() {
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}