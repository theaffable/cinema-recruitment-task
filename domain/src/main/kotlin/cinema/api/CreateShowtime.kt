package cinema.api

import cinema.catalog.MovieCatalogId
import cinema.catalog.Price
import cinema.showtime.Showtime
import java.time.ZonedDateTime

interface CreateShowtime {
    fun forMovieCatalogEntry(
        movieCatalogId: MovieCatalogId,
        dateStart: ZonedDateTime,
        dateEnd: ZonedDateTime,
        price: Price? = null
    ): Showtime
}