package cinema.movies

import java.time.LocalDate
import kotlinx.serialization.Serializable

data class Movie(
    val id: MovieId,
    val title: String,
    val description: String,
    val releaseDate: LocalDate,
    val imdbRating: String,
    val runtime: String
)

@Serializable
@JvmInline
value class MovieId(val value: String)
