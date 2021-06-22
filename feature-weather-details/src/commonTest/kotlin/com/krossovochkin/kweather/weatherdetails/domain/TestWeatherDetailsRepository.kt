package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.core.domain.CityId

class TestWeatherDetailsRepository : WeatherDetailsRepository {

    private var weatherDetailsMap = mutableMapOf<CityId, WeatherDetails>()

    override suspend fun getWeatherDetails(cityId: CityId): WeatherDetails {
        return weatherDetailsMap[cityId]!!
    }

    fun put(cityId: CityId, weatherDetails: WeatherDetails) {
        weatherDetailsMap[cityId] = weatherDetails
    }
}
