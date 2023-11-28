package parkinglotv2.domain.entities.parkingrules

import parkinglotv2.domain.entities.ParkingLot
import parkinglotv2.domain.entities.ParkingRule

class MostCapacityRule : ParkingRule {

  override fun find(parkingLots: List<ParkingLot>): ParkingLot =
    parkingLots.maxBy { it.capacity }

}
