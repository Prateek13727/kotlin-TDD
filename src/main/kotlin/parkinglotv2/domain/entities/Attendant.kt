package parkinglotv2.domain.entities

data class Attendant(
  val id: Int,
  val parkingLots: List<ParkingLot>
)
