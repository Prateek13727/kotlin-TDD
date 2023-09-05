package parkinglot


class ParkingLot(private val capacity: Int) {
    private val slots: MutableList<Any> = ArrayList()

    fun park(vehicle: Any) {
        if (isParked(vehicle)) {
            throw AlreadyParkedException()
        }
        if (isFull()) {
            throw LotFullException()
        }
        slots.add(vehicle)
    }

    private fun isFull(): Boolean = slots.size == capacity

    private fun isParked(vehicle: Any): Boolean = slots.contains(vehicle)

}
