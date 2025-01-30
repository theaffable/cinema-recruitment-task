package cinema.http.controllers

import cinema.catalog.Currency
import cinema.catalog.MovieCatalogEntry
import cinema.catalog.MovieCatalogId
import cinema.catalog.Price
import cinema.http.TestApplication
import cinema.movie.MovieId
import cinema.rating.Rating
import cinema.spi.MovieCatalogInventory
import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.mockk.every
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
import org.springframework.test.web.reactive.server.returnResult
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

    @Test
    fun `should return all movie catalog entries`() {
        // given
        val catalogEntries = listOf(
            MovieCatalogEntry(
                id = MovieCatalogId(value = Uuid.random()),
                movieId = MovieId(value = "morbi"),
                title = "litora",
                price = Price(amount = BigDecimal.ONE, currency = Currency.USD),
                rating = Rating(average = BigDecimal.ONE, count = 7721)
            )
        )
        every { movieCatalogInventory.getAll() } returns catalogEntries

        // when
        val response = client.get().uri("/catalog").exchange()
            .expectStatus().isOk()
            .returnResult<Collection<MovieCatalogEntryResource>>()
            .responseBody
            .blockLast()

        // then
        response.shouldNotBeEmpty()
    }

    @Test
    @WithMockUser(username = "user1", password = "password", roles = ["USER"])
    fun `should create new rating and return updated average and updated rating`() {
        // given
        val catalogEntryId = Uuid.random()
        val requestBody = CreateRatingRequest(rating = BigDecimal.ONE)

        // when and then
        val response =
            client.post().uri("/catalog/$catalogEntryId/rating").body<CreateRatingRequest>(Mono.just(requestBody))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.count").isEqualTo(2)
                .jsonPath("$.average").isEqualTo(BigDecimal("2.5"))
    }
}