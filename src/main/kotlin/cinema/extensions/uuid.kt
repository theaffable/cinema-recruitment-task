package cinema.extensions

import cinema.errors.InvalidUuidFormatException
import kotlin.uuid.Uuid

fun String.parseAsUuid(): Uuid {
    return try {
        Uuid.parse(this)
    } catch (e: IllegalArgumentException) {
        throw InvalidUuidFormatException(this)
    }
}