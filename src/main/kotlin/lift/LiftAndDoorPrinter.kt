package lift

class LiftAndDoorPrinter : LiftPrinter {
    override fun printLiftForFloor(lift: Lift, floor: Int): String =
            when {
                lift.floor == floor -> printLift(lift, floor)
                lift.hasRequestsForFloor(floor) -> "  *${LiftSystemPrinter.whitespace(lift.id.length)}"
                else -> "  ${LiftSystemPrinter.whitespace(lift.id.length)} "
            }

    private fun printLift(lift: Lift, floor: Int): String =
            when {
                lift.doorsOpen && lift.hasRequestsForFloor(floor) -> "]*${lift.id}["
                lift.doorsOpen -> " ]${lift.id}["
                lift.hasRequestsForFloor(floor) -> "[*${lift.id}]"
                else -> " [${lift.id}]"
            }


}
