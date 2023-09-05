package parkinglot

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
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
        val attendant = Attendant(lots, ParkingRule.MOST_CAPACITY)
        val car = object : IParkable {}
        attendant.park(car)

        assertTrue(anotherLot.isVehicleParked(car))
    }

    @Test
    internal fun `park in least parked vehicles lot`() {
        val lot = ParkingLot(2)
        val anotherLot = ParkingLot(2)
        val lots = mutableSetOf(lot, anotherLot)
        val attendant = Attendant(lots, ParkingRule.MOST_FREE_SPACE)
        val car = object : IParkable {}
        val anotherCar = object : IParkable {}
        attendant.park(car)

        attendant.park(anotherCar)

        assertTrue(anotherLot.isVehicleParked(anotherCar))
    }

}