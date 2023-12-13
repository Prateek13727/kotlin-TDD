package parkinglot

import org.amshove.kluent.*
import org.junit.jupiter.api.Test

class AttendantTest {
    @Test
    internal fun `park successful when lot is free`() {
        val lot = ParkingLot(capacity = 1)
        val lots = mutableSetOf(lot)
        val attendant = Attendant(lots = lots)
        val car = object : Vehicle {}

        invoking { attendant.park(vehicle = car) } shouldNotThrow AnyException
        lot.isParked(car) shouldBeEqualTo true
    }

}