package fizzbuzz

fun fizzBuzzStage2(i: Int): String {
    return getTextForStage(i, ::multipleOrContainsToText)
}

fun fizzBuzzStage1(i: Int): String {
    return getTextForStage(i, ::multipleToText)
}

private fun getTextForStage(value: Int, f: (Int, Int, String) -> String) =
        buildString {
            append(f(value, 3, "Fizz"))
            append(f(value, 5, "Buzz"))
        }.ifEmpty { "$value" }


private fun multipleToText(value: Int, divider: Int, text: String): String =
        if (value.isMultipleOf(divider)) text
        else ""

private fun multipleOrContainsToText(value: Int, divider: Int, text: String): String =
        if (value.isMultipleOf(divider) || value.containsDigit(divider)) text
        else ""

private fun Int.isMultipleOf(vararg of: Int): Boolean = of.none { this % it != 0 }

private fun Int.containsDigit(d: Int): Boolean {
    return this.toString().contains("$d")
}

fun main() {
    println("STAGE 1")
    (1..100).forEach {
        println(fizzBuzzStage1(it))
    }

    println("==================")

    println("STAGE 2")
    (1..100).forEach {
        println(fizzBuzzStage2(it))
    }

}
