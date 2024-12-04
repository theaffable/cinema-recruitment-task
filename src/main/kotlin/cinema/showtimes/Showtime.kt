package cinema.showtimes

import cinema.SerializableUuid
import cinema.ZonedDateTimeSerializer
import cinema.catalog.MovieCatalogId
import cinema.movies.Movie
import cinema.movies.MovieId
import java.time.ZonedDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Showtime(
    val id: ShowtimeId,
    val movie: Movie,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateStart: ZonedDateTime,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateEnd: ZonedDateTime
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
    val dateEnd: ZonedDateTime
)

@Serializable
data class ShowtimeResponse(
    val showtimeId: ShowtimeId,
    val movieId: MovieId,
    val title: String,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateStart: ZonedDateTime,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateEnd: ZonedDateTime
)
