package cinema.omdb

import cinema.HttpClientException
import cinema.movies.MovieId
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class OmdbHttpClient(
    @Value("\${omdb.apiKey}")
    private val apiKey: String,
    @Value("\${omdb.baseUrl}")
    private val baseUrl: String,
    private val client: OkHttpClient,
    private val serializer: Json
) {

    fun fetchMovieDetails(movieId: MovieId): OmdbMovieResponse? {
        client.newCall(get(movieId)).execute().use { response ->
            if (!response.isSuccessful) throw HttpClientException(statusCode = response.code)

            return serializer.decodeFromString(response.body!!.string())
        }
    }

    private fun get(movieId: MovieId): Request =
        Request.Builder()
            .url("$baseUrl?apikey=$apiKey&i=${movieId.value}")
            .get()
            .build()
}