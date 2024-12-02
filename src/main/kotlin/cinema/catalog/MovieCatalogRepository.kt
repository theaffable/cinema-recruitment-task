package cinema.catalog

import cinema.movies.MovieId

interface MovieCatalogRepository {
    fun contains(movieId: MovieId): Boolean
}