package cinema.catalog

import cinema.movies.MovieId
import java.nio.charset.Charset
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Repository

@Repository
class MovieCatalogFileBasedRepository(
    @Value("classpath:movie-catalog.json") private val movieCatalogResource: Resource
) : MovieCatalogRepository {

    private val movieCatalogEntries: Collection<MovieCatalogEntry> = loadCatalogEntries()

    private fun loadCatalogEntries(): Collection<MovieCatalogEntry> {
        val jsonString = movieCatalogResource.getContentAsString(Charset.defaultCharset())
        return Json.decodeFromString(jsonString)
    }

    override fun contains(movieId: MovieId): Boolean = movieCatalogEntries.any{ it.movieId == movieId }
}