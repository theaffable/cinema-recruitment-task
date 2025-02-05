package cinema.api

import cinema.movie.Movie
import cinema.movie.MovieId

interface GetMovieDetails {
    fun forMovie(movieId: MovieId): Movie
}