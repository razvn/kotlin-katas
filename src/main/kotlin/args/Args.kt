fun main() {
    //
}

/*
   schema format: <param>:<type><required>;
     - param = letter or string of the parameter
     - type = i = int, b = boolean, d = double, s = string

    ex: l:b;p:i
 */

class Args(schema: String) {
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
                    else -> throw IllegalAccessException("Parameter `$argName` type unknown")
                }
                currentArg++
            }
        }
        return params
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
        else -> throw IllegalArgumentException("Type $type is unknown. Supported types are: b,i,d,s (boolean, integer, double, string)")
    }


    enum class DataType(val value: Any) {
        BOOLEAN(false), INT(0), DOUBLE(0.0), STRING("")
    }
}
