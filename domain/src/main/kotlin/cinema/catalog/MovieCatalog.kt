package cinema.catalog

import java.math.BigDecimal
import kotlin.uuid.Uuid
import cinema.movie.MovieId
import cinema.rating.MovieRating

@JvmInline
value class MovieCatalogId(val value: Uuid)

data class MovieCatalogEntry(
    val id: MovieCatalogId,
    val movieId: MovieId,
    val title: String,
    val price: Price,
    val rating: MovieRating
)

data class Price(
    val amount: BigDecimal,
    val currency: Currency
)

enum class Currency {
    USD, EUR
}