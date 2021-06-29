package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.CityId

interface WeatherDetailsRepository {

    suspend fun getWeatherDetails(cityId: CityId): WeatherDetails
}
