package cinema.showtimes

import cinema.SerializableUuid
import cinema.ZonedDateTimeSerializer
import cinema.catalog.MovieCatalogId
import cinema.movies.Movie
import cinema.movies.MovieId
import cinema.price.Price
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
