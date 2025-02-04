package cinema

import cinema.catalog.MovieCatalogId
import cinema.catalog.RateMovieUseCase
import cinema.extensions.parseAsUuid
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.math.BigDecimal
import stub.RatingInventoryStub

class RateMovieTest : FunSpec({
    test("should add new rating for movie") {
        // given
        val movieId = MovieCatalogId("38da7d43-86e2-4b6c-87d0-3a4ca778cb00".parseAsUuid())
        val ratings = mutableMapOf(movieId to mutableMapOf<String, BigDecimal>())
        val ratingInventory = RatingInventoryStub(ratings)
        val rateMovie = RateMovieUseCase(ratingInventory)

        // when
        val userRating = rateMovie.forUser("user", movieId, BigDecimal("5.0"))

        // then
        userRating.rating shouldBe BigDecimal("5.0")
        userRating.newMovieRating.average shouldBe BigDecimal("5.0")
        userRating.newMovieRating.count shouldBe 1
    }

    test("average and count should be updated when new rating is added") {
        // given
        val movieId = MovieCatalogId("38da7d43-86e2-4b6c-87d0-3a4ca778cb00".parseAsUuid())
        val ratings = mutableMapOf(movieId to mutableMapOf("user1" to BigDecimal("1.0")))
        val ratingInventory = RatingInventoryStub(ratings)
        val rateMovie = RateMovieUseCase(ratingInventory)

        // when
        val userRating = rateMovie.forUser("user2", movieId, BigDecimal("5.0"))

        // then
        userRating.rating shouldBe BigDecimal("5.0")
        userRating.newMovieRating.average shouldBe BigDecimal("3.0")
        userRating.newMovieRating.count shouldBe 2
    }
})