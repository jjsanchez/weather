package jjsg.weather

import io.kotest.matchers.shouldBe
import jjsg.weather.data.City
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ArgsParserTest {
    private val parser: ArgsParser = ArgsParser()

    @Nested
    inner class CityIsPassed {
        @Test
        fun `correctly parses the given city when there's only one argument`() {
            val args = arrayOf("city=Madrid")

            val city = parser.parseCity(args)

            city shouldBe City("Madrid")
        }

        @Test
        fun `correctly parses the given city when there are multiple arguments`() {
            val args = arrayOf("arg1=value1", "city=Madrid")

            val city = parser.parseCity(args)

            city shouldBe City("Madrid")
        }

        @Test
        fun `returns the first city if multiple are given`() {
            val args = arrayOf("city=Madrid", "city=Barcelona")

            val city = parser.parseCity(args)

            city shouldBe City("Madrid")
        }
    }

    @Nested
    inner class CityIsMissing {
        @Test
        fun `returns null if there's no city argument`() {
            val args = arrayOf("something=Madrid", "anotherThing=Barcelona")

            val city = parser.parseCity(args)

            city shouldBe null
        }

        @Test
        fun `returns null if there's a city argument that's empty`() {
            val args = arrayOf("city=")

            val city = parser.parseCity(args)

            city shouldBe null
        }
    }
}
