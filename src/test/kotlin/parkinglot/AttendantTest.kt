package parkinglot

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class AttendantTest {
    @Test
    internal fun `park throws lot full exception when park in lot full`() {
        val lot = ParkingLot(capacity = 0)
        val attendant = Attendant(lots = mutableSetOf(lot))
        val car = object : IParkable {}

        assertThrows<LotFullException> { attendant.park(vehicle = car) }
    }

    @Test
    internal fun `park successful when lot is free`() {
        val lot = ParkingLot(capacity = 0)
        val anotherLot = ParkingLot(capacity = 1)
        val lots = mutableSetOf(lot, anotherLot)
        val attendant = Attendant(lots = lots)
        val car = object : IParkable {}

        assertDoesNotThrow { attendant.park(vehicle = car) }
    }
}