package parkinglot

class ParkingLot(val capacity: Int) {
    fun park(vehicle: Any) {
        if (capacity == 0) {
            throw LotFullException("lot is full")
        }
        return
    }

}
