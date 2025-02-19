package cinema.api

import cinema.showtime.ShowtimeId

interface DeleteShowtime {
    fun delete(showtimeId: ShowtimeId)
}