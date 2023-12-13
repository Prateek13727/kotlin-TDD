package parkinglot

class AlreadyParkedException(message: String) : RuntimeException(message)
class NotParkedException(message: String) : RuntimeException(message)
class LotFullException(message: String) : RuntimeException(message)
