package diamond

class Diamond {
    fun print(char: Char): String {
        if (char == 'A') return "A"

        var s = ""

        ('A'..char).forEachIndexed { i, c ->
            // handle indentation
            val indent = char - c
            s += " ".repeat(indent)
            s += c

            // add middle spaces and repeat the letter
            if (c != 'A') {
                s+= " ".repeat(c - 'A' + i - 1)
                s+= c
            }

            s += "\n"
        }

        // handle symmetry of the bottom
        val bottom = s.split("\n").dropLast(2).reversed().joinToString("\n")
        s += bottom

        println(s)
        return s
    }
}
