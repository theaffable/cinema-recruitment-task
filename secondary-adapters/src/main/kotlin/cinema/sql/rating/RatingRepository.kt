package cinema.sql.rating

import cinema.catalog.MovieCatalogId
import cinema.rating.MovieRating
import cinema.spi.RatingInventory
import ddd.DomainService
import java.math.BigDecimal
import kotlin.uuid.toJavaUuid
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.avg
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.upsert

@DomainService
class RatingRepository : RatingInventory {
    override fun createOrUpdateRating(username: String, movieCatalogId: MovieCatalogId, rating: BigDecimal) {
        return transaction {
            RatingTable.upsert(onUpdate = {
                insertValue(RatingTable.rating)
            }) {
                it[this.username] = username
                it[catalogEntryId] = movieCatalogId.value.toJavaUuid()
                it[RatingTable.rating] = rating
            }
        }
    }

    override fun findRating(movieCatalogId: MovieCatalogId): MovieRating {
        return transaction {
            // calculate new average & count
            val averageColumn = RatingTable.rating.avg().alias("avg")
            val countColumn = RatingTable.rating.count().alias("count")
            // return
            RatingTable.select(averageColumn, countColumn).map {
                MovieRating(
                    average = it[averageColumn] ?: BigDecimal.ZERO,
                    count = it[countColumn].toInt()
                )
            }.first()
        }
    }
}