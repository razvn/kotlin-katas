package lift

interface LiftPrinter {
    fun printLiftForFloor(lift: Lift, floor: Int): String
}

class SimpleLiftPrinter : LiftPrinter {
    override fun printLiftForFloor(lift: Lift, floor: Int): String {
        val padding: String by lazy { LiftSystemPrinter.whitespace(lift.id.length) }

        return when {
            lift.floor == floor -> printLift(lift, floor)
            lift.hasRequestsForFloor(floor) -> "*$padding"
            else -> "  $padding"
        }
    }

    private fun printLift(lift: Lift, floor: Int): String =
            if (lift.hasRequestsForFloor(floor)) "*${lift.id}" else "${lift.id} "
}
