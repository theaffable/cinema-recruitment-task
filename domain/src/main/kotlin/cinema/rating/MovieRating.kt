package cinema.rating

import java.math.BigDecimal

data class MovieRating(
    var average: BigDecimal,
    var count: Int
)