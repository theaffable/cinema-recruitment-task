package cinema.catalog

import cinema.movies.MovieId
import java.nio.charset.Charset
import kotlin.uuid.Uuid
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Repository

interface MovieCatalogRepository {
    fun findAll(): List<MovieCatalogEntry>
    fun findById(id: Uuid): MovieCatalogEntry?
    fun contains(movieId: MovieId): Boolean
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

    override fun findById(id: Uuid): MovieCatalogEntry? = movieCatalogEntries.find { it.id.value == id }

    override fun contains(movieId: MovieId): Boolean = movieCatalogEntries.any{ it.movieId == movieId }
}