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
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.mockk.every
import java.math.BigDecimal
import kotlin.uuid.Uuid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult

@SpringBootTest(
    classes = [TestApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWebTestClient
class MovieCatalogControllerTest : FunSpec() {
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @Autowired
    private lateinit var client: WebTestClient

    @MockkBean
    private lateinit var movieCatalogInventory: MovieCatalogInventory

    init {
        test("should return all movie catalog entries") {
            // given
            val catalogEntries = listOf(MovieCatalogEntry(
                id = MovieCatalogId(value = Uuid.random()),
                movieId = MovieId(value = "morbi"),
                title = "litora",
                price = Price(amount = BigDecimal.ONE, currency = Currency.USD),
                rating = Rating(average = BigDecimal.ONE, count = 7721)
            ))
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
    }
}