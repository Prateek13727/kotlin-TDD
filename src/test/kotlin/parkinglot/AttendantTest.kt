package parkinglot

import org.amshove.kluent.*
import org.junit.jupiter.api.Test

class AttendantTest {
    @Test
    internal fun `park throws lot full exception when park in lot full`() {
        val lot = ParkingLot(capacity = 0)
        val attendant = Attendant(parkingLot = lot)
        val car = object : Vehicle {}

        invoking { attendant.park(vehicle = car) } shouldThrow LotFullException::class withMessage "lot is full"
    }

    @Test
    internal fun `park successful when lot is free`() {
        val lot = ParkingLot(capacity = 1)
        val attendant = Attendant(parkingLot = lot)
        val car = object : Vehicle {}

        invoking { attendant.park(vehicle = car) } shouldNotThrow AnyException
    }
}