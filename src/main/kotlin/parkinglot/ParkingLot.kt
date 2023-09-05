package parkinglot

class ParkingLot(private val capacity: Int) {
    private val slots: MutableList<Any> = ArrayList()

    fun park(car: Any) {
        if (slots.size == capacity) {
            throw LotFullException()
        }
        slots.add(car)
    }

}
