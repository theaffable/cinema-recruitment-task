package cinema.movies

import cinema.MovieNotFound
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MovieController(val movieService: MovieHttpService) {

    @GetMapping("/movies/{movieId}")
    fun getMovieDetails(@PathVariable movieId: MovieId): Movie {
        return movieService.getMovieDetails(movieId) ?: throw MovieNotFound(movieId)
    }
}