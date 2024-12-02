package cinema.movies

import cinema.catalog.MovieCatalogService
import java.time.LocalDate
import org.springframework.stereotype.Service

@Service
class MovieHttpService(private val movieCatalogService: MovieCatalogService) {
    fun getMovieDetails(movieId: MovieId): Movie? {
        if (!movieCatalogService.isInCatalog(movieId)) return null
        return fetchMovieDetails(movieId)
    }

    private fun fetchMovieDetails(movieId: MovieId): Movie? {
        return Movie(
            id = MovieId(value = "fetchedFromOMDB_ID"),
            title = "fetchedFromOMDB_title",
            description = "fetchedFromOMDB_desc",
            releaseDate = LocalDate.now(),
            imdbRating = "fetchedFromOMDB_imdb_rating",
            runtime = "fetchedFromOMDB_runtime"
        )
    }
}