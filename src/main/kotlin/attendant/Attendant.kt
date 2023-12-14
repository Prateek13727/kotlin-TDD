package attendant

import parkinglot.ParkingLot
import parkinglot.Vehicle

class Attendant(val parkingLot: ParkingLot) {
    fun park(car: Vehicle) {
        parkingLot.park(car)
    }
}