package cinema.http.controllers

import cinema.catalog.Currency
import cinema.catalog.MovieCatalogEntry
import cinema.catalog.MovieCatalogId
import cinema.catalog.Price
import cinema.http.TestApplication
import cinema.movie.MovieId
import cinema.rating.MovieRating
import cinema.rating.UserRating
import cinema.spi.MovieCatalogInventory
import cinema.spi.RatingInventory
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.justRun
import java.math.BigDecimal
import kotlin.uuid.Uuid
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.body
import reactor.core.publisher.Mono

@SpringBootTest(
    classes = [TestApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWebTestClient
// This annotation makes it so no real web server is started but spring context is still loaded thanks to @SpringBootTest
// @WithMockUser won't work without @AutoConfigureMockMvc
@AutoConfigureMockMvc
class MovieCatalogControllerTest {
    @Autowired
    private lateinit var client: WebTestClient

    @MockkBean
    private lateinit var movieCatalogInventory: MovieCatalogInventory

    @MockkBean
    private lateinit var ratingInventory: RatingInventory

    @Test
    fun `should return all movie catalog entries`() {
        // given
        val catalogEntries = listOf(
            MovieCatalogEntry(
                id = MovieCatalogId(value = Uuid.random()),
                movieId = MovieId(value = "morbi"),
                title = "litora",
                price = Price(amount = BigDecimal.ONE, currency = Currency.USD),
                rating = MovieRating(average = BigDecimal.ONE, count = 7721)
            )
        )
        every { movieCatalogInventory.getAll() } returns catalogEntries

        // when && then
        client.get().uri("/catalog").exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$").isArray()
            .jsonPath("$").isNotEmpty()
    }

    @Test
    @WithMockUser(username = "user1", password = "password", roles = ["USER"])
    fun `should create new rating and return updated average and updated rating`() {
        // given
        val requestBody = CreateOrUpdateRatingRequest(rating = BigDecimal.ONE)
        justRun { ratingInventory.createOrUpdateRating(any(), any(), any()) }
        every { ratingInventory.findRating(any()) }.returns(
            MovieRating(
                average = BigDecimal("3.0"),
                count = 2
            )
        )

        // when && then
        client.post().uri("/catalog/${Uuid.random()}/rating").body<CreateOrUpdateRatingRequest>(Mono.just(requestBody))
            .exchange()
            .expectStatus().isCreated()
            .expectBody()
            .jsonPath("$.rating").isEqualTo("1.00")
            .jsonPath("$.new_movie_rating.average").isEqualTo(BigDecimal("3.00"))
            .jsonPath("$.new_movie_rating.count").isEqualTo(2)
    }
}