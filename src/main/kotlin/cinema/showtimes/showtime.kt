package cinema.showtimes

import cinema.catalog.Currency
import cinema.serializers.SerializableUuid
import cinema.serializers.ZonedDateTimeSerializer
import cinema.catalog.MovieCatalogId
import cinema.movies.Movie
import cinema.movies.MovieId
import cinema.catalog.Price
import java.time.ZonedDateTime
import kotlin.uuid.toKotlinUuid
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.timestampWithTimeZone

@JvmInline
@Serializable
value class ShowtimeId(val value: SerializableUuid)

@Serializable
data class Showtime(
    val id: ShowtimeId,
    val movieId: MovieId,
    val movieTitle: String,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateStart: ZonedDateTime,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateEnd: ZonedDateTime,
    val priceOverride: Price?
)

object ShowtimeTable: UUIDTable("showtimes") {
    val movieId = varchar("movie_id", length = 128).index()
    val movieTitle = varchar("movie_title", length = 128)
    val dateStart = timestampWithTimeZone("date_start")
    val dateEnd = timestampWithTimeZone("date_end")
    val priceOverrideAmount = decimal("price_override_amount", precision = 5, scale = 2).nullable()
    val priceOverrideCurrency = enumerationByName<Currency>("price_override_currency", length = 32).nullable()
}

@Serializable
data class CreateShowtimeRequest(
    val movieCatalogId: MovieCatalogId,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateStart: ZonedDateTime,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateEnd: ZonedDateTime,
    val priceOverride: Price?
)

@Serializable
data class ModifyShowtimeRequest(
    val movieCatalogId: MovieCatalogId? = null,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateStart: ZonedDateTime? = null,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateEnd: ZonedDateTime? = null,
    val priceOverride: Price? = null
)

@Serializable
data class ShowtimeResponse(
    val showtimeId: ShowtimeId,
    val movieId: MovieId,
    val title: String,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateStart: ZonedDateTime,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val dateEnd: ZonedDateTime,
    val priceOverride: Price?
)

fun ResultRow.toShowtime() =
    Showtime(
        id = ShowtimeId(value = this[ShowtimeTable.id].value.toKotlinUuid()),
        movieId = MovieId(value = this[ShowtimeTable.movieId]),
        movieTitle = this[ShowtimeTable.movieTitle],
        dateStart = this[ShowtimeTable.dateStart].toZonedDateTime(),
        dateEnd = this[ShowtimeTable.dateEnd].toZonedDateTime(),
        priceOverride = toShowtimePrice()
    )

fun ResultRow.toShowtimePrice(): Price? =
    this[ShowtimeTable.priceOverrideAmount]?.let {
        Price(
            amount = it,
            currency = this[ShowtimeTable.priceOverrideCurrency]!!
        )
    }