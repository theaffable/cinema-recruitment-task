package cinema.exceptions

import cinema.catalog.MovieCatalogId
import cinema.movie.MovieId
import cinema.rating.RatingConstraints
import cinema.showtime.ShowtimeId
import java.math.BigDecimal
import kotlin.math.min

class MovieNotFoundException(movieId: MovieId) : Exception("Movie with id=${movieId.value} was not found")

class CatalogEntryNotFoundException(movieCatalogId: MovieCatalogId) : Exception("Catalog entry with id=${movieCatalogId.value} was not found")

class ShowtimeNotFoundException(showtimeId: ShowtimeId) : Exception("Showtime with id=${showtimeId.value} was not found")

class InvalidUuidFormatException(providedId: String) : Exception("Invalid parameter format. Expected valid UUIDv4 but got $providedId")

class EmptyUpdateRequestException : Exception("At least on property needs to be modified")

class RatingValueOutOfRangeException(
    value: BigDecimal,
    constraints: RatingConstraints,
) : Exception("Rating value out of allowed range. Value = $value minInclusive = ${constraints.minIncl}, maxInclusive = ${constraints.maxIncl}")

class HttpClientException(val statusCode: Int) : Exception()
