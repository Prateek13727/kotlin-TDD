package parkinglotv2.domain.strategies

import parkinglotv2.domain.entities.ParkingLot

class MaxCapacityLotStrategy : ParkingStrategy{
    override fun getFreeParkingLot(parkingLots: List<ParkingLot>): ParkingLot? {
        val lots: List<ParkingLot> = parkingLots.sortedWith(compareBy { -it.capacity }).filter { it.freeSpace > 0 }
        return if(lots.isEmpty()) {
            null
        }else {
            lots.first()
        }
    }
}
