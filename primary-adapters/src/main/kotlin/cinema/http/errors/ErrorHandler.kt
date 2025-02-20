package cinema.http.errors

import cinema.exceptions.CatalogEntryNotFoundException
import cinema.exceptions.EmptyUpdateRequestException
import cinema.exceptions.HttpClientException
import cinema.exceptions.InvalidUuidFormatException
import cinema.exceptions.MovieNotFoundException
import cinema.exceptions.RatingValueOutOfRangeException
import cinema.exceptions.ShowtimeNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler
    fun handleMovieNotFound(e: MovieNotFoundException): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)

    @ExceptionHandler
    fun handleCatalogEntryNotFound(e: CatalogEntryNotFoundException): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)

    @ExceptionHandler
    fun handleShowtimeNotFound(e: ShowtimeNotFoundException): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)

    @ExceptionHandler(exception = [InvalidUuidFormatException::class, EmptyUpdateRequestException::class, RatingValueOutOfRangeException::class])
    fun handleClientError(e: Exception): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)

    @ExceptionHandler
    fun handleHttpClientException(e: HttpClientException): ResponseEntity<String> {
        val msg = "Internal http call returned status ${e.statusCode}"
        return ResponseEntity.status(HttpStatus.resolve(e.statusCode) ?: HttpStatus.BAD_REQUEST).body(msg)
    }

    @ExceptionHandler(exception = [NoSuchElementException::class, IllegalArgumentException::class])
    fun handleDatabaseError(e: Exception): ResponseEntity<String> {
        val msg = "Unexpected database error occurred"
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg)
    }
}