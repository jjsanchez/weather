package jjsg.weather

class ArgsParser {
    fun parseCity(args: Array<String>): City? {
        val cityArgument = findArgument(args, "city")

        val city = cityArgument?.get(1)

        return if (city != null && city.isNotEmpty()) {
            return City(city)
        } else null
    }

    private fun findArgument(args: Array<String>, key: String): List<String>? {
        return args.map { it.split("=") }.find { it[0] == key }
    }
}
