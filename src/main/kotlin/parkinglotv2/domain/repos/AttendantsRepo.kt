package parkinglotv2.domain.repos

import parkinglot.IParkingRule
import parkinglotv2.domain.entities.Attendant

interface AttendantsRepo {
  suspend fun create(parkingRule: IParkingRule): Attendant
  suspend fun get(id: Int): Attendant?
}
