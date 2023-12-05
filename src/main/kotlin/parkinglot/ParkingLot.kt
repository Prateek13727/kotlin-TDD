package parkinglot

class ParkingLot(private val capacity: Int) {
    private val vehicles: ArrayList<Vehicle> = ArrayList()

    fun park(vehicle: Vehicle) {
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

    fun unPark(vehicle: Vehicle) {
        if(!vehicles.contains(vehicle)){
            throw NotParkedException("vehicle is not parked")
        }
        vehicles.remove(vehicle)
        return
    }

}
