package cinema.catalog

import cinema.movies.MovieId
import java.math.BigDecimal
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface MovieCatalogService {
    fun findAll(): List<MovieCatalogEntry>
    fun findById(movieCatalogId: MovieCatalogId): MovieCatalogEntry?
    fun isInCatalog(movieId: MovieId): Boolean
    fun rate(movieCatalogId: MovieCatalogId, rating: BigDecimal): Rating
}

@Service
class SimpleMovieCatalogService(val movieCatalogRepository: MovieCatalogRepository): MovieCatalogService {
    @Transactional
    override fun findAll(): List<MovieCatalogEntry> = movieCatalogRepository.findAll()

    override fun findById(movieCatalogId: MovieCatalogId): MovieCatalogEntry? = movieCatalogRepository.findById(movieCatalogId)

    override fun isInCatalog(movieId: MovieId) = movieCatalogRepository.contains(movieId)

    override fun rate(movieCatalogId: MovieCatalogId, rating: BigDecimal): Rating =
        movieCatalogRepository.updateRating(movieCatalogId, rating)
}