package parkinglot

enum class ParkingRule {
    FIRST_AVAILABLE_LOT {
        override fun park(parkingLots: MutableList<ParkingLot>): ParkingLot {
            return parkingLots[0]
        }
    },
    MOST_CAPACITY {
        override fun park(parkingLots: MutableList<ParkingLot>): ParkingLot {
            var champion = parkingLots[0]
            parkingLots
                .asSequence()
                .filter { it.hasMoreCapacity(champion) }
                .forEach { champion = it }
            return champion
        }
    },
    MOST_FREE_SPACE {
        override fun park(parkingLots: MutableList<ParkingLot>): ParkingLot {
            var champion = parkingLots[0]
            parkingLots
                .asSequence()
                .filter { it.hasLeastNumberOfVehicles(champion) }
                .forEach { champion = it }
            return champion
        }
    };

    abstract fun park(parkingLots: MutableList<ParkingLot>): ParkingLot

}
