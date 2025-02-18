package cinema.exceptions

import cinema.catalog.MovieCatalogId
import cinema.movie.MovieId

class MovieNotFoundException(movieId: MovieId) : Exception("Movie with id=${movieId.value} was not found")

class CatalogEntryNotFoundException(movieCatalogId: MovieCatalogId) : Exception("Catalog entry with id=${movieCatalogId.value} was not found")

class InvalidUuidFormatException(private val providedId: String) : Exception("Invalid parameter format. Expected valid UUIDv4 but got $providedId")

class HttpClientException(val statusCode: Int) : Exception()
