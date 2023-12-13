package parkinglotv2.domain.usecases.external

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.AnyException
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldNotThrow
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import parkinglotv2.domain.entities.Attendant
import parkinglotv2.domain.entities.ParkingLot
import parkinglotv2.domain.entities.Slot
import parkinglotv2.domain.entities.Vehicle
import parkinglotv2.domain.repos.AttendantsRepo
import parkinglotv2.domain.repos.ParkingLotRepo
import parkinglotv2.domain.repos.VehiclesRepo
import parkinglotv2.domain.usecases.internal.ParkVehicleInParkingLot

internal class AttendantParkVehicleTest {
    private lateinit var attendantRepoMock: AttendantsRepo
    private lateinit var vehicleRepoMock: VehiclesRepo
    private lateinit var parkingLotRepoMock: ParkingLotRepo
    private lateinit var parkVehicleInParkingLot: ParkVehicleInParkingLot
    private lateinit var attendantParkVehicle: AttendantParkVehicle

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @BeforeEach
    internal fun setUp() {
        attendantRepoMock = mock()
        vehicleRepoMock = mock()
        parkingLotRepoMock = mock()
        parkVehicleInParkingLot = ParkVehicleInParkingLot(parkingLotRepoMock)
        attendantParkVehicle =
            AttendantParkVehicle(attendantRepoMock, vehicleRepoMock, parkVehicleInParkingLot)
    }

    @Test
    internal fun ` park car in the first parking lot`() =
        testCoroutineScope.runBlockingTest {
            val lots = listOf(
                ParkingLot(1, 1, listOf(Slot(1, null))),
                ParkingLot(1, 2, listOf(Slot(1, null), Slot(2, null)))
            )
            val vehicle = Vehicle(1)
            val updatedParkingLot =
                ParkingLot(1, 1, listOf(Slot(1, vehicle)))
            whenever(attendantRepoMock.get(1)).thenReturn(Attendant(1, lots))
            whenever(vehicleRepoMock.getVehicle(1)).thenReturn(Vehicle(1))
            whenever(parkingLotRepoMock.getAllParkingLots()).thenReturn(lots)

            invoking { runBlocking { attendantParkVehicle.invoke(1, 1) } } shouldNotThrow AnyException
            verify(parkingLotRepoMock, times(1)).updateParkingLot(updatedParkingLot)
        }
}