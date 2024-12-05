package cinema.showtimes

import cinema.CatalogEntryNotFound
import cinema.MovieNotFound
import cinema.catalog.MovieCatalogId
import cinema.catalog.MovieCatalogService
import cinema.movies.MovieService
import cinema.price.Price
import java.time.ZonedDateTime
import kotlin.uuid.Uuid
import org.springframework.stereotype.Service

interface ShowtimeService {
    fun getAll(): List<Showtime>
    fun create(movieCatalogId: MovieCatalogId, dateTimeStart: ZonedDateTime, dateTimeEnd: ZonedDateTime, priceOverride: Price?): Showtime
}

@Service
class SimpleShowtimeService(
    private val showtimeRepository: ShowtimeRepository,
    private val movieCatalogService: MovieCatalogService,
    private val movieService: MovieService
): ShowtimeService {
    override fun getAll(): List<Showtime> = showtimeRepository.findAll()

    override fun create(movieCatalogId: MovieCatalogId, dateTimeStart: ZonedDateTime, dateTimeEnd: ZonedDateTime, priceOverride: Price?): Showtime {
        val catalogEntry = movieCatalogService.findById(movieCatalogId) ?: throw CatalogEntryNotFound(movieCatalogId)
        val movie = movieService.getMovieDetails(catalogEntry.movieId) ?: throw MovieNotFound(catalogEntry.movieId)
        return Showtime(
            id = ShowtimeId(Uuid.random()),
            movie = movie,
            dateStart = dateTimeStart,
            dateEnd = dateTimeEnd,
            priceOverride = priceOverride
        ).also { showtimeRepository.create(it) }
    }
}