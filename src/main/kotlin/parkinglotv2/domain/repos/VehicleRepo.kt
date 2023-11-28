package parkinglotv2.domain.repos

import parkinglotv2.domain.entities.Vehicle

interface VehicleRepo {
  suspend fun createVehicle(): Vehicle
  suspend fun getVehicle(id: Int): Vehicle?
}
