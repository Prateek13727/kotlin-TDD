package parkinglot

interface IParkingRule {
    fun find(parkingLots: MutableList<ParkingLot>): ParkingLot
}