package parkinglot

class ParkingLot(private val capacity: Int) {
    private val slots: MutableList<Any> = ArrayList()

    fun park(car: Any): Boolean {
        if (slots.size == capacity)
            return false
        return slots.add(car)
    }

}
