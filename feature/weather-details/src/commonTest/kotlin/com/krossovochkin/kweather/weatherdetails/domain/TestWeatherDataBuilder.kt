package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.WeatherDetails

private const val DEFAULT_TEMPERATURE = 28
private const val DEFAULT_WEATHER_CONDITIONS_IMAGE_URL =
    "https://openweathermap.org/img/wn/01d@2x.png"

class TestWeatherDataBuilder {

    private var temperature: Int = DEFAULT_TEMPERATURE
    private var conditionImageUrl: String = DEFAULT_WEATHER_CONDITIONS_IMAGE_URL

    fun setTemperature(temperature: Int) = apply {
        this.temperature = temperature
    }

    fun setConditionImageUrl(conditionImageUrl: String) = apply {
        this.conditionImageUrl = conditionImageUrl
    }

    fun build(): WeatherDetails.WeatherData {
        return WeatherDetails.WeatherData(
            temperature = temperature,
            conditionImageUrl = conditionImageUrl
        )
    }
}
