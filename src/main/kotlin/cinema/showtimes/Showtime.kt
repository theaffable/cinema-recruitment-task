package cinema.showtimes

import cinema.serializers.SerializableUuid
import cinema.serializers.ZonedDateTimeSerializer
import cinema.catalog.MovieCatalogId
import cinema.movies.Movie
import cinema.movies.MovieId
import cinema.catalog.Price
import java.time.ZonedDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Showtime(
    val id: ShowtimeId,
    val movie: Movie,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateStart: ZonedDateTime,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateEnd: ZonedDateTime,
    val priceOverride: Price?
)

@JvmInline
@Serializable
value class ShowtimeId(val value: SerializableUuid)

@Serializable
data class CreateShowtimeRequest(
    val movieCatalogId: MovieCatalogId,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateStart: ZonedDateTime,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateEnd: ZonedDateTime,
    val priceOverride: Price?
)

@Serializable
data class ModifyShowtimeRequest(
    val movieCatalogId: MovieCatalogId? = null,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateStart: ZonedDateTime? = null,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateEnd: ZonedDateTime? = null,
    val priceOverride: Price? = null
)

@Serializable
data class ShowtimeResponse(
    val showtimeId: ShowtimeId,
    val movieId: MovieId,
    val title: String,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateStart: ZonedDateTime,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateEnd: ZonedDateTime,
    val priceOverride: Price?
)
