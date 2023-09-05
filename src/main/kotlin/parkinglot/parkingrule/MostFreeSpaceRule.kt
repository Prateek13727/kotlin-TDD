package parkinglot.parkingrule

import parkinglot.IParkingRule
import parkinglot.ParkingLot

class MostFreeSpaceRule : IParkingRule {
    override fun find(parkingLots: MutableList<ParkingLot>): ParkingLot {
        var champion = parkingLots[0]
        parkingLots
            .asSequence()
            .filter { it.hasLeastNumberOfVehicles(champion) }
            .forEach { champion = it }
        return champion
    }
}