package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.core.domain.City

interface WeatherDetailsRepository {

    suspend fun getWeatherDetails(city: City): WeatherDetails
}
