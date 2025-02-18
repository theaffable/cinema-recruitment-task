package cinema.spi

import cinema.catalog.MovieCatalogEntry
import cinema.catalog.MovieCatalogId

interface MovieCatalogInventory {
    fun getAll(): Collection<MovieCatalogEntry>
    fun find(movieCatalogId: MovieCatalogId): MovieCatalogEntry?
}
