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
        val testOwner = TestOwner()
        val lot = ParkingLot(capacity = 1, testOwner)
        val car = car()

        lot.park(car)

        testOwner.isNotifiedFull() shouldBeEqualTo true
    }

    @Test
    fun `should not notify full for owner when lot is free`() {
        val testOwner = TestOwner()
        val lot = ParkingLot(capacity = 2, notifiable = testOwner)
        val car = car()

        lot.park(car)

        testOwner.isNotifiedFull() shouldBeEqualTo false
    }

    @Test
    fun `should notify free for owner when full lot has free space`() {
        val testOwner = TestOwner()
        val lot = ParkingLot(capacity = 1, testOwner)
        val car = car()
        lot.park(car)
        lot.unPark(car)

        testOwner.isNotifiedFree() shouldBeEqualTo true
    }

    private fun car(): Vehicle {
        return object : Vehicle {}
    }

}

class TestOwner : Notifiable {
    private var notifyFree: Boolean = false
    private var notifiedFull: Boolean = false

    override fun notifyFull() {
        notifiedFull = true
    }

    override fun notifyFree() {
        notifyFree = true
    }

    fun isNotifiedFull(): Boolean {
        return notifiedFull
    }
    fun isNotifiedFree(): Boolean {
        return notifyFree
    }

}
