package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.core.domain.TestCityBuilder
import com.krossovochkin.kweather.domain.City

private const val DEFAULT_TEMPERATURE = 28
private const val DEFAULT_WEATHER_CONDITIONS_IMAGE_URL =
    "https://openweathermap.org/img/wn/01d@2x.png"

class TestWeatherDetailsBuilder {

    private var city: City = TestCityBuilder().build()
    private var temperature: Int = DEFAULT_TEMPERATURE
    private var weatherConditionsImageUrl: String = DEFAULT_WEATHER_CONDITIONS_IMAGE_URL

    fun setCity(city: City) = apply {
        this.city = city
    }

    fun setTemperature(temperature: Int) = apply {
        this.temperature = temperature
    }

    fun setWeatherConditionsImageUrl(imageUrl: String) = apply {
        this.weatherConditionsImageUrl = imageUrl
    }

    fun build(): WeatherDetails {
        return WeatherDetails(
            city = city,
            temperature = temperature,
            weatherConditionsImageUrl = weatherConditionsImageUrl
        )
    }
}
