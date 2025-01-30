package cinema

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

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