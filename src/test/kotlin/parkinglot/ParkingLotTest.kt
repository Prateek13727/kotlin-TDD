package parkinglot

import io.mockk.mockk
import io.mockk.verify
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

    @Test
    fun `should notify observer when lot is full`() {
        val testObserver = mockk<INotifiable>(relaxed = true)
        val lot = ParkingLot(capacity = 1, mutableSetOf(testObserver))
        val car = car()

        lot.park(car)

        verify(exactly = 1) { testObserver.notifyFull() }
    }

    @Test
    fun `should not notify full for observer when lot is free`() {
        val testObserver = mockk<INotifiable>(relaxed = true)
        val lot = ParkingLot(capacity = 2, mutableSetOf(testObserver))
        val car = car()

        lot.park(car)

        verify(exactly = 0) { testObserver.notifyFull() }
    }

    @Test
    fun `should notify free for observer when full lot has free space`() {
        val testObserver = mockk<INotifiable>(relaxed = true)
        val lot = ParkingLot(capacity = 1, mutableSetOf(testObserver))
        val car = car()
        lot.park(car)
        lot.unPark(car)

        verify(exactly = 1) { testObserver.notifyFree() }
    }

    @Test
    fun `should notify multiple observers when lot is full`() {
        val observer = mockk<INotifiable>(relaxed = true)
        val anotherObserver = mockk<INotifiable>(relaxed = true)
        val observers = mutableSetOf(observer, anotherObserver)
        val lot = ParkingLot(capacity = 1, observers)
        val car = car()

        lot.park(car)

        verify(exactly = 1) { observer.notifyFull() }
        verify(exactly = 1) { anotherObserver.notifyFull() }
    }

    @Test
    fun `should notify multiple observers when full lot has space`() {
        val observer = mockk<INotifiable>(relaxed = true)
        val anotherObserver = mockk<INotifiable>(relaxed = true)
        val observers = mutableSetOf(observer, anotherObserver)
        val lot = ParkingLot(capacity = 1, observers)
        val car = car()
        lot.park(car)

        lot.unPark(car)

        verify(exactly = 1) { observer.notifyFree() }
        verify(exactly = 1) { anotherObserver.notifyFree() }
    }

    private fun car(): IParkable {
        return object : IParkable {}
    }
}

