package parkinglot

class Attendant(
    private val lots: MutableSet<ParkingLot>,
    private val parkingRule: ParkingRule = ParkingRule.FIRST_AVAILABLE_LOT
) : INotifiable {
    private var availableLots: MutableList<ParkingLot> = mutableListOf()

    init {
        availableLots.addAll(lots)
        lots.forEach {
            it.addNotifiable(this)
        }
    }

    fun park(vehicle: IParkable) {
        if (parkingRule == ParkingRule.MOST_CAPACITY) {
            var champion = availableLots[0]
            availableLots
                .asSequence()
                .filter { it.hasMoreCapacity(champion) }
                .forEach { champion = it }
            champion.park(vehicle)
            return
        }
        val champion = availableLots[0]
        champion.park(vehicle)
    }

    override fun notifyFull(lot: ParkingLot) {
        availableLots.remove(lot)
    }

    override fun notifyFree(lot: ParkingLot) {
        availableLots.add(lot)
    }
}
