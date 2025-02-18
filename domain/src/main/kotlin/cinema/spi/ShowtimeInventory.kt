package cinema.spi

import cinema.catalog.Price
import cinema.movie.MovieId
import cinema.showtime.Showtime
import java.time.ZonedDateTime

interface ShowtimeInventory {
    fun create(
        movieId: MovieId,
        movieTitle: String,
        dateStart: ZonedDateTime,
        dateEnd: ZonedDateTime,
        price: Price
    ): Showtime
}