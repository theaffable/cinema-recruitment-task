package cinema

import cinema.http.omdb.OmdbHttpClient
import java.io.File
import java.util.concurrent.TimeUnit
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.converter.json.KotlinSerializationJsonHttpMessageConverter

@Configuration
class RestConfiguration(
    @Value("\${omdb.cacheDir}")
    private val cacheDir: String
) {
    @Bean
    fun configureHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .callTimeout(10, TimeUnit.SECONDS)
            .cache(Cache(
                directory = File(cacheDir),
                maxSize = 1024L * 1024L * 50L))  // 50MiB
            .build()

    @Bean
    fun configureOmdbHttpClient(@Value("apiKey") apiKey: String, @Value("baseUrl") baseUrl: String, client: OkHttpClient, @Qualifier("omdbSerializer") serializer: Json): OmdbHttpClient = OmdbHttpClient(
        apiKey = apiKey,
        baseUrl = baseUrl,
        client = client,
        serializer = serializer
    )

    @OptIn(ExperimentalSerializationApi::class)
    @Bean
    @Primary
    fun configureSnakeCaseSerializer() = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        namingStrategy = JsonNamingStrategy.SnakeCase
    }

    @Bean
    fun configureSpringSerializer(): KotlinSerializationJsonHttpMessageConverter =
        KotlinSerializationJsonHttpMessageConverter(configureSnakeCaseSerializer())

    @Bean(name = ["omdbSerializer"])
    fun configureOmdbSerializer(): Json = Json {
        ignoreUnknownKeys = true
    }
}