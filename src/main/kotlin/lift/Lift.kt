package lift

data class Call(val floor: Int, val direction: Direction)

enum class Direction {
    UP, DOWN
}

data class Lift(val id: String, val floor: Int, val requests: List<Int> = listOf(), val doorsOpen: Boolean = false) {
    fun hasRequestsForFloor(floor: Int) = requests.contains(floor)
}

class LiftSystem(val floors: List<Int>,  val lifts: List<Lift>, val calls: List<Call>) {
    fun getFloorsInDescendingOrder() = floors.reversed()
    fun getCallsForFlooor(floor: Int): List<Call> = calls.filter { it.floor == floor }

    fun tick() {
        // TODO implement this function
    }
}

