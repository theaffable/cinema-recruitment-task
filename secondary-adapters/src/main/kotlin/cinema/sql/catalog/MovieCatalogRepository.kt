package cinema.sql.catalog

import cinema.catalog.MovieCatalogEntry
import cinema.catalog.MovieCatalogId
import cinema.spi.MovieCatalogInventory
import ddd.DomainService
import kotlin.uuid.toJavaUuid
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

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
                .firstOrNull()
        }
    }
}