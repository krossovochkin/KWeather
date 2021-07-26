package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.WeatherDetails

class TestOneDayWeatherDataBuilder {

    private var weatherData = TestDailyWeatherDataBuilder().build()
    private var hourlyWeatherData = listOf(
        TestHourlyWeatherDataBuilder().build()
    )

    fun build(): WeatherDetails.OneDayWeatherData {
        return WeatherDetails.OneDayWeatherData(
            weatherData = weatherData,
            hourlyWeatherData = hourlyWeatherData
        )
    }
}
