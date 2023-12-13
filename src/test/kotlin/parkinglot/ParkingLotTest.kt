package parkinglot

import org.amshove.kluent.*
import org.junit.jupiter.api.Test

class ParkingLotTest {
    @Test
    internal fun `should park when free space is available`() {
        val parkingLot = ParkingLot(1)

        invoking { parkingLot.park(car()) } shouldNotThrow AnyException
    }

    @Test
    internal fun `should not park car when no space`() {
        val parkingLot = ParkingLot(0)

        invoking { parkingLot.park(car()) } shouldThrow LotFullException::class withMessage "lot is full"
    }

    @Test
    internal fun `should not park car when lot is full`() {
        val parkingLot = ParkingLot(1)
        parkingLot.park(car())

        invoking { parkingLot.park(car()) } shouldThrow LotFullException::class withMessage "lot is full"
    }
    @Test
    internal fun `should not allow to park when car is already parked`() {
        val parkingLot = ParkingLot(2)
        val car = car()
        parkingLot.park(car)

        invoking { parkingLot.park(car) } shouldThrow AlreadyParkedException::class withMessage "already parked"
    }

    @Test
    internal fun `should be true when car is parked`() {
        val parkingLot = ParkingLot(1)
//        val car = car()
//        parkingLot.park(car)

//        parkingLot.isParked(car) shouldBeEqualTo true
    }

    private fun car() = object : Vehicle {}

}