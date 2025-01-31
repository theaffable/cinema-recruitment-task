package cinema.http.controllers

import cinema.catalog.MovieCatalogEntry

data class MovieCatalogEntryResource(
    val id: String,
    val movieId: String,
    val title: String,
    val price: PriceResponse,
    val rating: MovieRatingResponse
)

fun MovieCatalogEntry.toResponse() = MovieCatalogEntryResource(
    id = id.value.toString(),
    movieId = movieId.value,
    title = title,
    price = price.toResponse(),
    rating = rating.toResponse()

)