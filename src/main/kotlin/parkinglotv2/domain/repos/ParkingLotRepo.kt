package parkinglotv2.domain.repos

import parkinglotv2.domain.entities.ParkingLot

interface ParkingLotRepo {
  suspend fun createParkingLot(capacity: Int): ParkingLot
  suspend fun getAllParkingLots(): List<ParkingLot>
  suspend fun getParkingLot(id: Int): ParkingLot?
  suspend fun updateParkingLot(parkingLot: ParkingLot)
}
