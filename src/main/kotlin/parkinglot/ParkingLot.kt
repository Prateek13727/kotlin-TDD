package parkinglot

import java.lang.IllegalArgumentException


class ParkingLot(private val capacity: Int) {
    private val slots: MutableList<Any> = ArrayList()

    init {
        if (capacity < 0) {
            throw IllegalArgumentException("Capacity cannot be negative")
        }
    }

    fun park(vehicle: IParkable) {
        if (isParked(vehicle)) {
            throw AlreadyParkedException()
        }
        if (isFull()) {
            throw LotFullException()
        }
        slots.add(vehicle)
    }

    private fun isFull(): Boolean = slots.size == capacity

    private fun isParked(vehicle: IParkable): Boolean = slots.contains(vehicle)

}
