package cinema.catalog

import cinema.serializers.SerializableUuid
import cinema.movies.MovieId
import cinema.price.Price
import kotlinx.serialization.Serializable

@Serializable
data class MovieCatalogEntry(
    val id: MovieCatalogId,
    val movieId: MovieId,
    val title: String,
    val price: Price
)

@JvmInline
@Serializable
value class MovieCatalogId(val value: SerializableUuid)
