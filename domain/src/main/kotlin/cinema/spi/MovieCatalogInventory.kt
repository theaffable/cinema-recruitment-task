package cinema.spi

import cinema.catalog.MovieCatalogEntry
import cinema.catalog.MovieCatalogId
import cinema.rating.MovieRating

interface MovieCatalogInventory {
    fun getAll(): Collection<MovieCatalogEntry>
    fun find(movieCatalogId: MovieCatalogId): MovieCatalogEntry?
    fun updateRating(movieCatalogId: MovieCatalogId, rating: MovieRating)
}
