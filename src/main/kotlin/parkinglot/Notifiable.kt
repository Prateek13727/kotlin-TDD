package parkinglot

interface Notifiable {
    fun notifyFull(lot: ParkingLot)
    fun notifyFree(lot: ParkingLot)


}
