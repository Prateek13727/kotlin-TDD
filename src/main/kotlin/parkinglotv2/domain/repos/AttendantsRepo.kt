package parkinglotv2.domain.repos

import parkinglotv2.domain.entities.Attendant

interface AttendantsRepo {
    suspend fun create(): Attendant
    suspend fun get(id: Int): Attendant?
}
