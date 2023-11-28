package parkinglotv2.domain.entities

interface ParkingRule {
  fun find(parkingLots: List<ParkingLot>): ParkingLot
}
