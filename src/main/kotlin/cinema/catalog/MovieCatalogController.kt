package cinema.catalog

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MovieCatalogController(private val movieCatalogService: MovieCatalogService) {

    @GetMapping("/catalog")
    fun getAll(): List<MovieCatalogEntry> = movieCatalogService.findAll()
}