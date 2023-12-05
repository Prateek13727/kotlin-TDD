package parkinglotv2.domain.repos

import parkinglotv2.domain.entities.Attendant
import parkinglotv2.domain.entities.ParkingRule

interface AttendantsRepo {
    suspend fun create(parkingRule: ParkingRule): Attendant
    suspend fun get(id: Int): Attendant?
}
