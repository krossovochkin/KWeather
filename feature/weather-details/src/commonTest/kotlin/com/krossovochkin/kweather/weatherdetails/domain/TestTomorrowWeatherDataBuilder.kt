package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.WeatherDetails

class TestTomorrowWeatherDataBuilder {

    private var weatherData = TestDailyWeatherDataBuilder().build()
    private var hourlyWeatherData = listOf(
        TestHourlyWeatherDataBuilder().build()
    )

    fun build(): WeatherDetails.TomorrowWeatherData {
        return WeatherDetails.TomorrowWeatherData(
            weatherData = weatherData,
            hourlyWeatherData = hourlyWeatherData
        )
    }
}
