package cinema.api

import cinema.catalog.MovieCatalogId
import cinema.rating.UserRating
import java.math.BigDecimal

interface RateMovie {
    fun forUser(username: String, movieCatalogId: MovieCatalogId, rating: BigDecimal): UserRating
}
