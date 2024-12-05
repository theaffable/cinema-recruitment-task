package cinema.catalog

import cinema.movies.MovieId
import org.springframework.stereotype.Service

interface MovieCatalogService {
    fun findAll(): List<MovieCatalogEntry>
    fun findById(movieCatalogId: MovieCatalogId): MovieCatalogEntry?
    fun isInCatalog(movieId: MovieId): Boolean
}

@Service
class SimpleMovieCatalogService(val movieCatalogRepository: MovieCatalogRepository): MovieCatalogService {
    override fun findAll(): List<MovieCatalogEntry> = movieCatalogRepository.findAll()

    override fun findById(movieCatalogId: MovieCatalogId): MovieCatalogEntry? = movieCatalogRepository.findById(movieCatalogId.value)

    override fun isInCatalog(movieId: MovieId) = movieCatalogRepository.contains(movieId)
}