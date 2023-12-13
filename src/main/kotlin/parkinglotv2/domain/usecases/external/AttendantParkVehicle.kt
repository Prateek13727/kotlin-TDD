package parkinglotv2.domain.usecases.external

import parkinglotv2.domain.entities.Attendant
import parkinglotv2.domain.entities.ParkingLot
import parkinglotv2.domain.repos.AttendantsRepo
import parkinglotv2.domain.repos.VehiclesRepo
import parkinglotv2.domain.usecases.LotFullException
import parkinglotv2.domain.usecases.internal.ParkVehicleInParkingLot

class AttendantParkVehicle(
  private val attendantsRepo: AttendantsRepo,
  private val vehiclesRepo: VehiclesRepo,
  private val parkVehicleInParkingLot: ParkVehicleInParkingLot
) {

  suspend fun invoke(attendantId: Int, vehicleId: Int) {
    val attendant = attendantsRepo.get(attendantId) ?: return
    val vehicle = vehiclesRepo.getVehicle(vehicleId) ?: return

    val availableLots = getAvailableParkingLots(attendant)
//    if (availableLots.isEmpty()) throw LotFullException()

    parkVehicleInParkingLot.invoke(availableLots.first(), vehicle)
  }

  private suspend fun getAvailableParkingLots(attendant: Attendant): List<ParkingLot> =
    attendant.parkingLots
      .filter { hasEmptySlot(it) }

  private fun hasEmptySlot(it: ParkingLot) =
    it.slots.count { it.vehicle == null } > 0
}
