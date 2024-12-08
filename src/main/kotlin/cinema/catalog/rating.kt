package cinema.catalog

import cinema.serializers.BigDecimalSerializer
import java.math.BigDecimal
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

@Serializable
data class Rating(
    @Serializable(with = BigDecimalSerializer::class)
    var average: BigDecimal,
    var count: Int
)

object RatingTable: Table("ratings") {
    val username = varchar("username", length = 128)
    val catalogEntryId = uuid("catalog_entry_id").index()
    val rating = decimal("rating", precision = 3, scale = 2)

    override val primaryKey = PrimaryKey(username, catalogEntryId)
}

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
