package cinema.http.controllers

import cinema.catalog.Currency
import cinema.catalog.Price
import cinema.http.serializers.SerializableBigDecimal
import kotlinx.serialization.Serializable

@Serializable
data class PriceResponse(
    val amount: SerializableBigDecimal,
    val currency: Currency
)

@Serializable
data class PriceRequest(
    val amount: SerializableBigDecimal,
    val currency: Currency
)

fun Price.toResponse() = PriceResponse(amount = amount, currency = currency)

fun PriceRequest.toDomain() = Price(amount = amount, currency = currency)
