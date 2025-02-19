package cinema.showtime

import cinema.api.DeleteShowtime
import cinema.spi.ShowtimeInventory
import ddd.DomainService

@DomainService
class DeleteShowtimeUseCase(
    private val showtimeInventory: ShowtimeInventory
) : DeleteShowtime {
    override fun delete(showtimeId: ShowtimeId) {
        showtimeInventory.delete(showtimeId)
    }
}