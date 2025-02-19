package cinema.showtime

import cinema.api.UpdateShowtime
import cinema.catalog.MovieCatalogId
import cinema.catalog.Price
import cinema.exceptions.ShowtimeNotFoundException
import cinema.spi.MovieCatalogInventory
import cinema.spi.ShowtimeInventory
import ddd.DomainService
import java.time.ZonedDateTime

@DomainService
class UpdateShowtimeUseCase(
    private val showtimeInventory: ShowtimeInventory,
    private val movieCatalogInventory: MovieCatalogInventory
) : UpdateShowtime {
    override fun update(
        showtimeId: ShowtimeId,
        movieCatalogId: MovieCatalogId?,
        dateTimeStart: ZonedDateTime?,
        dateTimeEnd: ZonedDateTime?,
        price: Price?
    ): Showtime {
        val entry = movieCatalogId?.let { movieCatalogInventory.find(it) }
        return try {
            showtimeInventory.update(
                showtimeId = showtimeId,
                movieId = entry?.movieId,
                movieTitle = entry?.title,
                dateTimeStart = dateTimeStart,
                dateTimeEnd = dateTimeEnd,
                price = price
            )
        } catch (e: NoSuchElementException) {
            throw ShowtimeNotFoundException(showtimeId)
        }
    }
}