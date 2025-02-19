package cinema.api

import cinema.movie.MovieId
import cinema.showtime.Showtime
import java.time.ZonedDateTime

interface GetShowtimes {
    fun filterBy(movieId: MovieId?, dateStartGte: ZonedDateTime?, dateStartLte: ZonedDateTime?): Collection<Showtime>
}