package parkinglot

class ParkingLot(private val capacity: Int) {
    private val vehicles: ArrayList<Vehicle> = ArrayList()

    fun park(vehicle: Vehicle) {
        if (vehicles.contains(vehicle)) {
            throw AlreadyParkedException("already parked")
        }
        if (isFull()) {
            throw LotFullException("lot is full")
        }
        vehicles.add(vehicle)
        return
    }

    private fun isFull() = vehicles.size == capacity

}
