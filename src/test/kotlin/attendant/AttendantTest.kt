package attendant

import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import parkinglot.ParkingLot
import parkinglot.Vehicle

class AttendantTest {
    @Test
    fun `attendant should be able to park the car if space is available` () {
//        Arrange
        val parkingLot : ParkingLot = mock()
        val attendant = Attendant(parkingLot)
        val car : Vehicle = mock()

//        Action
        attendant.park(car)
//        Assert
        verify(parkingLot).park(car)
    }

    @Test
    fun `attendant should be able to unpark the car if car is already parked` () {
//        Arrange
        val parkingLot : ParkingLot = mock()
        val attendant = Attendant(parkingLot)
        val car : Vehicle = mock()

//        Action
        attendant.park(car)
//        Assert
        verify(parkingLot).park(car)
    }


}