package parkinglot

class Attendant(private val lots: MutableSet<ParkingLot>) : INotifiable {
    private var availableLots: MutableList<ParkingLot> = mutableListOf()

    init {
        availableLots.addAll(lots)
        lots.forEach {
            it.addNotifiable(this)
        }
    }

    fun park(vehicle: IParkable) {
        availableLots.forEach {
            it.park(vehicle)
            return
        }
        throw LotFullException()
    }

    override fun notifyFull(lot: ParkingLot) {
        availableLots.remove(lot)
    }

    override fun notifyFree(lot: ParkingLot) {
        availableLots.add(lot)
    }
}
