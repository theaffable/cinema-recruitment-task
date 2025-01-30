package cinema.http.controllers

import cinema.api.GetCatalogEntries
import java.math.BigDecimal
import java.security.Principal
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/catalog")
class MovieCatalogController(
    private val getCatalogEntries: GetCatalogEntries
) {

    @GetMapping
    fun getAll(): Collection<MovieCatalogEntryResource> = getCatalogEntries.all().map { it.toResponse() }

    @PostMapping("/{movie_catalog_id}/rating")
    @ResponseStatus(HttpStatus.CREATED)
    fun createRating(
        @PathVariable("movie_catalog_id") movieCatalogId: String,
        @RequestBody request: CreateRatingRequest,
        principal: Principal
    ): RatingResponse {
        return RatingResponse(average = BigDecimal.ONE, count = 1)
    }

    // @PutMapping("/{movie_catalog_id}/rating")
    // fun updateRating(
    //     @PathVariable("movie_catalog_id") movieCatalogId: String,
    //     @RequestBody request: RatingRequest,
    //     principal: Principal
    // ): RatingResponse {
    //     if (!request.isValid()) throw RatingValueOutOfRangeException(request.rating, min = 1, max = 5)
    //     return createOrUpdateRating(
    //         movieCatalogId = movieCatalogId.asMovieCatalogId(),
    //         username = principal.name,
    //         rating = request.rating
    //     )
    // }
    //
    // private fun createOrUpdateRating(movieCatalogId: MovieCatalogId, username: String, rating: BigDecimal): RatingResponse {
    //     val newRating = movieCatalogService.createOrUpdateRating(movieCatalogId, username, rating)
    //     return RatingResponse(
    //         rating = rating,
    //         newMovieRating = newRating
    //     )
    // }
}

// private fun String.asMovieCatalogId(): MovieCatalogId = MovieCatalogId(this.parseAsUuid())