package parkinglot

class Attendant(val lots: MutableSet<ParkingLot>) {
    fun park(vehicle: Vehicle) {
        lots.first().park(vehicle)
    }
}
