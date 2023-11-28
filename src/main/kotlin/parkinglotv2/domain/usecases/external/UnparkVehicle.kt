package parkinglotv2.domain.usecases.external

import parkinglotv2.domain.entities.ParkingLot
import parkinglotv2.domain.entities.Slot
import parkinglotv2.domain.entities.Vehicle
import parkinglotv2.domain.repos.ParkingLotRepo
import parkinglotv2.domain.repos.VehiclesRepo
import parkinglotv2.domain.usecases.NotParkedException

class UnparkVehicle(
  private val parkingLotRepo: ParkingLotRepo,
  private val vehiclesRepo: VehiclesRepo
) {

  suspend fun invoke(parkingLotId: Int, vehicleId: Int) {
    val parkingLot = parkingLotRepo.getParkingLot(parkingLotId) ?: return
    val vehicle = vehiclesRepo.getVehicle(vehicleId) ?: return

    val parkedSlot = getParkedSlot(parkingLot, vehicle)
    removeVehicleFromSlot(parkingLot, parkedSlot)
  }

  private fun getParkedSlot(parkingLot: ParkingLot, vehicle: Vehicle) =
    parkingLot.slots.find { it.vehicle?.id == vehicle.id }
      ?: throw NotParkedException()

  private suspend fun removeVehicleFromSlot(parkingLot: ParkingLot, parkedSlot: Slot) {
    val updatedParkingSlot = getUpdatedParkingLot(parkedSlot, parkingLot)
    parkingLotRepo.updateParkingLot(updatedParkingSlot)
  }

  private fun getUpdatedParkingLot(parkedSlot: Slot, parkingLot: ParkingLot): ParkingLot {
    val updatedSlot = parkedSlot.copy(vehicle = null)
    val updatedSlots =
      parkingLot.slots.map { if (it.id == updatedSlot.id) updatedSlot else it }
    return parkingLot.copy(slots = updatedSlots)
  }
}
