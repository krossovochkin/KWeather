package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.domain.WeatherDetails

internal interface WeatherDetailsRepository {

    suspend fun getWeatherDetails(city: City): WeatherDetails
}
