package cinema.http.omdb

import cinema.exceptions.HttpClientException
import cinema.movie.Movie
import cinema.movie.MovieId
import cinema.spi.MovieDetailsInventory
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.coroutines.executeAsync

open class OmdbHttpClient(
    private val apiKey: String,
    private val baseUrl: String,
    private val client: OkHttpClient,
    private val serializer: Json
) : MovieDetailsInventory {

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    @CircuitBreaker(name = "omdbApi")
    @Retry(name = "omdbApi")
    override fun fetchMovieDetails(movieId: MovieId): Movie {
        val call = client.newCall(Request.Builder()
            .url("$baseUrl?apikey=$apiKey&i=${movieId.value}")
            .build())
        return runBlocking {
            call.executeAsync().use { response ->
                if (response.isSuccessful) {
                    serializer.decodeFromString<OmdbMovieResponse>(response.body!!.string()).toMovie()
                } else {
                    throw HttpClientException(statusCode = response.code)
                }
            }
        }
    }
}