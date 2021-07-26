package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.WeatherDetails
import com.krossovochkin.kweather.weatherdetails.domain.TestDefaults.DEFAULT_TEMPERATURE

class TestTemperatureDataBuilder {

    private var temperatureDay = DEFAULT_TEMPERATURE

    fun build(): WeatherDetails.DailyWeatherData.TemperatureData {
        return WeatherDetails.DailyWeatherData.TemperatureData(
            temperatureDay = temperatureDay
        )
    }
}
