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
            notifiables.forEach { it.notifyFull(this) }
        }
    }

    fun unPark(vehicle: IParkable) {
        if (isParked(vehicle).not()) throw NotParkedException()
        if (isFull()) {
            notifiables.forEach { it.notifyFree(this) }
        }
        slots.remove(vehicle)
    }

    fun isFull(): Boolean = slots.size == capacity

    private fun isParked(vehicle: IParkable): Boolean = slots.contains(vehicle)
    fun addNotifiable(notifiable: INotifiable) {
        notifiables.add(notifiable)
        if (this.capacity == 0) {
            notifyFull()
        }
    }

    fun isVehicleParked(parkable: Any) = slots.contains(parkable)
    fun hasMoreCapacity(anotherLot: ParkingLot): Boolean {
        return this.capacity > anotherLot.capacity
    }

    fun hasLeastNumberOfVehicles(anotherLot: ParkingLot): Boolean {
        return hasMoreFreeSpace() > anotherLot.hasMoreFreeSpace()
    }

    private fun hasMoreFreeSpace() = capacity - slots.size

}
