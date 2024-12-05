package cinema.movies

import cinema.errors.MovieNotFound
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MovieController(val movieService: MovieService) {

    @GetMapping("/movies/{movie_id}")
    fun getMovieDetails(@PathVariable("movie_id") movieId: MovieId): Movie {
        return movieService.getMovieDetails(movieId) ?: throw MovieNotFound(movieId)
    }
}