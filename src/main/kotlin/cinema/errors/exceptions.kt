package cinema.errors

import cinema.catalog.MovieCatalogId
import cinema.movies.MovieId
import cinema.showtimes.ShowtimeId
import java.math.BigDecimal

class MovieNotFoundException(movieId: MovieId) : Exception("Movie with id=${movieId.value} was not found")

class CatalogEntryNotFoundException(movieCatalogId: MovieCatalogId) : Exception("Catalog entry with id=${movieCatalogId.value} was not found")

class ShowtimeNotFoundException(showtimeId: ShowtimeId) : Exception("Showtime with id=${showtimeId.value} was not found")

class HttpClientException(val statusCode: Int) : Exception()

class InvalidUuidFormatException(providedId: String) : Exception("Invalid parameter format. Expected valid UUIDv4 but got $providedId")

class EmptyUpdateRequestException() : Exception("At least on property needs to be modified")

class RatingValueOutOfRangeException(actual: BigDecimal, min: Int, max: Int) : Exception("Rating value must be between $min and $max, was $actual")