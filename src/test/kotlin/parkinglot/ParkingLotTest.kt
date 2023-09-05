package parkinglot

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertFailsWith

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
        val anotherCar = car()
        lot.park(car)

        assertThrows<LotFullException> { lot.park(anotherCar) }
    }

    @Test
    internal fun `should throw already parked exception for already parked vehicle`() {
        val lot = ParkingLot(capacity = 2)
        val car = car()
        lot.park(car)

        assertThrows<AlreadyParkedException> { lot.park(car) }
    }

    @Test
    fun `should not allow a parking lot creation with negative capacity`() {
        assertFailsWith<IllegalArgumentException> { ParkingLot(capacity = -1) }
    }

    @Test
    fun `should unPark`() {
        val lot = ParkingLot(capacity = 1)
        val car = car()
        lot.park(car)

        assertDoesNotThrow { lot.unPark(car) }
    }

    @Test
    fun `should throw NotParkedException when vehicle is not parked`() {
        val lot = ParkingLot(capacity = 1)
        val car = car()

        assertFailsWith<NotParkedException> { lot.unPark(car) }
    }

    private fun car(): IParkable {
        return object : IParkable {}
    }
}
