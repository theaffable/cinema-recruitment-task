package cinema.sql.showtime

import cinema.catalog.Price
import cinema.movie.MovieId
import cinema.showtime.Showtime
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

@DomainService
class ShowtimeRepository : ShowtimeInventory {
    override fun create(
        movieId: MovieId,
        movieTitle: String,
        dateStart: ZonedDateTime,
        dateEnd: ZonedDateTime,
        price: Price
    ): Showtime {
        return transaction {
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
    }

    override fun findAll(
        movieId: MovieId?,
        dateStartGte: ZonedDateTime?,
        dateStartLte: ZonedDateTime?
    ): Collection<Showtime> {
        return transaction {
            ShowtimeTable.selectAll().apply {
                movieId?.let { where(ShowtimeTable.movieId eq movieId.value) }
                dateStartGte?.let { where(ShowtimeTable.dateStart greaterEq dateStartGte.toOffsetDateTime() ) }
                dateStartLte?.let { where(ShowtimeTable.dateStart lessEq  dateStartLte.toOffsetDateTime() ) }
            }.map { it.toShowtime() }
        }
    }
}