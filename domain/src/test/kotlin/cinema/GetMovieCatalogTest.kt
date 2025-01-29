package cinema

import cinema.api.GetCatalogEntries
import cinema.catalog.Currency
import cinema.catalog.GetCatalogEntriesUseCase
import cinema.catalog.MovieCatalogEntry
import cinema.catalog.MovieCatalogId
import cinema.catalog.Price
import cinema.movie.MovieId
import cinema.rating.Rating
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import java.math.BigDecimal
import kotlin.uuid.Uuid

class GetMovieCatalogTest : FunSpec({
    test("should return all catalog entries") {
        // given
        val movies = listOf(
            MovieCatalogEntry(
                id = MovieCatalogId(value = Uuid.random()),
                movieId = MovieId(value = "te"),
                title = "posuere",
                price = Price(amount = BigDecimal.ONE, currency = Currency.USD),
                rating = Rating(average = BigDecimal.ONE, count = 8366)
            )
        )
        val getCatalogEntries: GetCatalogEntries = GetCatalogEntriesUseCase { movies }

        // when & then
        getCatalogEntries.all().shouldNotBeEmpty()
    }
})