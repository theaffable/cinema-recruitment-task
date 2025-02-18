package cinema.http.controllers

import cinema.api.CreateShowtime
import cinema.extensions.asMovieCatalogId
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/showtimes")
class ShowtimeController(
    private val createShowtime: CreateShowtime
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateShowtimeRequest): ShowtimeResponse {
        return createShowtime.forMovieCatalogEntry(
            movieCatalogId = request.movieCatalogId.asMovieCatalogId(),
            dateStart = request.dateStart,
            dateEnd = request.dateEnd,
            price = request.price?.toDomain()
        ).toResponse()
    }
}