package parkinglotv2.domain.usecases.internal

import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import parkinglotv2.domain.entities.ParkingLot
import parkinglotv2.domain.entities.Slot
import parkinglotv2.domain.entities.Vehicle
import parkinglotv2.domain.repos.ParkingLotRepo
import parkinglotv2.domain.repos.VehiclesRepo

internal class UnparkVehicleInParkingLotTest {
    lateinit var parkingLotRepoMock: ParkingLotRepo
    lateinit var vehiclesRepoMock: VehiclesRepo
    lateinit var unParkVehicleInParkingLot: UnparkVehicleInParkingLot

    @BeforeEach
    fun setup() {
        parkingLotRepoMock = mock()
        vehiclesRepoMock = mock()
        unParkVehicleInParkingLot = UnparkVehicleInParkingLot(parkingLotRepoMock, vehiclesRepoMock)
    }

    @Test
    internal fun `un park car from the lot`() = runBlockingTest {
        val vehicle = Vehicle(1)
        val parkingLot = ParkingLot(1, 1, emptySet(), listOf(Slot(1, vehicle)))
        val updatedParkingLot = ParkingLot(1, 1, emptySet(), listOf(Slot(1, null)))
        whenever(parkingLotRepoMock.getParkingLot(1)).thenReturn(parkingLot)
        whenever(vehiclesRepoMock.getVehicle(1)).thenReturn(vehicle)

        unParkVehicleInParkingLot.invoke(1, 1)

        verify(parkingLotRepoMock, times(1)).updateParkingLot(updatedParkingLot)
    }
}