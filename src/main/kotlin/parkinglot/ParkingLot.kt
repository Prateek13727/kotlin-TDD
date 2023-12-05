package parkinglot

class ParkingLot(private val capacity: Int) {
    constructor(capacity: Int, notifiable: Notifiable) : this(capacity) {
        this.notifiable = notifiable
    }

    private var notifiable: Notifiable? = null
    private val vehicles: ArrayList<Vehicle> = ArrayList()

    fun park(vehicle: Vehicle) {
        if (isParked(vehicle)) {
            throw AlreadyParkedException("vehicle already parked")
        }
        if (isFull()) {
            throw LotFullException("lot is full")
        }
        vehicles.add(vehicle)
        notifyForFull()
        return
    }

    private fun notifyForFull() {
        if (isFull()) {
            this.notifiable?.notifyFull()
        }
    }

    private fun isFull() = vehicles.size == capacity

    fun isParked(vehicle: Any) = vehicles.contains(vehicle)

    fun unPark(vehicle: Vehicle) {
        if (!vehicles.contains(vehicle)) {
            throw NotParkedException("vehicle is not parked")
        }
        vehicles.remove(vehicle)
        return
    }

}
