package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.CityId

internal interface WeatherDetailsRepository {

    suspend fun getWeatherDetails(cityId: CityId): WeatherDetails
}
