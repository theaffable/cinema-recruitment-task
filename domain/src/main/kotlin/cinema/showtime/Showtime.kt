package cinema.showtime

import cinema.catalog.Price
import cinema.movie.MovieId
import java.time.ZonedDateTime
import kotlin.uuid.Uuid

@JvmInline
value class ShowtimeId(val value: Uuid)

data class Showtime(
    val id: ShowtimeId,
    val movieId: MovieId,
    val movieTitle: String,
    val dateStart: ZonedDateTime,
    val dateEnd: ZonedDateTime,
    val price: Price?
)