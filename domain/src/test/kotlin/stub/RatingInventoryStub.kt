package stub

import cinema.catalog.MovieCatalogId
import cinema.rating.MovieRating
import cinema.spi.RatingInventory
import java.math.BigDecimal

class RatingInventoryStub(
    private val ratings: MutableMap<MovieCatalogId, MutableMap<String, BigDecimal>> = mutableMapOf()
) : RatingInventory {

    override fun createOrUpdateRating(username: String, movieCatalogId: MovieCatalogId, rating: BigDecimal) {
        val movieRatings = ratings.getOrPut(movieCatalogId) { mutableMapOf() }
        movieRatings[username] = rating
    }

    override fun findRating(movieCatalogId: MovieCatalogId): MovieRating {
        var sum = BigDecimal.ZERO
        var count = 0
        ratings[movieCatalogId]!!.values.forEach {
            sum += it
            count += 1
        }
        return MovieRating(
            average = sum.divide(count.toBigDecimal()),
            count = count
        )
    }
}