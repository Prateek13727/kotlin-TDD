package parkinglot.parkingrule

import parkinglot.IParkingRule
import parkinglot.ParkingLot

class MostCapacityRule : IParkingRule {
    override fun find(parkingLots: MutableList<ParkingLot>): ParkingLot {
        var champion = parkingLots[0]
        parkingLots
            .asSequence()
            .filter { it.hasMoreCapacity(champion) }
            .forEach { champion = it }
        return champion
    }
}