package parkinglot

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ParkingLotTest {
    @Test
    internal fun `should park when space is available`() {
        val lot = ParkingLot(capacity = 1)
        assertTrue { lot.park(car()) }
    }

    @Test
    internal fun `should not park when no space`() {
        val lot = ParkingLot(capacity = 0)
        val car = car()

        assertFalse { lot.park(car) }
    }

    @Test
    internal fun `should not park when space is full`() {
        val lot = ParkingLot(capacity = 1)
        val car = car()
        lot.park(car)

        assertFalse { lot.park(car) }
    }

    private fun car(): Any {
        return Any()
    }
}