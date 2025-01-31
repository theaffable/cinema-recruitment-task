package cinema.rating

import java.math.BigDecimal

data class UserRating(
    val rating: BigDecimal,
    val newMovieRating: MovieRating
)