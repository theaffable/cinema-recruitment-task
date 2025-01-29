package cinema.http.controllers

import cinema.rating.Rating
import java.math.BigDecimal

data class RatingResource(
    var average: BigDecimal,
    var count: Int
)

fun Rating.toResource() = RatingResource(average = this.average, count = this.count)
