package cinema.movies

import cinema.catalog.MovieCatalogService
import cinema.omdb.OmdbHttpClient
import cinema.omdb.OmdbMovieResponse
import cinema.parseOmdbDate
import org.springframework.stereotype.Service

interface MovieService {
    fun getMovieDetails(movieId: MovieId): Movie?
}

@Service
class MovieHttpService(
    private val movieCatalogService: MovieCatalogService,
    private val omdbHttpClient: OmdbHttpClient
): MovieService {
    override fun getMovieDetails(movieId: MovieId): Movie? {
        if (!movieCatalogService.isInCatalog(movieId)) return null
        return omdbHttpClient.fetchMovieDetails(movieId).toMovie()
    }
}

private fun OmdbMovieResponse?.toMovie(): Movie? =
    this?.let {
        Movie(
            id = MovieId(value = it.imdbID),
            title = it.title,
            director = it.director,
            writers = it.writer.splitAndTrim(),
            actors = it.actors.splitAndTrim(),
            rated = it.rated,
            description = it.plot,
            releaseDate = parseOmdbDate(it.released),
            imdbRating = it.imdbRating,
            runtime = it.runtime,
            languages = it.language.splitAndTrim()
        )
    }

private fun String.splitAndTrim(separator: String = ",") = this.split(separator).map { it.trim() }