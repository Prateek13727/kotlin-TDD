package parkinglot

class Attendant(private val parkingLot: ParkingLot) {
    fun park(vehicle: IParkable) {
        parkingLot.park(vehicle)
    }
}
