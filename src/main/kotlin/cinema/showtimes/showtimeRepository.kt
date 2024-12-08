package cinema.showtimes

import cinema.catalog.Price
import cinema.errors.ShowtimeNotFoundException
import cinema.movies.MovieId
import java.time.ZonedDateTime
import kotlin.uuid.toJavaUuid
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.updateReturning
import org.springframework.stereotype.Repository

interface ShowtimeRepository {
    fun findBy(showtimeId: ShowtimeId): Showtime?
    fun findBy(movieId: MovieId?, startsBefore: ZonedDateTime?, startsAfter: ZonedDateTime?): List<Showtime>
    fun create(showtime: Showtime)
    fun update(
        showtimeId: ShowtimeId,
        movieId: MovieId?,
        movieTitle: String?,
        dateTimeStart: ZonedDateTime?,
        dateTimeEnd: ZonedDateTime?,
        priceOverride: Price?
    ): Showtime
    fun delete(showtimeId: ShowtimeId)
}

@Repository
class ShowtimeDbRepository : ShowtimeRepository {
    override fun findBy(showtimeId: ShowtimeId) = ShowtimeTable.selectAll()
        .where { ShowtimeTable.id eq showtimeId.value.toJavaUuid() }
        .map { it.toShowtime() }
        .firstOrNull()

    override fun findBy(movieId: MovieId?, startsBefore: ZonedDateTime?, startsAfter: ZonedDateTime?): List<Showtime> =
        ShowtimeTable.selectAll().apply {
            movieId?.let { where { ShowtimeTable.movieId eq movieId.value } }
            startsBefore?.let { where { ShowtimeTable.dateStart lessEq startsBefore.toOffsetDateTime() } }
            startsAfter?.let { where { ShowtimeTable.dateStart greaterEq startsAfter.toOffsetDateTime() } }
        }.map { it.toShowtime() }

    override fun create(showtime: Showtime) {
        ShowtimeTable.insert {
            it[id] = showtime.id.value.toJavaUuid()
            it[movieId] = showtime.movieId.value
            it[movieTitle] = showtime.movieTitle
            it[dateStart] = showtime.dateStart.toOffsetDateTime()
            it[dateEnd] = showtime.dateEnd.toOffsetDateTime()
            it[priceOverrideAmount] = showtime.priceOverride?.amount
            it[priceOverrideCurrency] = showtime.priceOverride?.currency
        }
    }

    override fun update(
        showtimeId: ShowtimeId,
        movieId: MovieId?,
        movieTitle: String?,
        dateTimeStart: ZonedDateTime?,
        dateTimeEnd: ZonedDateTime?,
        priceOverride: Price?
    ): Showtime = ShowtimeTable.updateReturning(where = { ShowtimeTable.id eq showtimeId.value.toJavaUuid() }) {
        movieId?.let { movieId -> it[ShowtimeTable.movieId] = movieId.value }
        movieTitle?.let { movieTitle -> it[ShowtimeTable.movieTitle] = movieTitle }
        dateTimeStart?.let { dateTimeStart -> it[dateStart] = dateTimeStart.toOffsetDateTime() }
        dateTimeEnd?.let { dateTimeEnd -> it[dateEnd] = dateTimeEnd.toOffsetDateTime() }
        priceOverride?.let { priceOverride ->
            it[priceOverrideAmount] = priceOverride.amount
            it[priceOverrideCurrency] = priceOverride.currency
        }
    }.map { it.toShowtime() }.firstOrNull()!!

    override fun delete(showtimeId: ShowtimeId) {
        val removedCount = ShowtimeTable.deleteWhere { ShowtimeTable.id eq showtimeId.value.toJavaUuid() }
        if (removedCount == 0) throw ShowtimeNotFoundException(showtimeId)
    }
}