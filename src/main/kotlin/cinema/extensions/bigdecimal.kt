package cinema.extensions

import java.math.BigDecimal

fun BigDecimal.inRange(startIncl: Int, endIncl: Int) = this >= startIncl.toBigDecimal() && this <= endIncl.toBigDecimal()