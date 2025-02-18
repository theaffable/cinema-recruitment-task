package cinema.showtime

import cinema.api.CreateShowtime
import cinema.catalog.MovieCatalogId
import cinema.catalog.Price
import cinema.exceptions.CatalogEntryNotFoundException
import cinema.spi.MovieCatalogInventory
import cinema.spi.ShowtimeInventory
import ddd.DomainService
import java.time.ZonedDateTime

@DomainService
class CreateShowtimeUseCase(
    private val movieCatalogInventory: MovieCatalogInventory,
    private val showtimeInventory: ShowtimeInventory
) : CreateShowtime {
    override fun forMovieCatalogEntry(
        movieCatalogId: MovieCatalogId,
        dateStart: ZonedDateTime,
        dateEnd: ZonedDateTime,
        price: Price?
    ): Showtime {
        val entry = movieCatalogInventory.find(movieCatalogId) ?: throw CatalogEntryNotFoundException(movieCatalogId)
        return showtimeInventory.create(
            movieId = entry.movieId,
            movieTitle = entry.title,
            dateStart = dateStart,
            dateEnd = dateEnd,
            price = entry.price
        )
    }
}