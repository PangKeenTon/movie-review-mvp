// src/main/java/com/example/moviereviewmvp/entity/Movie.java
package com.example.moviereviewmvp.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies", uniqueConstraints = {
        @UniqueConstraint(columnNames = "tmdbId") // Đảm bảo tmdbId là duy nhất (nếu có)
})
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID cục bộ của phim

    @Column(unique = true, nullable = true) // Cho phép null ban đầu, nhưng nếu có thì phải unique
    private Long tmdbId; // ID của phim trên TMDB

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer releaseYear;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "director_name")
    private String directorName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Review> reviews = new HashSet<>();

    // Constructors
    public Movie() {
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTmdbId() { return tmdbId; } // THÊM MỚI
    public void setTmdbId(Long tmdbId) { this.tmdbId = tmdbId; } // THÊM MỚI

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    // ... (các getters/setters khác giữ nguyên) ...
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }
    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
    public String getDirectorName() { return directorName; }
    public void setDirectorName(String directorName) { this.directorName = directorName; }
    public Set<Genre> getGenres() { return genres; }
    public void setGenres(Set<Genre> genres) { this.genres = genres; }
    public Set<Review> getReviews() { return reviews; }
    public void setReviews(Set<Review> reviews) { this.reviews = reviews; }
}