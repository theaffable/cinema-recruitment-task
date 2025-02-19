package cinema.showtime

import cinema.api.GetShowtimes
import cinema.movie.MovieId
import cinema.spi.ShowtimeInventory
import ddd.DomainService
import java.time.ZonedDateTime

@DomainService
class GetShowtimesUseCase(
    private val showtimeInventory: ShowtimeInventory
) : GetShowtimes {
    override fun filterBy(
        movieId: MovieId?,
        dateStartGte: ZonedDateTime?,
        dateStartLte: ZonedDateTime?
    ): Collection<Showtime> = showtimeInventory.findAll(movieId = movieId, dateStartGte = dateStartGte, dateStartLte = dateStartLte)
}