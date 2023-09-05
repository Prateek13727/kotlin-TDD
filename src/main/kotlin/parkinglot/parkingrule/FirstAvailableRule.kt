package parkinglot.parkingrule

import parkinglot.IParkingRule
import parkinglot.ParkingLot

class FirstAvailableRule : IParkingRule {
    override fun find(parkingLots: MutableList<ParkingLot>): ParkingLot {
        return parkingLots[0]
    }
}