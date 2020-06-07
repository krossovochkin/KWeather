package com.krossovochkin.kweather.shared.feature.weatherdetails.domain

import com.krossovochkin.kweather.shared.feature.citylist.domain.City

interface WeatherDetailsRepository {

    suspend fun getWeatherDetails(city: City): WeatherDetails
}
