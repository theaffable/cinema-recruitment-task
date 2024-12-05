package cinema.showtimes

import cinema.errors.CatalogEntryNotFoundException
import cinema.errors.MovieNotFoundException
import cinema.errors.ShowtimeNotFoundException
import cinema.catalog.MovieCatalogId
import cinema.catalog.MovieCatalogService
import cinema.movies.MovieId
import cinema.movies.MovieService
import cinema.catalog.Price
import java.time.ZonedDateTime
import kotlin.uuid.Uuid
import org.springframework.stereotype.Service

interface ShowtimeService {
    fun findBy(
        movieId: MovieId?,
        startsBefore: ZonedDateTime?,
        startsAfter: ZonedDateTime?
    ): List<Showtime>

    fun create(
        movieCatalogId: MovieCatalogId,
        dateTimeStart: ZonedDateTime,
        dateTimeEnd: ZonedDateTime,
        priceOverride: Price?
    ): Showtime

    fun update(
        showtimeId: ShowtimeId,
        movieCatalogId: MovieCatalogId?,
        dateTimeStart: ZonedDateTime?,
        dateTimeEnd: ZonedDateTime?,
        priceOverride: Price?
    ): Showtime

    fun delete(showtimeId: ShowtimeId)
}

@Service
class SimpleShowtimeService(
    private val showtimeRepository: ShowtimeRepository,
    private val movieCatalogService: MovieCatalogService,
    private val movieService: MovieService
): ShowtimeService {
    override fun findBy(
        movieId: MovieId?,
        startsBefore: ZonedDateTime?,
        startsAfter: ZonedDateTime?
    ): List<Showtime> =
        showtimeRepository.findBy(
            movieId = movieId,
            startsBefore = startsBefore,
            startsAfter = startsAfter
        )

    override fun create(
        movieCatalogId: MovieCatalogId,
        dateTimeStart: ZonedDateTime,
        dateTimeEnd: ZonedDateTime,
        priceOverride: Price?
    ): Showtime {
        val catalogEntry = movieCatalogService.findById(movieCatalogId) ?: throw CatalogEntryNotFoundException(movieCatalogId)
        val movie = movieService.getMovieDetails(catalogEntry.movieId) ?: throw MovieNotFoundException(catalogEntry.movieId)
        return Showtime(
            id = ShowtimeId(Uuid.random()),
            movie = movie,
            dateStart = dateTimeStart,
            dateEnd = dateTimeEnd,
            priceOverride = priceOverride
        ).also { showtimeRepository.create(it) }
    }

    override fun update(
        showtimeId: ShowtimeId,
        movieCatalogId: MovieCatalogId?,
        dateTimeStart: ZonedDateTime?,
        dateTimeEnd: ZonedDateTime?,
        priceOverride: Price?
    ): Showtime {
        val showtime = showtimeRepository.findBy(showtimeId) ?: throw ShowtimeNotFoundException(showtimeId)
        val movie = movieCatalogId
            ?. let { movieCatalogService.findById(it) }
            ?. let { movieService.getMovieDetails(it.movieId) }
        return showtimeRepository.update(
            showtimeId = showtimeId,
            movie = movie ?: showtime.movie,
            dateTimeStart = dateTimeStart ?: showtime.dateStart,
            dateTimeEnd = dateTimeEnd ?: showtime.dateEnd,
            priceOverride = priceOverride ?: showtime.priceOverride
        )
    }

    override fun delete(showtimeId: ShowtimeId) {
        showtimeRepository.delete(showtimeId)
    }
}