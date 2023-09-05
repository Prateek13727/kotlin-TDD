package parkinglot

interface INotifiable {
    fun notifyFull(lot: ParkingLot)
    fun notifyFree(lot: ParkingLot)
}