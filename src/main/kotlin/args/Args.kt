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
    var params: Map<String, Any> = emptyMap()

    init {
        paramTypes = parseSchema(schema)
    }

    fun parse(args: String) {
        val argsList = args.split(" ")
        val paramsBuild = mutableMapOf<String, Any>()
        var currentArg = 0
        while (currentArg < argsList.size) {
            val arg = argsList[currentArg]
            if (!arg.startsWith("-")) {
                throw IllegalArgumentException("Argument `$arg` should start with `-` and be one of `${paramTypes.keys.joinToString(",")}`")
            }
            val argName = arg.drop(1)
            if (!paramTypes.containsKey(arg.drop(1))) {
                throw IllegalArgumentException("Argument `$argName` should be one of `${paramTypes.keys.joinToString(",")}`")
            }

            when {
                paramTypes[argName] == DataType.BOOLEAN -> paramsBuild[argName] = true
                paramTypes[argName] == DataType.INT -> {
                    if (currentArg + 1 > (argsList.size - 1) ) throw IllegalArgumentException("Parameter `$argName` requires an argument")

                }

            }

            currentArg++
        }
        params = paramsBuild
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
