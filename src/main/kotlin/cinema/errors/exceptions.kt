package cinema.errors

import cinema.catalog.MovieCatalogId
import cinema.movies.MovieId
import cinema.showtimes.ShowtimeId

class MovieNotFound(movieId: MovieId) : Exception("Movie with id=${movieId.value} was not found")

class CatalogEntryNotFound(movieCatalogId: MovieCatalogId) : Exception("Catalog entry with id=${movieCatalogId.value} was not found")

class ShowtimeNotFound(showtimeId: ShowtimeId) : Exception("Showtime with id=${showtimeId.value} was not found")

class HttpClientException(val statusCode: Int) : Exception()

class InvalidUuidFormat(val providedId: String) : Exception("Invalid parameter format. Expected valid UUIDv4 but got $providedId")