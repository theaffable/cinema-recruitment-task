package cinema.http

import ddd.DomainService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.context.annotation.Primary
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.http.converter.json.KotlinSerializationJsonHttpMessageConverter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

// Application and configuration classes defined below are a fix to quite peculiar problem:
// controller classes are separated from application main class,
// former are located in primary-adapters module while latter is located in application module
// This setup causes problem with spring boot context initialization when running SpringBootTests.
// Defining an application class for test purposes was the simplest solution I've found.
@SpringBootApplication
class TestApplication

fun main(args: Array<String>) {
    runApplication<TestApplication>(*args)
}

@Configuration
@ComponentScan(
    basePackages = ["cinema"],
    includeFilters = [ComponentScan.Filter(type = FilterType.ANNOTATION, classes = [DomainService::class])]
)
class TestDomainConfiguration

@Configuration
class SerializerConfiguration {
    @OptIn(ExperimentalSerializationApi::class)
    @Bean
    @Primary
    fun configureSnakeCaseSerializer() = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        namingStrategy = JsonNamingStrategy.SnakeCase
    }

    @Bean
    fun messageConverter(): KotlinSerializationJsonHttpMessageConverter {
        return KotlinSerializationJsonHttpMessageConverter(configureSnakeCaseSerializer())
    }
}

@Configuration
@EnableWebSecurity
class SecurityConfiguration {
    @Bean
    fun userDetailsService(): UserDetailsService {
        // should use a dedicated password encoder in production
        val users = User.withDefaultPasswordEncoder()
        return InMemoryUserDetailsManager().apply {
            for (i in 1..10) {
                createUser(users.username("user$i").password("password").roles("USER").build())
            }
            createUser(users.username("admin").password("password").roles("USER", "ADMIN").build())
        }
    }

    @Bean
    @Order(1)
    fun showtimesFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            securityMatcher("/showtimes/**")
            authorizeHttpRequests {
                authorize(HttpMethod.POST, "/showtimes", hasRole("ADMIN"))
                authorize(HttpMethod.PATCH, "/showtimes/**", hasRole("ADMIN"))
                authorize(HttpMethod.DELETE, "/showtimes/**", hasRole("ADMIN"))
                authorize(HttpMethod.GET, "/showtimes", permitAll)
                authorize(anyRequest, denyAll)
            }
            httpBasic {  }
            csrf {
                disable()
            }
        }
        return http.build()
    }


    @Bean
    fun ratingFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.invoke {
            securityMatcher("/catalog/*/rating")
            authorizeHttpRequests {
                authorize(HttpMethod.POST, "/catalog/*/rating", hasRole("USER"))
                authorize(HttpMethod.PUT, "/catalog/*/rating", hasRole("USER"))
                authorize(anyRequest, denyAll)
            }
            httpBasic {  }
            csrf {
                disable()
            }
        }
        return http.build()
    }
}