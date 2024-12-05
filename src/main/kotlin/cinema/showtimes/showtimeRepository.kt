package cinema.showtimes

import cinema.errors.ShowtimeNotFoundException
import cinema.movies.Movie
import cinema.movies.MovieId
import cinema.price.Price
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.uuid.Uuid
import org.springframework.stereotype.Repository

interface ShowtimeRepository {
    fun findBy(showtimeId: ShowtimeId): Showtime?
    fun findBy(movieId: MovieId?, startsBefore: ZonedDateTime?, startsAfter: ZonedDateTime?): List<Showtime>
    fun create(showtime: Showtime)
    fun update(
        showtimeId: ShowtimeId,
        movie: Movie?,
        dateTimeStart: ZonedDateTime?,
        dateTimeEnd: ZonedDateTime?,
        priceOverride: Price?
    ): Showtime
    fun delete(showtimeId: ShowtimeId)
}

@Repository
class DatabaseShowtimeRepository(): ShowtimeRepository {
    private val showtimes = mutableListOf(
        Showtime(
            id = ShowtimeId(Uuid.parse("6aef2def-0665-4587-856e-0270da4289e7")),
            movie = Movie(
                id = MovieId(value = "test-movie-id"),
                title = "any title",
                director = "",
                writers = listOf(),
                actors = listOf(),
                rated = "",
                description = "",
                releaseDate = LocalDate.now(),
                imdbRating = "",
                runtime = "",
                languages = listOf()
            ),
            dateStart = ZonedDateTime.now(),
            dateEnd = ZonedDateTime.now().plusHours(2),
            priceOverride = null
        )
    )

    override fun findBy(showtimeId: ShowtimeId) = showtimes.find { showtimeId == it.id }

    override fun findBy(movieId: MovieId?, startsBefore: ZonedDateTime?, startsAfter: ZonedDateTime?): List<Showtime> =
        showtimes
            .filter { movieId == null || movieId == it.movie.id }
            .filter { startsBefore == null || it.dateStart.isBefore(startsBefore) }
            .filter { startsAfter == null || it.dateStart.isAfter(startsAfter) }

    override fun create(showtime: Showtime) {
        showtimes.add(showtime)
    }

    override fun update(
        showtimeId: ShowtimeId,
        movie: Movie?,
        dateTimeStart: ZonedDateTime?,
        dateTimeEnd: ZonedDateTime?,
        priceOverride: Price?
    ): Showtime {
        val showtime = findBy(showtimeId) ?: throw ShowtimeNotFoundException(showtimeId)
        val updated = showtime.copy(
            movie = movie ?: showtime.movie,
            dateStart = dateTimeStart ?: showtime.dateStart,
            dateEnd = dateTimeEnd ?: showtime.dateEnd,
            priceOverride = priceOverride ?: showtime.priceOverride
        )
        showtimes.replaceAll { if (it.id == showtimeId) updated else it }
        return updated
    }

    override fun delete(showtimeId: ShowtimeId) {
        val wasRemoved = showtimes.removeIf { showtimeId == it.id }
        if (!wasRemoved) throw ShowtimeNotFoundException(showtimeId)
    }
}