package parkinglotv2.domain.entities

data class ParkingLot(
  val id: Int,
  val capacity: Int,
  val attendants: Set<Attendant>,
  val slots: List<Slot>
)
