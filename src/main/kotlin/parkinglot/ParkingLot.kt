package parkinglot

import java.lang.IllegalArgumentException


class ParkingLot(private val capacity: Int, private val notifiables: MutableSet<INotifiable> = mutableSetOf()) {
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
        notifyFull()
    }

    private fun notifyFull() {
        if (isFull()) {
            notifiables.forEach { it.notifyFull() }
        }
    }

    fun unPark(vehicle: IParkable) {
        if (isParked(vehicle).not()) throw NotParkedException()
        if (isFull()) {
            notifiables.forEach { it.notifyFree() }
        }
        slots.remove(vehicle)
    }

    private fun isFull(): Boolean = slots.size == capacity

    private fun isParked(vehicle: IParkable): Boolean = slots.contains(vehicle)

}
