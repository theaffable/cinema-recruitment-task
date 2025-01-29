package cinema.stub

import cinema.catalog.Currency
import cinema.catalog.MovieCatalogEntry
import cinema.catalog.MovieCatalogId
import cinema.catalog.Price
import cinema.movie.MovieId
import cinema.rating.Rating
import cinema.spi.MovieCatalogInventory
import ddd.DomainService
import java.math.BigDecimal
import kotlin.uuid.Uuid

@DomainService
class MovieCatalogInventoryStub: MovieCatalogInventory {
    override fun getAll(): Collection<MovieCatalogEntry> {
        return listOf(
            MovieCatalogEntry(
                id = MovieCatalogId(value = Uuid.random()),
                movieId = MovieId(value = "eirmod"),
                title = "numquam",
                price = Price(amount = BigDecimal.ONE, currency = Currency.USD),
                rating = Rating(average = BigDecimal.ONE, count = 4602)
            )
        )
    }
}