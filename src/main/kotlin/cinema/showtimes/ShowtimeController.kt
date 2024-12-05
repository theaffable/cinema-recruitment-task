package cinema.showtimes

import cinema.errors.EmptyUpdateRequestException
import cinema.extensions.parseAsUuid
import cinema.movies.MovieId
import java.time.ZonedDateTime
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/showtimes")
class ShowtimeController(
    private val showtimeService: ShowtimeService
) {

    @GetMapping
    fun getAll(
        @RequestParam("movie_id") movieId: MovieId?,
        @RequestParam("date_start_gte") dateStartGte: ZonedDateTime?,
        @RequestParam("date_start_lte") dateStartLte: ZonedDateTime?
    ): List<ShowtimeResponse> =
        showtimeService.findBy(movieId = movieId, startsBefore = dateStartLte, startsAfter = dateStartGte).map { it.toResponse() }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateShowtimeRequest): ShowtimeResponse =
        showtimeService.create(
            movieCatalogId = request.movieCatalogId,
            dateTimeStart = request.dateStart,
            dateTimeEnd = request.dateEnd,
            priceOverride = request.priceOverride
        ).toResponse()

    @PatchMapping("/{showtime_id}")
    fun update(@PathVariable("showtime_id") showtimeId: String, @RequestBody request: ModifyShowtimeRequest): ShowtimeResponse {
        if (request.isEmpty()) throw EmptyUpdateRequestException()
        return showtimeService.update(
            showtimeId = showtimeId.toShowtimeId(),
            movieCatalogId = request.movieCatalogId,
            dateTimeStart = request.dateStart,
            dateTimeEnd = request.dateEnd,
            priceOverride = request.priceOverride
        ).toResponse()
    }

    @DeleteMapping("/{showtime_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("showtime_id") showtimeId: String) {
        showtimeService.delete(showtimeId.toShowtimeId())
    }
}

private fun Showtime.toResponse() = ShowtimeResponse(
    showtimeId = this.id,
    movieId = this.movie.id,
    title = this.movie.title,
    dateStart = this.dateStart,
    dateEnd = this.dateEnd,
    priceOverride = this.priceOverride
)



private fun String.toShowtimeId(): ShowtimeId = ShowtimeId(this.parseAsUuid())

private fun ModifyShowtimeRequest.isEmpty() =
    listOf(this.movieCatalogId, this.dateStart, this.dateEnd, this.priceOverride).all { it == null }
