package parkinglot

class ParkingLot(private val capacity: Int) {
    private val slots: MutableList<Any> = ArrayList()

    fun park(vehicle: Any) {
        if (slots.contains(vehicle)) {
            throw AlreadyParkedException()
        }
        if (slots.size == capacity) {
            throw LotFullException()
        }
        slots.add(vehicle)
    }

}
