package parkinglot

import org.amshove.kluent.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

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

    @Test
    internal fun `should un-park vehicle`() {
        val parkingLot = ParkingLot(1)
        val car = car()
        parkingLot.park(car)

        invoking { parkingLot.unPark(car) } shouldNotThrow AnyException
    }

    @Test
    internal fun `should not un-park vehicle when not parked`() {
        val parkingLot = ParkingLot(0)
        val car = car()

        invoking { parkingLot.unPark(car) } shouldThrow NotParkedException::class withMessage "vehicle is not parked"
    }

    @Test
    internal fun `should not un-park vehicle when already un-parked`() {
        val parkingLot = ParkingLot(1)
        val car = car()
        parkingLot.park(car)
        parkingLot.unPark(car)

        invoking { parkingLot.unPark(car) } shouldThrow NotParkedException::class withMessage "vehicle is not parked"
    }

    @Test
    internal fun `should be true when vehicle is parked`() {
        val parkingLot = ParkingLot(1)
        val car = car()
        parkingLot.park(car)

        val status = parkingLot.isParked(car)

        status shouldBeEqualTo true
    }

    @Test
    internal fun `should be false when vehicle is not parked`() {
        val parkingLot = ParkingLot(1)
        val car = car()

        val status = parkingLot.isParked(car)

        status shouldBeEqualTo false
    }

    @Test
    fun `should notify owner when lot is full`() {
        val testOwner = mock<Notifiable>()
        val lot = ParkingLot(capacity = 1, notifiables = mutableSetOf(testOwner))
        val car = car()

        lot.park(car)

        verify(testOwner, times(1)).notifyFull()
    }

    @Test
    fun `should not notify full for owner when lot is free`() {
        val testOwner = mock<Notifiable>()
        val lot = ParkingLot(capacity = 2, notifiables = mutableSetOf(testOwner))
        val car = car()

        lot.park(car)

        verify(testOwner, never()).notifyFull()
    }

    @Test
    fun `should notify free for owner when full lot has free space`() {
        val testOwner = mock<Notifiable>()
        val lot = ParkingLot(capacity = 1, notifiables = mutableSetOf(testOwner))
        val car = car()
        lot.park(car)
        lot.unPark(car)

        verify(testOwner, times(1)).notifyFree()
    }

    @Test
    fun `should notify multiple observers when lot is full`() {
        val observer :Notifiable= mock()
        val anotherObserver:Notifiable = mock()
        val observers = mutableSetOf(observer, anotherObserver)
        val lot = ParkingLot(capacity = 1, notifiables = observers)
        val car = car()

        lot.park(car)

        verify(observer, times(1)).notifyFull()
        verify(anotherObserver, times(1)).notifyFull()
    }

    @Test
    fun `should notify multiple observers when full lot has space`() {
        val observer :Notifiable= mock()
        val anotherObserver:Notifiable = mock()
        val observers = mutableSetOf(observer, anotherObserver)
        val lot = ParkingLot(capacity = 1, notifiables = observers)
        val car = car()
        lot.park(car)

        lot.unPark(car)

        verify(observer, times(1)).notifyFree()
        verify(anotherObserver, times(1)).notifyFree()
    }

    private fun car(): Vehicle {
        return object : Vehicle {}
    }

}