package cinema.catalog

import cinema.movies.MovieId
import java.math.BigDecimal
import kotlin.uuid.toJavaUuid
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Repository

interface MovieCatalogRepository {
    fun findAll(): List<MovieCatalogEntry>
    fun findById(movieCatalogId: MovieCatalogId): MovieCatalogEntry?
    fun contains(movieId: MovieId): Boolean
    fun updateRating(movieCatalogId: MovieCatalogId, rating: BigDecimal): Rating
    fun saveAll(entries: Collection<MovieCatalogEntry>)
}

@Repository
class MovieCatalogDbRepository: MovieCatalogRepository {
    override fun findAll(): List<MovieCatalogEntry> = MovieCatalogEntriesTable.selectAll().map { it.toMovieCatalogEntry() }

    override fun findById(movieCatalogId: MovieCatalogId): MovieCatalogEntry? =
        MovieCatalogEntriesTable.selectAll()
            .where { MovieCatalogEntriesTable.id eq movieCatalogId.value.toJavaUuid() }
            .map { it.toMovieCatalogEntry() }
            .firstOrNull()

    override fun contains(movieId: MovieId): Boolean =
        MovieCatalogEntriesTable.selectAll()
            .where { MovieCatalogEntriesTable.movieId eq movieId.value }
            .any()

    override fun updateRating(movieCatalogId: MovieCatalogId, rating: BigDecimal): Rating {
        TODO("Not yet implemented")
    }

    override fun saveAll(entries: Collection<MovieCatalogEntry>) {
        entries.forEach { entry ->
            MovieCatalogEntriesTable.insert {
                it[id] = entry.id.value.toJavaUuid()
                it[movieId] = entry.movieId.value
                it[title] = entry.title
                it[priceAmount] = entry.price.amount
                it[priceCurrency] = entry.price.currency
                it[ratingAvg] = entry.rating.average
                it[ratingCount] = entry.rating.count
            }
        }
    }
}