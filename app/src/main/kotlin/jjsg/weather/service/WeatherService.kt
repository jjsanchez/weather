package jjsg.weather.service

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon
import jjsg.weather.data.City
import jjsg.weather.data.Weather

class WeatherService(private val openWeatherMapService: OpenWeatherMapService) {
    private val converter = object : Converter {
        override fun canConvert(cls: Class<*>) = cls == Weather::class.java

        override fun toJson(value: Any): String {
            return with(value as Weather) {
                """{"mainDescription": "${this.mainDescription}", "temperature": "${this.temperature}"}"""
            }
        }

        override fun fromJson(jv: JsonValue): Any? {
            val mainDescription = jv.obj?.obj("weather")?.string("main")
            val temperature = jv.obj?.obj("main")?.string("temp")

            if (mainDescription == null || temperature == null) {
                throw IllegalArgumentException("Invalid Json string for Wather")
            }

            return Weather(mainDescription, temperature)
        }
    }

    fun getCurrentWeather(city: City): Weather? {
        val weatherAsJsonString = openWeatherMapService.fetchWeather(city)

        return Klaxon().converter(converter).parse(weatherAsJsonString)
    }
}
