package cinema.http.controllers

import cinema.api.CreateShowtime
import cinema.api.DeleteShowtime
import cinema.api.GetShowtimes
import cinema.api.UpdateShowtime
import cinema.exceptions.EmptyUpdateRequestException
import cinema.extensions.asMovieCatalogId
import cinema.extensions.asShowtimeId
import cinema.movie.MovieId
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
    private val createShowtime: CreateShowtime,
    private val getShowtimes: GetShowtimes,
    private val updateShowtime: UpdateShowtime,
    private val deleteShowtime: DeleteShowtime
) {

    @GetMapping
    fun getAll(
        @RequestParam("movie_id") movieId: MovieId?,
        @RequestParam("date_start_gte") dateStartGte: ZonedDateTime?,
        @RequestParam("date_start_lte") dateStartLte: ZonedDateTime?
    ): Collection<ShowtimeResponse> = getShowtimes
            .filterBy(movieId = movieId, dateStartGte = dateStartGte, dateStartLte = dateStartLte)
            .map { it.toResponse() }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateShowtimeRequest): ShowtimeResponse = createShowtime.forMovieCatalogEntry(
        movieCatalogId = request.movieCatalogId.asMovieCatalogId(),
        dateStart = request.dateStart,
        dateEnd = request.dateEnd,
        price = request.price?.toDomain()
    ).toResponse()

    @PatchMapping("/{showtime_id}")
    fun update(@PathVariable("showtime_id") showtimeId: String, @RequestBody request: ModifyShowtimeRequest): ShowtimeResponse {
        if (request.isEmpty()) throw EmptyUpdateRequestException()
        return updateShowtime.update(
            showtimeId = showtimeId.asShowtimeId(),
            movieCatalogId = request.movieCatalogId?.asMovieCatalogId(),
            dateTimeStart = request.dateStart,
            dateTimeEnd = request.dateEnd,
            price = request.price?.toDomain()
        ).toResponse()
    }

    @DeleteMapping("/{showtime_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("showtime_id") showtimeId: String) {
        deleteShowtime.delete(showtimeId.asShowtimeId())
    }
}

private fun ModifyShowtimeRequest.isEmpty() =
    listOf(this.movieCatalogId, this.dateStart, this.dateEnd, this.price).all { it == null }