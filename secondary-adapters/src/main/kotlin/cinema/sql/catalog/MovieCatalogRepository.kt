package cinema.sql.catalog

import cinema.catalog.MovieCatalogEntry
import cinema.spi.MovieCatalogInventory
import ddd.DomainService
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

@DomainService
class MovieCatalogRepository: MovieCatalogInventory {
    override fun getAll(): Collection<MovieCatalogEntry> {
        return transaction {
            MovieCatalogEntriesTable.selectAll().map { it.toMovieCatalogEntry() }
        }
    }
}