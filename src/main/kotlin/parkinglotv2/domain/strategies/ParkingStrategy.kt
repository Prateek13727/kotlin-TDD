package parkinglotv2.domain.strategies

import parkinglotv2.domain.entities.ParkingLot

interface ParkingStrategy {
    fun getFreeParkingLot(parkingLots: List<ParkingLot>) : ParkingLot?
}
