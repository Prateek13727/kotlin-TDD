package parkinglot

import java.lang.IllegalArgumentException


class ParkingLot(private val capacity: Int) {
    private var notifiable: INotifiable? = null
    private val slots: MutableList<Any> = ArrayList()

    constructor(capacity: Int, notifiable: INotifiable) : this(capacity) {
        this.notifiable = notifiable
    }

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
            notifiable?.notifyFull()
        }
    }

    fun unPark(vehicle: IParkable) {
        if (isParked(vehicle).not()) throw NotParkedException()
        slots.remove(vehicle)
    }

    private fun isFull(): Boolean = slots.size == capacity

    private fun isParked(vehicle: IParkable): Boolean = slots.contains(vehicle)

}
