package cinema.movie

import java.time.LocalDate

data class Movie(
    val id: MovieId,
    val title: String,
    val director: String,
    val writers: List<String>,
    val actors: List<String>,
    val rated: String,
    val description: String,
    val releaseDate: LocalDate,
    val imdbRating: String,
    val runtime: String,
    val languages: List<String>,
)

@JvmInline
value class MovieId(val value: String)