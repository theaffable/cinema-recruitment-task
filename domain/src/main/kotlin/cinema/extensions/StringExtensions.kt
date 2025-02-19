package cinema.extensions

import cinema.catalog.MovieCatalogId
import cinema.exceptions.InvalidUuidFormatException
import cinema.showtime.ShowtimeId
import kotlin.uuid.Uuid

@Throws(InvalidUuidFormatException::class)
fun String.parseAsUuid(): Uuid {
    return try {
        Uuid.parse(this)
    } catch (e: IllegalArgumentException) {
        throw InvalidUuidFormatException(this)
    }
}

@Throws(InvalidUuidFormatException::class)
fun String.asMovieCatalogId() = MovieCatalogId(value = this.parseAsUuid())

@Throws(InvalidUuidFormatException::class)
fun String.asShowtimeId() = ShowtimeId(value = this.parseAsUuid())