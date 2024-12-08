package cinema.catalog

import cinema.serializers.BigDecimalSerializer
import java.math.BigDecimal
import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    @Serializable(with = BigDecimalSerializer::class)
    var average: BigDecimal,
    var count: Int
)

@Serializable
data class RatingRequest(
    @Serializable(with = BigDecimalSerializer::class)
    val rating: BigDecimal,
)

@Serializable
data class RatingResponse(
    @Serializable(with = BigDecimalSerializer::class)
    val rating: BigDecimal,
    val newMovieRating: Rating
)
