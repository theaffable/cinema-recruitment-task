package cinema.spi

import cinema.movie.Movie
import cinema.movie.MovieId

interface MovieDetailsInventory {
    fun fetchMovieDetails(movieId: MovieId): Movie
}
