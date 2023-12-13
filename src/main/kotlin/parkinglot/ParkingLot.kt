package parkinglot

class ParkingLot(private val capacity: Int, private val notifiables: MutableSet<Notifiable> = mutableSetOf()) {
    private val slots: ArrayList<Vehicle> = ArrayList()

    fun park(vehicle: Vehicle) {
        if (isParked(vehicle)) {
            throw AlreadyParkedException("vehicle already parked")
        }
        if (isFull()) {
            throw LotFullException("lot is full")
        }
        slots.add(vehicle)
        notifyLotFull()
        return
    }

    private fun notifyLotFull() {
        if (isFull()) {
            notifiables.forEach { it.notifyFull(this) }
        }
    }

    private fun isFull() = slots.size == capacity

    fun isParked(vehicle: Any) = slots.contains(vehicle)

    fun unPark(vehicle: Vehicle) {
        if (slots.contains(vehicle).not()) {
            throw NotParkedException("vehicle is not parked")
        }
        slots.remove(vehicle)
        notifyLotFree()
        return
    }

    private fun notifyLotFree() {
        if (slots.size == capacity - 1) {
            notifiables.forEach { it.notifyFree(this) }
        }
    }

    fun addNotifiable(notifiable: Notifiable) {
        notifiables.add(notifiable)
        if(this.isFull()){
            notifyLotFull()
        }
    }

}
