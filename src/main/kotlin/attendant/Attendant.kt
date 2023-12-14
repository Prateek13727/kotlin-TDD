package attendant

import parkinglot.ParkingLot
import parkinglot.Vehicle
import java.util.*

class Attendant() {
    private var parkingLots: ArrayList<ParkingLot> = ArrayList();
    constructor(parkingLot: ParkingLot) : this() {
        parkingLots.add(parkingLot)
    }

    constructor(parkingLotsInput: List<ParkingLot>) : this() {
        parkingLots.addAll(parkingLotsInput)
    }

    fun park(car: Vehicle) {
        parkingLots[0].park(car)
    }
}