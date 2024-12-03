package cinema.catalog

import cinema.movies.MovieId
import org.springframework.stereotype.Service

interface MovieCatalogService {
    fun isInCatalog(movieId: MovieId): Boolean
}

@Service
class SimpleMovieCatalogService(val movieCatalogRepository: MovieCatalogRepository): MovieCatalogService {
    override fun isInCatalog(movieId: MovieId) = movieCatalogRepository.contains(movieId)
}