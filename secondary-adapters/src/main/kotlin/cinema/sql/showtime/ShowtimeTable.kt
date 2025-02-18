package cinema.sql.showtime

import cinema.catalog.Currency
import cinema.catalog.Price
import cinema.movie.MovieId
import cinema.showtime.Showtime
import cinema.showtime.ShowtimeId
import kotlin.uuid.toKotlinUuid
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.timestampWithTimeZone

object ShowtimeTable: UUIDTable("showtimes") {
    val movieId = varchar("movie_id", length = 128).index()
    val movieTitle = varchar("movie_title", length = 128)
    val dateStart = timestampWithTimeZone("date_start")
    val dateEnd = timestampWithTimeZone("date_end")
    val priceAmount = decimal("price_amount", precision = 5, scale = 2).nullable()
    val priceCurrency = enumerationByName<Currency>("price_currency", length = 32).nullable()
}

fun ResultRow.toShowtime() =
    Showtime(
        id = ShowtimeId(value = this[ShowtimeTable.id].value.toKotlinUuid()),
        movieId = MovieId(value = this[ShowtimeTable.movieId]),
        movieTitle = this[ShowtimeTable.movieTitle],
        dateStart = this[ShowtimeTable.dateStart].toZonedDateTime(),
        dateEnd = this[ShowtimeTable.dateEnd].toZonedDateTime(),
        price = toShowtimePrice()
    )

fun ResultRow.toShowtimePrice(): Price? =
    this[ShowtimeTable.priceAmount]?.let {
        Price(
            amount = it,
            currency = this[ShowtimeTable.priceCurrency]!!
        )
    }