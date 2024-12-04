package cinema.showtimes

import cinema.movies.Movie
import cinema.movies.MovieId
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.uuid.Uuid
import org.springframework.stereotype.Repository

interface ShowtimeRepository {
    fun findAll(): List<Showtime>
    fun create(showtime: Showtime)
}

@Repository
class DatabaseShowtimeRepository(): ShowtimeRepository {
    private val showtimes = mutableListOf<Showtime>(
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
            dateEnd = ZonedDateTime.now().plusHours(2)
        )
    )

    override fun findAll(): List<Showtime> = showtimes

    override fun create(showtime: Showtime) {
        showtimes.add(showtime)
    }
}