package cinema.http.controllers

import cinema.http.serializers.ZonedDateTimeSerializer
import cinema.showtime.Showtime
import java.time.ZonedDateTime
import kotlinx.serialization.Serializable

@Serializable
data class CreateShowtimeRequest(
    val movieCatalogId: String,
    @Serializable(with = ZonedDateTimeSerializer::class) val dateStart: ZonedDateTime,
    @Serializable(with = ZonedDateTimeSerializer::class) val dateEnd: ZonedDateTime,
    val price: PriceRequest?
)

@Serializable
data class ShowtimeResponse(
    val showtimeId: String,
    val movieId: String,
    val title: String,
    @Serializable(with = ZonedDateTimeSerializer::class) val dateStart: ZonedDateTime,
    @Serializable(with = ZonedDateTimeSerializer::class) val dateEnd: ZonedDateTime,
    val price: PriceResponse?
)

fun Showtime.toResponse() = ShowtimeResponse(
    showtimeId = id.value.toString(),
    movieId = movieId.value,
    title = movieTitle,
    dateStart = dateStart,
    dateEnd = dateEnd,
    price = price?.toResponse()
)