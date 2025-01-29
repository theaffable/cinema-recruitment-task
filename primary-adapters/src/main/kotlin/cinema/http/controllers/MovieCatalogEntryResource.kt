package cinema.http.controllers

import cinema.catalog.MovieCatalogEntry

data class MovieCatalogEntryResource(
    val id: String,
    val movieId: String,
    val title: String,
    val price: PriceResource,
    val rating: RatingResource
)

fun MovieCatalogEntry.toResource() = MovieCatalogEntryResource(
    id = id.value.toString(),
    movieId = movieId.value,
    title = title,
    price = price.toResource(),
    rating = rating.toResource()

)