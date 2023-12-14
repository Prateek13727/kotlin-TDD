package parkinglotv2.domain.usecases.external

import parkinglotv2.domain.entities.ParkingLot
import parkinglotv2.domain.repos.AttendantsRepo
import parkinglotv2.domain.repos.VehiclesRepo
import parkinglotv2.domain.usecases.AttendentNotPresent
import parkinglotv2.domain.usecases.LotFullException
import parkinglotv2.domain.usecases.internal.ParkVehicleInParkingLot

class AttendantParkVehicle(
  private val attendantsRepo: AttendantsRepo,
  private val vehiclesRepo: VehiclesRepo,
  private val parkVehicleInParkingLot: ParkVehicleInParkingLot
) {

  suspend fun invoke(attendantId: Int, vehicleId: Int) {
    val attendant = attendantsRepo.get(attendantId) ?: throw AttendentNotPresent()
    val vehicle = vehiclesRepo.getVehicle(vehicleId) ?: return

    val availableLots = attendant.parkingLots
    val freeParkingLot: ParkingLot? = attendant.parkingStrategy.getFreeParkingLot(availableLots)

    if(freeParkingLot != null) {
      parkVehicleInParkingLot.invoke(freeParkingLot, vehicle)
    } else {
      throw LotFullException()
    }

  }

//  private fun getFreeParkingLots(parkingLots: List<ParkingLot>) : List<ParkingLot> {
//         return parkingLots.sortedWith(compareBy { -it.capacity }).filter { it.freeSpace > 0 }
////   return parkingLots.filter { it.freeSpace > 0 }
//  }



}
