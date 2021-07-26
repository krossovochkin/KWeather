package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.WeatherDetails

class TestTodayWeatherDataBuilder {

    private var currentWeatherData = TestCurrentWeatherDataBuilder().build()
    private var hourlyWeatherData = listOf(
        TestHourlyWeatherDataBuilder().build()
    )

    fun build(): WeatherDetails.TodayWeatherData {
        return WeatherDetails.TodayWeatherData(
            currentWeatherData = currentWeatherData,
            hourlyWeatherData = hourlyWeatherData
        )
    }
}
