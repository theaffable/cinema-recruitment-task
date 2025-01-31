package cinema.extensions

import cinema.catalog.MovieCatalogId
import cinema.exceptions.InvalidUuidFormatException
import kotlin.jvm.Throws
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