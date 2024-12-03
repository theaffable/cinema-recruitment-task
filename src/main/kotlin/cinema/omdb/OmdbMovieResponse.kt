package cinema.omdb

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OmdbMovieResponse(
    val imdbID: String,

    @SerialName("Title")
    val title: String,

    @SerialName("Rated")
    val rated: String,

    @SerialName("Released")
    val released: String,

    @SerialName("Runtime")
    val runtime: String,

    @SerialName("Director")
    val director: String,

    @SerialName("Writer")
    val writer: String,

    @SerialName("Actors")
    val actors: String,

    @SerialName("Plot")
    val plot: String,

    @SerialName("Language")
    val language: String,

    val imdbRating: String,
)
