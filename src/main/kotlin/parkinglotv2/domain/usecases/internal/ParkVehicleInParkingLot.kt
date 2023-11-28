package parkinglotv2.domain.usecases.internal

import parkinglotv2.domain.entities.ParkingLot
import parkinglotv2.domain.entities.Slot
import parkinglotv2.domain.entities.Vehicle
import parkinglotv2.domain.repos.ParkingLotRepo
import parkinglotv2.domain.usecases.AlreadyParkedException
import parkinglotv2.domain.usecases.LotFullException

class ParkVehicleInParkingLot(
  private val parkingLotRepo: ParkingLotRepo
) {

  suspend fun invoke(parkingLot: ParkingLot, vehicle: Vehicle) {
    if (isParked(parkingLot, vehicle)) throw AlreadyParkedException()

    val emptySlot = getEmptySlot(parkingLot)
    assignVehicleToSlot(parkingLot, emptySlot, vehicle)
  }

  private fun isParked(parkingLot: ParkingLot, vehicle: Vehicle): Boolean =
    vehicle in getParkedVehicles(parkingLot)

  private fun getParkedVehicles(parkingLot: ParkingLot) =
    parkingLot.slots.mapNotNull { it.vehicle }

  private fun getEmptySlot(parkingLot: ParkingLot) =
    parkingLot.slots.find { it.vehicle == null }
      ?: throw LotFullException()

  private suspend fun assignVehicleToSlot(parkingLot: ParkingLot, emptySlot: Slot, vehicle: Vehicle) {
    val updatedParkingLot = getUpdatedParkingLot(emptySlot, vehicle, parkingLot)
    parkingLotRepo.updateParkingLot(updatedParkingLot)
  }

  private fun getUpdatedParkingLot(emptySlot: Slot, vehicle: Vehicle, parkingLot: ParkingLot): ParkingLot {
    val updatedSlot = emptySlot.copy(vehicle = vehicle)
    val updatedSlots =
      parkingLot.slots
        .map { if (it.id == emptySlot.id) updatedSlot else it }
    return parkingLot.copy(slots = updatedSlots)
  }
}
