package cinema.movie

import cinema.api.GetMovieDetails
import cinema.spi.MovieDetailsInventory
import ddd.DomainService

@DomainService
class GetMovieDetailsUseCase(private val movieDetailsInventory: MovieDetailsInventory) : GetMovieDetails {
    override fun forMovie(movieId: MovieId): Movie {
        return movieDetailsInventory.fetchMovieDetails(movieId)
    }
}