package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.City

internal interface WeatherDetailsRepository {

    suspend fun getWeatherDetails(city: City): WeatherDetails
}
