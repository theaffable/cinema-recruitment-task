package cinema

import java.io.File
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit
import kotlin.uuid.Uuid
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.convert.converter.Converter
import org.springframework.http.converter.json.KotlinSerializationJsonHttpMessageConverter

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

    // @Bean
    // fun configureUuidConverter(): UuidConverter {
    //     return object : UuidConverter {
    //         override fun convert(source: String): Uuid {
    //             return Uuid.parse(source)
    //         }
    //     }
    // }
}

// // this interface is required because of runtime type erasure
// interface UuidConverter : Converter<String, Uuid>