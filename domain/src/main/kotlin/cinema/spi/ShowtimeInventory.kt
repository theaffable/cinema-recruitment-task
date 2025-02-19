package cinema.spi

import cinema.catalog.Price
import cinema.movie.MovieId
import cinema.showtime.Showtime
import cinema.showtime.ShowtimeId
import java.time.ZonedDateTime

interface ShowtimeInventory {
    fun create(
        movieId: MovieId,
        movieTitle: String,
        dateStart: ZonedDateTime,
        dateEnd: ZonedDateTime,
        price: Price
    ): Showtime

    fun findAll(
        movieId: MovieId?,
        dateStartGte: ZonedDateTime?,
        dateStartLte: ZonedDateTime?
    ): Collection<Showtime>

    fun update(
        showtimeId: ShowtimeId,
        movieId: MovieId?,
        movieTitle: String?,
        dateTimeStart: ZonedDateTime?,
        dateTimeEnd: ZonedDateTime?,
        price: Price?
    ): Showtime

    fun delete(showtimeId: ShowtimeId)
}