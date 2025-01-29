package cinema.catalog

import cinema.api.GetCatalogEntries
import cinema.spi.MovieCatalogInventory
import ddd.DomainService

@DomainService
class GetCatalogEntriesUseCase(private val movieCatalogInventory: MovieCatalogInventory): GetCatalogEntries {
    override fun all(): Collection<MovieCatalogEntry> = movieCatalogInventory.getAll()
}