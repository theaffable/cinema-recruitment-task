package cinema.http.controllers

import cinema.catalog.Currency
import cinema.catalog.Price
import java.math.BigDecimal

data class PriceResource(
    val amount: BigDecimal,
    val currency: Currency
)

fun Price.toResource() = PriceResource(amount = amount, currency = currency)
