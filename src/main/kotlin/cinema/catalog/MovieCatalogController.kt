package cinema.catalog

import cinema.errors.RatingValueOutOfRangeException
import cinema.extensions.inRange
import cinema.extensions.parseAsUuid
import java.math.BigDecimal
import java.security.Principal
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

private fun RatingRequest.isValid() = this.rating.inRange(startIncl = 1, endIncl = 5)

private fun String.asMovieCatalogId(): MovieCatalogId = MovieCatalogId(this.parseAsUuid())