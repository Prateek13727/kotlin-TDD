package parkinglotv2.data.psql

import parkinglotv2.domain.entities.Attendant
import parkinglotv2.domain.repos.AttendantsRepo

class PsqlAttendantsRepo : AttendantsRepo {
  override suspend fun create(): Attendant {
    TODO("Not yet implemented")
  }

  override suspend fun get(id: Int): Attendant? {
    TODO("Not yet implemented")
  }
}
