package parkinglotv2.domain.entities

import parkinglotv2.domain.strategies.FirstAvailableLotStrategy
import parkinglotv2.domain.strategies.ParkingStrategy

data class Attendant(
  val id: Int,
  val parkingLots: List<ParkingLot>,
  val parkingStrategy: ParkingStrategy = FirstAvailableLotStrategy()
)
