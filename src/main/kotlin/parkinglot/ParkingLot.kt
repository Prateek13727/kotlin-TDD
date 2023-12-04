package parkinglot

class ParkingLot(private val capacity: Int) {
    private val vehicles: ArrayList<Any> = ArrayList()

    fun park(vehicle: Any) {
        if (isParked(vehicle)) {
            throw AlreadyParkedException("vehicle already parked")
        }
        if (isFull()) {
            throw LotFullException("lot is full")
        }
        vehicles.add(vehicle)
        return
    }

    private fun isFull() = vehicles.size == capacity

    private fun isParked(vehicle: Any) = vehicles.contains(vehicle)

}
