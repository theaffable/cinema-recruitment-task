package cinema.rating

import java.math.BigDecimal

class RatingConstraints(
    val minIncl: BigDecimal,
    val maxIncl: BigDecimal
) {
    fun met(value: BigDecimal) = value in minIncl..maxIncl
}
