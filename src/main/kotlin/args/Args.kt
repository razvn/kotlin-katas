fun main() {
    //
}

/*
   schema format: <param>:<type>;\
     - param = letter or string of the parameter
     - type = i = int, b = boolean, d = double, s = string,
        li = list of integers, lb = list of booleans, ls = list of strings, ld = list of doubles

     - default list separator is `,` but can be changed in the constructor
    ex: l:b;p:i;s:ls
 */

class Args(schema: String, private val listSeparator: Char = ',') {
    val paramTypes: Map<String, DataType>
    private val paramsDefaultValues: Map<String, Any>
    init {
        paramTypes = parseSchema(schema)
        paramsDefaultValues = paramTypes.map { it.key to defaultValueFor(it.value) }.toMap()
    }

    private fun defaultValueFor(type: DataType): Any = when (type) {
        DataType.BOOLEAN -> false
        DataType.INT -> 0
        DataType.STRING -> ""
        DataType.DOUBLE -> 0.0
        DataType.LISTSTRING -> emptyList<String>()
        DataType.LISTINT -> emptyList<Int>()
        DataType.LISTDOUBLE -> emptyList<Double>()
    }

    fun parse(args: String = ""): Map<String, Any> {
        val argsList = args.split(" ")
        val params = paramsDefaultValues.toMutableMap()
        if (args.isNotBlank()) {
            var currentArg = 0
            while (currentArg < argsList.size) {
                val arg = argsList[currentArg]
                if (!arg.startsWith("-")) {
                    throw IllegalArgumentException(
                        "Argument `$arg` should start with `-` and be one of `${paramTypes.keys.joinToString(
                            ","
                        )}`"
                    )
                }
                val argName = arg.drop(1)
                if (!paramTypes.containsKey(arg.drop(1))) {
                    throw IllegalArgumentException("Argument `$argName` should be one of `${paramTypes.keys.joinToString(",")}`")
                }

                val argumentsMaxIndex = argsList.size - 1
                params[argName] = when (paramTypes[argName]) {
                    DataType.BOOLEAN -> true
                    DataType.INT -> nextArgValue(++currentArg, argumentsMaxIndex, argName, argsList, String::toIntOrNull)
                    DataType.STRING -> nextArgValue(++currentArg, argumentsMaxIndex, argName, argsList, String::toString)
                    DataType.DOUBLE -> nextArgValue(++currentArg, argumentsMaxIndex, argName, argsList, String::toDoubleOrNull)
                    DataType.LISTSTRING -> nextArgValue(++currentArg, argumentsMaxIndex, argName, argsList, ::listOfString)
                    DataType.LISTINT -> nextArgValue(++currentArg, argumentsMaxIndex, argName, argsList, ::listOfInt)
                    DataType.LISTDOUBLE -> nextArgValue(++currentArg, argumentsMaxIndex, argName, argsList, ::listOfDouble)
                    else -> throw IllegalAccessException("Parameter `$argName` null")
                }
                currentArg++
            }
        }
        return params
    }

    private fun listOfString(s: String): List<String> {
        return s.split(listSeparator)
    }

    private fun listOfInt(s: String): List<Int> {
        return s.split(listSeparator).map { it.toIntOrNull() ?: throw IllegalAccessException("Parameter `$it` of the list `$s` should be an int") }
    }

    private fun listOfDouble(s: String): List<Double> {
        return s.split(listSeparator).map { it.toDoubleOrNull() ?: throw IllegalAccessException("Parameter `$it` of the list `$s` should be a double") }
    }

    private fun <T> nextArgValue(argIdx: Int, maxIdx: Int, argName: String, args: List<String>, f: ((String) -> T?)): T {
        val paramValue = if (argIdx > maxIdx) throw IllegalArgumentException("Parameter `$argName` requires a value")
        else args[argIdx]
        return f.invoke(paramValue) ?: throw IllegalAccessException("Parameter `$argName` value `$paramValue` can't be converted")
    }

    private fun parseSchema(schema: String): Map<String, DataType> {
        return schema.split(";")
            .map { p ->
                val param = p.split(":")
                if (param.size != 2) throw IllegalArgumentException("Incorrect parameter `$p` formant. Should be `<param>:<type>")
                else param.first() to parseType(param.last())
            }.toMap()

    }

    private fun parseType(type: String): DataType = when (type) {
        "b" -> DataType.BOOLEAN
        "i" -> DataType.INT
        "d" -> DataType.DOUBLE
        "s" -> DataType.STRING
        "ls" -> DataType.LISTSTRING
        "li" -> DataType.LISTINT
        "ld" -> DataType.LISTDOUBLE
        else -> throw IllegalArgumentException("Type $type is unknown. Supported types are: b,i,d,s,li,ld,ls (boolean, integer, double, string, list of integers, list of doubles, list of strings)")
    }


    enum class DataType {
        BOOLEAN, INT, DOUBLE, STRING, LISTSTRING, LISTINT, LISTDOUBLE
    }
}
