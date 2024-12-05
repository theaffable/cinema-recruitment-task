package cinema.catalog

import cinema.errors.CatalogEntryNotFoundException
import cinema.movies.MovieId
import java.math.BigDecimal
import java.nio.charset.Charset
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Repository

interface MovieCatalogRepository {
    fun findAll(): List<MovieCatalogEntry>
    fun findById(movieCatalogId: MovieCatalogId): MovieCatalogEntry?
    fun contains(movieId: MovieId): Boolean
    fun updateRating(movieCatalogId: MovieCatalogId, rating: BigDecimal): Rating
}

@Repository
class MovieCatalogFileBasedRepository(
    @Value("classpath:movie-catalog.json")
    private val movieCatalogResource: Resource,
    private val serializer: Json
) : MovieCatalogRepository {

    private val movieCatalogEntries = loadCatalogEntries()

    private fun loadCatalogEntries(): Collection<MovieCatalogEntry> =
        movieCatalogResource.getContentAsString(Charset.defaultCharset()).let { serializer.decodeFromString(it) }

    override fun findAll(): List<MovieCatalogEntry> = movieCatalogEntries.toList()

    override fun findById(movieCatalogId: MovieCatalogId): MovieCatalogEntry? = movieCatalogEntries.find { it.id == movieCatalogId }

    override fun contains(movieId: MovieId): Boolean = movieCatalogEntries.any{ it.movieId == movieId }

    override fun updateRating(movieCatalogId: MovieCatalogId, rating: BigDecimal): Rating {
        val currentRating = findById(movieCatalogId)?.rating ?: throw CatalogEntryNotFoundException(movieCatalogId)
        if (currentRating.count == 0) {
            currentRating.count = 1;
            currentRating.average = rating
        } else {
            val count = currentRating.count.toBigDecimal()
            val newAvg = (currentRating.average * count + rating) / count.inc()
            currentRating.count += 1
            currentRating.average = newAvg
        }
        return currentRating
    }
}