package cinema.showtimes

import cinema.movies.Movie
import cinema.movies.MovieId
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.uuid.Uuid
import org.springframework.stereotype.Repository

interface ShowtimeRepository {
    fun findBy(movieId: MovieId?, startsBefore: ZonedDateTime?, startsAfter: ZonedDateTime?): List<Showtime>
    fun create(showtime: Showtime)
}

@Repository
class DatabaseShowtimeRepository(): ShowtimeRepository {
    private val showtimes = mutableListOf(
        Showtime(
            id = ShowtimeId(Uuid.random()),
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

    override fun findBy(movieId: MovieId?, startsBefore: ZonedDateTime?, startsAfter: ZonedDateTime?): List<Showtime> =
        showtimes
            .filter { movieId == null || movieId == it.movie.id }
            .filter { startsBefore == null || it.dateStart.isBefore(startsBefore) }
            .filter { startsAfter == null || it.dateStart.isAfter(startsAfter) }

    override fun create(showtime: Showtime) {
        showtimes.add(showtime)
    }
}