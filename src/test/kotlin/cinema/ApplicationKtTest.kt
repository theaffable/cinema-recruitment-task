package cinema

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@TestPropertySource(properties = ["omdb.apiKey = 100%legitKey"])
class ApplicationKtTest {
    @Test
    fun applicationShouldStart() {}
}