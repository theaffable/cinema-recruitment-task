package cinema.catalog

import cinema.movies.MovieId
import kotlinx.serialization.Serializable

@Serializable
data class MovieCatalogEntry(
    val movieId: MovieId,
    val title: String
)
