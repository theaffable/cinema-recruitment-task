package cinema.exceptions

class InvalidUuidFormatException(private val providedId: String) : Exception("Invalid parameter format. Expected valid UUIDv4 but got $providedId")
