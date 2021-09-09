package jjsg.weather.service

import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import jjsg.weather.data.City
import jjsg.weather.data.Weather
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

class WeatherServiceTest {
    private val city = City("Madrid")
    private val openWeatherMapService: OpenWeatherMapService = mockk()
    private val weatherService: WeatherService = WeatherService(openWeatherMapService)

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `correctly returns the weather`() {
        every { openWeatherMapService.fetchWeather(city) } returns """
            {
                "weather": {
                    "main": "Clear"
                },
                "main": {
                    "temp": "28"
                }
            }
        """

        val currentWeather = weatherService.getCurrentWeather(city)

        currentWeather shouldBe Weather(mainDescription = "Clear", temperature = "28")
    }

    @Test
    fun `throws exception if  a Weather object can not be parsed from JSON`() {
        every { openWeatherMapService.fetchWeather(city) } returns """
            {
                "weather": {
                },
                "main": {
                }
            }
        """

        assertThrows(IllegalArgumentException::class.java) { weatherService.getCurrentWeather(city) }
    }
}
