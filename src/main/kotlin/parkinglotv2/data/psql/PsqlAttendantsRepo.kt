package parkinglotv2.data.psql

import parkinglot.IParkingRule
import parkinglotv2.domain.entities.Attendant
import parkinglotv2.domain.repos.AttendantsRepo

class PsqlAttendantsRepo : AttendantsRepo {
  override suspend fun create(parkingRule: IParkingRule): Attendant {
    TODO("Not yet implemented")
  }

  override suspend fun get(id: Int): Attendant? {
    TODO("Not yet implemented")
  }
}
