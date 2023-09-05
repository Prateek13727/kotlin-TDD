package parkinglot

import parkinglot.parkingrule.FirstAvailableRule

class Attendant(
    private val lots: MutableSet<ParkingLot>,
    private val parkingRule: IParkingRule = FirstAvailableRule()
) : INotifiable {
    private var availableLots: MutableList<ParkingLot> = mutableListOf()

    init {
        availableLots.addAll(lots)
        lots.forEach {
            it.addNotifiable(this)
        }
    }

    fun park(vehicle: IParkable) {
        if (availableLots.size == 0) {
            throw LotFullException()
        }
        val champion: ParkingLot = parkingRule.find(availableLots)
        champion.park(vehicle)
    }

    override fun notifyFull(lot: ParkingLot) {
        availableLots.remove(lot)
    }

    override fun notifyFree(lot: ParkingLot) {
        availableLots.add(lot)
    }
}
