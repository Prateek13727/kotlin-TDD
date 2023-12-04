package parkinglot

import org.amshove.kluent.*
import org.junit.jupiter.api.Test

class ParkingLotTest {
    @Test
    internal fun `should park when free space is available`() {
        val parkingLot = ParkingLot(1)
        val car = car()

        invoking { parkingLot.park(car) } shouldNotThrow AnyException
    }

    @Test
    internal fun `should not park car when no space`() {
        val parkingLot = ParkingLot(0)
        val car = car()

        invoking { parkingLot.park(car) } shouldThrow LotFullException::class withMessage "lot is full"
    }

    @Test
    internal fun `should not park car when lot is full`() {
        val parkingLot = ParkingLot(1)
        val car = car()
        val anotherCar = car()
        parkingLot.park(car)

        invoking { parkingLot.park(anotherCar) } shouldThrow LotFullException::class withMessage "lot is full"
    }

    @Test
    internal fun `should not park car when already parked`() {
        val parkingLot = ParkingLot(2)
        val car = car()
        parkingLot.park(car)

        invoking { parkingLot.park(car) } shouldThrow AlreadyParkedException::class withMessage "vehicle already parked"
    }

    private fun car(): Vehicle {
        return object : Vehicle {}
    }

}