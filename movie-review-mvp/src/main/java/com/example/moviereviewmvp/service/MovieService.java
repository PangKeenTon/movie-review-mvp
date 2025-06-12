// src/main/java/com/example/moviereviewmvp/service/MovieService.java
package com.example.moviereviewmvp.service;

import com.example.moviereviewmvp.dto.TmdbMovieDetailsDto;
import com.example.moviereviewmvp.entity.Genre;
import com.example.moviereviewmvp.entity.Movie;
import com.example.moviereviewmvp.repository.GenreRepository;
import com.example.moviereviewmvp.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService { // <--- Ngoặc mở của lớp

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    } // <--- Ngoặc đóng của getAllMovies

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    } // <--- Ngoặc đóng của getMovieById

    @Transactional
    public Movie findOrCreateMovieByTmdbDetails(TmdbMovieDetailsDto tmdbDetails) {
        if (tmdbDetails == null || tmdbDetails.getId() == null) {
            throw new IllegalArgumentException("TMDB movie details hoặc TMDB ID không được null.");
        }
        Optional<Movie> existingMovieOpt = movieRepository.findByTmdbId(tmdbDetails.getId());
        if (existingMovieOpt.isPresent()) {
            return existingMovieOpt.get();
        } else {
            Movie newMovie = new Movie();
            newMovie.setTmdbId(tmdbDetails.getId());
            newMovie.setTitle(tmdbDetails.getTitle());
            newMovie.setDescription(tmdbDetails.getOverview());
            if (tmdbDetails.getReleaseDate() != null && tmdbDetails.getReleaseDate().length() >= 4) {
                try {
                    newMovie.setReleaseYear(Integer.parseInt(tmdbDetails.getReleaseDate().substring(0, 4)));
                } catch (NumberFormatException e) {
                    logger.warn("Could not parse release year from TMDB date: {}", tmdbDetails.getReleaseDate());
                }
            }
            if (tmdbDetails.getPosterPath() != null) {
                newMovie.setPosterUrl(tmdbDetails.getPosterPath());
            }
            if (tmdbDetails.getCredits() != null && tmdbDetails.getCredits().getCrew() != null) {
                tmdbDetails.getCredits().getCrew().stream()
                        .filter(crew -> "Director".equalsIgnoreCase(crew.getJob()))
                        .findFirst()
                        .ifPresent(director -> newMovie.setDirectorName(director.getName()));
            }
            if (tmdbDetails.getGenres() != null && !tmdbDetails.getGenres().isEmpty()) {
                Set<Genre> localGenres = new HashSet<>();
                for (com.example.moviereviewmvp.dto.TmdbGenreDto tmdbGenreDto : tmdbDetails.getGenres()) {
                    Genre genre = genreRepository.findByName(tmdbGenreDto.getName())
                            .orElseGet(() -> genreRepository.save(new Genre(tmdbGenreDto.getName())));
                    localGenres.add(genre);
                }
                newMovie.setGenres(localGenres);
            }
            return movieRepository.save(newMovie);
        }
    } // <--- Ngoặc đóng của findOrCreateMovieByTmdbDetails

    @Transactional
    public Movie saveMovie(Movie movie) {
        logger.info("Saving movie: {}", movie.getTitle());
        return movieRepository.save(movie);
    } // <--- Ngoặc đóng của saveMovie

    @Transactional
    public void deleteMovieById(Long id) throws Exception {
        if (!movieRepository.existsById(id)) {
            throw new Exception("Không tìm thấy phim cục bộ với ID: " + id + " để xóa.");
        }
        logger.info("Deleting movie with ID: {}", id);
        movieRepository.deleteById(id);
    }
}