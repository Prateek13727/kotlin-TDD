package parkinglotv2.data.psql

import parkinglotv2.domain.entities.Vehicle
import parkinglotv2.domain.repos.VehiclesRepo

class PsqlVehiclesRepo : VehiclesRepo {
  override suspend fun createVehicle(): Vehicle {
    TODO("Not yet implemented")
  }

  override suspend fun getVehicle(id: Int): Vehicle? {
    TODO("Not yet implemented")
  }
}
