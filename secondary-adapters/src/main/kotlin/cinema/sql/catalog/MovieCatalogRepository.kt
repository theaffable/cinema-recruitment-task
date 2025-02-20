package cinema.sql.catalog

import cinema.catalog.MovieCatalogEntry
import cinema.catalog.MovieCatalogId
import cinema.rating.MovieRating
import cinema.spi.MovieCatalogInventory
import ddd.DomainService
import kotlin.uuid.toJavaUuid
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

@DomainService
class MovieCatalogRepository: MovieCatalogInventory {
    override fun getAll(): Collection<MovieCatalogEntry> {
        return transaction {
            MovieCatalogEntriesTable.selectAll().map { it.toMovieCatalogEntry() }
        }
    }

    override fun find(movieCatalogId: MovieCatalogId): MovieCatalogEntry? {
        return transaction {
            MovieCatalogEntriesTable.selectAll()
                .where { MovieCatalogEntriesTable.id eq movieCatalogId.value.toJavaUuid() }
                .map { it.toMovieCatalogEntry() }
                .single()
        }
    }

    override fun updateRating(movieCatalogId: MovieCatalogId, rating: MovieRating) {
        transaction {
            MovieCatalogEntriesTable.update(where = { MovieCatalogEntriesTable.id eq movieCatalogId.value.toJavaUuid() }) {
                it[ratingAvg] = rating.average
                it[ratingCount] = rating.count
            }
        }
    }
}