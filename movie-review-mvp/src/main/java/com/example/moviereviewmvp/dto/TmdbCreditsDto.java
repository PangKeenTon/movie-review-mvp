// src/main/java/com/example/moviereviewmvp/dto/TmdbCreditsDto.java
package com.example.moviereviewmvp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// import lombok.Getter;
// import lombok.Setter;

import java.util.List;

// @Getter
// @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbCreditsDto {
    private List<TmdbCastMemberDto> cast;
    private List<TmdbCrewMemberDto> crew;

    // Getters and Setters
    public List<TmdbCastMemberDto> getCast() { return cast; }
    public void setCast(List<TmdbCastMemberDto> cast) { this.cast = cast; }
    public List<TmdbCrewMemberDto> getCrew() { return crew; }
    public void setCrew(List<TmdbCrewMemberDto> crew) { this.crew = crew; }
}