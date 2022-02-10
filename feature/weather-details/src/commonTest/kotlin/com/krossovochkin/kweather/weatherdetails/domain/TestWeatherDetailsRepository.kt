package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.domain.WeatherDetails
import kotlinx.coroutines.delay

class TestWeatherDetailsRepository(
    private val delayMillis: Long? = null
) : WeatherDetailsRepository {

    private var weatherDetailsMap = mutableMapOf<City, WeatherDetails>()

    override suspend fun getWeatherDetails(city: City): WeatherDetails {
        delayMillis?.let { delay(it) }
        return weatherDetailsMap[city]!!
    }

    fun put(city: City, weatherDetails: WeatherDetails) {
        weatherDetailsMap[city] = weatherDetails
    }
}
