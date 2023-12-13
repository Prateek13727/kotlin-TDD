package parkinglot

class ParkingLot(private val capacity: Int) {
    private var allotedSlots: Int = 0

    fun park() {
        if (isFull()) {
            throw LotFullException("lot is full")
        }
        allotedSlots += 1
        return
    }

    private fun isFull() = allotedSlots == capacity

}
