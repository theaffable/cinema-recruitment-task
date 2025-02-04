package cinema.spi

import cinema.catalog.MovieCatalogEntry

fun interface MovieCatalogInventory {
    fun getAll(): Collection<MovieCatalogEntry>
}
