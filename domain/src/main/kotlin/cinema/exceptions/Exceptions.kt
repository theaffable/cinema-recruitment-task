package cinema.exceptions

import cinema.catalog.MovieCatalogId
import cinema.movie.MovieId
import cinema.showtime.ShowtimeId

class MovieNotFoundException(movieId: MovieId) : Exception("Movie with id=${movieId.value} was not found")

class CatalogEntryNotFoundException(movieCatalogId: MovieCatalogId) : Exception("Catalog entry with id=${movieCatalogId.value} was not found")

class ShowtimeNotFoundException(showtimeId: ShowtimeId) : Exception("Showtime with id=${showtimeId.value} was not found")

class InvalidUuidFormatException(providedId: String) : Exception("Invalid parameter format. Expected valid UUIDv4 but got $providedId")

class HttpClientException(val statusCode: Int) : Exception()

class EmptyUpdateRequestException : Exception("At least on property needs to be modified")