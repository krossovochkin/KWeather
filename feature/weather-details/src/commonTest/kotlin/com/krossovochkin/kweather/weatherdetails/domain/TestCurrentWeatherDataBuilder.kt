package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.WeatherDetails

class TestCurrentWeatherDataBuilder {

    private var temperature: Int = TestDefaults.DEFAULT_TEMPERATURE
    private var temperatureFeelsLike: Int = TestDefaults.DEFAULT_TEMPERATURE
    private var pressure: Int = TestDefaults.DEFAULT_PRESSURE
    private var humidity: Int = TestDefaults.DEFAULT_HUMIDITY
    private var windSpeed: Double = TestDefaults.DEFAULT_WIND_SPEED
    private var windDegree: Int = TestDefaults.DEFAULT_WIND_DEGREE
    private var conditionImageUrl: String = TestDefaults.DEFAULT_CONDITION_IMAGE_URL
    private var conditionDescription: String = TestDefaults.DEFAULT_CONDITION_DESCRIPTION

    fun build(): WeatherDetails.TodayWeatherData.CurrentWeatherData {
        return WeatherDetails.TodayWeatherData.CurrentWeatherData(
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
