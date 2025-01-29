package cinema.api

import cinema.catalog.MovieCatalogEntry

interface GetCatalogEntries {
    fun all(): Collection<MovieCatalogEntry>
}
