package cinema.catalog

import cinema.movies.MovieId
import java.math.BigDecimal
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface MovieCatalogService {
    fun findAll(): List<MovieCatalogEntry>
    fun findById(movieCatalogId: MovieCatalogId): MovieCatalogEntry?
    fun isInCatalog(movieId: MovieId): Boolean
    fun createOrUpdateRating(movieCatalogId: MovieCatalogId, user: String, rating: BigDecimal): Rating
}

@Service
@Transactional
class SimpleMovieCatalogService(val movieCatalogRepository: MovieCatalogRepository): MovieCatalogService {
    override fun findAll(): List<MovieCatalogEntry> = movieCatalogRepository.findAll()

    override fun findById(movieCatalogId: MovieCatalogId): MovieCatalogEntry? = movieCatalogRepository.findById(movieCatalogId)

    override fun isInCatalog(movieId: MovieId) = movieCatalogRepository.contains(movieId)

    override fun createOrUpdateRating(movieCatalogId: MovieCatalogId, user: String, rating: BigDecimal): Rating {
        movieCatalogRepository.upsert(movieCatalogId, user, rating)
        return movieCatalogRepository.getAverageRating(movieCatalogId).also {
            movieCatalogRepository.updateRating(
                movieCatalogId = movieCatalogId,
                newRating = it.average,
                newCount = it.count
            )
        }
    }
}