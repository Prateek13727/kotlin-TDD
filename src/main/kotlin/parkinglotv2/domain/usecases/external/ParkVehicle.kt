package parkinglotv2.domain.usecases.external

import parkinglotv2.domain.entities.ParkingLot
import parkinglotv2.domain.repos.AttendantsRepo
import parkinglotv2.domain.repos.ParkingLotRepo
import parkinglotv2.domain.repos.VehiclesRepo
import parkinglotv2.domain.usecases.LotFullException
import parkinglotv2.domain.usecases.internal.ParkVehicleInParkingLot

class ParkVehicle(
  private val attendantsRepo: AttendantsRepo,
  private val vehiclesRepo: VehiclesRepo,
  private val parkingLotRepo: ParkingLotRepo,

  private val parkVehicleInParkingLot: ParkVehicleInParkingLot
) {

  suspend fun invoke(attendantId: Int, vehicleId: Int) {
    val attendant = attendantsRepo.get(attendantId) ?: return
    val vehicle = vehiclesRepo.getVehicle(vehicleId) ?: return

    val availableLots = getAvailableParkingLots()
    if (availableLots.isEmpty()) throw LotFullException()

    val championParkingLot = attendant.parkingRule.find(availableLots)
    parkVehicleInParkingLot.invoke(championParkingLot, vehicle)
  }

  private suspend fun getAvailableParkingLots(): List<ParkingLot> =
    parkingLotRepo.getAllParkingLots()
      .filter { hasEmptySlot(it) }

  private fun hasEmptySlot(it: ParkingLot) =
    it.slots.count { it.vehicle == null } > 0
}
