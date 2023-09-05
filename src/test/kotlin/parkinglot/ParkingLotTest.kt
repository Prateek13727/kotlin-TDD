package parkinglot

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class ParkingLotTest {
    @Test
    internal fun `should park when space is available`() {
        val lot = ParkingLot(capacity = 1)
        val car = car()

        assertDoesNotThrow { lot.park(car) }
    }

    @Test
    internal fun `should throw lot full exception for park in no space`() {
        val lot = ParkingLot(capacity = 0)
        val car = car()

        assertThrows<LotFullException> { lot.park(car) }
    }

    @Test
    internal fun `should throw lot full exception for park when space is full`() {
        val lot = ParkingLot(capacity = 1)
        val car = car()
        lot.park(car)

        assertThrows<LotFullException> { lot.park(car) }
    }

    private fun car(): Any {
        return Any()
    }
}