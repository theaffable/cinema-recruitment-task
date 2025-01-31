package cinema.spi

import cinema.catalog.MovieCatalogEntry

interface MovieCatalogInventory {
    fun getAll(): Collection<MovieCatalogEntry>
}
