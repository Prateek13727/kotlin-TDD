package parkinglot

class ParkingLot(private val capacity: Int) {
    private val vehicles: ArrayList<Any> = ArrayList()

    fun park(vehicle: Any) {
        if (vehicles.size == capacity) {
            throw LotFullException("lot is full")
        }
        vehicles.add(vehicle)
        return
    }

}
