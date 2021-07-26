package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.WeatherDetails
import com.krossovochkin.kweather.weatherdetails.domain.TestDefaults.DEFAULT_CONDITION_DESCRIPTION
import com.krossovochkin.kweather.weatherdetails.domain.TestDefaults.DEFAULT_CONDITION_IMAGE_URL
import com.krossovochkin.kweather.weatherdetails.domain.TestDefaults.DEFAULT_HUMIDITY
import com.krossovochkin.kweather.weatherdetails.domain.TestDefaults.DEFAULT_LOCAL_DATE_TIME
import com.krossovochkin.kweather.weatherdetails.domain.TestDefaults.DEFAULT_PRESSURE
import com.krossovochkin.kweather.weatherdetails.domain.TestDefaults.DEFAULT_TEMPERATURE
import com.krossovochkin.kweather.weatherdetails.domain.TestDefaults.DEFAULT_WIND_DEGREE
import com.krossovochkin.kweather.weatherdetails.domain.TestDefaults.DEFAULT_WIND_SPEED
import kotlinx.datetime.LocalDateTime

class TestHourlyWeatherDataBuilder {

    private var localDateTime: LocalDateTime = DEFAULT_LOCAL_DATE_TIME
    private var temperature: Int = DEFAULT_TEMPERATURE
    private var temperatureFeelsLike: Int = DEFAULT_TEMPERATURE
    private var pressure: Int = DEFAULT_PRESSURE
    private var humidity: Int = DEFAULT_HUMIDITY
    private var windSpeed: Double = DEFAULT_WIND_SPEED
    private var windDegree: Int = DEFAULT_WIND_DEGREE
    private var conditionImageUrl: String = DEFAULT_CONDITION_IMAGE_URL
    private var conditionDescription: String = DEFAULT_CONDITION_DESCRIPTION

    fun setLocalDateTime(localDateTime: LocalDateTime) = apply {
        this.localDateTime = localDateTime
    }

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

    fun build(): WeatherDetails.HourlyWeatherData {
        return WeatherDetails.HourlyWeatherData(
            localDateTime = localDateTime,
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
