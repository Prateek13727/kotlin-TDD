package parkinglotv2.domain.usecases.internal

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import parkinglotv2.domain.entities.ParkingLot
import parkinglotv2.domain.entities.Slot
import parkinglotv2.domain.entities.Vehicle
import parkinglotv2.domain.repos.ParkingLotRepo

internal class ParkVehicleInParkingLotTest {
    private lateinit var parkingLotRepoMock: ParkingLotRepo
    private lateinit var parkVehicleInParkingLot: ParkVehicleInParkingLot

    @BeforeEach
    internal fun setUp() {
        parkingLotRepoMock = mock()
        parkVehicleInParkingLot = ParkVehicleInParkingLot(parkingLotRepoMock)
    }

    @Test
    internal fun `park vehicle when not parked`() = runBlocking {
        val slot = Slot(1, null)
        val parkingLot = ParkingLot(1, 1, listOf(slot))
        val vehicle = Vehicle(1)
        val updatedParkingLot = ParkingLot(1, 1, listOf(Slot(1, vehicle)))

        parkVehicleInParkingLot.invoke(parkingLot, vehicle)

        verify(parkingLotRepoMock, times(1)).updateParkingLot(updatedParkingLot)
    }
}