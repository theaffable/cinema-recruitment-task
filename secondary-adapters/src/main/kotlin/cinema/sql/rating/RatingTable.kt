package cinema.sql.rating

import cinema.rating.MovieRating
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object RatingTable: Table("ratings") {
    val username = varchar("username", length = 128)
    val catalogEntryId = uuid("catalog_entry_id").index()
    val rating = decimal("rating", precision = 3, scale = 2)

    override val primaryKey = PrimaryKey(username, catalogEntryId)
}

