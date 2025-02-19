package cinema.catalog

import cinema.api.RateMovie
import cinema.rating.UserRating
import cinema.spi.MovieCatalogInventory
import cinema.spi.RatingInventory
import ddd.DomainService
import java.math.BigDecimal

@DomainService
class RateMovieUseCase(
    private val ratingInventory: RatingInventory,
    private val movieCatalogInventory: MovieCatalogInventory
) : RateMovie {
    override fun forUser(username: String, movieCatalogId: MovieCatalogId, rating: BigDecimal): UserRating {
        // two rating entities exist in the system - first one represents rating given by one particular user, second one represents the average rating for a movie
        // first the rating given by the user is saved
        ratingInventory.createOrUpdateRating(username, movieCatalogId, rating)

        // then the movie catalogue is updated with the new average
        val newRating = ratingInventory.findRating(movieCatalogId)
        movieCatalogInventory.updateRating(movieCatalogId, newRating)

        return UserRating(
            rating = rating,
            newMovieRating = newRating
        )
    }
}