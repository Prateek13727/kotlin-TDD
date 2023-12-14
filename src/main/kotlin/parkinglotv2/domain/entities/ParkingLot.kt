package parkinglotv2.domain.entities

data class ParkingLot(
  val id: Int,
  val capacity: Int,
  val slots: List<Slot>
) {

  val freeSpace: Int
    get() = capacity - filledSlotsCnt

  private val filledSlotsCnt
    get() = slots.count { it.vehicle != null }

}
