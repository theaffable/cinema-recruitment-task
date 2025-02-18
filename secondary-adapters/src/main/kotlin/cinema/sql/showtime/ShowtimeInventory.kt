package cinema.sql.showtime

import cinema.catalog.Price
import cinema.movie.MovieId
import cinema.showtime.Showtime
import cinema.spi.ShowtimeInventory
import ddd.DomainService
import java.time.ZonedDateTime
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.transactions.transaction

@DomainService
class ShowtimeInventory : ShowtimeInventory {
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
}