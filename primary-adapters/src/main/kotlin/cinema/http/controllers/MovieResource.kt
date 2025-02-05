package cinema.http.controllers

import cinema.http.serializers.LocalDateSerializer
import cinema.movie.Movie
import cinema.movie.MovieId
import java.time.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val id: String,
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

fun Movie.toResponse() = MovieResponse(
    id = id.value,
    title = title,
    director = director,
    writers = writers,
    actors = actors,
    rated = rated,
    description = description,
    releaseDate = releaseDate,
    imdbRating = imdbRating,
    runtime = runtime,
    languages = languages
)