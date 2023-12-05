package parkinglot

class Attendant(val parkingLot: ParkingLot) {
    fun park(vehicle: Vehicle) {
        parkingLot.park(vehicle)
    }


}
