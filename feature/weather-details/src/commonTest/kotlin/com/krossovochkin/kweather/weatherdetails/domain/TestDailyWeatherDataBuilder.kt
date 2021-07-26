package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.WeatherDetails
import com.krossovochkin.kweather.weatherdetails.domain.TestDefaults.DEFAULT_CONDITION_DESCRIPTION
import com.krossovochkin.kweather.weatherdetails.domain.TestDefaults.DEFAULT_CONDITION_IMAGE_URL
import com.krossovochkin.kweather.weatherdetails.domain.TestDefaults.DEFAULT_HUMIDITY
import com.krossovochkin.kweather.weatherdetails.domain.TestDefaults.DEFAULT_LOCAL_DATE_TIME
import com.krossovochkin.kweather.weatherdetails.domain.TestDefaults.DEFAULT_PRESSURE
import com.krossovochkin.kweather.weatherdetails.domain.TestDefaults.DEFAULT_WIND_DEGREE
import com.krossovochkin.kweather.weatherdetails.domain.TestDefaults.DEFAULT_WIND_SPEED
import kotlinx.datetime.LocalDateTime

class TestDailyWeatherDataBuilder {

    private var localDateTime: LocalDateTime = DEFAULT_LOCAL_DATE_TIME
    private var temperature = TestTemperatureDataBuilder().build()
    private var temperatureFeelsLike = TestTemperatureDataBuilder().build()
    private var pressure: Int = DEFAULT_PRESSURE
    private var humidity: Int = DEFAULT_HUMIDITY
    private var windSpeed: Double = DEFAULT_WIND_SPEED
    private var windDegree: Int = DEFAULT_WIND_DEGREE
    private var conditionImageUrl: String = DEFAULT_CONDITION_IMAGE_URL
    private var conditionDescription: String = DEFAULT_CONDITION_DESCRIPTION

    fun build(): WeatherDetails.DailyWeatherData {
        return WeatherDetails.DailyWeatherData(
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
