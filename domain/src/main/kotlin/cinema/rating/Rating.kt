package cinema.rating

import java.math.BigDecimal

data class Rating(
    var average: BigDecimal,
    var count: Int
)