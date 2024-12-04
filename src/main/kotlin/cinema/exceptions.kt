package cinema

import cinema.catalog.MovieCatalogId
import cinema.movies.MovieId

class MovieNotFound(movieId: MovieId) : Exception("Movie with id=${movieId.value} was not found")

class CatalogEntryNotFound(movieCatalogId: MovieCatalogId) : Exception("Catalog entry with id=${movieCatalogId.value} was not found")

class HttpClientException(val statusCode: Int) : Exception()