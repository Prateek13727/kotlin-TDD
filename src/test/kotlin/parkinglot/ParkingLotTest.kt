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

    private fun car(): Any {
        return Any()
    }

}