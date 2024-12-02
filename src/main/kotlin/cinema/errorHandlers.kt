package cinema

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler
    fun handleMovieNotFound(error: MovieNotFound): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.message)
}