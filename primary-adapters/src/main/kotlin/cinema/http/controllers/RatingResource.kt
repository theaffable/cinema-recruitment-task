package cinema.http.controllers

import cinema.rating.Rating
import java.math.BigDecimal

data class RatingResponse(
    var average: BigDecimal,
    var count: Int
)

data class CreateRatingRequest(
    val rating: BigDecimal
)

fun Rating.toResponse() = RatingResponse(average = this.average, count = this.count)
