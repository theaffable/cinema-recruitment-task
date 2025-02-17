package cinema.http.controllers

import cinema.api.GetMovieDetails
import cinema.exceptions.MovieNotFoundException
import cinema.movie.MovieId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MovieController(
    private val getMovieDetails: GetMovieDetails
) {

    @GetMapping("/movies/{movie_id}")
    fun getMovieDetails(@PathVariable("movie_id") movieId: String): MovieResponse {
        val id = MovieId(value = movieId)
        return getMovieDetails.forMovie(id)?.toResponse() ?: throw MovieNotFoundException(id)
    }
}
