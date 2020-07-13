import NumberToLCD.StringForLine.*

class NumberToLCD {
    private val linesOfThree = mapOf(
            1 to listOf(BLANK, RIGHT, RIGHT),
            2 to listOf(MIDDLE_BOTTOM, MIDDLE_RIGHT, LEFT_MIDDLE),
            3 to listOf(MIDDLE_BOTTOM, MIDDLE_RIGHT, MIDDLE_RIGHT),
            4 to listOf(BLANK, LEFT_MIDDLE_RIGHT, RIGHT),
            5 to listOf(MIDDLE_BOTTOM, LEFT_MIDDLE, MIDDLE_RIGHT),
            6 to listOf(MIDDLE_BOTTOM, LEFT_MIDDLE, LEFT_MIDDLE_RIGHT),
            7 to listOf(MIDDLE_BOTTOM, RIGHT, RIGHT),
            8 to listOf(MIDDLE_BOTTOM, LEFT_MIDDLE_RIGHT, LEFT_MIDDLE_RIGHT),
            9 to listOf(MIDDLE_BOTTOM, LEFT_MIDDLE_RIGHT, MIDDLE_RIGHT)
    )

    private val linesOfFive = mapOf(
            1 to listOf(BLANK, RIGHT, BLANK, RIGHT, BLANK),
            2 to listOf(MIDDLE, RIGHT, MIDDLE, LEFT, MIDDLE),
            3 to listOf(MIDDLE, RIGHT, MIDDLE, RIGHT, MIDDLE),
            4 to listOf(BLANK, LEFT_RIGHT, MIDDLE, RIGHT, BLANK),
            5 to listOf(MIDDLE, LEFT, MIDDLE, RIGHT, MIDDLE),
            6 to listOf(MIDDLE, LEFT, MIDDLE, LEFT_RIGHT, MIDDLE),
            7 to listOf(MIDDLE, RIGHT, BLANK, RIGHT, BLANK),
            8 to listOf(MIDDLE, LEFT_RIGHT, MIDDLE, LEFT_RIGHT, MIDDLE),
            9 to listOf(MIDDLE, LEFT_RIGHT, MIDDLE, RIGHT, MIDDLE)
    )

    // width = 0 and height = 0 use linesOfThree representation otherwise use linesOfFive
    fun nbToLCD(nb: Int, width: Int = 0, height: Int = 0): String {
        val digitsRepresentation = numberToListOfDigits(nb).mapNotNull {
            if (width == 0) linesOfThree[it] else linesOfFive[it]
        }
        val mapLines = mutableMapOf<Int, String>()
        digitsRepresentation.forEach {
            it.forEachIndexed { index, value ->
                mapLines[index] = (if (mapLines.containsKey(index)) {
                    mapLines[index] + " "
                } else "") + value.toString(width)
            }
        }
        return if (height == 0) mapToConstantHeight(mapLines) else mapToVariableHeight(mapLines, height)
    }

    private fun mapToConstantHeight(mapLines: Map<Int, String>): String =
            mapLines.values.joinToString("\n")

    private fun mapToVariableHeight(mapLines: Map<Int, String>, height: Int): String {
        return buildString {
            append(mapLines[0])
            append("\n")
            repeat(height) {
                append(mapLines[1])
                append("\n")
            }
            append(mapLines[2])
            append("\n")
            repeat(height) {
                append(mapLines[3])
                append("\n")
            }
            append(mapLines[4])
        }
    }

    private fun numberToListOfDigits(nb: Int): List<Int> {
        var n = nb
        val arr = mutableListOf<Int>()
        while (n > 0) {
            val r = n % 10
            arr.add(r)
            n /= 10
        }
        arr.reverse()
        return arr
    }

    enum class StringForLine(private val value: String) {
        BLANK("   "),
        LEFT("|  "),
        RIGHT("  |"),
        MIDDLE(" - "),
        MIDDLE_BOTTOM(" _ "),
        LEFT_RIGHT("| |"),
        LEFT_MIDDLE("|_ "),
        MIDDLE_RIGHT(" _|"),
        LEFT_MIDDLE_RIGHT("|_|");

        override fun toString(): String {
            return this.value
        }

        fun toString(repeat: Int = 1): String {
            val rep = if (repeat < 1) 1 else repeat
            val first = value[0]
            val second = value[1].toString()
            val third = value[2]
            return "$first${second.repeat(rep)}$third"
        }
    }
}
