package parkinglot

interface IParkingRule {
    fun park(parkingLots: MutableList<ParkingLot>): ParkingLot
}