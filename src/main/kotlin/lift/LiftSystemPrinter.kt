package lift

import java.lang.IllegalArgumentException
import kotlin.math.max

class LiftSystemPrinter {
    fun print(liftSystem: LiftSystem): String = this.print(liftSystem, LiftAndDoorPrinter())

    fun printWithoutDoors(liftSystem: LiftSystem): String = print(liftSystem, SimpleLiftPrinter())

    fun print(liftSystem: LiftSystem, liftPrinter: LiftPrinter): String = buildString {
        val floorLength = calculateFloorLength(liftSystem.getFloorsInDescendingOrder())

        liftSystem.getFloorsInDescendingOrder().forEach { floor ->
            // if the floor number doesn't use all the characters, pad with whitespace
            val floorPadding = whitespace(floorLength - "$floor".length)
            // append(floorPadding)
            append("%${floorPadding}d".format(floor))

            val calls = liftSystem.getCallsForFlooor(floor).joinToString("") { printCallDirection(it) }

            // if there are less thant 2 calls on a floor we add padding to keep everything alignes
            append(" ")
            append("%-2s".format(calls))
            append(" ")
            val lifts = liftSystem.lifts.joinToString(" ") { liftPrinter.printLiftForFloor(it, floor) }
            append(lifts)

            // put the floor number at both ends of the line to make it more readable when there are lots of lifts,
            // and to prevent the IDE from doing rstrip on save and messing up the approved files.
            append("%${floorPadding}d".format(floor))

            append('\n')
        }
    }

    private fun printCallDirection(call: Call) = when(call.direction) {
        Direction.DOWN -> "v"
        Direction.UP -> "^"
    }

    @Throws(IllegalArgumentException::class)
    private fun calculateFloorLength(floors: List<Int>): Int {
        if (floors.isEmpty()) {
            throw IllegalArgumentException("Must have at least one floor")
        }
        val highestFloor = floors.max()
        val lowestFloor = floors.min()
        val highestFloorNameLength = "$highestFloor".length
        val lowestFloorNameLength = "$lowestFloor".length
        return max(highestFloorNameLength, lowestFloorNameLength)
    }

    companion object {
        fun whitespace(length: Int) = " ".repeat(max(0, length))
    }
}
// 3     [A]               3
// 2          [B]  ]C[     2
