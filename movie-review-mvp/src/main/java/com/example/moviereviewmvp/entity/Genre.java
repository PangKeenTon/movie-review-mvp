// src/main/java/com/example/moviereviewmvp/entity/Genre.java
package com.example.moviereviewmvp.entity;

import jakarta.persistence.*;
import java.util.Set; // Sẽ dùng cho mối quan hệ

@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // Mối quan hệ ManyToMany với Movie
    // 'mappedBy = "genres"' chỉ ra rằng mối quan hệ này được quản lý bởi trường "genres" trong class Movie.
    // Điều này giúp tránh việc tạo bảng nối từ cả hai phía.
    @ManyToMany(mappedBy = "genres")
    private Set<Movie> movies;

    // Constructors
    public Genre() {}
    public Genre(String name) { this.name = name; }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<Movie> getMovies() { return movies; }
    public void setMovies(Set<Movie> movies) { this.movies = movies; }
}