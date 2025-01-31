package cinema.catalog

import cinema.api.RateMovie
import cinema.rating.UserRating
import cinema.spi.RatingInventory
import ddd.DomainService
import java.math.BigDecimal

@DomainService
class RateMovieUseCase(private val ratingInventory: RatingInventory) : RateMovie {
    override fun forUser(username: String, movieCatalogId: MovieCatalogId, rating: BigDecimal): UserRating {
        ratingInventory.createOrUpdateRating(username, movieCatalogId, rating)
        val newMovieRating = ratingInventory.findRating(movieCatalogId)
        return UserRating(
            rating = rating,
            newMovieRating = newMovieRating
        )
    }
}