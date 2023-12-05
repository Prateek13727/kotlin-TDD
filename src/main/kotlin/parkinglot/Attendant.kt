package parkinglot

class Attendant(val lots: MutableSet<ParkingLot>) {
    fun park(vehicle: Vehicle) {
        lots.forEach {
            if (it.isFull().not()) {
                it.park(vehicle)
                return
            }
        }
        throw LotFullException("lot is full")
    }

}
