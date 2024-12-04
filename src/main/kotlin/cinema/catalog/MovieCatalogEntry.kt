package cinema.catalog

import cinema.SerializableUuid
import cinema.movies.MovieId
import kotlinx.serialization.Serializable

@Serializable
data class MovieCatalogEntry(
    val id: MovieCatalogId,
    val movieId: MovieId,
    val title: String
)

@JvmInline
@Serializable
value class MovieCatalogId(val value: SerializableUuid)
