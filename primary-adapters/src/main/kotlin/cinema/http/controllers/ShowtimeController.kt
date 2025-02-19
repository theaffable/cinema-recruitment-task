package cinema.http.controllers

import cinema.api.CreateShowtime
import cinema.api.GetShowtimes
import cinema.extensions.asMovieCatalogId
import cinema.movie.MovieId
import java.time.ZonedDateTime
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
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
    private val getShowtimes: GetShowtimes
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
}