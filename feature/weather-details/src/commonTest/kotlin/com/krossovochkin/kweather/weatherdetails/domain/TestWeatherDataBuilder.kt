package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.WeatherDetails

private const val DEFAULT_TEMPERATURE = 20
private const val DEFAULT_PRESSURE = 1010
private const val DEFAULT_HUMIDITY = 54
private const val DEFAULT_WIND_SPEED = 3.26
private const val DEFAULT_WIND_DEGREE = 271
private const val DEFAULT_CONDITION_IMAGE_URL =
    "https://openweathermap.org/img/wn/01d@2x.png"
private const val DEFAULT_CONDITION_DESCRIPTION = "scattered clouds"

class TestWeatherDataBuilder {

    private var temperature: Int = DEFAULT_TEMPERATURE
    private var temperatureFeelsLike: Int = DEFAULT_TEMPERATURE
    private var pressure: Int = DEFAULT_PRESSURE
    private var humidity: Int = DEFAULT_HUMIDITY
    private var windSpeed: Double = DEFAULT_WIND_SPEED
    private var windDegree: Int = DEFAULT_WIND_DEGREE
    private var conditionImageUrl: String = DEFAULT_CONDITION_IMAGE_URL
    private var conditionDescription: String = DEFAULT_CONDITION_DESCRIPTION

    fun setTemperature(temperature: Int) = apply {
        this.temperature = temperature
    }

    fun setTemperatureFeelsLike(temperatureFeelsLike: Int) = apply {
        this.temperatureFeelsLike = temperatureFeelsLike
    }

    fun setPressure(pressure: Int) = apply {
        this.pressure = pressure
    }

    fun setHumidity(humidity: Int) = apply {
        this.humidity = humidity
    }

    fun setWindSpeed(windSpeed: Double) = apply {
        this.windSpeed = windSpeed
    }

    fun setWindDegree(windDegree: Int) = apply {
        this.windDegree = windDegree
    }

    fun setConditionImageUrl(conditionImageUrl: String) = apply {
        this.conditionImageUrl = conditionImageUrl
    }

    fun setConditionDescription(conditionDescription: String) = apply {
        this.conditionDescription = conditionDescription
    }

    fun build(): WeatherDetails.WeatherData {
        return WeatherDetails.WeatherData(
            temperature = temperature,
            temperatureFeelsLike = temperatureFeelsLike,
            pressure = pressure,
            humidity = humidity,
            windSpeed = windSpeed,
            windDegree = windDegree,
            conditionImageUrl = conditionImageUrl,
            conditionDescription = conditionDescription
        )
    }
}
