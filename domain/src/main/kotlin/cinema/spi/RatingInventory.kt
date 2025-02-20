package cinema.spi

import cinema.catalog.MovieCatalogId
import cinema.rating.MovieRating
import java.math.BigDecimal

interface RatingInventory {
    fun createOrUpdateRating(username: String, movieCatalogId: MovieCatalogId, rating: BigDecimal)
    fun calculateRatingForCatalogEntry(movieCatalogId: MovieCatalogId): MovieRating
}