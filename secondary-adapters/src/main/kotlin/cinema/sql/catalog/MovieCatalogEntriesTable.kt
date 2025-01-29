package cinema.sql.catalog

import cinema.catalog.Currency
import cinema.catalog.MovieCatalogEntry
import cinema.catalog.MovieCatalogId
import cinema.catalog.Price
import cinema.movie.MovieId
import cinema.rating.Rating
import kotlin.uuid.toKotlinUuid
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow

object MovieCatalogEntriesTable: UUIDTable("catalog") {
    val movieId = varchar("movie_id", length = 128).index()
    val title = varchar("title", length = 128)
    val priceAmount = decimal("price_amount", precision = 5, scale = 2)
    val priceCurrency = enumerationByName<Currency>("price_currency", length = 32)
    val ratingCount = integer("rating_count")
    val ratingAvg = decimal("rating_avg", precision = 3, scale = 2)
}

fun ResultRow.toMovieCatalogEntry() =
    MovieCatalogEntry(
        id = MovieCatalogId(this[MovieCatalogEntriesTable.id].value.toKotlinUuid()),
        movieId = MovieId(value = this[MovieCatalogEntriesTable.movieId]),
        title = this[MovieCatalogEntriesTable.title],
        price = Price(
            amount = this[MovieCatalogEntriesTable.priceAmount],
            currency = this[MovieCatalogEntriesTable.priceCurrency]
        ),
        rating = Rating(
            average = this[MovieCatalogEntriesTable.ratingAvg],
            count = this[MovieCatalogEntriesTable.ratingCount]
        )
    )

