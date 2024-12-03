package cinema

import cinema.movies.MovieId

class MovieNotFound(movieId: MovieId) : Exception("Movie with id=${movieId.value} was not found")

class HttpClientException(val statusCode: Int) : Exception()