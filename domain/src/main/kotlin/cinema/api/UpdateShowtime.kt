package cinema.api

import cinema.catalog.MovieCatalogId
import cinema.catalog.Price
import cinema.showtime.Showtime
import cinema.showtime.ShowtimeId
import java.time.ZonedDateTime

interface UpdateShowtime {
    fun update(
        showtimeId: ShowtimeId,
        movieCatalogId: MovieCatalogId?,
        dateTimeStart: ZonedDateTime?,
        dateTimeEnd: ZonedDateTime?,
        price: Price?
    ): Showtime
}