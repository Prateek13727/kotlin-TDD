package parkinglot

class Attendant(val lots: MutableSet<ParkingLot>) : Notifiable {
    private var availableLots: MutableList<ParkingLot> = mutableListOf()

    init {
        availableLots.addAll(lots)
        lots.forEach {
            it.addNotifiable(this)
        }
    }

    fun park(vehicle: Vehicle) {
        availableLots.first().park(vehicle)
    }

    override fun notifyFull(lot: ParkingLot) {
        availableLots.remove(lot)
    }

    override fun notifyFree(lot: ParkingLot) {
        availableLots.add(lot)
    }
}
