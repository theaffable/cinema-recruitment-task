package cinema.catalog

import cinema.movies.MovieId
import org.springframework.stereotype.Service

@Service
class MovieCatalogService(val movieCatalogRepository: MovieCatalogRepository) {
    fun isInCatalog(movieId: MovieId) = movieCatalogRepository.contains(movieId)
}