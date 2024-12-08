package cinema.catalog

import cinema.movies.MovieId
import java.math.BigDecimal
import kotlin.uuid.toJavaUuid
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.avg
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.updateReturning
import org.jetbrains.exposed.sql.upsert
import org.springframework.stereotype.Repository

interface MovieCatalogRepository {
    fun findAll(): List<MovieCatalogEntry>
    fun findById(movieCatalogId: MovieCatalogId): MovieCatalogEntry?
    fun contains(movieId: MovieId): Boolean
    fun upsert(movieCatalogId: MovieCatalogId, user: String, rating: BigDecimal)
    fun saveAll(entries: Collection<MovieCatalogEntry>)
    fun getAverageRating(movieCatalogId: MovieCatalogId): Rating
    fun updateRating(movieCatalogId: MovieCatalogId, newRating: BigDecimal, newCount: Int)
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

    override fun upsert(movieCatalogId: MovieCatalogId, user: String, rating: BigDecimal) {
        RatingTable.upsert(onUpdate = {
            insertValue(RatingTable.rating)
        }) {
            it[username] = user
            it[catalogEntryId] = movieCatalogId.value.toJavaUuid()
            it[RatingTable.rating] = rating
        }
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

    override fun getAverageRating(movieCatalogId: MovieCatalogId): Rating {
        val averageColumn = RatingTable.rating.avg().alias("avg")
        val countColumn = RatingTable.rating.count().alias("count")
        return RatingTable.select(averageColumn, countColumn).map {
            Rating(
                average = it[averageColumn] ?: BigDecimal.ZERO,
                count = it[countColumn].toInt()
            )
        }.first()
    }

    override fun updateRating(movieCatalogId: MovieCatalogId, newRating: BigDecimal, newCount: Int) {
        MovieCatalogEntriesTable.updateReturning(where = { MovieCatalogEntriesTable.id eq movieCatalogId.value.toJavaUuid() }) {
            it[ratingAvg] = newRating
            it[ratingCount] = newCount
        }
    }
}