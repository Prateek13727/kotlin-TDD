package parkinglot

class ParkingLot(private val capacity: Int, private val notifiables: MutableSet<Notifiable> = mutableSetOf()) {
    private val vehicles: ArrayList<Vehicle> = ArrayList()

    fun park(vehicle: Vehicle) {
        if (isParked(vehicle)) {
            throw AlreadyParkedException("vehicle already parked")
        }
        if (isFull()) {
            throw LotFullException("lot is full")
        }
        vehicles.add(vehicle)
        notifyLotFull()
        return
    }

    private fun notifyLotFull() {
        if (isFull()) {
            notifiables.forEach { it.notifyFull(this) }
        }
    }

    private fun isFull() = vehicles.size == capacity

    fun isParked(vehicle: Any) = vehicles.contains(vehicle)

    fun unPark(vehicle: Vehicle) {
        if (vehicles.contains(vehicle).not()) {
            throw NotParkedException("vehicle is not parked")
        }
        vehicles.remove(vehicle)
        notifyLotFree()
        return
    }

    private fun notifyLotFree() {
        if (vehicles.size == capacity - 1) {
            notifiables.forEach { it.notifyFree(this) }
        }
    }

    fun addNotifiable(notifiable: Notifiable) {
        notifiables.add(notifiable)
        if(this.capacity == 0){
            notifyLotFull()
        }
    }

}
