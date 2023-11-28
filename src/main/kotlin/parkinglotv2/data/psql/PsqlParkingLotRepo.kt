package parkinglotv2.data.psql

import parkinglotv2.domain.entities.ParkingLot
import parkinglotv2.domain.repos.ParkingLotRepo

class PsqlParkingLotRepo: ParkingLotRepo {
  override suspend fun createParkingLot(capacity: Int): ParkingLot {
    TODO("Not yet implemented")
  }

  override suspend fun getAllParkingLots(): List<ParkingLot> {
    TODO("Not yet implemented")
  }

  override suspend fun getParkingLot(id: Int): ParkingLot? {
    TODO("Not yet implemented")
  }

  override suspend fun updateParkingLot(parkingLot: ParkingLot) {
    TODO("Not yet implemented")
  }
}
