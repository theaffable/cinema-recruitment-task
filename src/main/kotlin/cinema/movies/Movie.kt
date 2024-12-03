package cinema.movies

import cinema.LocalDateSerializer
import java.time.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: MovieId,
    val title: String,
    val director: String,
    val writers: List<String>,
    val actors: List<String>,
    val rated: String,
    val description: String,
    @Serializable(with = LocalDateSerializer::class)
    val releaseDate: LocalDate,
    val imdbRating: String,
    val runtime: String,
    val languages: List<String>,
)

@Serializable
@JvmInline
value class MovieId(val value: String)
