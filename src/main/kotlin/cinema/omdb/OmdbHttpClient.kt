package cinema.omdb

import cinema.errors.HttpClientException
import cinema.movies.MovieId
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.coroutines.executeAsync
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class OmdbHttpClient(
    @Value("\${omdb.apiKey}")
    private val apiKey: String,
    @Value("\${omdb.baseUrl}")
    private val baseUrl: String,
    private val client: OkHttpClient,
    @Qualifier(value = "omdbSerializer")
    private val serializer: Json
) {

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    @CircuitBreaker(name = "omdbApi")
    @Retry(name = "omdbApi")
    suspend fun fetchMovieDetails(movieId: MovieId): OmdbMovieResponse? {
        val call = client.newCall(get(movieId))
        return call.executeAsync().use { response ->
            withContext(Dispatchers.IO) { // switches given coroutine to a thread pool optimized for IO operations
                if (response.isSuccessful) serializer.decodeFromString<OmdbMovieResponse>(response.body!!.string()) else throw HttpClientException(statusCode = response.code)
            }
        }
    }

    private fun get(movieId: MovieId): Request =
        Request.Builder()
            .url("$baseUrl?apikey=$apiKey&i=${movieId.value}")
            .get()
            .build()
}