package parkinglotv2.domain.strategies

import parkinglotv2.domain.entities.ParkingLot

class FirstAvailableLotStrategy : ParkingStrategy{
    override fun getFreeParkingLot(parkingLots: List<ParkingLot>): ParkingLot? {
        val lots =  parkingLots.filter { it.freeSpace > 0 }
        return if(lots.isEmpty()) {
            null
        }else {
            lots.first()
        }
    }
}
