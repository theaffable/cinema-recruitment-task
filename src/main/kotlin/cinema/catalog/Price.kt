package cinema.catalog

import cinema.serializers.BigDecimalSerializer
import java.math.BigDecimal
import kotlinx.serialization.Serializable

@Serializable
data class Price(
    @Serializable(with = BigDecimalSerializer::class)
    val amount: BigDecimal,
    val currency: Currency
)

enum class Currency {
    USD, EUR
}
