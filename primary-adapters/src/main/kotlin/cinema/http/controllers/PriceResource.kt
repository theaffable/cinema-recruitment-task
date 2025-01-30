package cinema.http.controllers

import cinema.catalog.Currency
import cinema.catalog.Price
import java.math.BigDecimal

data class PriceResponse(
    val amount: BigDecimal,
    val currency: Currency
)

fun Price.toResponse() = PriceResponse(amount = amount, currency = currency)
