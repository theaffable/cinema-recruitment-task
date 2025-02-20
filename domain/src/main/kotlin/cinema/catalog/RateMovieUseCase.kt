package cinema.catalog

import cinema.api.RateMovie
import cinema.exceptions.RatingValueOutOfRangeException
import cinema.rating.RatingConstraints
import cinema.rating.UserRating
import cinema.spi.MovieCatalogInventory
import cinema.spi.RatingInventory
import ddd.DomainService
import java.math.BigDecimal

@DomainService
class RateMovieUseCase(
    private val ratingInventory: RatingInventory,
    private val movieCatalogInventory: MovieCatalogInventory,
    private val constraints: RatingConstraints
) : RateMovie {
    override fun forUser(username: String, movieCatalogId: MovieCatalogId, rating: BigDecimal): UserRating {
        if (!constraints.met(rating)) throw RatingValueOutOfRangeException(rating, constraints)

        // two rating entities exist in the system - first one represents rating given by one particular user, second one represents the average rating for a movie
        // first the rating given by the user is saved
        ratingInventory.createOrUpdateRating(username, movieCatalogId, rating)

        // then the movie catalogue is updated with the new average
        val newRating = ratingInventory.calculateRatingForCatalogEntry(movieCatalogId)
        movieCatalogInventory.updateRating(movieCatalogId, newRating)

        return UserRating(
            rating = rating,
            newMovieRating = newRating
        )
    }
}