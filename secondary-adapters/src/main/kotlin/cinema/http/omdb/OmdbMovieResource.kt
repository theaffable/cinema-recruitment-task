package cinema.http.omdb

import cinema.movie.Movie
import cinema.movie.MovieId
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OmdbMovieResponse(
    val imdbID: String,
    @SerialName("Title") val title: String,
    @SerialName("Rated") val rated: String,
    @SerialName("Released") val released: String,
    @SerialName("Runtime") val runtime: String,
    @SerialName("Director") val director: String,
    @SerialName("Writer") val writer: String,
    @SerialName("Actors") val actors: String,
    @SerialName("Plot") val plot: String,
    @SerialName("Language") val language: String,
    val imdbRating: String,
)

fun OmdbMovieResponse.toMovie(): Movie = Movie(
    id = MovieId(value = imdbID),
    title = title,
    director = director,
    writers = writer.toStringList(),
    actors = actors.toStringList(),
    rated = rated,
    description = plot,
    releaseDate = LocalDate.parse(released, omdbDateFormatter),
    imdbRating = imdbRating,
    runtime = runtime,
    languages = language.toStringList()
)

private val omdbDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

private fun String.toStringList(separator: String = ",") = this.split(separator).map { it.trim() }
