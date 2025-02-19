package cinema.sql.showtime

import cinema.catalog.MovieCatalogId
import cinema.catalog.Price
import cinema.movie.MovieId
import cinema.showtime.Showtime
import cinema.showtime.ShowtimeId
import cinema.spi.ShowtimeInventory
import ddd.DomainService
import java.time.ZonedDateTime
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.updateReturning

@DomainService
class ShowtimeRepository : ShowtimeInventory {
    override fun create(
        movieId: MovieId,
        movieTitle: String,
        dateStart: ZonedDateTime,
        dateEnd: ZonedDateTime,
        price: Price
    ): Showtime = transaction {
        ShowtimeTable.insertReturning {
                it[id] = Uuid.random().toJavaUuid()
                it[ShowtimeTable.movieId] = movieId.value
                it[ShowtimeTable.movieTitle] = movieTitle
                it[ShowtimeTable.dateStart] = dateStart.toOffsetDateTime()
                it[ShowtimeTable.dateEnd] = dateEnd.toOffsetDateTime()
                it[priceAmount] = price.amount
                it[priceCurrency] = price.currency
            }.map { it.toShowtime() }
            .first()
    }

    override fun findAll(
        movieId: MovieId?,
        dateStartGte: ZonedDateTime?,
        dateStartLte: ZonedDateTime?
    ): Collection<Showtime> = transaction {
        ShowtimeTable.selectAll().apply {
            movieId?.let { where(ShowtimeTable.movieId eq movieId.value) }
            dateStartGte?.let { where(ShowtimeTable.dateStart greaterEq dateStartGte.toOffsetDateTime() ) }
            dateStartLte?.let { where(ShowtimeTable.dateStart lessEq  dateStartLte.toOffsetDateTime() ) }
        }.map { it.toShowtime() }
    }

    override fun update(
        showtimeId: ShowtimeId,
        movieId: MovieId?,
        movieTitle: String?,
        dateTimeStart: ZonedDateTime?,
        dateTimeEnd: ZonedDateTime?,
        price: Price?
    ): Showtime = transaction {
        ShowtimeTable.updateReturning(where = { ShowtimeTable.id eq showtimeId.value.toJavaUuid() }) {
            movieId?.let { movieId -> it[ShowtimeTable.movieId] = movieId.value }
            movieTitle?.let { movieTitle -> it[ShowtimeTable.movieTitle] = movieTitle }
            dateTimeStart?.let { dateTimeStart -> it[dateStart] = dateTimeStart.toOffsetDateTime() }
            dateTimeEnd?.let { dateTimeEnd -> it[dateEnd] = dateTimeEnd.toOffsetDateTime() }
            price?.let { price ->
                it[priceAmount] = price.amount
                it[priceCurrency] = price.currency
            }
        }.single().toShowtime()
    }
}