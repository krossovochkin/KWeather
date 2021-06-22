package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.core.domain.City

class TestWeatherDetailsRepository : WeatherDetailsRepository {

    private var weatherDetailsMap = mutableMapOf<City, WeatherDetails>()

    override suspend fun getWeatherDetails(city: City): WeatherDetails {
        return weatherDetailsMap[city]!!
    }

    fun put(city: City, weatherDetails: WeatherDetails) {
        weatherDetailsMap[city] = weatherDetails
    }
}
