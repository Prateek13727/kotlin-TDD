package parkinglot

class Attendant(private val lots: MutableSet<ParkingLot>) {
    fun park(vehicle: IParkable) {
        lots.forEach {
            if (!it.isFull()) {
                it.park(vehicle)
                return
            }
        }
        throw LotFullException()
    }
}
