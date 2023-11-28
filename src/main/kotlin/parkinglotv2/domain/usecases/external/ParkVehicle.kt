package parkinglotv2.domain.usecases.external

import parkinglotv2.domain.entities.ParkingLot
import parkinglotv2.domain.repos.AttendantsRepo
import parkinglotv2.domain.repos.ParkingLotRepo
import parkinglotv2.domain.repos.VehicleRepo
import parkinglotv2.domain.usecases.internal.ParkVehicleInParkingLot

class ParkVehicle(
  private val attendantsRepo: AttendantsRepo,
  private val vehiclesRepo: VehicleRepo,
  private val parkingLotRepo: ParkingLotRepo,

  private val parkVehicleInParkingLot: ParkVehicleInParkingLot
) {
  suspend fun invoke(attendantId: Int, vehicleId: Int) {
    val attendant = attendantsRepo.get(attendantId) ?: return
    val vehicle = vehiclesRepo.getVehicle(vehicleId) ?: return

    val availableLots = getAvailableParkingLots()
    val championParkingLot = attendant.parkingRule.find(availableLots)
    parkVehicleInParkingLot.invoke(championParkingLot, vehicle)
  }

  private suspend fun getAvailableParkingLots(): List<ParkingLot> =
    parkingLotRepo.getParkingLots()
      .filter { hasEmptySlot(it) }

  private fun hasEmptySlot(it: ParkingLot) =
    it.slots.count { it.vehicle == null } > 0
}
