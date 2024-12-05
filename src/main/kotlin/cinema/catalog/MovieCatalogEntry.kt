package cinema.catalog

import cinema.serializers.SerializableUuid
import cinema.movies.MovieId
import kotlinx.serialization.Serializable

@Serializable
data class MovieCatalogEntry(
    val id: MovieCatalogId,
    val movieId: MovieId,
    val title: String,
    val price: Price,
    val rating: Rating
)

@JvmInline
@Serializable
value class MovieCatalogId(val value: SerializableUuid)
