package parkinglot

class ParkingLot(private val capacity: Int) {
    private val vehicles: ArrayList<Vehicle> = ArrayList()
    private var allotedSlots: Int = 0

    fun park(vehicle: Vehicle) {
        if (vehicles.contains(vehicle)) {
            throw AlreadyParkedException("already parked")
        }
        if (isFull()) {
            throw LotFullException("lot is full")
        }
        allotedSlots += 1
        vehicles.add(vehicle)
        return
    }

    private fun isFull() = allotedSlots == capacity

}
