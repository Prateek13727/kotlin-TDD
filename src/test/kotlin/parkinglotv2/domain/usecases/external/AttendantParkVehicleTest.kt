package parkinglotv2.domain.usecases.external

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import parkinglotv2.domain.entities.Attendant
import parkinglotv2.domain.entities.ParkingLot
import parkinglotv2.domain.entities.Slot
import parkinglotv2.domain.entities.Vehicle
import parkinglotv2.domain.repos.AttendantsRepo
import parkinglotv2.domain.repos.ParkingLotRepo
import parkinglotv2.domain.repos.VehiclesRepo
import parkinglotv2.domain.strategies.MaxCapacityLotStrategy
import parkinglotv2.domain.usecases.AttendentNotPresent
import parkinglotv2.domain.usecases.LotFullException
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
    internal fun `park car in the first parking lot`() =
        testCoroutineScope.runBlockingTest {
            val lots = listOf(
                ParkingLot(1, 1, listOf(Slot(1, null))),
                ParkingLot(1, 0, listOf(Slot(1, null), Slot(2, null)))
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

    @Test
    internal fun `should not be able to park if all the lots are full`() {
        testCoroutineScope.runBlockingTest {
            val firstVehicle = Vehicle(1)
            val secondVehicle = Vehicle(2)

            val newVehicle = Vehicle(3)

            val lots = listOf(
                ParkingLot(1, 1, listOf(Slot(1, firstVehicle))),
                ParkingLot(1, 1, listOf(Slot(1, secondVehicle)))
            )
            whenever(attendantRepoMock.get(1)).thenReturn(Attendant(1, lots))

            whenever(vehicleRepoMock.getVehicle(1)).thenReturn(firstVehicle)
            whenever(vehicleRepoMock.getVehicle(2)).thenReturn(secondVehicle)
            whenever(vehicleRepoMock.getVehicle(3)).thenReturn(newVehicle)
//            whenever(parkingLotRepoMock.getAllParkingLots()).thenReturn(lots)

            invoking { runBlocking { attendantParkVehicle.invoke(1, 3) } } shouldThrow LotFullException::class
//            verify(parkingLotRepoMock, times(1)).updateParkingLot(any())

        }

    }


    @Test
    internal fun `should not be able to park when the attendant is not available`() {
        testCoroutineScope.runBlockingTest {
            val firstVehicle = Vehicle(1)
            val newVehicle = Vehicle(2)

            val lots = listOf(
                ParkingLot(1, 1, listOf(Slot(1, firstVehicle))),
                ParkingLot(2, 1, listOf(Slot(1, null)))
            )

            val updatedParkingLot =
                ParkingLot(2, 1, listOf(Slot(1, newVehicle)))

            whenever(attendantRepoMock.get(1)).thenReturn(null)
            whenever(vehicleRepoMock.getVehicle(1)).thenReturn(firstVehicle)
            whenever(vehicleRepoMock.getVehicle(2)).thenReturn(newVehicle)

            invoking { runBlocking { attendantParkVehicle.invoke(1, 2) } } shouldThrow AttendentNotPresent::class
        }
    }

    @Test
    internal fun `should be able to park in the lot with maximum capacity`() {
        testCoroutineScope.runBlockingTest {
            val newVehicle = Vehicle(1)

            val lots = listOf(
                ParkingLot(1, 1, listOf(Slot(1, null))),
                ParkingLot(2, 1, listOf(Slot(1, null))),
                ParkingLot(3, 2, listOf(Slot(1, null), Slot(2, null)))
            )

            val updatedParkingLot =
                ParkingLot(3, 2, listOf(Slot(1, newVehicle), Slot(2, null)))

            whenever(attendantRepoMock.get(1)).thenReturn(Attendant(1, lots, MaxCapacityLotStrategy()))
            whenever(vehicleRepoMock.getVehicle(1)).thenReturn(newVehicle)

            invoking { runBlocking { attendantParkVehicle.invoke(1, 1) } } shouldNotThrow AnyException
            verify(parkingLotRepoMock, times(1)).updateParkingLot(updatedParkingLot)
        }
    }
}