package parkinglot

import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import parkinglot.parkingrule.FirstAvailableRule
import parkinglot.parkingrule.MostCapacityRule
import parkinglot.parkingrule.MostFreeSpaceRule
import kotlin.test.assertTrue

class AttendantTest {

    @Test
    internal fun `park successful when lot is free`() {
        val lot = ParkingLot(capacity = 0)
        val anotherLot = ParkingLot(capacity = 1)
        val lots = mutableSetOf(lot, anotherLot)
        val attendant = Attendant(lots = lots)
        val car = object : IParkable {}

        assertDoesNotThrow { attendant.park(vehicle = car) }
    }

    @Test
    internal fun `park in most capacity lot`() {
        val lot = ParkingLot(1)
        val anotherLot = ParkingLot(2)
        val lots = mutableSetOf(lot, anotherLot)
        val attendant = Attendant(lots, MostCapacityRule())
        val car = object : IParkable {}
        attendant.park(car)

        assertTrue(anotherLot.isVehicleParked(car))
    }

    @Test
    internal fun `park in least parked vehicles lot`() {
        val lot = ParkingLot(2)
        val anotherLot = ParkingLot(2)
        val lots = mutableSetOf(lot, anotherLot)
        val attendant = Attendant(lots, MostFreeSpaceRule())
        val car = object : IParkable {}
        val anotherCar = object : IParkable {}
        attendant.park(car)

        attendant.park(anotherCar)

        assertTrue(anotherLot.isVehicleParked(anotherCar))
    }

    @Test
    internal fun `throw lot full exception for park in lot full`() {
        val lot = ParkingLot(0)
        val anotherLot = ParkingLot(1)
        val lots = mutableSetOf(lot, anotherLot)
        val attendant = Attendant(lots, FirstAvailableRule())
        val car = object : IParkable {}
        val anotherCar = object : IParkable {}
        attendant.park(car)

        assertThrows<LotFullException> { attendant.park(anotherCar) }
    }

}