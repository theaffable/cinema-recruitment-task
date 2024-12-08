package cinema.catalog

import cinema.errors.RatingValueOutOfRangeException
import cinema.extensions.inRange
import cinema.extensions.parseAsUuid
import java.math.BigDecimal
import java.security.Principal
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/catalog")
class MovieCatalogController(private val movieCatalogService: MovieCatalogService) {

    @GetMapping
    fun getAll(): List<MovieCatalogEntry> = movieCatalogService.findAll()

    @PostMapping("/{movie_catalog_id}/rating")
    @ResponseStatus(HttpStatus.CREATED)
    fun createRating(
        @PathVariable("movie_catalog_id") movieCatalogId: String,
        @RequestBody request: RatingRequest,
        principal: Principal
    ): RatingResponse {
        if (!request.isValid()) throw RatingValueOutOfRangeException(request.rating, min = 1, max = 5)
        return createOrUpdateRating(
            movieCatalogId = movieCatalogId.asMovieCatalogId(),
            username = principal.name,
            rating = request.rating
        )
    }

    @PutMapping("/{movie_catalog_id}/rating")
    fun updateRating(
        @PathVariable("movie_catalog_id") movieCatalogId: String,
        @RequestBody request: RatingRequest,
        principal: Principal
    ): RatingResponse {
        if (!request.isValid()) throw RatingValueOutOfRangeException(request.rating, min = 1, max = 5)
        return createOrUpdateRating(
            movieCatalogId = movieCatalogId.asMovieCatalogId(),
            username = principal.name,
            rating = request.rating
        )
    }

    private fun createOrUpdateRating(movieCatalogId: MovieCatalogId, username: String, rating: BigDecimal): RatingResponse {
        val newRating = movieCatalogService.createOrUpdateRating(movieCatalogId, username, rating)
        return RatingResponse(
            rating = rating,
            newMovieRating = newRating
        )
    }
}

private fun RatingRequest.isValid() = this.rating.inRange(startIncl = 1, endIncl = 5)

private fun String.asMovieCatalogId(): MovieCatalogId = MovieCatalogId(this.parseAsUuid())