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
        if (availableLots.size == 0) {
            throw LotFullException("lot is full")
        }
        availableLots.first().park(vehicle)
        return
    }

    fun unPark(vehicle: Vehicle) {
        lots.forEach {
            if (it.isParked(vehicle)) {
                it.unPark(vehicle)
                return
            }
        }
        throw NotParkedException("vehicle not parked")
    }

    override fun notifyFull(lot: ParkingLot) {
        availableLots.remove(lot)
    }

    override fun notifyFree(lot: ParkingLot) {
        availableLots.add(lot)
    }

}
