package cinema.http.controllers

import cinema.http.serializers.SerializableBigDecimal
import cinema.rating.MovieRating
import cinema.rating.UserRating
import kotlinx.serialization.Serializable

@Serializable
data class MovieRatingResponse(
    var average: SerializableBigDecimal,
    var count: Int
)

@Serializable
data class CreateOrUpdateRatingRequest(
    val rating: SerializableBigDecimal
)

@Serializable
data class UserRatingResponse(

    val rating: SerializableBigDecimal,
    val newMovieRating: MovieRatingResponse
)

fun UserRating.toResponse() = UserRatingResponse(
    rating = this.rating,
    newMovieRating = this.newMovieRating.toResponse()
)

fun MovieRating.toResponse() = MovieRatingResponse(
    average = this.average,
    count = this.count
)
