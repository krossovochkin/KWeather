package com.krossovochkin.kweather.weatherdetails.data

import com.krossovochkin.kweather.core.domain.CityId


interface WeatherDetailsApi {

    suspend fun getWeatherDetails(cityId: CityId): WeatherDetailsDto
}
