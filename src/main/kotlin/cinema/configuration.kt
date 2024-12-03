package cinema

import java.io.File
import java.util.concurrent.TimeUnit
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuration(
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
                maxSize = 1024L * 1024L * 50L // 50MiB
            ))
            .build()

    @Bean
    fun configureSerializer(): Json = Json { ignoreUnknownKeys = true }
}