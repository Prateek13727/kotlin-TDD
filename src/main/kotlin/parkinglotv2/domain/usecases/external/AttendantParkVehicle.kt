package parkinglotv2.domain.usecases.external

import parkinglotv2.domain.repos.AttendantsRepo
import parkinglotv2.domain.repos.VehiclesRepo
import parkinglotv2.domain.usecases.internal.ParkVehicleInParkingLot

class AttendantParkVehicle(
  private val attendantsRepo: AttendantsRepo,
  private val vehiclesRepo: VehiclesRepo,
  private val parkVehicleInParkingLot: ParkVehicleInParkingLot
) {

  suspend fun invoke(attendantId: Int, vehicleId: Int) {
    val attendant = attendantsRepo.get(attendantId) ?: return
    val vehicle = vehiclesRepo.getVehicle(vehicleId) ?: return

    val availableLots = attendant.parkingLots

    parkVehicleInParkingLot.invoke(availableLots.first(), vehicle)
  }

}
